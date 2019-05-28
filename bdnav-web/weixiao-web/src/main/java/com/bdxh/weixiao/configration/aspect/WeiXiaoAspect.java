package com.bdxh.weixiao.configration.aspect;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.order.feign.OrdersControllerClient;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
/*

    @Autowired
    private ProductControllerClient productControllerClient;

    @Autowired
    private OrdersControllerClient ordersControllerClient;
*/

    @Pointcut(value = "@annotation(com.bdxh.weixiao.configration.aspect.WeiXiaoChargeApp))")
    public void weiXiaoChargeApp() {

    }

    //前置增强
    @Before("weiXiaoChargeApp()")
    public Object beforeOrder(JoinPoint joinPoint) {
        //1.获取到所有的参数值的数组
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
        Method method = methodSignature.getMethod();
        Class[] parameterType = methodSignature.getParameterTypes();
        System.out.println("---------------参数列表开始-------------------------");
        String studentCardNumber = null;
        String schoolCode = null;
        for (int i = 0, len = parameterNames.length; i < len; i++) {
            if (parameterNames[i].equals("cardNumber")) {
                studentCardNumber += args[i];
            }
            if (parameterNames[i].equals("schoolCode")) {
                schoolCode += args[i];
            }
            System.out.println("参数名：" + parameterNames[i]);
        }
        //从后台获取家长卡号 现在先定死 等李康那边写好方法
        String fCardNumber = "20190516002";
        List<ServiceUser> pageInfo = serviceUserControllerClient.queryAllServiceUser(fCardNumber, schoolCode, studentCardNumber).getResult();
        //判断当前家长是否有服务购买记录或者试用记录
        if (CollectionUtils.isNotEmpty(pageInfo)) {
            for (ServiceUser serviceUser : pageInfo) {
                //如果是试用期那么就是所有产品都是在使用期间
                if (serviceUser.getType().equals(Byte.valueOf("1"))) {
                    if(true){

                    }
                //如果有正式使用记录
                } else if (serviceUser.getType().equals(Byte.valueOf("2"))) {

                }
            }
        } else {
        //如果没有一条记录那么去引导他领取试用
            Map<String,Object> result=new HashMap<String,Object>();
            result.put("errorCode","");
            result.put("","");
            return WrapMapper.error();
        }
        log.info(pageInfo + "");
        log.info("前置增强进来了");
    return null;
    }
}