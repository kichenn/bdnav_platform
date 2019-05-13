package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.dto.ShowSchoolUserModifyPrefixDto;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.enums.SchoolUserStatusEnum;
import com.bdxh.school.fallback.SchoolUserControllerClientFallback;
import com.bdxh.school.vo.SchoolUserShowVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校系统用户feign客户端
 * @Author: Kang
 * @Date: 2019/3/26 16:22
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolUserControllerClientFallback.class)
public interface SchoolUserControllerClient {

    /**
     * 根据用户名查询用户对象
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/schoolUser/findSchoolUserByName", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolUser> findSchoolUserByName(@RequestParam("userName") String userName);

    /**
     * 查询所有系统用户信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolUser/findAllSchoolUserInfo", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolUser>> findAllSchoolUserInfo();

    /**
     * 添加系统用户
     *
     * @return
     */
    @RequestMapping(value = "/schoolUser/addSchoolUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolUser(@RequestBody AddSchoolUserDto addSchoolUserDto);

    /**
     * 根据id查询学校用户信息
     *
     * @return
     */
    @RequestMapping(value = "/schoolUser/findSchoolUserById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<ShowSchoolUserModifyPrefixDto> findSchoolUserById(@RequestParam(name = "id") Long id);

    /**
     * 修改系统用户
     *
     * @return
     */
    @RequestMapping(value = "/schoolUser/modifySchoolUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolUser(@RequestBody ModifySchoolUserDto modifySchoolUserDto);


    /**
     * 根据条件查询用户列表并分页
     */
    @RequestMapping(value = "/schoolUser/findSchoolUsersInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolUserShowVo>> findSchoolUsersInConditionPage(@RequestBody SchoolUserQueryDto schoolUserQueryDto);

    /**
     * 删除系统用户
     *
     * @return
     */
    @RequestMapping(value = "/schoolUser/delSchoolUser", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delSchoolUser(@RequestParam(name = "id") Long id);


    /**
     * 批量删除系统用户
     *
     * @return
     */
    @RequestMapping(value = "/schoolUser/delBatchSchoolUserInIds", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBatchSchoolUserInIds(@RequestParam(name = "ids") List<Long> ids);


    /**
     * @Description: 修改用户状态(启用或者禁用)
     * @Author: Kang
     * @Date: 2019/3/27 10:35
     */
    @RequestMapping(value = "/schoolUser/modifySchoolUserStatusById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolUserStatusById(@RequestParam(name = "id") Long id, @RequestParam(name = "statusEnum") SchoolUserStatusEnum statusEnum);

    /**
     * @Description: 根据学校ID和username查找用户信息
     * @Author: Kang
     * @Date: 2019/3/27 10:35
     */
    @RequestMapping(value = "/schoolUser/findSchoolUserByUserNameAndSchoolId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolUser> findSchoolUserByUserNameAndSchoolId(@RequestParam(name = "schoolId")Long schoolId,@RequestParam(name = "userName")String userName);
}
