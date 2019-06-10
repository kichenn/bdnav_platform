package com.bdxh.backend.controller.appmarket;

import com.bdxh.appmarket.dto.AddAppVersionDto;
import com.bdxh.appmarket.entity.AppVersion;
import com.bdxh.appmarket.feign.AppVersionControllerClient;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-14 18:19
 **/

@RestController
@RequestMapping("/appVersion")
@Slf4j
@Validated
@Api(value = "APP版本控制器控制器", tags = "APP版本控制器控制器")
public class AppVersionWebController {

        @Autowired
        private AppVersionControllerClient appVersionControllerClient;

        /**
         * 添加app新的版本信息
         * @param addAppVersionDto
         * @return
         */
        @RequestMapping(value = "/addAppVersion",method = RequestMethod.POST)
        @ApiOperation(value="添加app新的版本信息",response = Boolean.class)
        public Object addAppVersion(@Valid @RequestBody AddAppVersionDto addAppVersionDto){
            try {
                appVersionControllerClient.addAppVersion(addAppVersionDto);
                return WrapMapper.ok();
            }catch (Exception e){
                return WrapMapper.error();
            }
        }
        /**
         * 查看APP版本历史
         */
        @RequestMapping(value = "/findAppVersion",method = RequestMethod.GET)
        @ApiOperation(value="查看APP版本历史",response = AppVersion.class)
        public Object findAppVersion(@RequestParam("appId")Long appId){
            try {
                return appVersionControllerClient.findAppVersion(appId);
            }catch (Exception e){
                return WrapMapper.error();
            }
        }
}