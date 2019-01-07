package com.bdxh.web.wechatpay.configration;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.wrapper.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 全局异常处理器
 * @author: xuyuan
 * @create: 2019-01-04 12:16
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public void handleException(Exception e, HttpServletResponse response){
        log.error(e.getMessage());
        String jsonStr= JSON.toJSONString(WrapMapper.error(e.getMessage()));
        try {
            response.getOutputStream().write(jsonStr.getBytes("utf-8"));
        }catch (Exception ex){
            e.printStackTrace();
            log.error(ex.getMessage());
        }
    }
}
