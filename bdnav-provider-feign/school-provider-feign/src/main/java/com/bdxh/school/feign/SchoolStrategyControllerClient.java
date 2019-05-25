package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolMode;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.fallback.SchoolStrategyControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * @Description: 验证学校策略优先级
     */
    @RequestMapping(value = "/schoolStrategy/getByPriority", method = RequestMethod.GET)
    @ResponseBody
    Wrapper getByPriority(@RequestParam("schoolCode") String schoolCode, @RequestParam("priority")Integer priority);


    /**
     * @Description: 查询全部策略
     */
    @RequestMapping(value = "/schoolStrategy/findAllStrategy", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<SchoolStrategy>> findAllStrategy();


    /**
     * @Description: 根据schoolcode查询全部策略列表
     */
    @RequestMapping(value = "/schoolStrategy/getStrategyList", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<SchoolStrategy>> getStrategyList(@RequestBody QuerySchoolStrategy querySchoolStrategy);


    /**
     * @Description: 修改推送状态
     */
    @RequestMapping(value = "/schoolStrategy/updatePolicyPushStatus", method = RequestMethod.GET)
    @ResponseBody
    Wrapper updatePolicyPushStatus(@RequestParam("id") Long id,@RequestParam("pushState") Byte pushState);

    /**
     * @Description: 根据id查询策略信息
     */
    @RequestMapping(value = "/schoolStrategy/findStrategyById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<QuerySchoolStrategy> findStrategyById(@RequestParam("id") Long id);


}
