/**
 * Copyright (C), 2019-2019
 * FileName: FamilyController
 * Author:   bdxh
 * Date:     2019/2/26 16:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * binzh          2019/2/26 16:11           版本号       家庭成员信息控制层
 */
package com.bdxh.user.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.FamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.service.FamilyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *家庭成员控制Controller类
 */

@RestController
@RequestMapping("/family")
@Validated
@Slf4j
public class FamilyController {
    @Autowired
    private FamilyService familyService;
    //新增家庭成员信息
    @RequestMapping(value = "/addFamily",method = RequestMethod.POST)
    public Object addFamily(@Valid @RequestBody FamilyDto familyDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Family family = BeanMapUtils.map(familyDto, Family.class);
            familyService.save(family);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    //根据ID删除家庭成员信息
    @RequestMapping(value = "/removeFamily",method = RequestMethod.POST)
    @Transactional
    public Object removeFamily(@RequestParam(name = "id") @NotNull(message = "家长id不能为空") String id){
       String fid[]=id.split(",");
        try{
            familyService.deleteFamilys(id);
             return WrapMapper.ok();
        }catch (Exception e){
             e.printStackTrace();
             return WrapMapper.error(e.getMessage());
        }
    }



    //修改家庭成员信息
    @RequestMapping(value = "/updateFamily",method = RequestMethod.POST)
    public Object updateFamily(@Valid @RequestBody FamilyDto familyDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Family family = BeanMapUtils.map(familyDto, Family.class);
            familyService.update(family);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
    * 修改时根据Id查询
    * @param id
    * @return family
    */
     @RequestMapping(value ="/queryFamilyById",method = RequestMethod.POST)
     public Object queryFamilyListPage(@Valid @RequestBody  Long id) {
         try {

             return familyService.selectByKey(id);
         } catch (Exception e) {
             e.printStackTrace();
             return WrapMapper.error(e.getMessage());
         }
     }
    /**
     * 根据条件分页查找
     * @param familyQueryDto
     * @return PageInfo<Family>
     */
    @RequestMapping(value = "/queryFamilyListPage",method = RequestMethod.GET)
    public Object queryFamilyListPage(@Valid @RequestBody  FamilyQueryDto familyQueryDto) {
        try {
            // 封装分页之后的数据
            return familyService.getFamilyList(familyQueryDto);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
