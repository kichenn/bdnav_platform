package com.bdxh.product.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.product.enums.BusinessTypeEnum;
import com.bdxh.product.enums.ProductTypeEnum;
import com.bdxh.product.persistence.ProductChildMapper;
import com.bdxh.product.persistence.ProductMapper;
import com.bdxh.product.dto.ProductAddDto;
import com.bdxh.product.dto.ProductUpdateDto;
import com.bdxh.product.entity.Product;
import com.bdxh.product.entity.ProductChild;
import com.bdxh.product.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
    private ProductChildMapper productChildMapper;

    @Override
    public void addProduct(ProductAddDto productAddDto) {
        //判断同名商品是否存在
        Product product = new Product();
        product.setProductShowName(productAddDto.getProductShowName());
        Product productData = productMapper.selectOne(product);
        Preconditions.checkArgument(productData==null,"不能重复添加商品");
        BeanMapUtils.copy(productAddDto,product);
        //判断是单品还是套餐 单品
        if (productAddDto.getProductType().intValue()== ProductTypeEnum.SINGLE.getCode().intValue()){
            productMapper.insertSelective(product);
        }
        //套餐
        if (productAddDto.getProductType().intValue()== ProductTypeEnum.GROUP.getCode().intValue()){
            //判断商品的业务类型 微校增值服务
            if (productAddDto.getBusinessType().intValue()== BusinessTypeEnum.WEIXIAO_ADD_SERVICE.getCode().intValue()){
                String productChildIds=productAddDto.getProductChildIds();
                //设置套餐包含的子产品
                product.setProductExtra(productAddDto.getProductChildIds());
                productMapper.insertSelective(product);
                if (StringUtils.isNotEmpty(productChildIds)){
                    String[] ids = productChildIds.split(",");
                    for (int i=0;i<ids.length;i++){
                        String id=ids[i];
                        ProductChild productChild=new ProductChild();
                        productChild.setProductId(product.getId());
                        productChild.setProductChildId(Long.valueOf(id));
                        productChildMapper.insertSelective(productChild);
                    }
                }
            }
        }
    }

    @Override
    public void updateProduct(ProductUpdateDto productUpdateDto) {
        //根据id查询商品
        Product oldProduct = productMapper.selectByPrimaryKey(productUpdateDto.getId());
        Preconditions.checkNotNull(oldProduct,"商品信息不存在，无法进行更新");
        //判断名称是否重复
        if(!StringUtils.equals(productUpdateDto.getProductShowName(),oldProduct.getProductShowName())){
            Product product = new Product();
            product.setProductShowName(productUpdateDto.getProductShowName());
            Product productData = productMapper.selectOne(product);
            Preconditions.checkArgument(productData==null,"不能重复添加商品");
        }
        //将dto转换成实体对象
        Product product = BeanMapUtils.map(productUpdateDto, Product.class);
        //设置更新时间
        product.setUpdateDate(new Date());
        //判断是单品还是套餐 单品
        if (productUpdateDto.getProductType().intValue()== ProductTypeEnum.SINGLE.getCode().intValue()){
            //删除之前的子产品 重新插入
            ProductChild productChild = new ProductChild();
            productChild.setProductId(productUpdateDto.getId());
            productChildMapper.delete(productChild);
            product.setProductExtra("");
            productMapper.updateByPrimaryKeySelective(product);
        }
        //套餐
        if (productUpdateDto.getProductType().intValue()== ProductTypeEnum.GROUP.getCode().intValue()){
            //判断商品的业务类型 微校增值服务
            if (productUpdateDto.getBusinessType().intValue()== BusinessTypeEnum.WEIXIAO_ADD_SERVICE.getCode().intValue()){
                String productChildIds=productUpdateDto.getProductChildIds();
                //设置套餐包含的子产品
                product.setProductExtra(productUpdateDto.getProductChildIds());
                productMapper.updateByPrimaryKeySelective(product);
                if (StringUtils.isNotEmpty(productChildIds)){
                    //删除之前的子产品 重新插入
                    ProductChild productChild = new ProductChild();
                    productChild.setProductId(productUpdateDto.getId());
                    productChildMapper.delete(productChild);
                    String[] ids = productChildIds.split(",");
                    for (int i=0;i<ids.length;i++){
                        String id=ids[i];
                        productChild=new ProductChild();
                        productChild.setProductId(product.getId());
                        productChild.setProductChildId(Long.valueOf(id));
                        productChildMapper.insertSelective(productChild);
                    }
                }
            }
        }
    }

    @Override
    public void deleteProduct(Long productId) {
        //查询商品
        Product product = productMapper.selectByPrimaryKey(productId);
        Preconditions.checkNotNull(product,"商品信息不存在");
        //单品时，被套餐商品包含不能删除
        if (product.getProductType().intValue()==ProductTypeEnum.SINGLE.getCode().intValue()){
            int exists = productChildMapper.exists(productId);
            Preconditions.checkArgument(exists==0,"该商品被套餐包含，不能删除");
        }
        productMapper.deleteByPrimaryKey(productId);
        //删除子产品
        ProductChild productChild = new ProductChild();
        productChild.setProductId(productId);
        productChildMapper.delete(productChild);
    }

    @Override
    public PageInfo queryListPage(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> products = productMapper.getByCondition(param);
        PageInfo pageInfo = new PageInfo<>(products);
        return pageInfo;
    }

    @Override
    public List<Product> queryList(Map<String, Object> param) {
        List<Product> products = productMapper.getByCondition(param);
        return products;
    }

}
