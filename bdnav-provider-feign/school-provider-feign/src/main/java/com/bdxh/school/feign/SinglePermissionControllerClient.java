package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.fallback.SchoolClassControllerClientFallback;
import com.bdxh.school.fallback.SinglePermissionControllerClientFallback;
import com.bdxh.school.vo.SchoolClassTreeVo;
import com.bdxh.school.vo.SinglePermissionShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校门禁单feign客户端
 * @Author: Kang
 * @Date: 2019/3/27 16:44
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SinglePermissionControllerClientFallback.class)
public interface SinglePermissionControllerClient {

    /**
     * @Description: 增加门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/singlePermission/addSinglePermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSinglePermission(@RequestBody AddSinglePermission addSinglePermission);

    /**
     * @Description: 修改门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/singlePermission/modifySinglePermission", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySinglePermission(@RequestBody ModifySinglePermission modifySinglePermission);

    /**
     * @Description: 删除门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/singlePermission/delSinglePermissionById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delSinglePermissionById(@RequestParam("id") Long id);

    /**
     * @Description: 批量删除门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/singlePermission/delBatchSinglePermissionByInIds", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBatchSinglePermissionByInIds(@RequestParam("ids") List<Long> ids);

    /**
     * @Description: id查询门禁单信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/singlePermission/findSinglePermissionById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SinglePermission> findSinglePermissionById(@RequestParam("id") Long id);

    /**
     * @Description: 门禁单人信息根据条件分页查询
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/singlePermission/findSinglePermissionInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SinglePermissionShowVo>> findSinglePermissionInConditionPage(@RequestBody SinglePermissionQueryDto singlePermissionQueryDto);
}
