package com.bdxh.school.service.impl;


import com.bdxh.common.helper.tree.bean.TreeBean;
import com.bdxh.common.helper.tree.utils.TreeLoopUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.school.dto.*;
import com.bdxh.school.service.SchoolOrgService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.persistence.SchoolOrgMapper;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toConcurrentMap;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-05-31 14:06:08
 */
@Service
@Slf4j
public class SchoolOrgServiceImpl extends BaseService<SchoolOrg> implements SchoolOrgService {

    @Autowired
    private SchoolOrgMapper schoolOrgMapper;

    @Override
    public Integer getSchoolOrgAllCount() {
        return schoolOrgMapper.getSchoolOrgAllCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSchoolOrgInIds(List<Long> ids) {
        return schoolOrgMapper.delSchoolOrgInIds(ids) > 0;
    }

    @Override
    public List<SchoolOrg> findAllSchoolOrgInfo(SchoolOrgQueryDto schoolOrgQueryDto) {
        Map<String, Object> param = BeanToMapUtil.objectToMap(schoolOrgQueryDto);
        return schoolOrgMapper.findAllSchoolOrgInfo(param);
    }

    @Override
    public SchoolOrg findSchoolOrgInfo(Long id) {
        return schoolOrgMapper.findSchoolOrgInfo(id);
    }

    /**
     *
     * 查学生的组织架构 学生组织架构的类 1,2,3,4,5,8
     * 先查询出所有的学生的组织架构集合,确实根节点的集合
     * @Author: WanMing
     * @Date: 2019/6/27 10:48
     */
    @Override
    public List<TreeBean> findClassOrg(Long schoolId) {
        List<SchoolOrg> classOrgs = schoolOrgMapper.findClassOrg(schoolId);
        //空集合
        if(CollectionUtils.isEmpty(classOrgs)){
            return new ArrayList<>();
        }
        //有数据,转普通树
        List<TreeBean> treeBeans = new ArrayList<>();
        classOrgs.forEach(classOrg->{
            TreeBean treeBean = new TreeBean();
            BeanUtils.copyProperties(classOrg, treeBean);
            treeBean.setTitle(classOrg.getOrgName());
            treeBean.setCreateDate(classOrg.getCreateDate());
            treeBeans.add(treeBean);
        });
        //查询根节点
        Map<Long, SchoolOrg> schoolOrgConcurrentMap = Optional.of(classOrgs).map(list -> list.stream()
                .collect(toConcurrentMap(SchoolOrg::getId, Function.identity()))).orElseGet(ConcurrentHashMap::new);
        for (SchoolOrg classOrg : classOrgs) {
            String parentIds = classOrg.getParentIds();
            if(StringUtils.isEmpty(parentIds)){
                continue;
            }
            boolean flag = Stream.of(parentIds.split(",")).filter(str -> StringUtils.isNotEmpty(str))
                    .mapToLong(Long::parseLong).anyMatch(id -> schoolOrgConcurrentMap.get(id) != null);
            if(flag){
                schoolOrgConcurrentMap.remove(classOrg.getId());
            }
        }
        //根节点集合
        List<SchoolOrg> rootOrgs = new ArrayList<>(schoolOrgConcurrentMap.values());
        //转层级树
        List<TreeBean> treeBeanList = new ArrayList<>();
        rootOrgs.forEach(rootOrg->{
            TreeBean treeBean = new TreeBean();
            BeanUtils.copyProperties(rootOrg, treeBean);
            treeBean.setTitle(rootOrg.getOrgName());
            treeBean.setCreateDate(rootOrg.getCreateDate());
            treeBean.setChildren(new TreeLoopUtils<>().getChild(rootOrg.getId(),treeBeans));
            treeBeanList.add(treeBean);
        });
        return treeBeanList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeSchoolOrgInfo(Long id) {
        Boolean result = schoolOrgMapper.removeSchoolOrgInfo(id) > 0;
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSchoolOrgInfo(SchoolOrgUpdateDto schoolOrgUpdateDto) {
        SchoolOrg schoolOrg=BeanMapUtils.map(schoolOrgUpdateDto,SchoolOrg.class);
        if (new Long("-1").equals(schoolOrg.getParentId())) {
            schoolOrg.setParentNames("");
            schoolOrg.setThisUrl(schoolOrg.getOrgName());
            schoolOrg.setParentIds("");
        } else {
            //查询父亲节点
            SchoolOrg schoolOrgTemp = findSchoolOrgInfo(schoolOrg.getParentId());
            //树状
            schoolOrg.setParentNames(schoolOrgTemp.getParentNames() + "/" + schoolOrgTemp.getOrgName());
            schoolOrg.setThisUrl(schoolOrgTemp.getParentNames() + "/" + schoolOrgTemp.getOrgName() + "/" + schoolOrg.getOrgName());
            schoolOrg.setParentIds(schoolOrgTemp.getParentIds() + "," + schoolOrgTemp.getId());
        }
        //查询当前节点的子节点
        // 修改当前组织，  子部门组织的 url parentnames 要跟着修改
        SchoolOrgQueryDto schoolOrgQueryDto=new SchoolOrgQueryDto();
        schoolOrgQueryDto.setParentId(schoolOrg.getId());
        List<SchoolOrg> orgs = findAllSchoolOrgInfo(schoolOrgQueryDto);
        if (CollectionUtils.isNotEmpty(orgs)) {
            orgs.forEach(e -> {
                e.setParentNames(schoolOrg.getParentNames() + "/" + schoolOrg.getOrgName());
                e.setThisUrl(schoolOrg.getParentNames() + "/" + schoolOrg.getOrgName() + "/" + e.getOrgName());
                schoolOrgMapper.updateByPrimaryKeySelective(e);
            });
        }
        Boolean result = schoolOrgMapper.updateSchoolOrg(schoolOrg) > 0;
        return result;
    }

    @Override
    public List<SchoolOrg> findAllOrgInfo() {
        return  schoolOrgMapper.selectAll();
    }

    @Override
    public Boolean insertSchoolOrgInfo(SchoolOrgAddDto schoolOrgAddDto) {
        SchoolOrg schoolOrg=BeanMapUtils.map(schoolOrgAddDto,SchoolOrg.class);
        if (new Long("-1").equals(schoolOrg.getParentId())) {
            schoolOrg.setParentNames("");
            schoolOrg.setThisUrl(schoolOrg.getOrgName());
            schoolOrg.setParentIds("");
        } else {
            //查询父亲节点
            SchoolOrg schoolOrgTemp = findSchoolOrgInfo(schoolOrg.getParentId());
            //树状
            schoolOrg.setParentNames(schoolOrgTemp.getParentNames() + "/" + schoolOrgTemp.getOrgName());
            schoolOrg.setThisUrl(schoolOrgTemp.getParentNames() + "/" + schoolOrgTemp.getOrgName() + "/" + schoolOrgTemp.getOrgName());
            schoolOrg.setParentIds(schoolOrgTemp.getParentIds() + "," + schoolOrgTemp.getId());
        }
        Boolean result = schoolOrgMapper.insertSelective(schoolOrg) > 0;
        return result;
    }

    @Override
    public List<SchoolOrg> findBySchoolOrgByParentId(Long parentId) {
        return schoolOrgMapper.findBySchoolOrgByParentId(parentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateSchoolClassInfo(ClassAdministratorsUpdateDto classAdministratorsUpdateDto) {
        SchoolOrg schoolOrg=BeanMapUtils.map(classAdministratorsUpdateDto,SchoolOrg.class);
        return schoolOrgMapper.updateSchoolClassInfo(schoolOrg) > 0;
    }

    @Override
    public List<SchoolOrg> findTeacherDeptInfo(Long schoolId) {
        return schoolOrgMapper.findTeacherDeptInfo(schoolId);
    }
}
