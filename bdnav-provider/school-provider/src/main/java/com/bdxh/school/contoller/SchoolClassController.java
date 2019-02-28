package com.bdxh.school.contoller;


import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.service.SchoolClassService;
import com.bdxh.school.vo.SchoolClassVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 学校关系
 * @Author: Kang
 * @Date: 2019/2/26 17:24
 */
@Controller
@RequestMapping("/schoolClass")
@Slf4j
@Validated
@Api(value = "学校专业院校关系", tags = "学校专业院校关系")
public class SchoolClassController {

    //当前为父级等级节点（1为当前第一级节点，也就是父级节点）
    private static final Byte LEVEL = 1;

    @Autowired
    private SchoolClassService schoolClassService;

    /**
     * @Description: 学校id递归查询院校结构关系
     * @Author: Kang
     * @Date: 2019/2/26 17:26
     */
    @RequestMapping(value = "/findSchoolClassTreeBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "院校树形结构关系", response = List.class)
    @ResponseBody
    public Object findSchoolClassTreeBySchoolId(@RequestParam Long schoolId) {
        List<SchoolClass> schoolClassesParents = schoolClassService.findSchoolParentClassBySchoolId(schoolId, LEVEL);
        if (CollectionUtils.isEmpty(schoolClassesParents)) {
            return WrapMapper.error("该学校不存在院系关系，请检查！！！");
        }
        List<SchoolClassVo> schoolClassDtos = schoolClassesParents.stream().map(e -> {
            SchoolClassVo tempDto = new SchoolClassVo();
            BeanUtils.copyProperties(e, tempDto);
            tempDto.setSchoolClassVos(schoolClassService.findSchoolClassRelation(tempDto));
            return tempDto;
        }).collect(Collectors.toList());
        return WrapMapper.ok(schoolClassDtos);
    }

    /**
     * @Description: 根据id查询院系关系信息
     * @Author: Kang
     * @Date: 2019/2/27 17:07
     */
    @RequestMapping(value = "/findSchoolClassById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询部门关系信息", response = List.class)
    @ResponseBody
    public Object findSchoolClassById(@RequestParam Long id) {
        return WrapMapper.ok(schoolClassService.findSchoolClassById(id).orElse(new SchoolClass()));
    }

    /**
     * @Description: 所有学校院系关系信息（全部无条件）
     * @Author: Kang
     * @Date: 2019/2/27 17:02
     */
    @RequestMapping(value = "/findSchoolClassAll", method = RequestMethod.GET)
    @ApiOperation(value = "所有学校院系关系信息（全部无条件）", response = List.class)
    @ResponseBody
    public Object findSchoolClassAll() {
        return WrapMapper.ok(schoolClassService.findSchoolClassAll());
    }

    /**
     * @Description: 增加院校的院系关系
     * @Author: Kang
     * @Date: 2019/2/26 17:39
     */
    @RequestMapping(value = "/addSchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "增加院校结构关系", response = Boolean.class)
    @ResponseBody
    public Object addSchoolClass(@Validated @RequestBody SchoolClassDto schoolClassDto) {
        return WrapMapper.ok(schoolClassService.addSchoolClass(schoolClassDto));
    }

    /**
     * @Description: 修改院校结构关系
     * @Author: Kang
     * @Date: 2019/2/27 10:59
     */
    @RequestMapping(value = "/modifySchoolClass", method = RequestMethod.PATCH)
    @ApiOperation(value = "修改院校结构关系", response = Boolean.class)
    @ResponseBody
    public Object modifySchoolClass(@Validated @RequestBody SchoolClassModifyDto schoolClassDto) {
        return WrapMapper.ok(schoolClassService.modifySchoolClass(schoolClassDto));
    }

    /**
     * @Description: 根据id删除院校关系
     * @Author: Kang
     * @Date: 2019/2/27 12:00
     */
    @RequestMapping(value = "/delSchoolClassById", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除院校关系", response = Boolean.class)
    @ResponseBody
    public Object delSchoolClassById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolClassService.delSchoolClassById(id));
    }

    /**
     * @Description: 根据ids批量删除院校关系
     * @Author: Kang
     * @Date: 2019/2/27 12:01
     */
    @RequestMapping(value = "/batchDelSchoolClassInIds", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量删除院校关系", response = Boolean.class)
    @ResponseBody
    public Object batchDelSchoolClassInIds(@RequestBody List<Long> ids) {
        return WrapMapper.ok(schoolClassService.batchDelSchoolClassInIds(ids));
    }

    /**
     * @Description: 根据院校id 删除该院校底下所有关系
     * @Author: Kang
     * @Date: 2019/2/27 12:01
     */
    @RequestMapping(value = "/delSchoolClassBySchoolId", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除院校底下信息", response = Boolean.class)
    @ResponseBody
    public Object delSchoolClassBySchoolId(@RequestParam("shcoolId") Long shcoolId) {
        return WrapMapper.ok(schoolClassService.delSchoolClassBySchoolId(shcoolId));
    }

}