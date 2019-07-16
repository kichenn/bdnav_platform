package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolChargeDept;
import com.bdxh.school.entity.SchoolPosDeviceCharge;
import com.bdxh.school.enums.ChargeDetpTypeEnum;
import com.bdxh.school.service.SchoolPosDeviceChargeService;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.service.SchoolChargeDeptService;

import java.util.List;
import java.util.Map;

/**
 * @Description: 学校收费部门控制器
 * @Author WanMing
 * @Date 2019-07-10 18:12:31
 */
@RestController
@RequestMapping("/schoolChargeDept")
@Slf4j
@Validated
@Api(value = "学校收费部门管理", tags = "学校收费部门管理API")
public class SchoolChargeDeptController {

    @Autowired
    private SchoolChargeDeptService schoolChargeDeptService;

    @Autowired
    private SchoolPosDeviceChargeService schoolPosDeviceChargeService;



    /**
     * 添加学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:28
     */
    @RequestMapping(value = "/addSchoolChargeDept", method = RequestMethod.POST)
    @ApiOperation(value = "添加学校的收费部门", response = Boolean.class)
    public Object addSchoolChargeDept(@Validated @RequestBody AddSchoolChargeDeptDto addSchoolChargeDeptDto) {
        SchoolChargeDept schoolChargeDept = new SchoolChargeDept();
        BeanUtils.copyProperties(addSchoolChargeDeptDto, schoolChargeDept);
        try {
            return WrapMapper.ok(schoolChargeDeptService.save(schoolChargeDept) > 0);
        } catch (Exception e) {
            //schoolId 和 chargeDeptName 做联合唯一索引
            if (e instanceof DuplicateKeyException) {
                return WrapMapper.error("该部门名称已存在,请更换名称");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
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
        //查询该收费部门下是否有pos机
        List<SchoolPosDeviceCharge> posDeviceCharge = schoolPosDeviceChargeService.findSchoolChargeDeptByDeptId(id);
        if(CollectionUtils.isNotEmpty(posDeviceCharge)){
            return WrapMapper.error("该部门下有POS机在使用,请先解绑POS机");
        }
        return WrapMapper.ok(schoolChargeDeptService.deleteByKey(id) > 0);
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
        SchoolChargeDept schoolChargeDept = new SchoolChargeDept();
        BeanUtils.copyProperties(modifySchoolChargeDeptDto, schoolChargeDept);
        try {
            return WrapMapper.ok(schoolChargeDeptService.update(schoolChargeDept) > 0);
        } catch (Exception e) {
            //schoolId 和 chargeDeptName 做联合唯一索引
            if (e instanceof DuplicateKeyException) {
                return WrapMapper.error("该部门名称已存在,请更换名称");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
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
        return WrapMapper.ok(schoolChargeDeptService.findSchoolChargeDeptByCondition(querySchoolChargeDeptDto));
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
        return WrapMapper.ok(schoolChargeDeptService.findSchoolChargeDeptBySchool(schoolCode));
    }


    /**
     * 批量绑定消费机到学校的消费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:41
     */
    @RequestMapping(value = "/addSchoolPosDeviceCharge", method = RequestMethod.POST)
    @ApiOperation(value = "批量绑定消费机到学校的消费部门", response = Boolean.class)
    public Object addSchoolPosDeviceCharge(@Validated @RequestBody AddSchoolPosDeviceChargeDto addSchoolPosDeviceChargeDto) {
        try {
            return WrapMapper.ok(schoolPosDeviceChargeService.addSchoolPosDeviceCharge(addSchoolPosDeviceChargeDto));
        } catch (Exception e) {
            //deviceId 是唯一的 一台设备只能绑定一个部门
            if (e instanceof DuplicateKeyException) {
                return WrapMapper.error("此消费机已绑定,请解绑或者绑定其他消费机");
            }
            e.printStackTrace();
        }
        return WrapMapper.ok();
    }


    /**
     * 消费机换绑到学校的其他消费部门
     * @Author: WanMing
     * @Date: 2019/7/15 15:02
     */
    @RequestMapping(value = "/changeSchoolPosDevice", method = RequestMethod.POST)
    @ApiOperation(value = "消费机换绑到学校的其他消费部门", response = Boolean.class)
    public Object changeSchoolPosDevice(@RequestBody ModifySchoolPosDeviceDto modifySchoolPosDeviceDto){
        return WrapMapper.ok(schoolPosDeviceChargeService.changeSchoolPosDevice(modifySchoolPosDeviceDto));
    }

    /**
     * 根据pos机id解绑学校部门的消费机
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:46
     */
    @RequestMapping(value = "/delSchoolPosDeviceCharge", method = RequestMethod.GET)
    @ApiOperation(value = "根据pos机id解绑学校部门的消费机", response = Boolean.class)
    public Object delSchoolPosDeviceCharge(@RequestParam("deviceId") Long deviceId) {
        return WrapMapper.ok(schoolPosDeviceChargeService.delSchoolPosDeviceCharge(deviceId));
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
        return WrapMapper.ok(schoolChargeDeptService.querySchoolChargeDeptAndPosNum(querySchoolChargeDeptDto));
    }



}