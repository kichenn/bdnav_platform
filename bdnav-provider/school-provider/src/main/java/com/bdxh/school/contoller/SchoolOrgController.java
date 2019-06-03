package com.bdxh.school.contoller;

import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SchoolOrgUpdateDto;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.vo.SchoolOrgTreeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.school.service.SchoolOrgService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-05-31 14:06:08
 */
@RestController
@RequestMapping("/schoolOrg")
@Slf4j
@Validated
@Api(value = "学校组织架构控制器", tags = "学校组织架构控制器")
public class SchoolOrgController {

    @Autowired
    private SchoolOrgService schoolOrgService;


    /**
     * 根据条件查询所有的学校组织架构信息
     *
     * @param schoolOrgQueryDto
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询所有的学校组织架构信息")
    public Object findAllSchoolOrgInfo(@RequestBody SchoolOrgQueryDto schoolOrgQueryDto) {
        return WrapMapper.ok(schoolOrgService.findAllSchoolOrgInfo(schoolOrgQueryDto));
    }

    //不分学院和部门
    @RequestMapping(value = "/findSchoolOrgTreeInfo",method = RequestMethod.GET)
    @ApiOperation(value = "查询单个学校的组织架构树形数据结构信息")
    public Object findSchoolOrgTreeInfo(@NotNull(message = "学校id不能为空")@RequestParam("schoolId")Long schoolId){
        SchoolOrgQueryDto schoolOrgQueryDto=new SchoolOrgQueryDto();
        schoolOrgQueryDto.setSchoolId(schoolId);
        List<SchoolOrg> schoolOrgList=schoolOrgService.findAllSchoolOrgInfo(schoolOrgQueryDto);
        if(CollectionUtils.isEmpty(schoolOrgList)){
          return WrapMapper.error("当前学校不存在组织架构信息");
        }
        List<SchoolOrgTreeVo> schoolOrgTreeVo = schoolOrgList.stream().map(e -> {
            SchoolOrgTreeVo treeVo = new SchoolOrgTreeVo();
            BeanUtils.copyProperties(e, treeVo);
            treeVo.setTitle(e.getOrgName());
            treeVo.setCreateDate(e.getCreateDate());
            return treeVo;
        }).collect(Collectors.toList());
        //树状
        TreeLoopUtils<SchoolOrgTreeVo> treeLoopUtils = new TreeLoopUtils<>();
        List<SchoolOrgTreeVo> result = treeLoopUtils.getTree(schoolOrgTreeVo);
        return WrapMapper.ok(result);
    }

    /**
     * 根据条件查询单个学校组织架构信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgInfo", method = RequestMethod.GET)
    @ApiOperation(value = "根据条件查询单个学校组织架构信息")
    public Object findSchoolOrgInfo(@NotNull(message = "id不能为空") @RequestParam("id") Long id) {
        return schoolOrgService.findSchoolOrgInfo(id);
    }


    /**
     * 根据学校Id查询学生组织架构信息
     *
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findClassOrg", method = RequestMethod.GET)
    @ApiOperation(value = "根据学校Id查询学生组织架构信息")
    public Object findClassOrg(@NotBlank(message = "schoolId不能为空") @RequestParam("schoolId") Long schoolId) {
        return schoolOrgService.findClassOrg(schoolId);
    }


    /**
     * 根据ID删除组织架构信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "根据ID删除组织架构信息")
    public Object removeSchoolOrgInfo(@NotNull(message = "id不能为空") @RequestParam("id") Long id) {
        return schoolOrgService.removeSchoolOrgInfo(id);
    }


    /**
     * 修改组织架构信息
     *
     * @param schoolOrgUpdateDto
     * @return
     */
    @RequestMapping(value = "/updateSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改组织架构信息")
    public Object updateSchoolOrgInfo(@Validated @RequestBody SchoolOrgUpdateDto schoolOrgUpdateDto) {
        return schoolOrgService.updateSchoolOrgInfo(schoolOrgUpdateDto);
    }

    /**
     * 查询所有组织架构信息
     *
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有组织架构信息")
    public Object findAllOrgInfo() {
        return schoolOrgService.findAllOrgInfo();
    }
}