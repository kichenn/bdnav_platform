package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.service.SchoolDeptService;
import com.bdxh.school.vo.SchoolDeptVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/schoolDept")
@Slf4j
@Validated
@Api(value = "学校部门关系", tags = "学校部门关系")
public class SchoolDeptController {

    //当前为父级等级节点（1为当前第一级节点，也就是父级节点）
    private static final Byte LEVEL = 1;

    @Autowired
    private SchoolDeptService schoolDeptService;

    /**
     * @Description: 学校id递归查询院校部门关系
     * @Author: Kang
     * @Date: 2019/2/27 15:55
     */
    @RequestMapping(value = "/findSchoolDeptTreeBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "学校部门树形结构关系", response = List.class)
    @ResponseBody
    public Object findSchoolDeptTreeBySchoolId(@RequestParam Long schoolId) {
        List<SchoolDept> schoolDeptParents = schoolDeptService.findSchoolParentDeptBySchoolId(schoolId, LEVEL);
        if (CollectionUtils.isEmpty(schoolDeptParents)) {
            return WrapMapper.error("该学校不存在院系关系，请检查！！！");
        }
        List<SchoolDeptVo> schoolClassDtos = schoolDeptParents.stream().map(e -> {
            SchoolDeptVo tempDto = new SchoolDeptVo();
            BeanUtils.copyProperties(e, tempDto);
            tempDto.setSchoolDeptVos(schoolDeptService.findSchoolDeptRelation(tempDto));
            return tempDto;
        }).collect(Collectors.toList());
        return WrapMapper.ok(schoolClassDtos);
    }

    /**
     * @Description: 根据id查询部门关系信息
     * @Author: Kang
     * @Date: 2019/2/27 17:02
     */
    @RequestMapping(value = "/findSchoolDeptById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询部门关系信息", response = List.class)
    @ResponseBody
    public Object findSchoolDeptById(@RequestParam Long id) {
        return WrapMapper.ok(schoolDeptService.findSchoolDeptById(id).orElse(new SchoolDept()));
    }

    /**
     * @Description: 所有学校部门关系信息（全部无条件）
     * @Author: Kang
     * @Date: 2019/2/27 17:02
     */
    @RequestMapping(value = "/findSchoolDeptAll", method = RequestMethod.GET)
    @ApiOperation(value = "所有学校部门关系信息（全部无条件）", response = List.class)
    @ResponseBody
    public Object findSchoolDeptAll() {
        return WrapMapper.ok(schoolDeptService.findSchoolDeptAll());
    }

    /**
     * @Description: 增加学校的部门关系信息
     * @Author: Kang
     * @Date: 2019/2/27 16:01
     */
    @RequestMapping(value = "/addSchoolDept", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校的部门关系信息", response = Boolean.class)
    @ResponseBody
    public Object addSchoolDept(@Validated @RequestBody SchoolDeptDto schoolDeptDto) {
        return WrapMapper.ok(schoolDeptService.addSchoolDept(schoolDeptDto));
    }

    /**
     * @Description: 修改学校的部门关系信息
     * @Author: Kang
     * @Date: 2019/2/27 16:10
     */
    @RequestMapping(value = "/modifySchoolDept", method = RequestMethod.PATCH)
    @ApiOperation(value = "修改学校的部门关系信息", response = Boolean.class)
    @ResponseBody
    public Object modifySchoolDept(@Validated @RequestBody SchoolDeptModifyDto schoolDeptDto) {
        return WrapMapper.ok(schoolDeptService.modifySchoolDept(schoolDeptDto));
    }

    /**
     * @Description: 根据id删除部门关系信息
     * @Author: Kang
     * @Date: 2019/2/27 16:12
     */
    @RequestMapping(value = "/delSchoolDeptById", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据id删除部门关系信息", response = Boolean.class)
    @ResponseBody
    public Object delSchoolDeptById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolDeptService.delSchoolDeptById(id));
    }

    /**
     * @Description: 根据ids批量删除学校部门信息
     * @Author: Kang
     * @Date: 2019/2/27 16:12
     */
    @RequestMapping(value = "/batchDelSchoolDeptInIds", method = RequestMethod.DELETE)
    @ApiOperation(value = "根据ids批量删除学校部门信息", response = Boolean.class)
    @ResponseBody
    public Object batchDelSchoolDeptInIds(@RequestBody List<Long> ids) {
        return WrapMapper.ok(schoolDeptService.batchDelSchoolDeptInIds(ids));
    }

    /**
     * @Description: 根据院校id 删除该学校部门所有关系
     * @Author: Kang
     * @Date: 2019/2/27 16:13
     */
    @RequestMapping(value = "/delSchoolDeptBySchoolId", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除该学校部门所有关系", response = Boolean.class)
    @ResponseBody
    public Object delSchoolDeptBySchoolId(@RequestParam("shcoolId") Long shcoolId) {
        return WrapMapper.ok(schoolDeptService.delSchoolDeptBySchoolId(shcoolId));
    }
}
