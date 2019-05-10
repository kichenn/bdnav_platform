package com.bdxh.client.controller.user;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-18 15:14
 **/
@RestController
@RequestMapping("/fenceAlarmWeb")
@Slf4j
@Validated
@Api(value = "家长围栏警报API", tags = "家长围栏警报API")
public class FenceAlarmWebController {

    @Autowired
    private FenceAlarmControllerClient fenceAlarmControllerClient;
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    /**
     * 查询所有
     * @param fenceAlarmQueryDto
     * @return
     */
    @ApiOperation("查询所有围栏警报接口")
    @RequestMapping(value = "/getAllFenceAlarmInfos",method = RequestMethod.POST)
    public Object getAllFenceAlarmInfos(@Valid @RequestBody FenceAlarmQueryDto fenceAlarmQueryDto, BindingResult bindingResult){
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            SchoolUser schoolUser= SecurityUtils.getCurrentUser();
            fenceAlarmQueryDto.setSchoolCode(schoolUser.getSchoolCode());
            return WrapMapper.ok(fenceAlarmControllerClient.getAllFenceAlarmInfos(fenceAlarmQueryDto).getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation("查询单个围栏警报接口")
    @RequestMapping(value="/getFenceAlarmInfo",method = RequestMethod.POST)
    public Object getFenceAlarmInfo(
                                    @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
                                    @RequestParam(name="id") @NotNull(message = "id不能为空")  String id){
        try {
            SchoolUser schoolUser= SecurityUtils.getCurrentUser();
            return WrapMapper.ok(fenceAlarmControllerClient.getFenceAlarmInfo(schoolUser.getSchoolCode(),cardNumber,id).getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除单个
     * @param id
     * @param cardNumber
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation("删除单个围栏警报接口")
    @RequestMapping(value="/removeFenceAlarmInfo",method = RequestMethod.GET)
    public Object removeFenceAlarmInfo(@RequestParam("id")String id,@RequestParam("cardNumber") String  cardNumber){
        try {
            SchoolUser schoolUser= SecurityUtils.getCurrentUser();
            return fenceAlarmControllerClient.removeFenceAlarmInfo(id,schoolUser.getSchoolCode(),cardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @param cardNumbers
     */
    @ApiOperation("批量删除围栏警报接口")
    @RequestMapping(value="/batchRemoveFenceAlarmInfo",method = RequestMethod.POST)
    public Object batchRemoveFenceAlarmInfo(@RequestParam("ids")String ids,@RequestParam("cardNumbers")String  cardNumbers){
        try {
            SchoolUser schoolUser= SecurityUtils.getCurrentUser();
            int idLength=ids.split(",").length;
            StringBuffer schoolCodes=new StringBuffer();
            for (int i = 0; i < idLength; i++) {
                if(idLength==(i+1)){
                    schoolCodes.append(schoolUser.getSchoolCode());
                }else{
                    schoolCodes.append(schoolUser.getSchoolCode()+",");
                }
            }
            return fenceAlarmControllerClient.batchRemoveFenceAlarmInfo(ids,schoolCodes.toString(),cardNumbers);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改围栏警报接口
     * @param updateFenceAlarmDto
     */
    @ApiOperation("修改围栏警报接口")
    @RequestMapping(value="/updateFenceAlarmInfo",method = RequestMethod.POST)
    public Object updateFenceAlarmInfo(@Valid @RequestBody UpdateFenceAlarmDto updateFenceAlarmDto, BindingResult bindingResult){
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            SchoolUser schoolUser= SecurityUtils.getCurrentUser();
            updateFenceAlarmDto.setSchoolCode(schoolUser.getSchoolCode());
            return  fenceAlarmControllerClient.updateFenceAlarmInfo(updateFenceAlarmDto);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增围栏警报接口
     * @param addFenceAlarmDto
     */
    @ApiOperation("新增围栏警报接口")
    @RequestMapping(value="/insertFenceAlarmInfo",method = RequestMethod.POST)
    public Object insertFenceAlarmInfo(@Valid @RequestBody AddFenceAlarmDto addFenceAlarmDto, BindingResult bindingResult){
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            SchoolUser schoolUser= SecurityUtils.getCurrentUser();
            SchoolInfoVo school= schoolControllerClient.findSchoolById(schoolUser.getSchoolId()).getResult();
            addFenceAlarmDto.setSchoolCode(school.getSchoolCode());
            addFenceAlarmDto.setSchoolId(school.getId());
            addFenceAlarmDto.setSchoolName(school.getSchoolName());
            return fenceAlarmControllerClient.insertFenceAlarmInfo(addFenceAlarmDto);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}