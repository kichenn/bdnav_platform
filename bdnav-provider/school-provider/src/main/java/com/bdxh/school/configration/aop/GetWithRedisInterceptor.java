//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.bdxh.school.configration.aop;

import com.alibaba.fastjson.JSON;
import com.bdxh.school.configration.anno.GetWithRedis;
import com.bdxh.school.configration.redis.RedisCache;
import com.github.pagehelper.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
* @Author: Kang
* @Date: 2019/2/26 9:42
*/
@Aspect
@Component
@Slf4j
public class GetWithRedisInterceptor {

    @Autowired
    private RedisCache<String> redisCache;

    @Around("@annotation(com.bdxh.school.configration.anno.GetWithRedis)")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        GetWithRedis annotation = targetMethod.getAnnotation(GetWithRedis.class);
        Object result;
        String key = null;
        if (StringUtil.isNotEmpty(annotation.key())) {
            key = annotation.key();
            String argsKey = "";
            for (Object arg : pjp.getArgs()) {
                argsKey = argsKey + "_" + arg;
            }
            key = key + ":" + argsKey;
            String jsonValueInRedis = redisCache.get(key);

            Type genericReturnType = targetMethod.getGenericReturnType();

            if (jsonValueInRedis != null) {
                result = JSON.parseObject(jsonValueInRedis, genericReturnType);
                return result;
            }
        }
        result = pjp.proceed();
        if (key != null) {
            String jsonValueInRedis = JSON.toJSONString(result);
            try {
                long expiredSecond = annotation.expiredSecond();
                if (annotation.expired()) {
                    redisCache.set(key, jsonValueInRedis, expiredSecond);
                } else {
                    redisCache.set(key, jsonValueInRedis);
                }
            } catch (Exception e) {
                log.error("", e);
            }
        }
        return result;
    }
}
