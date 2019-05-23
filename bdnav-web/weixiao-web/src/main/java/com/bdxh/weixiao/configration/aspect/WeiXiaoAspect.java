package com.bdxh.weixiao.configration.aspect;

import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

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
    public void beforeOrder(JoinPoint joinPoint){
        //1.获取到所有的参数值的数组
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Method method = methodSignature.getMethod();
        Class[] parameterType=methodSignature.getParameterTypes();
        System.out.println("---------------参数列表开始-------------------------");
        for (int i =0 ,len=parameterNames.length;i < len ;i++){
            System.out.println("参数名："+ parameterNames[i] + " = " +args[i]+"参数类型等于"+parameterType[i]);
        }
        //判断当前家长是否有服务购买记录或者试用记录
        QueryServiceUserDto queryServiceUserDto=new QueryServiceUserDto();
       PageInfo<ServiceUser> pageInfo= serviceUserControllerClient.queryServiceUser(queryServiceUserDto).getResult();
       productControllerClient.findProductById(pageInfo.getList().get(0).getProductId());
       log.info(pageInfo+"");
       log.info("前置增强进来了");
    }
}