package com.bdxh.school.feign;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.fallback.SchoolControllerClientFallback;
import com.bdxh.school.vo.BaseEchartsVo;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.school.vo.SchoolShowVo;
import com.github.pagehelper.PageInfo;
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
     * @Description: 根据id批量查询信息
     * @Author: Kang
     * @Date: 2019/3/29 12:08
     */
    @RequestMapping(value = "/school/findSchoolInIds", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<School>> findSchoolInIds(@RequestParam("ids") List<Long> ids);

    /**
     * @Description: 根据学校Code查询学校
     * @Author: bin
     * @Date: 019/3/23 10:18
     */
    @RequestMapping(value = "/school/findSchoolBySchoolCode", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<School> findSchoolBySchoolCode(@RequestParam("schoolCode") String schoolCode);

    /**
     * @Description: 查询不同地区下的学校数量
     * @Author: wanming
     * @Date: 2019/5/24 17:38
     */
    @RequestMapping(value = "/school/querySchoolNumByArea", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<BaseEchartsVo>> querySchoolNumByArea();

}
