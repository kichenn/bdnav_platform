package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolClassDto;
import com.bdxh.school.dto.SchoolClassModifyDto;
import com.bdxh.school.dto.SchoolDeptDto;
import com.bdxh.school.dto.SchoolDeptModifyDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolDept;
import com.bdxh.school.fallback.SchoolControllerClientFallback;
import com.bdxh.school.vo.SchoolClassTreeVo;
import com.bdxh.school.vo.SchoolDeptTreeVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 学校feign客户端
 * @Author: Kang
 * @Date: 2019/3/12 10:43
 */
@Service
@FeignClient(value = "school-provider-clusterr", fallback = SchoolControllerClientFallback.class)
public interface SchoolDeptControllerClient {


    /**
     * @Description: 学校id递归查询院校部门关系
     * @Author: Kang
     * @Date: 2019/3/12 11:25
     */
    @RequestMapping(value = "/schoolDept/findSchoolDeptTreeBySchoolId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolDeptTreeVo>> findSchoolClassTreeBySchoolId(@RequestParam("schoolId") Long schoolId);

    /**
     * @Description: 根据id查询部门关系信息
     * @Author: Kang
     * @Date: 2019/3/12 11:25
     */
    @RequestMapping(value = "/schoolDept/findSchoolDeptById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolDept> findSchoolDeptById(@RequestParam("id") Long id);

    /**
     * @Description: 所有学校部门信息（全部无条件）
     * @Author: Kang
     * @Date: 2019/3/12 11:26
     */
    @RequestMapping(value = "/schoolDept/findSchoolDeptAll", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolDept>> findSchoolDeptAll();

    /**
     * @Description: 增加学校的部门关系信息
     * @Author: Kang
     * @Date: 2019/3/12 11:27
     */
    @RequestMapping(value = "/schoolDept/addSchoolDept", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolDept(@RequestBody SchoolDeptDto schoolDeptDto);

    /**
     * @Description: 修改学校的部门关系信息
     * @Author: Kang
     * @Date: 2019/3/12 11:27
     */
    @RequestMapping(value = "/schoolDept/modifySchoolDept", method = RequestMethod.PATCH)
    @ResponseBody
    Wrapper modifySchoolDept(@RequestBody SchoolDeptModifyDto schoolDeptDto);

    /**
     * @Description: 根据id删除部门关系信息
     * @Author: Kang
     * @Date: 2019/3/12 11:28
     */
    @RequestMapping(value = "/schoolDept/delSchoolDeptById", method = RequestMethod.DELETE)
    @ResponseBody
    Wrapper delSchoolDeptById(@RequestParam("id") Long id);

    /**
     * @Description: 根据ids批量删除学校部门信息
     * @Author: Kang
     * @Date: 2019/3/12 11:28
     */
    @RequestMapping(value = "/schoolDept/batchDelSchoolDeptInIds", method = RequestMethod.DELETE)
    @ResponseBody
    Wrapper batchDelSchoolDeptInIds(@RequestBody List<Long> ids);

    /**
     * @Description: 根据院校id 删除该学校部门所有关系
     * @Author: Kang
     * @Date: 2019/3/12 11:29
     */
    @RequestMapping(value = "/schoolDept/delSchoolDeptBySchoolId", method = RequestMethod.DELETE)
    @ResponseBody
    Wrapper delSchoolDeptBySchoolId(@RequestParam("schoolId") Long schoolId);

}
