package com.bdxh.school.feign;


import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddGroupPermissionDto;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifyGroupPermissionDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.fallback.SchoolModeControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(value = "/schoolMode/delModesById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delSchoolModesById(@RequestParam("id") Long id);
}
