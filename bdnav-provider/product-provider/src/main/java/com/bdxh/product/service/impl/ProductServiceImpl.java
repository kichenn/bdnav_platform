package com.bdxh.product.service.impl;

import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.product.dto.*;
import com.bdxh.product.entity.Product;
import com.bdxh.product.entity.ProductImage;
import com.bdxh.product.enums.ProductImgTypeEnum;
import com.bdxh.product.enums.ProductSellStatusEnum;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.persistence.ProductImageMapper;
import com.bdxh.product.persistence.ProductMapper;
import com.bdxh.product.service.ProductService;
import com.bdxh.product.vo.ProductDetailsVo;
import com.bdxh.product.vo.ProductListVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.xiaoleilu.hutool.collection.CollUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.List;

/**
 * @Description: 商品service实现
 * @Author: Kang
 * @Date: 2019/6/14 14:50
 */
@Service
public class ProductServiceImpl extends BaseService<Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addProduct(ProductAddDto productDto) {
        Product product = BeanMapUtils.map(productDto, Product.class);
        Integer count = productMapper.findProductByName(product.getProductName());
        Preconditions.checkArgument(count == 0, "已有重复的商品名称");
        //如果是套餐则设置单品信息
        if (productDto.getProductType().equals(ProductTypeEnum.GROUP.getCode())) {
            product.setProductExtra(productDto.getProductChildIds());
        }
        for (ProductImageAddDto productImageTemp : productDto.getImage()) {
            //如果是商品图片展示 并设置为默认图标展示
            if (productImageTemp.getImgType().equals(ProductImgTypeEnum.IMAGE.getKey())) {
                product.setImgUrl(productImageTemp.getImageUrl());
                break;
            }
        }
        productMapper.insertProduct(product);

