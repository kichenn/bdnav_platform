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
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.service.FamilyService;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;


@Api(value ="家长信息管理接口API", tags = "家长信息管理接口API")
@RestController
@RequestMapping("/family")
@Validated
@Slf4j
public class FamilyController {
    @Autowired
    private FamilyService familyService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    /**
     * @Description： //新增家庭成员信息
     * @Date： 15:27 2019/3/7
     * @Param： familyDto, bindingResult
     **/
    @ApiOperation(value="新增家庭成员信息")
    @RequestMapping(value = "/addFamily",method = RequestMethod.POST)
    public Object addFamily(@Valid @RequestBody AddFamilyDto addFamilyDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Family family = BeanMapUtils.map(addFamilyDto, Family.class);
            if (familyService.isNullFamily(family.getSchoolCode(),family.getCardNumber())==null){
                family.setId(snowflakeIdWorker.nextId());
                familyService.save(family);
                return WrapMapper.ok();
            }
            return WrapMapper.error("当前学校已有相同cardNumber(卡号)");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value="删除家长信息")
    @RequestMapping(value = "/removeFamily",method = RequestMethod.POST)
    public Object removeFamily(@RequestParam(name = "schoolCode") @NotNull(message="学校Code不能为空")String schoolCode,
                               @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber){
        try{
            familyService.deleteFamilyInfo(schoolCode,cardNumber);
             return WrapMapper.ok();
        }catch (Exception e){
             e.printStackTrace();
             return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value="批量删除家长信息")
    @RequestMapping(value = "/removeFamilys",method = RequestMethod.POST)
    public Object removeFamilys(@RequestParam(name = "schoolCodes") @NotNull(message="学校Code不能为空")String schoolCodes,
                                @RequestParam(name = "cardNumbers") @NotNull(message="微校卡号不能为空")String cardNumbers
                               ){
        try{
                familyService.deleteBatchesFamilyInfo(schoolCodes,cardNumbers);
                return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    //修改家庭成员信息
    @ApiOperation(value="修改家长信息")
    @RequestMapping(value = "/updateFamily",method = RequestMethod.POST)
    public Object updateFamily(@Valid @RequestBody UpdateFamilyDto updateFamilyDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            familyService.updateFamily(updateFamilyDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * @Author： binzh
     * @Description： //修改时根据条件查询单个家长信息
     * @Date： 9:53 2019/3/8
     * @Param： [schoolCode, cardNumber]
     * @return： java.lang.Object
     **/
     @ApiOperation(value="查询家长信息")
     @RequestMapping(value ="/queryFamilyInfo",method = RequestMethod.POST)
     public Object queryFamilyInfo(@RequestParam(name = "schoolCode") @NotNull(message="学校Code不能为空")String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber) {
         try {
             FamilyVo familyVo=familyService.selectBysCodeAndCard(schoolCode,cardNumber);
             return WrapMapper.ok(familyVo) ;
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
    @ApiOperation(value="根据条件分页查询家长数据")
    @RequestMapping(value = "/queryFamilyListPage",method = RequestMethod.GET)
    public Object queryFamilyListPage(@ModelAttribute  FamilyQueryDto familyQueryDto) {
        try {
            // 封装分页之后的数据
            PageInfo<Family> family=familyService.getFamilyList(familyQueryDto);
            return WrapMapper.ok(family);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}
