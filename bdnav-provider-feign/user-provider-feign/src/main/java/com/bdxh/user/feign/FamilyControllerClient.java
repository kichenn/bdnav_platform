package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.Student;
import com.bdxh.user.fallback.FamilyControllerFallback;
import com.bdxh.user.vo.FamilyVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:18
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = FamilyControllerFallback.class)
public interface FamilyControllerClient {
    /**
     * 新增家长
     * @param addFamilyDto
     * @return
     */
    @RequestMapping(value = "/family/addFamily",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addFamily(@RequestBody AddFamilyDto addFamilyDto);

    /**
     * 删除家长
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value = "/family/removeFamily",method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeFamily(@RequestParam(name = "schoolCode")String schoolCode,@RequestParam(name = "cardNumber") String cardNumber);

    /**
     * 批量删除家长
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @RequestMapping(value = "/family/removeFamilys",method = RequestMethod.POST)
    @ResponseBody
    Wrapper removeFamilys(@RequestParam(name = "schoolCodes")String schoolCodes,@RequestParam(name = "cardNumbers") String cardNumbers);

    /**
     * 修改家长
     * @param updateFamilyDto
     * @return
     */
    @RequestMapping(value = "/family/updateFamily",method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateFamily( @RequestBody UpdateFamilyDto updateFamilyDto);

    /**
     * 查询家长
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @RequestMapping(value ="/family/queryFamilyInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<FamilyVo> queryFamilyInfo(@RequestParam(name = "schoolCode")String schoolCode, @RequestParam(name = "cardNumber")String cardNumber);

    /**
     * 分页查询家长
     * @param familyQueryDto
     * @return
     */
    @RequestMapping(value = "/family/queryFamilyListPage",method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryFamilyListPage(@RequestBody  FamilyQueryDto familyQueryDto);

    /**
     * 批量新增家长信息
     * @param familyList
     * @return
     */
    @RequestMapping(value = "/family/batchSaveFamilyInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchSaveFamilyInfo(@RequestBody List<AddFamilyDto> familyList);

    /**
     * 根据学校Code查询所有家长卡号
     * @param schoolCode
     * @return
     */
    @RequestMapping(value = "/family/queryFamilyCardNumberBySchoolCode", method = RequestMethod.POST)
    @ResponseBody
    Wrapper queryFamilyCardNumberBySchoolCode(@RequestParam("schoolCode") String schoolCode);
}