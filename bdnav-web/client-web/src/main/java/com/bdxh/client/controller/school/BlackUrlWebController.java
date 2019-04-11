package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.BlackUrlQueryDto;
import com.bdxh.school.dto.ModifyBlackUrlDto;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.BlackUrlControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Date;
import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@RestController
@RequestMapping("/clientBlackUrlWeb")
@Slf4j
@Validated
@Api(value = "学校管理--Url黑名单", tags = "学校管理--Url黑名单交互API")
public class BlackUrlWebController {


    @Autowired
    private BlackUrlControllerClient blackUrlControllerClient;


    /**
     * @Description: 增加url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/addBlack", method = RequestMethod.POST)
    @ApiOperation(value = "增加url黑名单(需学校ADMIN权限)", response = Boolean.class)
    public Object addBlack(@Validated @RequestBody AddBlackUrlDto addBlackUrlDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        addBlackUrlDto.setOperator(user.getId());
        addBlackUrlDto.setOperatorName(user.getUserName());
        addBlackUrlDto.setSchoolId(user.getSchoolId());
        addBlackUrlDto.setSchoolCode(user.getSchoolCode());
        Wrapper wrapMapper = blackUrlControllerClient.addBlack(addBlackUrlDto);
        return wrapMapper;
    }

    /**
     * @Description: 修改url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/modifyBlack", method = RequestMethod.POST)
    @ApiOperation(value = "修改url黑名单(需学校ADMIN权限)", response = Boolean.class)
    public Object modifyBlack(@Validated @RequestBody ModifyBlackUrlDto modifyBlackUrlDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        modifyBlackUrlDto.setOperator(user.getId());
        modifyBlackUrlDto.setOperatorName(user.getUserName());
        modifyBlackUrlDto.setUpdateDate(new Date());
        modifyBlackUrlDto.setSchoolId(user.getSchoolId());
        modifyBlackUrlDto.setSchoolCode(user.getSchoolCode());
        Wrapper wrapMapper = blackUrlControllerClient.modifyBlack(modifyBlackUrlDto);
        return wrapMapper;
    }

    /**
     * @Description: 删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delBlackById", method = RequestMethod.POST)
    @ApiOperation(value = "删除url黑名单(需学校ADMIN权限)", response = Boolean.class)
    public Object delBlackById(@RequestParam("id") Long id) {
        Wrapper wrapMapper = blackUrlControllerClient.delBlackById(id);
        return wrapMapper;
    }


    /**
     * @Description: 批量删除url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RolesAllowed({"ADMIN"})
    @RequestMapping(value = "/delBlackInIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除url黑名单(需学校ADMIN权限)", response = Boolean.class)
    public Object delBlackInIds(@RequestParam("ids") List<Long> ids) {
        Wrapper wrapMapper = blackUrlControllerClient.delBlackInIds(ids);
        return wrapMapper;
    }

    /**
     * @Description: id查询url黑名单
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/findBlackById", method = RequestMethod.GET)
    @ApiOperation(value = "id查询url黑名单", response = BlackUrl.class)
    public Object findBlackById(@RequestParam("id") Long id) {
        Wrapper wrapMapper = blackUrlControllerClient.findBlackById(id);
        return WrapMapper.ok(wrapMapper.getResult());
    }

    /**
     * @Description: 分页查询(查询当前学校禁用的URL)
     * @Author: Kang
     * @Date: 2019/4/11 10:10
     */
    @RequestMapping(value = "/findBlackInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询(查询当前学校禁用的URL)", response = BlackUrl.class)
    public Object findBlackInConditionPaging(@Validated @RequestBody BlackUrlQueryDto blackUrlQueryDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        blackUrlQueryDto.setSchoolId(user.getSchoolId());
        Wrapper wrapMapper = blackUrlControllerClient.findBlackInConditionPaging(blackUrlQueryDto);
        return WrapMapper.ok(wrapMapper.getResult());
    }
}