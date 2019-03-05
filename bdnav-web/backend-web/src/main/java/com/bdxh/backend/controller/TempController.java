package com.bdxh.backend.controller;


import com.bdxh.common.utils.wrapper.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DenyAll;
import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/temp")
@Slf4j
public class TempController {

    @DenyAll
//    @SuppressWarnings({"USER"})
    @RequestMapping(value="/temp1",method = RequestMethod.GET)
    public Object temp1() {
        return WrapMapper.ok("user权限访问......");
    }

    @RolesAllowed({"ADMIN"})
    @RequestMapping(value="/temp2",method = RequestMethod.GET)
    public Object temp2() {
        return WrapMapper.ok("ADMIN权限访问.....");
    }

    @RolesAllowed({"USER"})
    @RequestMapping(value="/temp3",method = RequestMethod.GET)
    public Object temp3() {
        return WrapMapper.ok("USER权限访问.....");
    }


}
