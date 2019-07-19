package com.bdxh.backend.controller.school;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.school.dto.*;
import com.bdxh.school.feign.SchoolChargeDeptControllerClient;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import com.bdxh.system.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**学校收费部门管理
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
        User user = SecurityUtils.getCurrentUser();
        addSchoolChargeDeptDto.setOperator(user.getId());
        addSchoolChargeDeptDto.setOperatorName(user.getUserName());
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
        User user = SecurityUtils.getCurrentUser();
        modifySchoolChargeDeptDto.setOperator(user.getId());
        modifySchoolChargeDeptDto.setOperatorName(user.getUserName());
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
     * 绑定消费机到学校的消费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:41
     */
    @RequestMapping(value = "/addSchoolPosDeviceCharge", method = RequestMethod.POST)
    @ApiOperation(value = "绑定消费机到学校的消费部门", response = Boolean.class)
    public Object addSchoolPosDeviceCharge(@Validated @RequestBody AddSchoolPosDeviceChargeDto addSchoolPosDeviceChargeDto) {
        User user = SecurityUtils.getCurrentUser();
        addSchoolPosDeviceChargeDto.setOperator(user.getId());
        addSchoolPosDeviceChargeDto.setOperatorName(user.getUserName());
        return schoolChargeDeptControllerClient.addSchoolPosDeviceCharge(addSchoolPosDeviceChargeDto);
    }

    /**
     * 消费机换绑到学校的其他消费部门
     * @Author: WanMing
     * @Date: 2019/7/15 15:02
     */
    @RequestMapping(value = "/changeSchoolPosDevice", method = RequestMethod.POST)
    @ApiOperation(value = "消费机换绑到学校的其他消费部门", response = Boolean.class)
    public Object changeSchoolPosDevice(@Validated @RequestBody ModifySchoolPosDeviceDto modifySchoolPosDeviceDto){
        User user = SecurityUtils.getCurrentUser();
        modifySchoolPosDeviceDto.setOperator(user.getId());
        modifySchoolPosDeviceDto.setOperatorName( user.getUserName());
        return schoolChargeDeptControllerClient.changeSchoolPosDevice(modifySchoolPosDeviceDto);


    }

    /**
     * 根据POS机id解绑学校消费部门的消费机
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:46
     */
    @RequestMapping(value = "/delSchoolPosDeviceCharge", method = RequestMethod.GET)
    @ApiOperation(value = "根据POS机id解绑学校消费部门的消费机", response = Boolean.class)
    public Object delSchoolPosDeviceCharge(@RequestParam("deviceId") Long deviceId) {
        return schoolChargeDeptControllerClient.delSchoolPosDeviceCharge(deviceId);
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
