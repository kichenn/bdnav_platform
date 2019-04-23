package com.bdxh.school.service.impl;

import com.bdxh.common.helper.excel.ExcelExportUtils;
import com.bdxh.common.helper.excel.bean.SchoolExcelReportBean;
import com.bdxh.common.helper.excel.utils.DateUtils;
import com.bdxh.common.support.BaseService;
import com.bdxh.school.dto.ModifySchoolDto;
import com.bdxh.school.dto.SchoolDto;
import com.bdxh.school.dto.SchoolQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.enums.SchoolTypeEnum;
import com.bdxh.school.persistence.SchoolMapper;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.vo.SchoolShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class SchoolServiceImpl extends BaseService<School> implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;


    //添加学校信息
    @Override
    public Boolean addSchool(SchoolDto schoolDto) {
        School school = new School();
        BeanUtils.copyProperties(schoolDto, school);
        //school.setAppKey();
        //school.setAppSecret();
        Boolean result = schoolMapper.insertSelective(school) > 0;
//        SchoolShowVo schoolvo = new SchoolShowVo();
//        BeanUtils.copyProperties(school, schoolvo);
       /* if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
        }*/
        return result;
    }

    //修改学校信息
    @Override
    public Boolean modifySchool(ModifySchoolDto schoolDto) {
        School school = new School();
        BeanUtils.copyProperties(schoolDto, school);
        school.setUpdateDate(new Date());
        school.setCreateDate(null);
        Boolean result = schoolMapper.updateByPrimaryKey(school) > 0;
        /*if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.delete(SCHOOL_LIST_PREFIX + "_" + school.getId());
        }*/
        return result;
    }

    //删除学校信息
    @Override
    public Boolean delSchool(Long id) {
        Boolean result = schoolMapper.deleteByPrimaryKey(id) > 0;
       /* if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.delete(SCHOOL_LIST_PREFIX + "_" + id);
        }*/
        return result;
    }

    //批量删除学校信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchool(List<Long> id) {
        Boolean result = schoolMapper.batchDelSchool(id) > 0;
       /* if (result) {
            //删除列表缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
            //删除详情缓存
            redisCache.deleteByPrex(SCHOOL_LIST_PREFIX);
        }*/
        return result;
    }

    //id查询学校信息
    @Override
//    @GetWithRedis(key = SCHOOL_INFO_PREFIX)
    public Optional<School> findSchoolById(Long id) {
        School school = schoolMapper.selectByPrimaryKey(id);
        return Optional.ofNullable(school);
    }

    //筛选条件查询学校信息
    @Override
//    @GetWithRedis(key = SCHOOL_LIST_PREFIX)
    public PageInfo<SchoolShowVo> findSchoolShowVoInConditionPaging(SchoolQueryDto schoolQueryDto) {
        Page page = PageHelper.startPage(schoolQueryDto.getPageNum(), schoolQueryDto.getPageSize());
        List<School> schools = schoolMapper.findIdsInCondition(schoolQueryDto);
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

        PageInfo<SchoolShowVo> pageInfo = new PageInfo(showVos);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    //分页筛选条件查询学校列表
    @Override
    public PageInfo<School> findSchoolsInConditionPaging(SchoolQueryDto schoolQueryDto) {
        PageHelper.startPage(schoolQueryDto.getPageNum(), schoolQueryDto.getPageSize());
        List<School> schools = schoolMapper.findIdsInCondition(schoolQueryDto);
        return new PageInfo(schools);
    }


    //条件筛选条件查询学校列表
    @Override
    public List<School> findSchoolsInCondition(SchoolQueryDto schoolQueryDto) {
        return schoolMapper.findIdsInCondition(schoolQueryDto);
    }

    //查询学校列表（全部，无条件）
    @Override
    public List<School> findSchoolAll() {
        List<School> schools = schoolMapper.selectAll();
        return schools;
    }


    //根据id批量查询信息
    @Override
    public List<School> findSchoolInIds(List<Long> ids) {
        List<School> schools = schoolMapper.findSchoolInIds(ids);
        return schools;
    }


    //学校列表信息导出
    @Override
    public void downloadReportItemsExcel(List<SchoolExcelReportBean> schoolExcelReportBeans, OutputStream out) throws Exception {
        ExcelExportUtils.getInstance().exportObjects2Excel(schoolExcelReportBeans, SchoolExcelReportBean.class, true, "北斗星航", true, out);
    }

    @Override
    public School findSchoolBySchoolCode(String schoolCode) {
        return schoolMapper.findSchoolBySchoolCode(schoolCode);
    }
}
