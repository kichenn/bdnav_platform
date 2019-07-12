package com.bdxh.client.controller.school;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.school.dto.AddSchoolChargeDeptDto;
import com.bdxh.school.dto.AddSchoolPosDeviceChargeDto;
import com.bdxh.school.dto.ModifySchoolChargeDeptDto;
import com.bdxh.school.dto.QuerySchoolChargeDeptDto;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolChargeDeptControllerClient;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author WanMing
 * @date 2019/7/12 11:27
 */
@RestController
@RequestMapping("/schoolChargeDeptWeb")
@Slf4j
@Validated
@Api(value = "学校收费部门管理", tags = "学校收费部门管理API")
public class SchoolChargeDeptWebController {


    @Autowired
    private SchoolChargeDeptControllerClient schoolChargeDeptControllerClient;


    /**
     * 添加学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:28
     */
    @RequestMapping(value = "/addSchoolChargeDept", method = RequestMethod.POST)
    @ApiOperation(value = "添加学校的收费部门", response = Boolean.class)
    public Object addSchoolChargeDept(@Validated @RequestBody AddSchoolChargeDeptDto addSchoolChargeDeptDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        addSchoolChargeDeptDto.setOperator(user.getId());
        addSchoolChargeDeptDto.setOperatorName(user.getUserName());
        addSchoolChargeDeptDto.setSchoolId(user.getSchoolId());
        addSchoolChargeDeptDto.setSchoolCode(user.getSchoolCode());
        return schoolChargeDeptControllerClient.addSchoolChargeDept(addSchoolChargeDeptDto);
    }

    /**
     * 根据id删除学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:39
     */
    @RequestMapping(value = "delSchoolChargeDept", method = RequestMethod.GET)
    @ApiOperation(value = "根据id删除学校的收费部门", response = Boolean.class)
    public Object delSchoolChargeDept(@RequestParam("id") Long id) {
        return schoolChargeDeptControllerClient.delSchoolChargeDept(id);
    }

    /**
     * 修改学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:41
     */
    @RequestMapping(value = "/modifySchoolChargeDept", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校的收费部门", response = Boolean.class)
    public Object modifySchoolChargeDept(@Validated @RequestBody ModifySchoolChargeDeptDto modifySchoolChargeDeptDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        modifySchoolChargeDeptDto.setOperator(user.getId());
        modifySchoolChargeDeptDto.setOperatorName(user.getUserName());
        modifySchoolChargeDeptDto.setSchoolId(user.getSchoolId());
        modifySchoolChargeDeptDto.setSchoolCode(user.getSchoolCode());
        return schoolChargeDeptControllerClient.modifySchoolChargeDept(modifySchoolChargeDeptDto);
    }

    /**
     * 根据条件分页查询学校的收费部门列表
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:58
     */
    @RequestMapping(value = "/findSchoolChargeDeptByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件分页查询学校的收费部门列表", response = SchoolChargeDeptVo.class)
    public Object findSchoolChargeDeptByCondition(@RequestBody QuerySchoolChargeDeptDto querySchoolChargeDeptDto) {
        return schoolChargeDeptControllerClient.findSchoolChargeDeptByCondition(querySchoolChargeDeptDto);
    }

    /**
     * 根据学校查询收费部门列表
     *
     * @Author: WanMing
     * @Date: 2019/7/11 9:38
     */
    @RequestMapping(value = "/findSchoolChargeDeptBySchool", method = RequestMethod.GET)
    @ApiOperation(value = "根据学校查询收费部门列表", response = SchoolChargeDeptVo.class)
    public Object findSchoolChargeDeptBySchool(@RequestParam("schoolCode") String schoolCode) {
        return schoolChargeDeptControllerClient.findSchoolChargeDeptBySchool(schoolCode);
    }

    /**
     * 绑定消费机到学校的消费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:41
     */
    @RequestMapping(value = "/addSchoolPosDeviceCharge", method = RequestMethod.POST)
    @ApiOperation(value = "绑定消费机到学校的消费部门", response = Boolean.class)
    public Object addSchoolPosDeviceCharge(@Validated @RequestBody AddSchoolPosDeviceChargeDto addSchoolPosDeviceChargeDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        addSchoolPosDeviceChargeDto.setOperator(user.getId());
        addSchoolPosDeviceChargeDto.setOperatorName(user.getUserName());
        addSchoolPosDeviceChargeDto.setSchoolId(user.getSchoolId());
        addSchoolPosDeviceChargeDto.setSchoolCode(user.getSchoolCode());
        return schoolChargeDeptControllerClient.addSchoolPosDeviceCharge(addSchoolPosDeviceChargeDto);
    }

    /**
     * 根据id解绑学校部门的消费机
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:46
     */
    @RequestMapping(value = "/delSchoolPosDeviceCharge", method = RequestMethod.GET)
    @ApiOperation(value = "根据id解绑学校消费部门的消费机", response = Boolean.class)
    public Object delSchoolPosDeviceCharge(@RequestParam("id") Long id) {
        return schoolChargeDeptControllerClient.delSchoolPosDeviceCharge(id);
    }


    /**
     * 查询学校消费部门信息和POS机的数量
     *
     * @Author: WanMing
     * @Date: 2019/7/11 16:52
     */
    @RequestMapping(value = "/querySchoolChargeDeptAndPosNum", method = RequestMethod.POST)
    @ApiOperation(value = "查询学校消费部门信息和POS机的数量", response = SchoolChargeDeptVo.class)
    public Object querySchoolChargeDeptAndPosNum(@RequestBody QuerySchoolChargeDeptDto querySchoolChargeDeptDto) {
        return schoolChargeDeptControllerClient.querySchoolChargeDeptAndPosNum(querySchoolChargeDeptDto);
    }
}
