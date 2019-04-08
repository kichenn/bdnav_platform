package com.bdxh.backend.controller;


import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/temp")
@Slf4j
@Api(value = "示例信息", tags = "示例信息交互API")
public class TempController {

    @DenyAll
    @RequestMapping(value = "/temp1", method = RequestMethod.GET)
    public Object temp1() {
        return WrapMapper.ok("没有权限访问......");
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/temp2", method = RequestMethod.GET)
    public Object temp2() {
        return WrapMapper.ok("ADMIN权限访问.....");
    }

    @RolesAllowed({"USER"})
    @RequestMapping(value = "/temp3", method = RequestMethod.GET)
    public Object temp3() {
        return WrapMapper.ok("USER权限访问.....");
    }

    @PermitAll
    @RequestMapping(value = "/temp4", method = RequestMethod.GET)
    public Object temp4() {
        return WrapMapper.ok("所有权限访问.....");
    }
}
