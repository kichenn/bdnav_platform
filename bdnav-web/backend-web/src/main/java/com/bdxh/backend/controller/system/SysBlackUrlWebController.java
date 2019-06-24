package com.bdxh.backend.controller.system;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.SysBlackUrlQueryDto;
import com.bdxh.system.dto.AddSysBlackUrlDto;
import com.bdxh.system.entity.User;
import com.bdxh.system.feign.SysBlackUrlControllerClient;
import com.bdxh.system.vo.SysBlackUrlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WanMing
 * @date 2019/6/20 16:14
 */
@RestController
@RequestMapping("/sysBlackUrlWeb")
@Slf4j
@Validated
@Api(value = "系统黑名单(包括第三方金山)", tags = "系统黑名单(包括第三方金山)API")
public class SysBlackUrlWebController {

    @Autowired
    private SysBlackUrlControllerClient sysBlackUrlControllerClient;

    /**
     * 添加系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/addSysBlackUrl",method = RequestMethod.POST)
    @ApiOperation(value = "添加系统的黑名单",response = Boolean.class)
    public Object addSysBlackUrl(@Validated @RequestBody AddSysBlackUrlDto addSysBlackUrlDto){
        User currentUser = SecurityUtils.getCurrentUser();
        addSysBlackUrlDto.setOperator(currentUser.getId());
        addSysBlackUrlDto.setOperatorName(currentUser.getUserName());
        return sysBlackUrlControllerClient.addSysBlackUrl(addSysBlackUrlDto);
    }

    /**
     * 删除系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/delSysBlackUrl",method = RequestMethod.GET)
    @ApiOperation(value = "删除系统的黑名单",response = Boolean.class)
    public Object delSysBlackUrl(@RequestParam("id") Long id){
        return sysBlackUrlControllerClient.delSysBlackUrl(id);
    }

    /**
     * 批量删除系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/batchDelSysBlackUrl",method = RequestMethod.GET)
    @ApiOperation(value = "批量删除系统的黑名单",response = Boolean.class)
    public Object batchDelSysBlackUrl(@RequestParam("ids") List<Long> ids){
        return sysBlackUrlControllerClient.batchDelSysBlackUrl(ids);
    }




    /**
     * 根据条件分页查询系统的黑名单
     * @Author: WanMing
     * @Date: 2019/6/20 11:52
     */
    @RequestMapping(value = "/findSysBlackUrlByCondition",method = RequestMethod.POST)
    @ApiOperation(value = "根据条件分页查询系统的黑名单",response = SysBlackUrlVo.class)
    public Object findSysBlackUrlByCondition(@Validated @RequestBody SysBlackUrlQueryDto sysBlackUrlQueryDto){
        return sysBlackUrlControllerClient.findSysBlackUrlByCondition(sysBlackUrlQueryDto);
    }


}
