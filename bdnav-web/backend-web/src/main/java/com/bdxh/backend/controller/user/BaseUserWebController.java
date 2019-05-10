package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.feign.BaseUserControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @description:
 * @author: binzh
 * @create: 2019-04-25 16:38
 **/
@RestController
@RequestMapping("/baseUserWeb")
@Validated
@Slf4j
@Api(value = "学校用户信息交互API", tags = "学校用户信息交互API")
public class BaseUserWebController {
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;
    /**
     * 判断电话号码是否存在
     * @return
     */
    @ApiOperation(value="判断电话号码是否存在")
    @RequestMapping(value = "/queryUserPhoneByPhone",method = RequestMethod.POST)
    public Object queryUserPhoneByPhone(@RequestBody BaseUserQueryDto baseUserQueryDto){
        try {
            Integer count=(Integer) baseUserControllerClient.queryUserPhoneByPhone(baseUserQueryDto).getResult();
            if(count>0){
                return WrapMapper.ok(false);
            }
            return WrapMapper.ok(true);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}