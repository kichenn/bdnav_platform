package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.GroupPermission;
import com.bdxh.school.entity.SchoolDevice;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.fallback.SchoolDeviceControllerClientFallback;
import com.bdxh.school.fallback.SinglePermissionControllerClientFallback;
import com.bdxh.school.vo.ChargeDeptAndDeviceVo;
import com.bdxh.school.vo.SchoolDeviceShowVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校门禁信息feign客户端
 * @Author: Kang
 * @Date: 2019/3/27 16:44
 */
@Service
@FeignClient(value = "school-provider-cluster", fallback = SchoolDeviceControllerClientFallback.class)
public interface SchoolDeviceControllerClient {

    /**
     * @Description: 增加门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/schoolDevice/addSchoolDevice", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolDevice(@RequestBody AddSchoolDeviceDto addSchoolDeviceDto);

    /**
     * @Description: 修改门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/schoolDevice/modifySchoolDevice", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolDevice(@RequestBody ModifySchoolDeviceDto modifySchoolDeviceDto);

    /**
     * @Description: 删除门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/schoolDevice/delSchoolDeviceById", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delSchoolDeviceById(@RequestParam("id") Long id);

    /**
     * @Description: 批量删除门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/schoolDevice/delBatchSchoolDeviceInIds", method = RequestMethod.POST)
    @ResponseBody
    Wrapper delBatchSchoolDeviceInIds(@RequestParam("ids") List<Long> ids);

    /**
     * @Description: id查询门禁信息
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/schoolDevice/findSchoolDeviceById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolDevice> findSchoolDeviceById(@RequestParam("id") Long id);

    /**
     * @Description: 门禁信息根据条件分页查询
     * @Author: Kang
     * @Date: 2019/3/27 16:44
     */
    @RequestMapping(value = "/schoolDevice/findSchoolDeviceInConditionPage", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolDeviceShowVo>> findSchoolDeviceInConditionPage(@RequestBody SchoolDeviceQueryDto schoolDeviceQueryDto);

    /**
     * 根据条件查询单个学校下可用设备列表
     * @Author: WanMing
     * @Date: 2019/7/11 15:18
     */
    @RequestMapping(value = "/schoolDevice/findSchoolDeviceBySchool",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolDevice>> findSchoolPosDeviceBySchool(@RequestBody SchoolPosDeviceQueryDto schoolPosDeviceQueryDto);

    /**
     * 根据收费部门id查询下面的消费机列表
     *
     * @Author: WanMing
     * @Date: 2019/7/11 18:46
     */
    @RequestMapping(value = "/schoolDevice/querySchoolPosDeviceByChargeDept", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolDevice>> querySchoolPosDeviceByChargeDept(@RequestBody SchoolDeviceAndChargeDeptQueryDto deptQueryDto);

    /**
     * 查询收费部门和pos机的关系列表
     * @Author: WanMing
     * @Date: 2019/7/16 10:12
     */
    @RequestMapping(value = "/schoolDevice/findChargeDeptAndDeviceRelation", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<ChargeDeptAndDeviceVo>> findChargeDeptAndDeviceRelation(@RequestParam(value = "schoolCode",required = false) String schoolCode);
}
