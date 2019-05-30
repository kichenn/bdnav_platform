package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddSchoolServicePermitDto;
import com.bdxh.school.dto.ModifySchoolServicePermitDto;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.fallback.SchoolServicePermitControllerClientFallBack;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * 学校服务许可权限Feign客户端
 * @author WanMing
 * @date 2019/5/28 19:42
 */
@Service
@FeignClient(value = "school-provider-cluster",fallback = SchoolServicePermitControllerClientFallBack.class)
public interface SchoolServicePermitControllerClient {

    /**
     * 增加学校服务许可权限
     * @Author: WanMing
     * @Date: 2019/5/28 11:11
     */
    @RequestMapping(value = "/schoolServicePermit/addSchoolServicePermit",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolServicePermit(@RequestBody AddSchoolServicePermitDto addSchoolServicePermitDto);


    /**
     * 修改学校服务许可权限
     * @Author: WanMing
     * @Date: 2019/5/28 14:26
     */
    @RequestMapping(value = "/schoolServicePermit/modifySchoolServicePermit",method = RequestMethod.POST)
    @ResponseBody
    Wrapper  modifySchoolServicePermit( @RequestBody ModifySchoolServicePermitDto modifySchoolServicePermitDto);



    /**
     * 分页查询学校服务许可权限信息
     * @Author: WanMing
     * @Date: 2019/5/28 17:07
     */
    @RequestMapping(value = "/schoolServicePermit/findSchoolServicePermitInConditionPage",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolServicePermitShowVo>> findSchoolServicePermitInConditionPage(@RequestBody SchoolServicePermitQueryDto schoolServicePermitQueryDto);

    /**
     * 根据id查询学校服务许可权限信息
     * @Author: WanMing
     * @Date: 2019/5/28 17:13
     */
    @RequestMapping(value = "/schoolServicePermit/findSchoolServicePermitById",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolServicePermit>   findSchoolServicePermitById(@RequestParam(name = "id") Long id);




}