        for (int i = 0; i < productDto.getImage().size(); i++) {
            ProductImageAddDto productImageAddDto = productDto.getImage().get(i);
            ProductImage productImage = BeanMapUtils.map(productImageAddDto, ProductImage.class);
            productImage.setProductId(product.getId());
            productImage.setSort(productImageAddDto.getSort());
            productImage.setImgType(productImageAddDto.getImgType());
            productImage.setOperator(productDto.getOperator());
            productImage.setOperatorName(productDto.getOperatorName());
            productImage.setRemark(productDto.getRemark());
            productImageMapper.insert(productImage);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(ProductUpdateDto productUpdateDto) {
        Product product = BeanMapUtils.map(productUpdateDto, Product.class);
        //如果是修改普通的单品
        if (product.getProductType().equals(ProductTypeEnum.SINGLE.getCode())) {
            Product findProduct = new Product();
            findProduct.setProductType(ProductTypeEnum.GROUP.getCode());
            List<Product> productList = productMapper.select(findProduct);
            //循环所有套餐商品
            for (Product fatherProduct : productList) {
                if (StringUtils.isNotEmpty(fatherProduct.getProductExtra())) {
                    String[] chdilIds = fatherProduct.getProductExtra().split(",");
                    for (int i = 0; i < chdilIds.length; i++) {
                        if (product.getId().equals(Long.parseLong(chdilIds[i].trim()))) {
                            //如果商品下架就修改套餐商品的信息商品
                            if (product.getSellStatus().equals(Byte.parseByte("1"))) {
                                List<String> ids = Arrays.asList(chdilIds);
                                List<String> idsArrayList = new ArrayList<>(ids);
                                idsArrayList.remove(i);
                                fatherProduct.setProductExtra(StringUtils.strip(idsArrayList.toString(), "[]").replace(" ", ""));
                                productMapper.updateByPrimaryKeySelective(fatherProduct);
                                continue;
                            }
                        }
                    }
                }
            }
        }
        //修改图片
        List<ProductImageUpdateDto> productImages = productUpdateDto.getImage();
        List<ProductImage> productImageList = BeanMapUtils.mapList(productImages, ProductImage.class);
        productImageMapper.deleteProductImageByProductId(product.getId());
        for (ProductImage productImage : productImageList) {
            productImage.setSort(productImage.getSort());
            productImage.setProductId(product.getId());
            productImage.setImgType(productImage.getImgType());
            productImage.setOperator(product.getOperator());
            productImage.setOperatorName(product.getOperatorName());
            productImage.setRemark(product.getRemark());
            productImageMapper.insert(productImage);
        }
        product.setProductExtra(productUpdateDto.getProductChildIds());
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Long productId) {
        //查询出所有类型为套餐商品的信息
        Product findProduct = new Product();
        findProduct.setProductType(ProductTypeEnum.GROUP.getCode());
        List<Product> productList = productMapper.select(findProduct);
        if (CollUtil.isNotEmpty(productList)) {
            String childIds = "";
            //循环套餐信息提拼接他们的商品ids
            for (int i = 0; i < productList.size(); i++) {
                if ((i + 1) >= productList.size()) {
                    childIds += productList.get(i).getProductExtra();
                } else {
                    childIds += productList.get(i).getProductExtra() + ",";
                }
            }
            if (!childIds.equals("")) {
                //子商品ID去重
                String[] idsArray = childIds.split(",");
                Set<String> ids = new HashSet(Arrays.asList(idsArray));
                List<String> idList = new ArrayList<>(ids);
                for (String s : idList) {
                    //判断是否有套餐商品的IDS中存在当前商品的ID
                    Preconditions.checkArgument(!productId.equals(Long.parseLong(s)), "该商品被套餐包含，不能删除");
                }
            }
        }
        ProductImage productImage = new ProductImage();
        productImage.setProductId(productId);
        List<ProductImage> productImages = productImageMapper.select(productImage);
        if (CollectionUtils.isNotEmpty(productImages)) {
            for (ProductImage image : productImages) {
                FileOperationUtils.deleteFile(image.getImageName(), null);
            }
            //删除商品的图片数据
            productImageMapper.delete(productImage);
        }
        //删除商品表数据
        productMapper.deleteByPrimaryKey(productId);
    }


    @Override
    public PageInfo<ProductListVo> findProduct(ProductQueryDto productQueryDto) {
        PageHelper.startPage(productQueryDto.getPageNum(), productQueryDto.getPageSize());
        List<Product> productList = productMapper.findProduct(productQueryDto);
        PageInfo<Product> productInfo = new PageInfo<>(productList);
        List<ProductListVo> productListVos = new ArrayList<>();
        for (Product product : productList) {
            ProductListVo productVo = new ProductListVo();
            BeanUtils.copyProperties(product, productVo);
            productVo.setImgUrl(productImageMapper.findImgUrlByProductId(productVo.getId()));
            productListVos.add(productVo);
        }
        PageInfo<ProductListVo> productVoStudent = new PageInfo<>(productListVos);
        productVoStudent.setPageNum(productInfo.getPageNum());
        productVoStudent.setPageSize(productInfo.getPageSize());
        productVoStudent.setSize(productInfo.getSize());
        productVoStudent.setPageSize(productInfo.getPageSize());
        productVoStudent.setTotal(productInfo.getTotal());
        productVoStudent.setHasNextPage(productInfo.isHasNextPage());
        return productVoStudent;
    }

    @Override
    public ProductDetailsVo findProductDetails(Long id) {
        ProductDetailsVo productDetailsVo = productMapper.findProductDetails(id);
        //判断商品是不是套餐
        if (productDetailsVo != null && productDetailsVo.getProductType().equals(ProductTypeEnum.GROUP.getCode())) {
            String[] productChildArr = productDetailsVo.getProductChildIds().split(",");
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < productChildArr.length; i++) {
                Product product = productMapper.selectByPrimaryKey(Long.parseLong(productChildArr[i].trim()));
                if (null != product) {
                    productList.add(product);
                }
            }
            productDetailsVo.setProductList(productList);
        }
        return productDetailsVo;
    }

    @Override
    public List<Product> findProductByIds(String productIds) {

        return productMapper.findProductByIds(productIds);
    }

    /**
     * @Description: 商品名称查询商品信息
     * @Author: Kang
     * @Date: 2019/6/24 10:59
     */
    @Override
    public Product findProductByName(String productName) {
        Product product = new Product();
        product.setProductName(productName);
        return productMapper.selectOne(product);
    }

    /**
     * 查询所有上架的单品信息
     *
     * @Author: WanMing
     * @Date: 2019/7/4 17:52
     */
    @Override
    public List<ProductListVo> querySingleProductList() {
        Product product = new Product();
        product.setSellStatus(ProductSellStatusEnum.PUTAWAY.getKey());
        product.setProductType(ProductTypeEnum.SINGLE.getCode());
        List<Product> products = productMapper.select(product);
        List<ProductListVo> productListVos = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(products)){
            products.forEach(productItem->{
                ProductListVo productListVo = new ProductListVo();
                BeanUtils.copyProperties(productItem, productListVo);
                productListVos.add(productListVo);
            });
        }
        return productListVos;
    }
}
