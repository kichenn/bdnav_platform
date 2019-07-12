package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSchoolDeviceDto;
import com.bdxh.school.dto.ModifySchoolDeviceDto;
import com.bdxh.school.dto.SchoolDeviceQueryDto;
import com.bdxh.school.dto.SchoolPosDeviceQueryDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.service.SchoolDeviceService;

import java.util.Date;
import java.util.List;

/**
 * @Description: 学校设备信息交互API
 * @Author: Kang
 * @Date: 2019/3/27 15:17
 */
@RestController
@RequestMapping("/schoolDevice")
@Slf4j
@Validated
@Api(value = "学校设备信息交互", tags = "学校设备信息交互API")
public class SchoolDeviceController {

    @Autowired
    private SchoolDeviceService schoolDeviceService;


    /**
     * @Description: 增加设备信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/addSchoolDevice")
    @ApiOperation(value = "增加设备信息", response = Boolean.class)
    public Object addSchoolDevice(@Validated @RequestBody AddSchoolDeviceDto addSchoolDeviceDto) {
        //复制实体
        SchoolDevice schoolDevice = new SchoolDevice();
        BeanUtils.copyProperties(addSchoolDeviceDto, schoolDevice);
        //设置状态
        schoolDevice.setDeviceStatus(addSchoolDeviceDto.getDeviceStatusEnum().getKey());

        //判断该设备型号,设备类型是否已存在
        //判断该设备型号,设备类型是否已存在
        SchoolDevice schoolDevice1 = schoolDeviceService.findSchoolDeviceByIdOnModel(addSchoolDeviceDto.getDeviceId(), null);
        if (schoolDevice1 != null && !(schoolDevice1.getId().equals(schoolDevice.getId()))) {
            return WrapMapper.error("该设备编码已存在");
        }
        SchoolDevice schoolDevice2 = schoolDeviceService.findSchoolDeviceByIdOnModel(null, addSchoolDeviceDto.getDeviceModel());
        if (schoolDevice2 != null && !(schoolDevice2.getId().equals(schoolDevice.getId()))) {
            return WrapMapper.error("该设备型号已存在");
        }


        return WrapMapper.ok(schoolDeviceService.save(schoolDevice) > 0);
    }

    /**
     * @Description: 修改设备信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/modifySchoolDevice")
    @ApiOperation(value = "修改设备信息", response = Boolean.class)
    public Object modifySchoolDevice(@Validated @RequestBody ModifySchoolDeviceDto modifySchoolDeviceDto) {
        //复制实体
        SchoolDevice schoolDevice = new SchoolDevice();
        BeanUtils.copyProperties(modifySchoolDeviceDto, schoolDevice);
        schoolDevice.setUpdateDate(new Date());
        //设置状态
        schoolDevice.setDeviceStatus(modifySchoolDeviceDto.getDeviceStatusEnum().getKey());
        //判断该设备型号,设备类型是否已存在
        SchoolDevice schoolDevice1 = schoolDeviceService.findSchoolDeviceByIdOnModel(modifySchoolDeviceDto.getDeviceId(), null);
        if (schoolDevice1 != null && !(schoolDevice1.getId().equals(schoolDevice.getId()))) {
            return WrapMapper.error("该设备编码已存在");
        }
        SchoolDevice schoolDevice2 = schoolDeviceService.findSchoolDeviceByIdOnModel(null, modifySchoolDeviceDto.getDeviceModel());
        if (schoolDevice2 != null && !(schoolDevice2.getId().equals(schoolDevice.getId()))) {
            return WrapMapper.error("该设备型号已存在");
        }

        return WrapMapper.ok(schoolDeviceService.update(schoolDevice) > 0);
    }

    /**
     * @Description: 删除设备信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/delSchoolDeviceById")
    @ApiOperation(value = "删除设备信息", response = Boolean.class)
    public Object delSchoolDeviceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolDeviceService.deleteByKey(id) > 0);
    }

    /**
     * @Description: 批量删除设备信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/delBatchSchoolDeviceInIds")
    @ApiOperation(value = "批量删除设备信息", response = Boolean.class)
    public Object delBatchSchoolDeviceInIds(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(schoolDeviceService.batchDelSchoolDeviceInIds(ids));
    }

    /**
     * @Description: id查询设备信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @GetMapping("/findSchoolDeviceById")
    @ApiOperation(value = "id查询设备信息", response = SchoolDevice.class)
    public Object findSchoolDeviceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolDeviceService.selectByKey(id));
    }

    /**
     * @Description: 设备信息根据条件分页查询
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/findSchoolDeviceInConditionPage")
    @ApiOperation(value = "设备信息根据条件分页查询", response = PageInfo.class)
    public Object findSchoolDeviceInConditionPage(@RequestBody SchoolDeviceQueryDto deviceQueryDto) {
        return WrapMapper.ok(schoolDeviceService.findSchoolDeviceInConditionPage(deviceQueryDto));
    }

    /**
     * 根据条件查询单个学校下的设备列表
     * @Author: WanMing
     * @Date: 2019/7/11 15:18
     */
    @RequestMapping(value = "/findSchoolDeviceBySchool",method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询单个学校下的设备列表",response = PageInfo.class)
    public Object findSchoolPosDeviceBySchool(@RequestBody SchoolPosDeviceQueryDto schoolPosDeviceQueryDto){
        return WrapMapper.ok(schoolDeviceService.findSchoolPosDeviceBySchool(schoolPosDeviceQueryDto));
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
        return WrapMapper.ok(schoolDeviceService.querySchoolPosDeviceByChargeDept(deptId));
    }
}