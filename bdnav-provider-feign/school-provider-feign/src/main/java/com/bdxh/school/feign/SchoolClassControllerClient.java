package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.fallback.SchoolClassControllerClientFallback;
import com.bdxh.school.vo.SchoolClassTreeVo;
import io.swagger.annotations.ApiOperation;
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
@FeignClient(value = "school-provider-cluster", fallback = SchoolClassControllerClientFallback.class)
public interface SchoolClassControllerClient {


    /**
     * @Description: 学校id递归查询院校结构关系
     * @Author: Kang
     * @Date: 2019/3/12 11:14
     */
    @RequestMapping(value = "/schoolClass/findSchoolClassTreeBySchoolId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolClassTreeVo>> findSchoolClassTreeBySchoolId(@RequestParam("schoolId") Long schoolId);

    /**
     * @Description: 根据id查询院系信息
     * @Author: Kang
     * @Date: 2019/3/12 11:14
     */
    @RequestMapping(value = "/schoolClass/findSchoolClassById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolClass> findSchoolClassById(@RequestParam("id") Long id);

    /**
     * @Description: 所有学校院系信息列表（全部无条件）
     * @Author: Kang
     * @Date: 2019/3/12 11:16
     */
    @RequestMapping(value = "/schoolClass/findSchoolClassAll", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolClass>> findSchoolClassAll();

    /**
     * @Description: 增加院校结构关系
     * @Author: Kang
     * @Date: 2019/3/12 11:17
     */
    @RequestMapping(value = "/schoolClass/addSchoolClass", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchoolClass(@RequestBody SchoolClassDto schoolClassDto);

    /**
     * @Description: 修改院校结构关系
     * @Author: Kang
     * @Date: 2019/3/12 11:18
     */
    @RequestMapping(value = "/schoolClass/modifySchoolClass", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolClass(@RequestBody SchoolClassModifyDto schoolClassDto);

    /**
     * @Description: 根据id删除院校关系
     * @Author: Kang
     * @Date: 2019/3/12 11:18
     */
    @RequestMapping(value = "/schoolClass/delSchoolClassById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchoolClassById(@RequestParam("id") Long id);

    /**
     * @Description: 根据ids批量删除院校关系
     * @Author: Kang
     * @Date: 2019/3/12 11:19
     */
    @RequestMapping(value = "/schoolClass/batchDelSchoolClassInIds", method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchDelSchoolClassInIds(@RequestBody List<Long> ids);

    /**
     * @Description: 根据院校id 删除该院校底下所有关系
     * @Author: Kang
     * @Date: 2019/3/12 11:20
     */
    @RequestMapping(value = "/schoolClass/delSchoolClassBySchoolId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchoolClassBySchoolId(@RequestParam("schoolId") Long schoolId);

    /**
     * 根据条件查询院校信息
     * @param schoolClass
     * @return
     */
    @RequestMapping(value = "/schoolClass/findSchoolClassBySchoolClass", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<SchoolClass> findSchoolClassBySchoolClass(@RequestBody SchoolClass schoolClass);

    /**    * @Description: 父id查询院系信息
     * @Author: Kang
     * @Date: 2019/3/22 18:41
     */
    @RequestMapping(value = "/schoolClass/findSchoolClassByParentId", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolClass>> findSchoolClassByParentId(@RequestParam("parentId") Long parentId);

    /**
     * 查询单个学校院系路径和Ids
     * @param schoolCode
     * @Author: bin
     * @return
     */
    @RequestMapping(value = "/schoolClass/queryClassUrlBySchoolCode", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolClass>> queryClassUrlBySchoolCode(@RequestParam("schoolCode") String schoolCode);
}
