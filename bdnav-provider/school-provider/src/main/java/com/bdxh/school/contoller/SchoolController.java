package com.bdxh.school.contoller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.configration.redis.RedisCache;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.vo.SchoolVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 学校相关信息
 * @Author: Kang
 * @Date: 2019/2/25 16:15
 */
@Controller
@RequestMapping("/school")
@Slf4j
@Validated
@Api(value = "学校控制器", tags = "学校")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;


    /**
     * @Description: 增加学校
     * @Author: Kang
     * @Date: 2019/2/25 16:56
     */
    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校", response = Boolean.class)
    @ResponseBody
    public Object addSchool(@Valid @RequestBody SchoolDto schoolDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean result = schoolService.addSchool(schoolDto);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * @Description: 修改学校信息
     * @Author: Kang
     * @Date: 2019/2/26 11:03
     */
    @RequestMapping(value = "/modifySchoolInfo", method = RequestMethod.PATCH)
    @ApiOperation(value = "修改学校信息", response = Boolean.class)
    @ResponseBody
    public Object modifySchoolInfo(@Valid @RequestBody ModifySchoolDto school, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean result = schoolService.modifySchool(school);
            //。。。。。更新缓存
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * @Description: 删除学校信息
     * @Author: Kang
     * @Date: 2019/2/26 11:11
     */
    @RequestMapping(value = "/delSchool", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除学校信息", response = Boolean.class)
    @ResponseBody
    public Object delSchool(@RequestParam("id") Long id) {
        Boolean result = schoolService.delSchool(id);
        //。。。。更新缓存
        return WrapMapper.ok(result);
    }


    /**
     * @Description: id查询学校信息
     * @Author: Kang
     * @Date: 2019/2/26 10:04
     */
    @RequestMapping(value = "/findSchoolById", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校详情", response = School.class)
    @ResponseBody
    public Object findSchoolById(@RequestParam("schoolId") Long id) {
        School school = schoolService.findSchoolById(id).orElse(new School());
        return WrapMapper.ok(school);
    }

    /**
     * @Description: 分页查询学校信息
     * @Author: Kang
     * @Date: 2019/2/26 10:18
     */
    @RequestMapping(value = "/findSchoolList", method = RequestMethod.POST)
    @ApiOperation(value = "查询学校", response = SchoolVo.class)
    @ResponseBody
    public Object findSchoolList(@Valid @RequestBody SchoolQueryDto schoolQueryDto) {
        //符合条件的学校信息
        List<School> Roles = schoolService.findSchools(schoolQueryDto).orElse(new ArrayList<>());
        return WrapMapper.ok(Roles);
    }


}
