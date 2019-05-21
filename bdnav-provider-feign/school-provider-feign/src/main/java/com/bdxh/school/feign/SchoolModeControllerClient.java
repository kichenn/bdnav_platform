package com.bdxh.school.feign;


import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.entity.SchoolMode;
import com.bdxh.school.fallback.SchoolModeControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolModeControllerClientFallback.class)
public interface SchoolModeControllerClient {

    /**
     * @Description: 增加模式
     */
    @RequestMapping(value = "/schoolMode/addModesInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolModes(@RequestBody AddSchoolModeDto addSchoolModeDto);

    /**
     * @Description: 修改模式信息
     */
    @RequestMapping(value = "/schoolMode/updateModesInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolModes(@RequestBody ModifySchoolModeDto modifySchoolModeDto);

    /**
     * @Description: 删除模式信息
     */
    @RequestMapping(value = "/schoolMode/delModesById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchoolModesById(@RequestParam("id") Long id);

    /**
     * @Description: 根据id查询模式信息
     */
    @RequestMapping(value = "/schoolMode/getModesById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolMode> getModesById(@RequestParam("id") Long id);

    /**
     * @Description: 带条件的分页查询
     */
    @RequestMapping(value = "/schoolMode/findModesInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<QuerySchoolMode>> findModesInConditionPage(@RequestBody QuerySchoolMode querySchoolMode);


    /**
     * @Description: 查询模式列表查询
     */
    @RequestMapping(value = "/schoolMode/getModesAll", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolMode>> getModesAll();


    /**
     * @Description: 根据平台查询模式列表
     */
    @RequestMapping(value = "/schoolMode/getListByPlatform", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolMode>> getListByPlatform(@RequestParam("schoolId") Long schoolId,@RequestParam("platform") String platform);

}
