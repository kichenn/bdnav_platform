package com.bdxh.weixiao.configration.aspect;

import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-22 09:46
 **/
@Aspect
@Component
@Slf4j
public class WeiXiaoAspect {

    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;

    @Autowired
    private ProductControllerClient productControllerClient;

    @Autowired
    private OrdersControllerClient ordersControllerClient;

    @Pointcut(value = "@annotation(com.bdxh.weixiao.configration.aspect.WeiXiaoChargeApp))")
    public void weiXiaoChargeApp(){

    }

    //前置增强
    @Before("weiXiaoChargeApp()")
    public void beforeOrder(){
        //判断当前家长是否有服务购买记录或者试用记录
        QueryServiceUserDto queryServiceUserDto=new QueryServiceUserDto();
       PageInfo<ServiceUser> pageInfo= serviceUserControllerClient.queryServiceUser(queryServiceUserDto).getResult();
       productControllerClient.findProductById(pageInfo.getList().get(0).getProductId());
       log.info(pageInfo+"");
       log.info("前置增强进来了");
    }
}