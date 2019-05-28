package com.bdxh.school.service;

import com.bdxh.common.helper.excel.bean.SchoolExcelReportBean;
import com.bdxh.common.support.IService;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.vo.BaseEchartsVo;
import com.bdxh.school.vo.SchoolShowVo;
import com.github.pagehelper.PageInfo;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 学校基本信息
 * @Author: Kang
 * @Date: 2019/2/25 16:33
 */
public interface SchoolService extends IService<School> {

    String SCHOOL_INFO_PREFIX = "SCHOOL_INFO";

    String SCHOOL_LIST_PREFIX = "SCHOOL_LIST";

    //增加学校信息
    Boolean addSchool(SchoolDto schoolDto);

    //修改学校信息
    Boolean modifySchool(ModifySchoolDto school);

    //删除学校信息
    Boolean delSchool(Long id);

    //批量删除学校信息
    Boolean batchDelSchool(List<Long> id);

    //id查询学校信息
    Optional<School> findSchoolById(Long id);

    //分页筛选条件查询学校列表
    PageInfo<SchoolShowVo> findSchoolShowVoInConditionPaging(SchoolQueryDto schoolQueryDto);

    //分页筛选条件查询学校列表
    PageInfo<School> findSchoolsInConditionPaging(SchoolQueryDto schoolQueryDto);

    //条件筛选条件查询学校列表
    List<School> findSchoolsInCondition(SchoolQueryDto schoolQueryDto);

    //查询学校列表（全部，无条件）
    List<School> findSchoolAll();

    //根据id批量查询信息
    List<School> findSchoolInIds(List<Long> ids);

    //学校列表信息导出
    void downloadReportItemsExcel(List<SchoolExcelReportBean> schoolExcelReportBeans, OutputStream out) throws Exception;

    //根据学校Code查询学校
    School findSchoolBySchoolCode(String schoolCode);

    //查询不同地区下学校的数量
    List<BaseEchartsVo> querySchoolNumByArea();
}
