package com.bdxh.school.contoller;

import com.bdxh.common.helper.excel.bean.SchoolExcelReportBean;
import com.bdxh.common.helper.excel.utils.DateUtils;
import com.bdxh.common.helper.tree.utils.LongUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolExcelDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.enums.SchoolNatureEnum;
import com.bdxh.school.enums.SchoolTypeEnum;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.school.vo.SchoolShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 学校相关信息
 * @Author: Kang
 * @Date: 2019/2/25 16:15
 */
@Controller
@RequestMapping("/school")
@Slf4j
@Validated
@Api(value = "学校控制器", tags = "学校")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * @Description: 导出报表名称
     */
    private static final String title = "学校列表报表信息.xlsx";

    /**
     * @Description: 增加学校
     * @Author: Kang
     * @Date: 2019/2/25 16:56
     */
    @RequestMapping(value = "/addSchool", method = RequestMethod.POST)
    @ApiOperation(value = "增加学校", response = Boolean.class)
    @ResponseBody
    public Object addSchool(@Validated @RequestBody SchoolDto schoolDto) {
        School school = schoolService.findSchoolBySchoolCode(schoolDto.getSchoolCode());
        if (school != null) {
            return WrapMapper.error("该学校编码已存在");
        }
        Boolean result = schoolService.addSchool(schoolDto);
        return WrapMapper.ok(result);
    }

    /**
     * @Description: 修改学校信息
     * @Author: Kang
     * @Date: 2019/2/26 11:03
     */
    @RequestMapping(value = "/modifySchoolInfo", method = RequestMethod.POST)
    @ApiOperation(value = "修改学校信息", response = Boolean.class)
    @ResponseBody
    public Object modifySchoolInfo(@Validated @RequestBody ModifySchoolDto schoolDto) {
        School school = schoolService.findSchoolBySchoolCode(schoolDto.getSchoolCode());
        if (school != null && !(school.getId().equals(schoolDto.getId()))) {
            return WrapMapper.error("该学校编码已存在");
        }
        Boolean result = schoolService.modifySchool(schoolDto);
        return WrapMapper.ok(result);
    }

    /**
     * @Description: 删除学校信息
     * @Author: Kang
     * @Date: 2019/2/26 11:11
     */
    @RequestMapping(value = "/delSchool", method = RequestMethod.GET)
    @ApiOperation(value = "删除学校信息", response = Boolean.class)
    @ResponseBody
    public Object delSchool(@RequestParam("id") Long id) {
        Boolean result = schoolService.delSchool(id);
        return WrapMapper.ok(result);
    }

    /**
     * @Description: 批量删除学校信息
     * @Author: Kang
     * @Date: 2019/2/26 17:08
     */
    @RequestMapping(value = "/batchDelSchool", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除学校信息", response = Boolean.class)
    @ResponseBody
    public Object batchDelSchool(@RequestBody List<Long> ids) {
        Boolean result = schoolService.batchDelSchool(ids);
        return WrapMapper.ok(result);
    }


    /**
     * @Description: id查询学校信息
     * @Author: Kang
     * @Date: 2019/2/26 10:04
     */
    @RequestMapping(value = "/findSchoolById", method = RequestMethod.GET)
    @ApiOperation(value = "查询学校详情", response = SchoolInfoVo.class)
    @ResponseBody
    public Object findSchoolById(@RequestParam("schoolId") Long id) {
        School school = schoolService.findSchoolById(id).orElse(new School());
        SchoolInfoVo schoolInfoVo = new SchoolInfoVo();
        BeanUtils.copyProperties(school, schoolInfoVo);
        schoolInfoVo.setSchoolNatureValue(SchoolNatureEnum.getValue(school.getSchoolNature()));
        schoolInfoVo.setSchoolTypeValue(SchoolTypeEnum.getValue(schoolInfoVo.getSchoolType()));
        schoolInfoVo.setCreateDate(DateUtils.date2Str(school.getCreateDate(), "yyyyMMdd"));
        schoolInfoVo.setUpdateDate(DateUtils.date2Str(school.getCreateDate(), "yyyyMMdd"));
        return WrapMapper.ok(school);
    }

    /**
     * @Description: 分页查询学校信息 (筛选条件并分页)
     * @Author: Kang
     * @Date: 2019/2/26 10:18
     */
    @RequestMapping(value = "/findSchoolsInConditionPaging", method = RequestMethod.POST)
    @ApiOperation(value = "分页查询学校列表", response = SchoolShowVo.class)
    @ResponseBody
    public Object findSchoolsInConditionPaging(@Valid @RequestBody SchoolQueryDto schoolQueryDto) {
        //符合条件的学校信息
        return WrapMapper.ok(schoolService.findSchoolShowVoInConditionPaging(schoolQueryDto));
    }

    /**
     * @Description: 根据学校Code查询学校
     * @Author: bin
     * @Date: 019/3/23 10:18
     */
    @RequestMapping(value = "/findSchoolBySchoolCode", method = RequestMethod.GET)
    @ApiOperation(value = "根据学校Code查询学校", response = SchoolShowVo.class)
    @ResponseBody
    public Object findSchoolBySchoolCode(@RequestParam("schoolCode") String schoolCode) {
        //符合条件的单个学校信息
        return WrapMapper.ok(schoolService.findSchoolBySchoolCode(schoolCode));
    }

    /**
     * @Description: 查询学校列表 (筛选条件无分页)
     * @Author: Kang
     * @Date: 2019/2/28 15:30
     */
    @RequestMapping(value = "/findSchoolsInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "查询学校列表", response = SchoolShowVo.class)
    @ResponseBody
    public Object findSchoolsInCondition(@Valid @RequestBody SchoolQueryDto schoolQueryDto) {
        List<School> schools = schoolService.findSchoolsInCondition(schoolQueryDto);
        List<SchoolShowVo> showVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schools)) {
            schools.stream().forEach(e -> {
                SchoolShowVo schoolShowVo = new SchoolShowVo();
                BeanUtils.copyProperties(e, schoolShowVo);
                schoolShowVo.setSchoolTypeValue(SchoolTypeEnum.getValue(e.getSchoolType()));
                schoolShowVo.setCreateDate(DateUtils.date2Str(e.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
                //分割省市县
                if (StringUtils.isNotBlank(e.getSchoolArea()) && e.getSchoolArea().contains("/")) {
                    schoolShowVo.setProvince(e.getSchoolArea().substring(0, e.getSchoolArea().indexOf("/")));
                    schoolShowVo.setCity(e.getSchoolArea().substring(e.getSchoolArea().indexOf("/") + 1, e.getSchoolArea().lastIndexOf("/")));
                    schoolShowVo.setAreaOrcounty(e.getSchoolArea().substring(e.getSchoolArea().lastIndexOf("/") + 1));
                }
                showVos.add(schoolShowVo);
            });
        }
        return WrapMapper.ok(showVos);
    }

    /**
     * @Description: 查询所有学校列表信息
     * @Author: Kang
     * @Date: 2019/2/26 16:52
     */
    @RequestMapping(value = "/findSchools", method = RequestMethod.GET)
    @ApiOperation(value = "查询所有学校列表", response = SchoolShowVo.class)
    @ResponseBody
    public Object findSchools() {
        List<School> schools = schoolService.findSchoolAll();
        List<SchoolShowVo> showVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(schools)) {
            schools.stream().forEach(e -> {
                SchoolShowVo schoolShowVo = new SchoolShowVo();
                BeanUtils.copyProperties(e, schoolShowVo);
                schoolShowVo.setSchoolTypeValue(SchoolTypeEnum.getValue(e.getSchoolType()));
                schoolShowVo.setCreateDate(DateUtils.date2Str(e.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
                //分割省市县
                if (StringUtils.isNotBlank(e.getSchoolArea()) && e.getSchoolArea().contains("/")) {
                    schoolShowVo.setProvince(e.getSchoolArea().substring(0, e.getSchoolArea().indexOf("/")));
                    schoolShowVo.setCity(e.getSchoolArea().substring(e.getSchoolArea().indexOf("/") + 1, e.getSchoolArea().lastIndexOf("/")));
                    schoolShowVo.setAreaOrcounty(e.getSchoolArea().substring(e.getSchoolArea().lastIndexOf("/") + 1));
                }
                showVos.add(schoolShowVo);
            });
        }
        return WrapMapper.ok(showVos);
    }


    /**
     * @Description: 学校信息导出
     * @Author: Kang
     * @Date: 2019/2/27 18:31
     */
    @RequestMapping(value = "/downloadReportSchoolExcel", method = RequestMethod.POST)
    @ApiOperation(value = "学校信息导出")
    @ResponseBody
    public Object downloadReportSchoolExcel(@Validated @RequestBody SchoolExcelDto schoolExcelDto) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        List<School> schools = new ArrayList<>();
        switch (schoolExcelDto.getIsBy()) {
            case 1:
                //分页学校信息导出
                schools.addAll(schoolService.findSchoolsInConditionPaging(schoolExcelDto).getList());
                break;
            case 2:
                //id选择信息导出
                schools.addAll(schoolService.findSchoolInIds(schoolExcelDto.getIds()));
                break;
            case 3:
                //全部，满足条件的学校信息导出
                schools.addAll(schoolService.findSchoolsInCondition(schoolExcelDto));
                break;
            default:
                return WrapMapper.error("不存在的选项，请检查");
        }
        if (CollectionUtils.isEmpty(schools)) {
            return WrapMapper.error("抱歉，相关学校不存在。。");
        }

        List<SchoolExcelReportBean> schoolExcelReportBeans = schools.stream().map(e -> {
            SchoolExcelReportBean tempBean = new SchoolExcelReportBean();
            BeanUtils.copyProperties(e, tempBean);
            tempBean.setSchoolTypeValue(SchoolTypeEnum.getValue(e.getSchoolType()));
            tempBean.setCreateDate(DateUtils.date2Str(e.getCreateDate(), "yyyy/MM/dd HH:mm:ss"));
            //分割省市县
            if (StringUtils.isNotBlank(e.getSchoolArea()) && e.getSchoolArea().contains("/")) {
                tempBean.setProvince(e.getSchoolArea().substring(0, e.getSchoolArea().indexOf("/")));
                tempBean.setCity(e.getSchoolArea().substring(e.getSchoolArea().indexOf("/") + 1, e.getSchoolArea().lastIndexOf("/")));
                tempBean.setAreaOrcounty(e.getSchoolArea().substring(e.getSchoolArea().lastIndexOf("/") + 1));
            }
            return tempBean;
        }).collect(Collectors.toList());

        try (OutputStream out = response.getOutputStream()) {
            String fileName = URLEncoder.encode(title, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
            schoolService.downloadReportItemsExcel(schoolExcelReportBeans, out);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出失败：" + e.getMessage());
        }
        return WrapMapper.ok(true);
    }

    /**
     * @Description: 根据id批量查询信息
     * @Author: Kang
     * @Date: 2019/3/29 12:08
     */
    @RequestMapping(value = "/findSchoolInIds", method = RequestMethod.GET)
    @ApiOperation(value = "根据id批量查询信息")
    @ResponseBody
    public Object findSchoolInIds(@RequestParam("ids") List<Long> ids) {
        return WrapMapper.ok(schoolService.findSchoolInIds(ids));
    }


    /**
     * @Description: 查询不同地区下的学校数量
     * @Author: wanMing
     * @Date: 2019/5/24 17:38
     */
    @RequestMapping(value = "/querySchoolNumByArea", method = RequestMethod.GET)
    @ApiOperation(value = "查询不同地区下的学校数量")
    @ResponseBody
    public Object querySchoolNumByArea() {
        return WrapMapper.ok(schoolService.querySchoolNumByArea());

    }


}
