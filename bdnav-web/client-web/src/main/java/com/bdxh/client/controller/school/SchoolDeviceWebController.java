package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolDeviceDto;
import com.bdxh.school.dto.ModifySchoolDeviceDto;
import com.bdxh.school.dto.SchoolDeviceQueryDto;
import com.bdxh.school.dto.SchoolPosDeviceQueryDto;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolDeviceControllerClient;
import com.bdxh.school.vo.SchoolDeviceShowVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * @Description: 学校设备信息
 * @Author: Kang
 * @Date: 2019/3/27 17:37
 */
@RestController
@RequestMapping("/clientSchoolDeviceWeb")
@Validated
@Slf4j
@Api(value = "学校管理员--学校设备信息", tags = "学校管理员--学校设备信息交互API")
public class SchoolDeviceWebController {

    @Autowired
    private SchoolDeviceControllerClient schoolDeviceControllerClient;

    @RolesAllowed({"ADMIN"})
    @PostMapping("/addSchoolDevice")
    @ApiOperation(value = "增加设备信息（需学校admin权限）", response = Boolean.class)
    public Object addSchoolDevice(@Validated @RequestBody AddSchoolDeviceDto addSchoolDeviceDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        //设置操作人
        addSchoolDeviceDto.setSchoolCode(user.getSchoolCode());
        addSchoolDeviceDto.setSchoolId(user.getSchoolId());
        addSchoolDeviceDto.setOperator(user.getId());
        addSchoolDeviceDto.setOperatorName(user.getUserName());
        Wrapper wrapper = schoolDeviceControllerClient.addSchoolDevice(addSchoolDeviceDto);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/modifySchoolDevice")
    @ApiOperation(value = "修改设备信息（需学校admin权限）", response = Boolean.class)
    public Object modifySchoolDevice(@Validated @RequestBody ModifySchoolDeviceDto modifySchoolDeviceDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        //设置操作人
        modifySchoolDeviceDto.setOperator(user.getId());
        modifySchoolDeviceDto.setOperatorName(user.getUserName());
        modifySchoolDeviceDto.setSchoolId(user.getSchoolId());
        modifySchoolDeviceDto.setSchoolCode(user.getSchoolCode());
        Wrapper wrapper = schoolDeviceControllerClient.modifySchoolDevice(modifySchoolDeviceDto);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/delSchoolDeviceById")
    @ApiOperation(value = "删除设备信息（需学校admin权限）", response = Boolean.class)
    public Object delSchoolDeviceById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolDeviceControllerClient.delSchoolDeviceById(id);
        return wrapper;
    }

    @RolesAllowed({"ADMIN"})
    @PostMapping("/delBatchSchoolDeviceInIds")
    @ApiOperation(value = "批量删除设备信息（需学校admin权限）", response = Boolean.class)
    public Object delBatchSchoolDeviceInIds(@RequestParam("ids") List<Long> ids) {
        Wrapper wrapper = schoolDeviceControllerClient.delBatchSchoolDeviceInIds(ids);
        return wrapper;
    }

    @GetMapping("/findSchoolDeviceById")
    @ApiOperation(value = "id查询设备信息", response = SchoolDevice.class)
    public Object findSchoolDeviceById(@RequestParam("id") Long id) {
        Wrapper wrapper = schoolDeviceControllerClient.findSchoolDeviceById(id);
        return WrapMapper.ok(wrapper.getResult());
    }

    @PostMapping("/findSchoolDeviceInConditionPage")
    @ApiOperation(value = "设备信息根据条件分页查询", response = PageInfo.class)
    public Object findSchoolDeviceInConditionPage(@RequestBody SchoolDeviceQueryDto schoolDeviceQueryDto) {
        //获取当前用户
        SchoolUser user = SecurityUtils.getCurrentUser();
        schoolDeviceQueryDto.setSchoolId(user.getSchoolId());
        Wrapper<PageInfo<SchoolDeviceShowVo>> wrapper = schoolDeviceControllerClient.findSchoolDeviceInConditionPage(schoolDeviceQueryDto);
        return WrapMapper.ok(wrapper.getResult());
    }

    /**
     * 根据条件查询单个学校下的设备列表
     * @Author: WanMing
     * @Date: 2019/7/11 15:18
     */
    @RequestMapping(value = "/findSchoolDeviceBySchool",method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询单个学校下的设备列表",response = PageInfo.class)
    public Object findSchoolPosDeviceBySchool(@RequestBody SchoolPosDeviceQueryDto schoolPosDeviceQueryDto){
        return schoolDeviceControllerClient.findSchoolPosDeviceBySchool(schoolPosDeviceQueryDto);
    }

    /**
     * 根据收费部门id查询下面的消费机列表
     *
     * @Author: WanMing
     * @Date: 2019/7/11 18:46
     */
    @RequestMapping(value = "/querySchoolPosDeviceByChargeDept", method = RequestMethod.GET)
    @ApiOperation(value = "根据收费部门id查询下面的消费机列表", response = SchoolDevice.class)
    public Object querySchoolPosDeviceByChargeDept(@RequestParam Long deptId){
        return schoolDeviceControllerClient.querySchoolPosDeviceByChargeDept(deptId);
    }
}
