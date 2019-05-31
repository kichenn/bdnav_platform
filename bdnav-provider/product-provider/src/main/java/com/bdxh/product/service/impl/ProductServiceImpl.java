package com.bdxh.product.service.impl;

import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.product.dto.*;
import com.bdxh.product.entity.Product;
import com.bdxh.product.entity.ProductImage;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.persistence.ProductImageMapper;
import com.bdxh.product.persistence.ProductMapper;
import com.bdxh.product.service.ProductService;
import com.bdxh.product.vo.ProductDetailsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.xiaoleilu.hutool.collection.CollUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @description: 商品service实现
 * @author: xuyuan
 * @create: 2019-01-19 18:25
 **/
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
        //判断是不是新增的套餐
        if (productDto.getProductType().equals(ProductTypeEnum.GROUP.getCode())) {
            product.setProductExtra(productDto.getProductChildIds());
        }
        productMapper.insertProduct(product);
        Long productId = product.getId();
        //循环添加图片详情表图片顺序按照图片上传的先后顺序排列
        Byte i = 1;
        if (productDto.getImage().size() > 0) {
            for (ProductImageAddDto productImageAddDto : productDto.getImage()) {
                ProductImage productImage = BeanMapUtils.map(productImageAddDto, ProductImage.class);
                if (i == 1) {
                    productDto.setImgUrl(productImage.getImageUrl());
                }
                productImage.setSort(i);
                productImage.setProductId(productId);
                productImage.setOperator(productDto.getOperator());
                productImage.setOperatorName(productDto.getOperatorName());
                productImage.setRemark(productDto.getRemark());
                productImageMapper.insert(productImage);
                i++;
            }
        } else {
            productDto.setImgUrl("");
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
                                productMapper.updateProduct(fatherProduct);
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
        Byte i = 1;
        productImageMapper.deleteProductImageByProductId(product.getId());
        for (ProductImage productImage : productImageList) {
            productImage.setSort(i);
            productImage.setProductId(product.getId());
            productImage.setOperator(product.getOperator());
            productImage.setOperatorName(product.getOperatorName());
            productImage.setRemark(product.getRemark());
            productImageMapper.insert(productImage);
            i++;
        }
        product.setProductExtra(productUpdateDto.getProductChildIds());
        productMapper.updateProduct(product);
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
    public PageInfo<Product> findProduct(ProductQueryDto productQueryDto) {
        PageHelper.startPage(productQueryDto.getPageNum(), productQueryDto.getPageSize());
        List<Product> productList = productMapper.findProduct(productQueryDto);
        PageInfo<Product> pageInfoStudent = new PageInfo<>(productList);
        return pageInfoStudent;
    }

    @Override
    public ProductDetailsVo findProductDetails(Long id) {
        ProductDetailsVo productDetailsVo = productMapper.findProductDetails(id);
        //判断商品是不是套餐
        if (productDetailsVo.getProductType().equals(ProductTypeEnum.GROUP.getCode())) {
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
}
