package com.bdxh.school.contoller;

import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.SchoolOrgAddDto;
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

    /**
     * 查询单个学校的组织架构树形数据结构信息
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findSchoolOrgTreeInfoBySchoolId",method = RequestMethod.GET)
    @ApiOperation(value = "查询单个学校的组织架构树形数据结构信息")
    public Object findSchoolOrgTreeInfo(@NotNull(message = "学校id不能为空")@RequestParam("schoolId")Long schoolId){
        SchoolOrgQueryDto schoolOrgQueryDto=new SchoolOrgQueryDto();
        schoolOrgQueryDto.setSchoolId(schoolId);
        List<SchoolOrg> schoolOrgList=schoolOrgService.findAllSchoolOrgInfo(schoolOrgQueryDto);
        if(CollectionUtils.isEmpty(schoolOrgList)){
          return WrapMapper.wrap(200,"当前学校不存在组织架构信息");
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
        return WrapMapper.ok(schoolOrgService.findSchoolOrgInfo(id));
    }


    /**
     * 根据学校Id查询学生组织架构信息
     *
     * @param schoolId
     * @return
     */
    @RequestMapping(value = "/findClassOrg", method = RequestMethod.GET)
    @ApiOperation(value = "根据学校Id查询学生组织架构信息")
    public Object findClassOrg(@NotNull(message = "schoolId不能为空") @RequestParam("schoolId") Long schoolId) {
        List<SchoolOrg> schoolOrgList=schoolOrgService.findClassOrg(schoolId);
        if(CollectionUtils.isEmpty(schoolOrgList)){
            return WrapMapper.wrap(200,"当前学校不存在组织架构信息");
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
     * 根据ID删除组织架构信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/removeSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "根据ID删除组织架构信息")
    public Object removeSchoolOrgInfo(@NotNull(message = "id不能为空") @RequestParam("id") Long id) {
        return WrapMapper.ok(schoolOrgService.removeSchoolOrgInfo(id));
    }


    /**
     * 修改组织架构信息
     *
     * @param schoolOrgUpdateDto
     * @return
     */
    @RequestMapping(value = "/updateSchoolOrgInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改组织架构信息")
    public Object updateSchoolOrgInfo(@RequestBody SchoolOrgUpdateDto schoolOrgUpdateDto) {
        log.info("进控制器了");
        return WrapMapper.ok(schoolOrgService.updateSchoolOrgInfo(schoolOrgUpdateDto));
    }

    /**
     * 查询所有组织架构信息
     *
     * @return
     */
    @RequestMapping(value = "/findAllSchoolOrgInfo", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有组织架构信息")
    public Object findAllOrgInfo() {
        return WrapMapper.ok(schoolOrgService.findAllOrgInfo());
    }

    @RequestMapping(value = "/findClassOrgList", method = RequestMethod.GET)
    @ApiOperation(value = "查询院系组织架构信息")
    public Object findClassOrgList(@NotNull(message = "schoolId不能为空") @RequestParam("schoolId") Long schoolId){
        return WrapMapper.ok(schoolOrgService.findClassOrg(schoolId));
    }

    /**
     * 通过父ID查询学校组织架构信息
     * @param parentId
     * @return
     */
    @RequestMapping(value = "/findBySchoolOrgByParentId",method = RequestMethod.GET)
    @ApiOperation(value = "通过父ID查询学校组织架构信息")
    public Object findBySchoolOrgByParentId(@RequestParam("parentId") @NotNull(message = "父级ID不能为空") Long parentId) {
        return WrapMapper.ok(schoolOrgService.findBySchoolOrgByParentId(parentId));
    }

    /**
     * 新增组织架构
     * @param schoolOrgAddDto
     * @return
     */
    @RequestMapping(value = "/insertSchoolOrgInfo",method = RequestMethod.POST)
    @ApiOperation(value = "新增组织架构")
    public Object insertSchoolOrgInfo(@RequestBody SchoolOrgAddDto schoolOrgAddDto){
        return WrapMapper.ok(schoolOrgService.insertSchoolOrgInfo(schoolOrgAddDto));
    }
}