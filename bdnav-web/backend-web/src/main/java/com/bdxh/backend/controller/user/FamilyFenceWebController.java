package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.feign.FamilyFenceControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-11 18:58
 **/
@Validated
@RestController
@RequestMapping("/familyFenceWeb")
@Slf4j
@Api(value = "家长围栏交互API", tags = "家长围栏交互API")
public class FamilyFenceWebController {
    @Autowired
    private FamilyFenceControllerClient familyFenceControllerClient;

    /**
     * 修改围栏表信息
     * @param updateFamilyFenceDto
     * @return
     */
    @ApiOperation(value="修改围栏信息")
    @RequestMapping(value = "/updateFamilyFenceInfo",method = RequestMethod.POST)
    public Object updateFamilyFenceInfo(@Valid @RequestBody UpdateFamilyFenceDto updateFamilyFenceDto){
        try {
            Wrapper wrapper = familyFenceControllerClient.updateFamilyFenceInfo(updateFamilyFenceDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }

    }

    /**
     *  删除围栏表信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation(value="删除围栏信息")
    @RequestMapping(value = "/removeFamilyFenceInfo",method = RequestMethod.POST)
    public Object removeFamilyFenceInfo(@RequestParam("schoolCode") String schoolCode,
                                        @RequestParam("cardNumber") String cardNumber,
                                        @RequestParam("id") String id){
        try {
            Wrapper wrapper = familyFenceControllerClient.removeFamilyFenceInfo(schoolCode,cardNumber,id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 获取围栏表所有信息
     * @param familyFenceQueryDto
     * @return
     */
    @ApiOperation(value="获取围栏表所有信息")
    @RequestMapping(value = "/getFamilyFenceInfos",method = RequestMethod.POST)
    public Object getFamilyFenceInfos(@Valid @RequestBody  FamilyFenceQueryDto familyFenceQueryDto){
        try {
            Wrapper wrapper=familyFenceControllerClient.getFamilyFenceInfos(familyFenceQueryDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 获取围栏表单个信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation(value="获取围栏表单个信息")
    @RequestMapping(value = "/getFamilyFenceInfo",method = RequestMethod.POST)
    public Object getFamilyFenceInfo(@RequestParam("schoolCode") String schoolCode,
                                     @RequestParam("cardNumber") String cardNumber,
                                     @RequestParam("id") String id){
        try {
            Wrapper wrapper=familyFenceControllerClient.getFamilyFenceInfo(schoolCode,cardNumber,id);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增围栏设置
     * @param addFamilyFenceDto
     */
    @ApiOperation(value="新增围栏设置")
    @RequestMapping(value = "/addFamilyFenceInfo",method = RequestMethod.POST)
    public Object addFamilyFenceInfo(@Valid @RequestBody AddFamilyFenceDto addFamilyFenceDto){
        try {
            Wrapper wrapper =  familyFenceControllerClient.addFamilyFenceInfo(addFamilyFenceDto);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}