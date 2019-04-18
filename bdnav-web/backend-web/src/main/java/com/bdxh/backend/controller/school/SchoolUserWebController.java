package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.dto.ShowSchoolUserModifyPrefixDto;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.feign.SchoolUserControllerClient;
import com.bdxh.school.vo.SchoolUserShowVo;
import com.bdxh.system.entity.User;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @Description: 学校用户交互
 * @Author: Kang
 * @Date: 2019/3/26 17:06
 */
@RestController
@RequestMapping("/schoolUserWebController")
@Validated
@Slf4j
@Api(value = "学校用户交互API", tags = "学校用户交互交互API")
public class SchoolUserWebController {

    @Autowired
    private SchoolUserControllerClient schoolUserControllerClient;


    @RequestMapping(value = "/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "学校用户信息列表数据[分页筛选]", response = SchoolRole.class)
    public Object findSchoolsInConditionPaging(@RequestBody SchoolUserQueryDto userQueryDto) {
        Wrapper<PageInfo<SchoolUserShowVo>> wrapper = schoolUserControllerClient.findSchoolUsersInConditionPage(userQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }


    @RequestMapping(value = "/addSchoolUser", method = RequestMethod.POST)
    @ApiOperation(value = "添加学校用户信息", response = Boolean.class)
    public Object addSchoolUser(@Validated @RequestBody AddSchoolUserDto addUserDto) {
        //密码加密
        addUserDto.setPassword(new BCryptPasswordEncoder().encode(addUserDto.getPassword()));
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        addUserDto.setOperator(user.getId());
        addUserDto.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolUserControllerClient.addSchoolUser(addUserDto);
        return wrapper;
    }

    @RequestMapping(value = "/findSchoolUserById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询学校用户信息", response = SchoolUser.class)
    public Object findSchoolUserById(@RequestParam(name = "id") Long id) {
        Wrapper<ShowSchoolUserModifyPrefixDto> wrapper = schoolUserControllerClient.findSchoolUserById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @RequestMapping(value = "/modifySchoolUser", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校用户信息", response = Boolean.class)
    public Object modifySchoolUser(@Validated @RequestBody ModifySchoolUserDto modifySchoolUserDto) {
        //密码加密
        if (StringUtils.isNotEmpty(modifySchoolUserDto.getPassword())) {
            modifySchoolUserDto.setPassword(new BCryptPasswordEncoder().encode(modifySchoolUserDto.getPassword()));
        }
        //设置操作人
        User user = SecurityUtils.getCurrentUser();
        modifySchoolUserDto.setOperator(user.getId());
        modifySchoolUserDto.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolUserControllerClient.modifySchoolUser(modifySchoolUserDto);
        return wrapper;
    }


    @RequestMapping(value = "/delSchoolUser", method = RequestMethod.POST)
    @ApiOperation(value = "根据id删除学校用户信息", response = Boolean.class)
    public Object delSchoolUser(@RequestParam(name = "id") Long id) {
        User user = SecurityUtils.getCurrentUser();
        if (id.equals(user.getId())) {
            //如果删除的id和当前登录的用户id相同，则不能删除
            return WrapMapper.error("不能删除当前登录账号");
        }
        Wrapper wrapper = schoolUserControllerClient.delSchoolUser(id);
        return wrapper;
    }


    @RequestMapping(value = "/delBatchSchoolUserInIds", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除学校用户信息", response = Boolean.class)
    public Object delBatchSchoolUserInIds(@RequestParam(name = "ids") List<Long> ids) {
        Wrapper wrapper = schoolUserControllerClient.delBatchSchoolUserInIds(ids);
        return wrapper;
    }

    @RequestMapping(value = "/modifySchoolUserStatusById", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户状态(启用或者禁用)", response = Boolean.class)
    public Object modifySchoolUserStatusById(@RequestParam(name = "id") Long id, @RequestParam(name = "statusEnum") SchoolUserStatusEnum statusEnum) {
        User user = SecurityUtils.getCurrentUser();
        if (id.equals(user.getId())) {
            //如果删除的id和当前登录的用户id相同，则不能禁用
            return WrapMapper.error("不能禁用当前登录账号");
        }
        Wrapper wrapper = schoolUserControllerClient.modifySchoolUserStatusById(id, statusEnum);
        return wrapper;
    }

}
