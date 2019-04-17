package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolFenceDto;
import com.bdxh.school.dto.ModifySchoolFenceDto;
import com.bdxh.school.dto.SchoolFenceQueryDto;
import com.bdxh.school.entity.SchoolFence;
import com.bdxh.school.fallback.SchoolDeviceControllerClientFallback;
import com.bdxh.school.fallback.SchoolFenceControllerClientFallback;
import com.bdxh.school.vo.SchoolFenceShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolFenceControllerClientFallback.class)
public interface SchoolFenceControllerClient {

    /**
     * 增加学校围栏
     */
    @RequestMapping(value = "/schoolFence/addFence", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addFence(@Validated @RequestBody AddSchoolFenceDto addSchoolFenceDto);

    /**
     * 修改学校围栏
     */
    @RequestMapping(value = "/schoolFence/modifyFence", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyFence(@Validated @RequestBody ModifySchoolFenceDto modifySchoolFenceDto);

    /**
     * 删除学校围栏
     */
    @RequestMapping(value = "/schoolFence/delFenceById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delFenceById(@RequestParam("id") Long id);

    /**
     * id查询学校围栏
     */
    @RequestMapping(value = "/schoolFence/findFenceById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolFence> findFenceById(@RequestParam("id") Long id);

    /**
     * 分页学校围栏查询
     */
    @RequestMapping(value = "/schoolFence/findFenceInConditionPaging", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolFenceShowVo>> findFenceInConditionPaging(@Validated @RequestBody SchoolFenceQueryDto schoolFenceQueryDto);

}