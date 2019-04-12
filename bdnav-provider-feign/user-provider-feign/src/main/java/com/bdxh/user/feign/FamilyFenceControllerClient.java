package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyFenceDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.UpdateFamilyFenceDto;
import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.fallback.FamliyFenceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-11 18:53
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = FamliyFenceFallback.class)
public interface FamilyFenceControllerClient {
    /**
     * 修改围栏信息
     * @param updateFamilyFenceDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/familyFence/updateFamilyFenceInfo",method = RequestMethod.POST)
    Wrapper updateFamilyFenceInfo(@Valid @RequestBody UpdateFamilyFenceDto updateFamilyFenceDto);

    /**
     *  删除围栏表信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/familyFence/removeFamilyFenceInfo",method = RequestMethod.POST)
    Wrapper removeFamilyFenceInfo(@RequestParam("schoolCode") String schoolCode,
                                        @RequestParam("cardNumber") String cardNumber,
                                        @RequestParam("id") String id);

    /**
     * 获取围栏表所有信息
     * @param familyFenceQueryDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/familyFence/getFamilyFenceInfos",method = RequestMethod.POST)
    Wrapper getFamilyFenceInfos(@Valid @RequestBody FamilyFenceQueryDto familyFenceQueryDto);

    /**
     * 获取围栏表单个信息
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/familyFence/getFamilyFenceInfo",method = RequestMethod.POST)
    Wrapper<FamilyFence> getFamilyFenceInfo(@RequestParam("schoolCode") String schoolCode,
                                            @RequestParam("cardNumber") String cardNumber,
                                            @RequestParam("id") String id);

    /**
     * 新增围栏设置
     * @param addFamilyFenceDto
     */
    @ResponseBody
    @RequestMapping(value = "/familyFence/addFamilyFenceInfo",method = RequestMethod.POST)
    Wrapper addFamilyFenceInfo(@Valid @RequestBody AddFamilyFenceDto addFamilyFenceDto);
}