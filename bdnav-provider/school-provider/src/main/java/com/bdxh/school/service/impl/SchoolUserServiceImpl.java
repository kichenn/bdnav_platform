package com.bdxh.school.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.support.BaseService;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.school.configration.rocketmq.properties.RocketMqProducerProperties;
import com.bdxh.school.dto.AddSchoolUserDto;
import com.bdxh.school.dto.ModifySchoolUserDto;
import com.bdxh.school.dto.SchoolUserQueryDto;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolRole;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.entity.SchoolUserRole;
import com.bdxh.school.persistence.SchoolUserMapper;
import com.bdxh.school.persistence.SchoolUserRoleMapper;
import com.bdxh.school.service.SchoolUserService;
import com.bdxh.school.vo.SchoolUserShowVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Description: 系统用户管理service实现
 * @Author: Kang
 * @Date: 2019/3/26 14:11
 */
@Service
@Slf4j
public class SchoolUserServiceImpl extends BaseService<SchoolUser> implements SchoolUserService {

    @Autowired
    private SchoolUserMapper schoolUserMapper;

    @Autowired
    private SchoolUserRoleMapper schoolUserRoleMapper;

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    @Override
    public PageInfo<SchoolUserShowVo> findListPage(SchoolUserQueryDto schoolUserQueryDto) {
        Page page = PageHelper.startPage(schoolUserQueryDto.getPageNum(), schoolUserQueryDto.getPageSize());
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(schoolUserQueryDto, schoolUser);
        if (schoolUserQueryDto.getSchoolUserSexEnum() != null) {
            schoolUser.setSex(schoolUserQueryDto.getSchoolUserSexEnum().getKey());
        }
        if (schoolUserQueryDto.getSchoolUserStatusEnum() != null) {
            schoolUser.setStatus(schoolUserQueryDto.getSchoolUserStatusEnum().getKey());
        }
        if (schoolUserQueryDto.getSchoolUserTypeEnum() != null) {
            schoolUser.setType(schoolUserQueryDto.getSchoolUserTypeEnum().getKey());
        }
        List<SchoolUserShowVo> roleLogs = schoolUserMapper.getByCondition(schoolUser, schoolUserQueryDto.getDeptName());
        PageInfo<SchoolUserShowVo> pageInfo = new PageInfo(roleLogs);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    @Override
    public SchoolUser getByUserName(String userName) {
        SchoolUser user = schoolUserMapper.getByUserName(userName);
        return user;
    }

    @Override
    public void delUser(Long id) {
        schoolUserMapper.deleteByPrimaryKey(id);
        SchoolUserRole userRole = new SchoolUserRole();
        userRole.setUserId(id);
        schoolUserRoleMapper.delete(userRole);
        JSONObject msgData = new JSONObject();
        msgData.put("delFlag",1);
        msgData.put("tableName", "t_school_user");
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("id",id.toString());
        data.add(map);
        msgData.put("data", data);
        Message schoolUserRoleMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUser, msgData.toString().getBytes());
        msgData.put("tableName", "t_school_user_role");
        data.clear();
        map.clear();
        map.put("id",id.toString());
        data.add(map);
        msgData.put("data",data);
        Message schoolUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUserRole, msgData.toString().getBytes());

