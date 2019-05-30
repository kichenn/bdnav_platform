package com.bdxh.weixiao.configration.aspect;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.product.entity.Product;
import com.bdxh.product.feign.ProductControllerClient;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.feign.ServiceUserControllerClient;
import com.bdxh.weixiao.configration.aspect.AspectConstant.AspectResultConstant;
import com.bdxh.weixiao.configration.security.userdetail.MyUserDetails;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-22 09:46
 **/
@Aspect
@Component
@Slf4j
public class WeiXiaoAspect {
    private Boolean result = true;

    @Autowired
    private ServiceUserControllerClient serviceUserControllerClient;


    @Autowired
    private ProductControllerClient productControllerClient;


    //切入点  接口被weiXiaoChargeApp修饰的注解都会被切面
    @Pointcut(value = "@annotation(com.bdxh.weixiao.configration.aspect.WeiXiaoChargeApp))")
    public void weiXiaoChargeApp() {
    }


    //环绕增强
    @Around(value = "weiXiaoChargeApp()")
    public Object weiXiaoChargeAppAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //1.获取到所有的入参值的数组
        Object[] paramValues = proceedingJoinPoint.getArgs();
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.获取方法入参的字符串数组
        String[] paramNames = methodSignature.getParameterNames();
        HashMap<String, Object> paramMap = new HashMap<>();
        for (int i = 0; i < paramNames.length; i++) {
            paramMap.put(paramNames[i], paramValues[i]);
        }
/*        //获取具体的应用
        WeiXiaoChargeApp weiXiaoChargeApp = methodSignature.getMethod().getAnnotation(WeiXiaoChargeApp.class);
        AspectRequestEnum aspectRequestEnum = weiXiaoChargeApp.aspectRequestEnum();*/

