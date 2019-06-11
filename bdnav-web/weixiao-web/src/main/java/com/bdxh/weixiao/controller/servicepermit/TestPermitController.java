package com.bdxh.weixiao.controller.servicepermit;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.weixiao.configration.security.exception.PermitException;
import com.bdxh.weixiao.configration.security.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(value = "/TestPermitWeb")
@RestController
@Api(value = "测试是否有权利使用该商品信息", tags = "测试是否有权利使用该商品信息API")
@Validated
public class TestPermitController {

    @RolesAllowed({"FANCE_TEST"})
    @ApiOperation("test1(测试需要围栏权限)")
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    public Object addServicePermit(@RequestParam("studentCardNumber") String studentCardNumber) {
        try {
            Map<String, List<String>> mapAuthorities = SecurityUtils.getCurrentAuthorized();
            //获取孩子列表信息
            List<String> thisCardNumbers = mapAuthorities.get("ROLE_" + "FANCE_TEST");
            Boolean isBy = thisCardNumbers.contains(studentCardNumber);
            if (!isBy) {
                throw new PermitException();
            }
        } catch (Exception e) {
            String messge="";
            if (e instanceof PermitException) {
                messge="抱歉，您该孩子没开通围栏权限";
            }
            return WrapMapper.error(messge);
        }
        return WrapMapper.ok("恭喜您，拥有围栏试用权限");
    }
}