        try {
            defaultMQProducer.send(schoolUserMsg);
            defaultMQProducer.send(schoolUserRoleMsg);
        } catch (Exception e) {
            log.info("推送学校用户角色信息失败，错误信息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Boolean delBatchSchoolUserInIds(List<Long> ids) {
        List<Map<String,String>> data=new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        for (Long id : ids) {
            map.put("id",id.toString());
            data.add(map);
        }
        JSONObject msgData = new JSONObject();
        msgData.put("delFlag",1);
        msgData.put("tableName", "t_school_user");
        msgData.put("data", data);
        Message schoolUserRoleMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUser, msgData.toString().getBytes());
        msgData.put("tableName", "t_school_user_role");
        data.clear();
        map.clear();
        for (Long id : ids) {
            map.put("id",id.toString());
            data.add(map);
        }
        msgData.put("data",data);
        Message schoolUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUserRole, msgData.toString().getBytes());
        try {
            defaultMQProducer.send(schoolUserMsg);
            defaultMQProducer.send(schoolUserRoleMsg);
        } catch (Exception e) {
            log.info("推送学校用户角色信息失败，错误信息：" + e.getMessage());
            e.printStackTrace();
        }
        return schoolUserMapper.delBatchSchoolUserInIds(ids) > 0;
    }

    @Override
    public List<String> findUserRoleByUserId(Long userId) {
        List<String> userRoles = new ArrayList<>();
        List<SchoolUserRole> UrList = schoolUserRoleMapper.findUserRoleByUserId(userId);
        if (CollectionUtils.isNotEmpty(UrList)) {
            UrList.stream().forEach(e -> {
                userRoles.add(String.valueOf(e.getRoleId()));
            });
        }
        return userRoles;
    }

    /**
     * @Description: 用户id启用或者禁用信息
     * @Author: Kang
     * @Date: 2019/3/27 10:38
     */
    @Override
    public Boolean modifySchoolUserStatusById(Long id, Byte status) {
        SchoolUser schoolUser = new SchoolUser();
        schoolUser.setId(id);
        schoolUser.setStatus(status);
        return schoolUserMapper.updateByPrimaryKeySelective(schoolUser) > 0;
    }

    /**
     * @Description: 新增学校用户信息
     * @Author: Kang
     * @Date: 2019/4/3 10:19
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSchoolUser(AddSchoolUserDto addSchoolUserDto) {
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(addSchoolUserDto, schoolUser);
        //设置类型值
        if (addSchoolUserDto.getSchoolUserSexEnum() != null) {
            schoolUser.setSex(addSchoolUserDto.getSchoolUserSexEnum().getKey());
        }
        if (addSchoolUserDto.getSchoolUserStatusEnum() != null) {
            schoolUser.setStatus(addSchoolUserDto.getSchoolUserStatusEnum().getKey());
        }
        if (addSchoolUserDto.getSchoolUserTypeEnum() != null) {
            schoolUser.setType(addSchoolUserDto.getSchoolUserTypeEnum().getKey());
        }
        boolean schoolUserResult = schoolUserMapper.insertSelective(schoolUser) > 0;

        //增加学校用户和角色的关系
        if (CollectionUtils.isNotEmpty(addSchoolUserDto.getRoles())) {
            for (int i = 0; i < addSchoolUserDto.getRoles().size(); i++) {
                SchoolUserRole schoolUserRole = new SchoolUserRole();
                schoolUserRole.setSchoolId(schoolUser.getSchoolId());
                schoolUserRole.setSchoolCode(schoolUser.getSchoolCode());
                schoolUserRole.setUserId(schoolUser.getId());
                schoolUserRole.setRoleId(Long.valueOf(addSchoolUserDto.getRoles().get(i)));
                Boolean userReleResult = schoolUserRoleMapper.insertSelective(schoolUserRole) > 0;
                //添加判断测试时只推送石齐的数据根据学校ID判断
                if (schoolUser.getSchoolId().equals(Long.parseLong("64"))) {
                    //推送消息
                    if (userReleResult) {
                        JSONObject msgData = new JSONObject();
                        msgData.put("delFlag", 0);
                        msgData.put("tableName", "t_school_user_role");
                        List<SchoolUserRole> schoolClassList = new ArrayList<>();
                        schoolClassList.add(schoolUserRole);
                        msgData.put("data", schoolClassList);
                        Message schoolUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUserRole, msgData.toString().getBytes());
                        try {
                            defaultMQProducer.send(schoolUserMsg);
                        } catch (Exception e) {
                            log.info("推送学校用户角色信息失败，错误信息：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        //添加判断测试时只推送石齐的数据根据学校ID判断
        if(schoolUser.getSchoolId().equals(Long.parseLong("64"))) {
            //推送消息
            if (schoolUserResult) {
                JSONObject msgData = new JSONObject();
                msgData.put("tableName", "t_school_user");
                msgData.put("delFlag", 0);
                List<SchoolUser> schoolUserList = new ArrayList<>();
                schoolUserList.add(schoolUser);
                msgData.put("data", schoolUserList);
                Message schoolUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUser, msgData.toString().getBytes());
                try {
                    defaultMQProducer.send(schoolUserMsg);
                } catch (Exception e) {
                    log.info("推送学校用户信息失败，错误信息：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @Description: 修改学校用户信息
     * @Author: Kang
     * @Date: 2019/4/2 11:35
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifySchoolUser(ModifySchoolUserDto modifySchoolUserDto) {
        //更新基本信息
        SchoolUser schoolUser = new SchoolUser();
        BeanUtils.copyProperties(modifySchoolUserDto, schoolUser);
        //设置类型值
        if (modifySchoolUserDto.getSchoolUserSexEnum() != null) {
            schoolUser.setSex(modifySchoolUserDto.getSchoolUserSexEnum().getKey());
        }
        if (modifySchoolUserDto.getSchoolUserStatusEnum() != null) {
            schoolUser.setStatus(modifySchoolUserDto.getSchoolUserStatusEnum().getKey());
        }
        if (modifySchoolUserDto.getSchoolUserTypeEnum() != null) {
            schoolUser.setType(modifySchoolUserDto.getSchoolUserTypeEnum().getKey());
        }
        if (StringUtils.isNotEmpty(modifySchoolUserDto.getBirth())) {
            schoolUser.setBirth(DateUtil.format(DateUtil.format(schoolUser.getBirth(), "yyyy-MM-dd"), "yyyy-MM-dd"));
        }
        schoolUser.setUpdateDate(new Date());
        Boolean schoolUserResult = schoolUserMapper.updateByPrimaryKeySelective(schoolUser) > 0;

        //删除学校用户和角色的关系
        schoolUserRoleMapper.delRoleByUserId(schoolUser.getId());
        //增加学校用户和角色的关系
        if (CollectionUtils.isNotEmpty(modifySchoolUserDto.getRoles())) {
            for (int i = 0; i < modifySchoolUserDto.getRoles().size(); i++) {
                SchoolUserRole schoolUserRole = new SchoolUserRole();
                schoolUserRole.setSchoolId(schoolUser.getSchoolId());
                schoolUserRole.setSchoolCode(schoolUser.getSchoolCode());
                schoolUserRole.setUserId(schoolUser.getId());
                schoolUserRole.setRoleId(Long.valueOf(modifySchoolUserDto.getRoles().get(i)));
                Boolean userReleResult = schoolUserRoleMapper.insertSelective(schoolUserRole) > 0;
                //添加判断测试时只推送石齐的数据根据学校ID判断
                if(schoolUser.getSchoolId().equals(Long.parseLong("64"))) {
                    if (userReleResult) {
                        JSONObject msgData = new JSONObject();
                        msgData.put("tableName", "t_school_user_role");
                        List<SchoolUserRole> schoolUserRoleList = new ArrayList<>();
                        schoolUserRoleList.add(schoolUserRole);
                        msgData.put("data", schoolUserRoleList);
                        msgData.put("delFlag", 0);
                        Message schoolUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUserRole, msgData.toString().getBytes());
                        try {
                            defaultMQProducer.send(schoolUserMsg);
                        } catch (Exception e) {
                            log.info("推送学校用户角色信息失败，错误信息：" + e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        //推送消息
        //添加判断测试时只推送石齐的数据根据学校ID判断
        if(schoolUser.getSchoolId().equals(Long.parseLong("64"))) {
            if (schoolUserResult) {
                SchoolUser oldUser=schoolUserMapper.selectByPrimaryKey(schoolUser.getId());
                schoolUser.setPassword(oldUser.getPassword());
                JSONObject msgData = new JSONObject();
                msgData.put("tableName", "t_school_user");
                List<SchoolUser> schoolUserList = new ArrayList<>();
                schoolUserList.add(schoolUser);
                msgData.put("data", schoolUserList);
                msgData.put("delFlag", 0);
                Message schoolUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.schoolUserInfoTag_schoolUser, msgData.toString().getBytes());
                try {
                    defaultMQProducer.send(schoolUserMsg);
                } catch (Exception e) {
                    log.info("推送学校用户信息失败，错误信息：" + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }
}