        //执行前置方法 paramMap:接口入参信息, prodcutId:商品id
        Wrapper resultWrap = weiXiaoChargeBefore(paramMap);
        //原来的返回值
        Object rep = proceedingJoinPoint.proceed();
        //如果不是在正常使用期间那么修改他的返回值
        if (resultWrap.getCode() != Wrapper.SUCCESS_CODE) {
            rep = resultWrap;
        }
        return rep;
    }

    /**
     * 环绕增强前置方法
     *
     * @param params 参数map集合
     * @return 注意学生的学号和schoolCode是必须要填的参数
     * bin
     */
    public Wrapper weiXiaoChargeBefore(Map<String, Object> params) {
        //从后台获取家长卡号
        MyUserDetails myUserDetails=SecurityUtils.getMyUserDetails();
        myUserDetails.getUserInfo().getSchoolCode();
        myUserDetails.getUserInfo().getCardNumber();
        String fCardNumber = "20190516002";
        String productName=StringUtils.isNotEmpty(params.get("appCode").toString()) ? params.get("appCode").toString() : null;
        String schoolCode = StringUtils.isNotEmpty(params.get("schoolCode").toString()) ? params.get("schoolCode").toString() : null;
        String studentCardNumber = StringUtils.isNotEmpty(params.get("cardNumber").toString()) ? params.get("cardNumber").toString() : null;
        QueryServiceUserDto queryServiceUsedDto = new QueryServiceUserDto();
        queryServiceUsedDto.setCardNumber(fCardNumber);
        queryServiceUsedDto.setSchoolCode(schoolCode);
        queryServiceUsedDto.setStudentNumber(studentCardNumber);
        if (StringUtils.isNotEmpty(schoolCode) && StringUtils.isNotEmpty(studentCardNumber)) {
            List<ServiceUser> serviceUserList = serviceUserControllerClient.queryAllServiceUser(queryServiceUsedDto).getResult();
            if (CollectionUtils.isNotEmpty(serviceUserList)) {
                //查询未过期的试用记录
                queryServiceUsedDto.setType(AspectResultConstant.TYPE_TRIAL);
                queryServiceUsedDto.setStatus(AspectResultConstant.STATUS_NOT_EXPIRED);
                List<ServiceUser> serviceUserTrialList = serviceUserControllerClient.queryAllServiceUser(queryServiceUsedDto).getResult();
                if (CollectionUtils.isNotEmpty(serviceUserTrialList)) {
                    for (ServiceUser serviceUser : serviceUserTrialList) {
                        //如果试用记录刚好过期那么就修改他的认证状态过期 否则就是存在所有的应用使用权返回正确信息
                        if (new Date().getTime() > serviceUser.getEndTime().getTime()) {
                            ModifyServiceUserDto modifyServiceUserDto = new ModifyServiceUserDto();
                            modifyServiceUserDto.setCardNumber(serviceUser.getCardNumber());
                            modifyServiceUserDto.setSchoolCode(serviceUser.getSchoolCode());
                            modifyServiceUserDto.setId(serviceUser.getId());
                            modifyServiceUserDto.setStatus(AspectResultConstant.STATUS_EXPIRED);
                            serviceUserControllerClient.updateServiceUser(modifyServiceUserDto);
                        } else {
                            return WrapMapper.ok();
                        }
                    }
                }
                //查询未过期的服务应用
                queryServiceUsedDto.setType(AspectResultConstant.TYPE_NORMAL);
                queryServiceUsedDto.setStatus(AspectResultConstant.STATUS_NOT_EXPIRED);
                List<ServiceUser> serviceUserNormalList = serviceUserControllerClient.queryAllServiceUser(queryServiceUsedDto).getResult();
                if (CollectionUtils.isNotEmpty(serviceUserNormalList)) {
                    //未过期的认证商品ID集合
                    List<Long> productIdList = new ArrayList<>();
                    for (ServiceUser serviceUser : serviceUserNormalList) {
                        //如果试用记录刚好过期那么就修改他的认证状态过期 否则就是存在所有的应用使用权返回正确信息
                        if (new Date().getTime() > serviceUser.getEndTime().getTime()) {
                            ModifyServiceUserDto modifyServiceUserDto = new ModifyServiceUserDto();
                            modifyServiceUserDto.setCardNumber(serviceUser.getCardNumber());
                            modifyServiceUserDto.setSchoolCode(serviceUser.getSchoolCode());
                            modifyServiceUserDto.setId(serviceUser.getId());
                            modifyServiceUserDto.setStatus(AspectResultConstant.STATUS_EXPIRED);
                            serviceUserControllerClient.updateServiceUser(modifyServiceUserDto);
                        } else {
                            //将没有过期的认证的商品ID添加到集合中
                            productIdList.add(serviceUser.getProductId());
                        }
                    }
                    String productIds = StringUtils.strip(productIdList.toString(), "[]").replace(" ", "");
                    //根据Id集合查询商品信息
                    List<Product> productList = productControllerClient.findProductByIds(productIds).getResult();
                    for (Product product : productList) {
                        //商品类型 1 单品 2 套餐
                        if (product.getProductType().equals(Byte.valueOf("2"))) {
                            List<Product> products=productControllerClient.findProductByIds(product.getProductExtra()).getResult();
                            for (Product extraProduct : products) {
                                if (extraProduct.getProductName().equals(productName)) {
                                    //添加角色权限
                                    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
                                    authorities.add(new SimpleGrantedAuthority("ROLE_CHARGE"));
                                    myUserDetails.setAuthorities(authorities);
                                    return WrapMapper.ok();
                                }
                            }
                        } else {
                            if (product.getProductName().equals(productName)) {
                                return WrapMapper.ok();
                            }
                        }
                    }
                }
                return WrapMapper.wrap(AspectResultConstant.SERVICE_EXPIRED_CODE, "当前服务已过期或未购买");
            }
            //如果没有一条记录那么去引导他领取试用
            return WrapMapper.wrap(AspectResultConstant.NO_LICENSE_CODE, "暂无服务购买和试用记录");
        }
        return WrapMapper.error("参数异常");
    }

}