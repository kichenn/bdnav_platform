package com.bdxh.weixiao.controller.weixiao;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 微信网页授权域名类， @ApiIgnore 忽略不在swagger展示
 * @Author: Kang
 * @Date: 2019/5/31 15:40
 */
@ApiIgnore
@RestController
@RequestMapping
public class WeixiaoBaseController {

    @RequestMapping("/MP_verify_fL8hJJpPmRU0cZCm.txt")
    public void returnConfigFile(HttpServletResponse response) {
        try {
            //把MP_verify_xxxxxx.txt中的内容返回
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setStatus(200);
            response.setHeader("Content-type", "application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json;charset=utf-8");
            response.getOutputStream().write("fL8hJJpPmRU0cZCm".getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
