package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.fallback.SchoolChargeDeptControllerClientFallback;
import com.bdxh.school.vo.BaseEchartsVo;
import com.bdxh.school.vo.SchoolChargeDeptVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author WanMing
 * @date 2019/7/12 11:06
 */
@Service
@FeignClient(name = "school-provider-cluster",fallback = SchoolChargeDeptControllerClientFallback.class)
public interface SchoolChargeDeptControllerClient {

    /**
     * 添加学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:28
     */
    @RequestMapping(value = "/schoolChargeDept/addSchoolChargeDept", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolChargeDept(@Validated @RequestBody AddSchoolChargeDeptDto addSchoolChargeDeptDto);


    /**
     * 根据id删除学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:39
     */
    @RequestMapping(value = "/schoolChargeDept/delSchoolChargeDept", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchoolChargeDept(@RequestParam("id") Long id);


    /**
     * 修改学校的收费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:41
     */
    @RequestMapping(value = "/schoolChargeDept/modifySchoolChargeDept", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolChargeDept(@Validated @RequestBody ModifySchoolChargeDeptDto modifySchoolChargeDeptDto);

    /**
     * 根据条件分页查询学校的收费部门列表
     *
     * @Author: WanMing
     * @Date: 2019/7/10 18:58
     */
    @RequestMapping(value = "/schoolChargeDept/findSchoolChargeDeptByCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolChargeDeptVo>> findSchoolChargeDeptByCondition(@RequestBody QuerySchoolChargeDeptDto querySchoolChargeDeptDto);

    /**
     * 根据学校查询收费部门列表
     *
     * @Author: WanMing
     * @Date: 2019/7/11 9:38
     */
    @RequestMapping(value = "/schoolChargeDept/findSchoolChargeDeptBySchool", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolChargeDeptVo>> findSchoolChargeDeptBySchool(@RequestParam("schoolCode") String schoolCode,@RequestParam("chargeDeptType") Byte chargeDeptType);

    /**
     * 批量绑定消费机到学校的消费部门
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:41
     */
    @RequestMapping(value = "/schoolChargeDept/addSchoolPosDeviceCharge", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolPosDeviceCharge(@Validated @RequestBody AddSchoolPosDeviceChargeDto addSchoolPosDeviceChargeDto);


    /**
     * 消费机换绑到学校的其他消费部门
     * @Author: WanMing
     * @Date: 2019/7/15 15:02
     */
    @RequestMapping(value = "/schoolChargeDept/changeSchoolPosDevice", method = RequestMethod.POST)
    @ResponseBody
    Wrapper changeSchoolPosDevice(@RequestBody ModifySchoolPosDeviceDto modifySchoolPosDeviceDto);

    /**
     * 根据pos机id解绑学校部门的消费机
     *
     * @Author: WanMing
     * @Date: 2019/7/11 11:46
     */
    @RequestMapping(value = "/schoolChargeDept/delSchoolPosDeviceCharge", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchoolPosDeviceCharge(@RequestParam("deviceId") Long deviceId);

    /**
     * 查询学校消费部门信息和POS机的数量
     *
     * @Author: WanMing
     * @Date: 2019/7/11 16:52
     */
    @RequestMapping(value = "/schoolChargeDept/querySchoolChargeDeptAndPosNum", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolChargeDeptVo>> querySchoolChargeDeptAndPosNum(@RequestBody QuerySchoolChargeDeptDto querySchoolChargeDeptDto);

    /**
     * 查询学校消费部门数量和POS机的数量
     *
     * @Author: WanMing
     * @Date: 2019/7/11 16:52
     */
    @RequestMapping(value = "/schoolChargeDept/queryChargeDeptNumAndPosNum", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<BaseEchartsVo>> queryChargeDeptNumAndPosNum(@RequestParam(value = "schoolCode",required = false)String schoolCode);
}
