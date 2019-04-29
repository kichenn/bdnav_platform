package com.bdxh.school.contoller;


import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.service.SchoolClassService;
import com.bdxh.school.vo.SchoolClassTreeVo;
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
    public Object findSchoolClassTreeBySchoolId(@RequestParam("schoolId") Long schoolId) {
        List<SchoolClass> schoolClassesParents = schoolClassService.findSchoolParentClassBySchoolId(schoolId);
        if (CollectionUtils.isEmpty(schoolClassesParents)) {
            return WrapMapper.error("该学校不存在院系关系，请检查！！！");
        }
        List<SchoolClassTreeVo> schoolClassDtos = schoolClassesParents.stream().map(e -> {
            SchoolClassTreeVo treeVo = new SchoolClassTreeVo();
            BeanUtils.copyProperties(e, treeVo);
            treeVo.setTitle(e.getName());
            treeVo.setCreateDate(e.getCreateDate());
            return treeVo;
        }).collect(Collectors.toList());
        //树状
        TreeLoopUtils<SchoolClassTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<SchoolClassTreeVo> result = treeLoopUtils.getTree(schoolClassDtos);
        return WrapMapper.ok(result);
    }

    /**
     * @Description: 根据id查询院系信息
     * @Author: Kang
     * @Date: 2019/2/27 17:07
     */
    @RequestMapping(value = "/findSchoolClassById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询部门信息", response = List.class)
    @ResponseBody
    public Object findSchoolClassById(@RequestParam("id") Long id) {
        return WrapMapper.ok(schoolClassService.findSchoolClassById(id).orElse(new SchoolClass()));
    }

    /**
     * @Description: 所有学校院系信息列表（全部无条件）
     * @Author: Kang
     * @Date: 2019/2/27 17:02
     */
    @RequestMapping(value = "/findSchoolClassAll", method = RequestMethod.GET)
    @ApiOperation(value = "所有学校院系信息列表（全部无条件）", response = List.class)
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
    @RequestMapping(value = "/modifySchoolClass", method = RequestMethod.POST)
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
    @RequestMapping(value = "/delSchoolClassById", method = RequestMethod.GET)
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
    @RequestMapping(value = "/batchDelSchoolClassInIds", method = RequestMethod.POST)
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
    @RequestMapping(value = "/delSchoolClassBySchoolId", method = RequestMethod.GET)
    @ApiOperation(value = "删除院校底下信息", response = Boolean.class)
    @ResponseBody
    public Object delSchoolClassBySchoolId(@RequestParam("schoolId") Long schoolId) {
        return WrapMapper.ok(schoolClassService.delSchoolClassBySchoolId(schoolId));
    }

    /**
     * 根据条件查询院校信息
     *
     * @param schoolClass
     * @return
     */
    @RequestMapping(value = "/findSchoolClassBySchoolClass", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询院校信息", response = Boolean.class)
    @ResponseBody
    public Object findSchoolClassBySchoolClass(@RequestBody SchoolClass schoolClass) {
        return WrapMapper.ok(schoolClassService.findSchoolClassBySchoolClass(schoolClass));
    }

    /**
     * @Description: 父id查询院系信息
     * @Author: Kang
     * @Date: 2019/3/22 18:41
     */
    @RequestMapping(value = "/findSchoolClassByParentId", method = RequestMethod.GET)
    @ApiOperation(value = "父id查询院系信息", response = SchoolClass.class)
    @ResponseBody
    public Object findSchoolClassByParentId(@RequestParam("parentId") Long parentId) {
        return WrapMapper.ok(schoolClassService.findSchoolByParentId(parentId));
    }

    /**
     * 查询单个学校院系路径和Ids
     *
     * @param schoolCode
     * @return
     */
    @RequestMapping(value = "/queryClassUrlBySchoolCode", method = RequestMethod.GET)
    @ApiOperation(value = "查询单个学校院系路径和Ids", response = SchoolClass.class)
    @ResponseBody
    public Object queryClassUrlBySchoolCode(@RequestParam("schoolCode") String schoolCode) {
        List<SchoolClass> list = schoolClassService.queryClassBySchoolCode(schoolCode);
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getParentId().equals("-1")) {
                list.get(i).setParentIds(list.get(i).getParentIds().trim().substring(1) + "," + list.get(i).getId());
                list.get(i).setThisUrl(list.get(i).getThisUrl().trim().substring(1));
            }
        }
        return WrapMapper.ok(list);
    }
}