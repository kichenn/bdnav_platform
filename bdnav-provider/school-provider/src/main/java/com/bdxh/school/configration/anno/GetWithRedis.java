package com.bdxh.school.configration.anno;

import java.lang.annotation.*;

/**
* @Description:  redis
* @Author: Kang
* @Date: 2019/2/26 9:32
*/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetWithRedis {
    String key();

    boolean expired() default true;

    long expiredSecond() default 60 * 5;
}
