package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolExcelDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.fallback.SchoolControllerClientFallback;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.school.vo.SchoolShowVo;
import com.github.pagehelper.PageInfo;
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
@FeignClient(value = "school-provider-cluster", fallback = SchoolControllerClientFallback.class)
public interface SchoolControllerClient {


    /**
     * @Description: 增加学校信息
     * @Author: Kang
     * @Date: 2019/3/12 10:53
     */
    @RequestMapping(value = "/school/addSchool", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addSchool(@RequestBody SchoolDto schoolDto);

    /**
     * @Description: 修改学校信息
     * @Author: Kang
     * @Date: 2019/3/12 10:57
     */
    @RequestMapping(value = "/school/modifySchoolInfo", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifySchoolInfo(@RequestBody ModifySchoolDto schoolDto);

    /**
     * @Description: 删除学校信息
     * @Author: Kang
     * @Date: 2019/3/12 10:59
     */
    @RequestMapping(value = "/school/delSchool", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delSchool(@RequestParam("id") Long id);

    /**
     * @Description: 批量删除学校信息
     * @Author: Kang
     * @Date: 2019/3/12 11:00
     */
    @RequestMapping(value = "/school/batchDelSchool", method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchDelSchool(@RequestBody List<Long> ids);

    /**
     * @Description: id查询学校信息
     * @Author: Kang
     * @Date: 2019/3/12 11:02
     */
    @RequestMapping(value = "/school/findSchoolById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<SchoolInfoVo> findSchoolById(@RequestParam("schoolId") Long id);

    /**
     * @Description: 筛选条件筛选，然后分页返回
     * @Author: Kang
     * @Date: 2019/3/12 11:05
     */
    @RequestMapping(value = "/school/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<SchoolShowVo>> findSchoolsInConditionPaging(@RequestBody SchoolQueryDto schoolQueryDto);

    /**
     * @Description: 筛选条件筛选, 返回列表，不分页
     * @Author: Kang
     * @Date: 2019/3/12 11:07
     */
    @RequestMapping(value = "/school/findSchoolsInCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<SchoolShowVo>> findSchoolsInCondition(@RequestBody SchoolQueryDto schoolQueryDto);

    /**
     * @Description: 查询所有学校列表，无条件
     * @Author: Kang
     * @Date: 2019/3/12 11:07
     */
    @RequestMapping(value = "/school/findSchools", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<SchoolShowVo>> findSchools();

    /**
     * @Description: 学校信息导出
     * @Author: Kang
     * @Date: 2019/3/12 11:07
     */
    @RequestMapping(value = "/school/downloadReportSchoolExcel", method = RequestMethod.POST)
    @ResponseBody
    Wrapper downloadReportSchoolExcel(@RequestBody SchoolExcelDto schoolExcelDto);
}
