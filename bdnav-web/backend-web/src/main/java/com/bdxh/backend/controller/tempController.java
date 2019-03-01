package com.bdxh.backend.controller;


import com.bdxh.common.utils.wrapper.WrapMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.DenyAll;

@RestController
@RequestMapping("/temp")
@Slf4j
public class tempController {

    @DenyAll
    @RequestMapping(value="/login",method = RequestMethod.GET)
    public Object temp() {
        return WrapMapper.ok("进来了");
    }
}
