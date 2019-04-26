package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.fallback.FamilyStudentControllerFallback;
import com.bdxh.user.vo.FamilyStudentVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:18
 **/
@Service
@FeignClient(value = "user-provider-cluster", fallback = FamilyStudentControllerFallback.class)
public interface FamilyStudentControllerClient {
    /**
     * 绑定学生
     * @param addFamilyStudentDto
     * @return
     */
    @RequestMapping(value = "/familyStudent/bindingStudent",method = RequestMethod.POST)
    @ResponseBody
    Wrapper bindingStudent(@RequestBody AddFamilyStudentDto addFamilyStudentDto);

    /**
     * 删除关系
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/familyStudent/removeFamilyOrStudent",method = RequestMethod.GET)
    Wrapper removeFamilyOrStudent(@RequestParam(name = "schoolCode")String schoolCode,@RequestParam(name = "cardNumber")String cardNumber,@RequestParam(name = "id")String id);

    /**
     * 查询所有关系
     * @param familyStudentQueryDto
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/familyStudent/queryAllFamilyStudent",method =RequestMethod.POST)
    Wrapper<PageInfo<FamilyStudentVo>>  queryAllFamilyStudent(@RequestBody FamilyStudentQueryDto familyStudentQueryDto);


}