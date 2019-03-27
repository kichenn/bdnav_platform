package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolDeviceQueryDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.service.SchoolDeviceService;

import java.util.List;

/**
 * @Description: 学校门禁信息交互API
 * @Author: Kang
 * @Date: 2019/3/27 15:17
 */
@RestController
@RequestMapping("/schoolDevice")
@Slf4j
@Validated
@Api(value = "学校门禁信息交互API", tags = "学校门禁信息交互API")
public class SchoolDeviceController {

    @Autowired
    private SchoolDeviceService schoolDeviceService;


    /**
     * @Description: 增加门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/addSchoolDevice")
    @ApiOperation(value = "增加门禁信息", response = Boolean.class)
    public Object addSchoolDevice(@RequestBody SchoolDevice schoolDevice) {
        return WrapMapper.ok(schoolDeviceService.save(schoolDevice) > 0);
    }

    /**
     * @Description: 修改门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/modifySchoolDevice")
    @ApiOperation(value = "修改门禁信息", response = Boolean.class)
    public Object modifySchoolDevice(@RequestBody SchoolDevice schoolDevice) {
        return WrapMapper.ok(schoolDeviceService.update(schoolDevice) > 0);
    }

    /**
     * @Description: 删除门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/delSchoolDeviceById")
    @ApiOperation(value = "删除门禁信息", response = Boolean.class)
    public Object delSchoolDeviceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolDeviceService.deleteByKey(id) > 0);
    }

    /**
     * @Description: 批量删除门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/delBatchSchoolDeviceInIds")
    @ApiOperation(value = "批量删除门禁信息", response = Boolean.class)
    public Object delBatchSchoolDeviceInIds(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(schoolDeviceService.batchDelSchoolDeviceInIds(ids));
    }

    /**
     * @Description: id查询门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @GetMapping("/findSchoolDeviceById")
    @ApiOperation(value = "id查询门禁信息", response = SchoolDevice.class)
    public Object findSchoolDeviceById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolDeviceService.selectByKey(id));
    }

    /**
     * @Description: 门禁信息根据条件分页查询
     * @Author: Kang
     * @Date: 2019/3/27 14:09
     */
    @PostMapping("/findSchoolDeviceInConditionPage")
    @ApiOperation(value = "门禁信息根据条件分页查询", response = PageInfo.class)
    public Object findSchoolDeviceInConditionPage(@RequestBody SchoolDeviceQueryDto deviceQueryDto) {
        return WrapMapper.ok(schoolDeviceService.findSchoolDeviceInConditionPage(deviceQueryDto));
    }
}