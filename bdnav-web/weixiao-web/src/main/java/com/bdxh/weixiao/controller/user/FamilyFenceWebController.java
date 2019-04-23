package com.bdxh.weixiao.controller.user;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-23 09:16
 **/
@RestController
@RequestMapping("/familyFence")
@Validated
@Slf4j
@Api(value = "微校平台----家长围栏API", tags = "微校平台----家长围栏API")
public class FamilyFenceWebController {
}