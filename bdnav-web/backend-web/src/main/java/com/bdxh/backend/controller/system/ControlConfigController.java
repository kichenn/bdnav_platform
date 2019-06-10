package com.bdxh.backend.controller.system;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.AddControlConfig;
import com.bdxh.system.dto.ModifyControlConfig;
import com.bdxh.system.dto.QueryControlConfig;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.ControlConfigControllerClient;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/appControlConfigWeb")
@Validated
@Slf4j
@Api(value = "应用管控系统信息", tags = "应用管控系统信息")
public class ControlConfigController {

    @Autowired
    private ControlConfigControllerClient controlConfigControllerClient;

    /**
     * 增加应用管控信息
     *
     * @return
     */
    @RequestMapping(value = "/addControlConfig", method = RequestMethod.POST)
    @ApiOperation(value="添加应用管控系统信息",response = Boolean.class)
    public Object addControlConfig(@Validated @RequestBody AddControlConfig addControlConfig) {
        try {
            User user = SecurityUtils.getCurrentUser();
            addControlConfig.setOperator(user.getId());
            addControlConfig.setOperatorName(user.getUserName());
            Wrapper wrapper = controlConfigControllerClient.addControlConfig(addControlConfig);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 修改部门信息
     *
     * @return
     */
    @RequestMapping(value = "/modifyControlConfig", method = RequestMethod.POST)
    @ApiOperation(value="修改应用管控系统信息",response = Boolean.class)
    public Object modifyControlConfig(@Validated @RequestBody ModifyControlConfig modifyControlConfig) {
        try {
            User user = SecurityUtils.getCurrentUser();
            modifyControlConfig.setOperator(user.getId());
            modifyControlConfig.setOperatorName(user.getUserName());
            Wrapper wrapper = controlConfigControllerClient.modifyControlConfig(modifyControlConfig);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 删除应用管控系统信息
     *
     * @return
     */
    @RequestMapping(value = "/delControlConfig", method = RequestMethod.GET)
    @ApiOperation(value="删除应用管控系统信息",response = Boolean.class)
    public Object delControlConfig(@RequestParam(name = "id") Long id) {
        try {
            Wrapper wrapper = controlConfigControllerClient.delControlConfig(id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 带条件的分页查询
     *
     * @return
     */
    @RequestMapping(value = "/queryListPage", method = RequestMethod.POST)
    @ApiOperation(value="分页查询应用管控系统信息",response = PageInfo.class)
    public Object queryListPage(@Validated @RequestBody QueryControlConfig queryControlConfig) {
        try {
            Wrapper wrapper = controlConfigControllerClient.queryListPage(queryControlConfig);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }






}
