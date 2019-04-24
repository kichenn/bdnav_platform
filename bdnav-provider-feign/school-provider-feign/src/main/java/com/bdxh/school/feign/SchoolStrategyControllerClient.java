package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolMode;
import com.bdxh.school.fallback.SchoolStrategyControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolStrategyControllerClientFallback.class)
public interface SchoolStrategyControllerClient {

    /**
     * @Description: 增加策略
     */
    @RequestMapping(value = "/schoolStrategy/addPolicyInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addPolicyInCondition(@RequestBody AddPolicyDto addPolicyDto);

    /**
     * @Description: 修改策略
     */
    @RequestMapping(value = "/schoolStrategy/updatePolicyInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper updatePolicyInCondition(@RequestBody ModifyPolicyDto modifyPolicyDto);

    /**
     * @Description: 删除策略信息
     */
    @RequestMapping(value = "/schoolStrategy/delDataById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchoolStrategyById(@RequestParam("id") Long id);

    /**
     * @Description: 带条件的分页查询
     */
    @RequestMapping(value = "/schoolStrategy/findPolicyInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<QuerySchoolStrategy>> findPolicyInConditionPage(@RequestBody QuerySchoolStrategy querySchoolStrategy);
}
