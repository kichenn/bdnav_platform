package com.bdxh.user.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.base.constant.RocketMqConstrants;
import com.bdxh.common.helper.weixiao.authentication.AuthenticationUtils;
import com.bdxh.common.helper.weixiao.authentication.constant.AuthenticationConstant;
import com.bdxh.common.helper.weixiao.authentication.request.SynUserInfoRequest;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.support.BaseService;
import com.bdxh.user.configration.rocketmq.properties.RocketMqProducerProperties;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.entity.TeacherDept;
import com.bdxh.user.persistence.BaseUserMapper;
import com.bdxh.user.persistence.BaseUserUnqiueMapper;
import com.bdxh.user.persistence.TeacherDeptMapper;
import com.bdxh.user.persistence.TeacherMapper;
import com.bdxh.user.service.TeacherService;
import com.bdxh.user.vo.TeacherDeptVo;
import com.bdxh.user.vo.TeacherVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import com.netflix.discovery.converters.Auto;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @description: 老师信息service实现
 * @author: xuyuan
 * @create: 2019-02-26 10:39
 **/
@Service
@Slf4j
public class TeacherServiceImpl extends BaseService<Teacher> implements TeacherService {
    @Autowired
    private TeacherDeptMapper teacherDeptMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    @Autowired
    private BaseUserMapper baseUserMapper;

    @Autowired
    private BaseUserUnqiueMapper baseUserUnqiueMapper;

    @Autowired
    private RocketMqProducerProperties rocketMqProducerProperties;

    @Autowired
    private DefaultMQProducer defaultMQProducer;


    @Override
    public PageInfo<TeacherVo> getTeacherList(TeacherQueryDto teacherQueryDto) {
        PageHelper.startPage(teacherQueryDto.getPageNum(), teacherQueryDto.getPageSize());
        List<TeacherVo> listTeacher = teacherMapper.selectAllTeacherInfo(teacherQueryDto);
        PageInfo<TeacherVo> pageInfoTeacher = new PageInfo<TeacherVo>(listTeacher);
        return pageInfoTeacher;
    }


    @Override
    @Transactional
    public void deleteTeacherInfo(String schoolCode, String cardNumber) {
        BaseUser baseUser = baseUserMapper.queryBaseUserBySchoolCodeAndCardNumber(schoolCode, cardNumber);
        baseUserUnqiueMapper.deleteByPrimaryKey(baseUser.getId());
        Teacher teacher = teacherMapper.selectTeacherDetails(schoolCode, cardNumber);
        TeacherDept teacherDept = teacherDeptMapper.findTeacherBySchoolCodeAndCardNumber(schoolCode, cardNumber);
        teacherMapper.deleteTeacher(schoolCode, cardNumber);
        teacherDeptMapper.deleteTeacherDept(schoolCode, cardNumber, 0);
        baseUserMapper.deleteBaseUserInfo(schoolCode, cardNumber);
        try {
            JSONObject mesData = new JSONObject();
            mesData.put("tableName", "t_teacher");
            mesData.put("data", teacher);
            JSONObject data=mesData.getJSONObject("data");
            data.put("delFlag",1);
            mesData.put("data", data);
            Message studentMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacher, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
            mesData.put("tableName", "t_base_user");
            mesData.put("data", baseUser);
            JSONObject data2=mesData.getJSONObject("data");
            data2.put("delFlag",1);
            mesData.put("data", data2);
            Message baseUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_baseUser, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
            mesData.put("tableName", "t_teacher_dept");
            mesData.put("data", teacherDept);
            JSONObject data3=mesData.getJSONObject("data");
            data3.put("delFlag",1);
            mesData.put("data", data3);
            Message teacherDeptMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacherDept, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
            defaultMQProducer.send(studentMsg);
            defaultMQProducer.send(baseUserMsg);
            defaultMQProducer.send(teacherDeptMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    @Transactional
    public void deleteBatchesTeacherInfo(String schoolCodes, String cardNumbers) {
        String[] schoolCode = schoolCodes.split(",");
        String[] cardNumber = cardNumbers.split(",");

        List<Map<String, String>> list = new ArrayList<>();
        for (int i = 0; i < cardNumber.length; i++) {
            Map<String, String> map = new HashMap<>();
            map.put("cardNumber", schoolCode[i]);
            map.put("schoolCode", cardNumber[i]);
            list.add(map);
        }
        teacherMapper.batchRemoveTeacherInfo(list);
        teacherDeptMapper.batchRemoveTeacherDeptInfo(list);
        baseUserMapper.batchRemoveBaseUserInfo(list);

    }

    @Override
    @Transactional
    public void saveTeacherDeptInfo(AddTeacherDto teacherDto) {
        Teacher teacher = BeanMapUtils.map(teacherDto, Teacher.class);
        teacher.setId(snowflakeIdWorker.nextId());
        teacher.setActivate(Byte.valueOf("1"));
        BaseUser baseUser = BeanMapUtils.map(teacher, BaseUser.class);
        baseUser.setUserType(2);
        baseUser.setUserId(teacher.getId());
        baseUser.setId(snowflakeIdWorker.nextId());
        try {
            baseUserUnqiueMapper.insertUserPhone(baseUser.getId(),baseUser.getPhone());
        }catch (Exception e){
            String message=e.getMessage();
            if (e instanceof DuplicateKeyException) {
                Preconditions.checkArgument(false,"当前手机号码已重复请重新填写");
            }
        }
        Boolean teaResult = teacherMapper.insert(teacher) > 0;
        Boolean baseUserResult = baseUserMapper.insert(baseUser) > 0;
        TeacherDept teacherDept = new TeacherDept();
        try {
            if (null != teacherDto.getTeacherDeptDtoList()) {
                IntStream.range(0, teacherDto.getTeacherDeptDtoList().size())
                        .forEach(i -> {
                            teacherDept.setId(snowflakeIdWorker.nextId());
                            teacherDept.setSchoolCode(teacher.getSchoolCode());
                            teacherDept.setCardNumber(teacher.getCardNumber());
                            teacherDept.setTeacherId(teacher.getId());
                            teacherDept.setOperator(teacher.getOperator());
                            teacherDept.setOperatorName(teacher.getOperatorName());
                            teacherDept.setDeptId(teacherDto.getTeacherDeptDtoList().get(i).getDeptId());
                            teacherDept.setDeptName(teacherDto.getTeacherDeptDtoList().get(i).getDeptName());
                            teacherDept.setDeptIds(teacherDto.getTeacherDeptDtoList().get(i).getDeptIds());
                            teacherDept.setDeptNames(teacherDto.getTeacherDeptDtoList().get(i).getDeptNames().trim());
                            Boolean terDeptResult = teacherDeptMapper.insert(teacherDept) > 0;
                            if (terDeptResult) {
                                try {
                                    JSONObject mesData = new JSONObject();
                                    mesData.put("tableName", "t_teacher_dept");
                                    mesData.put("data", teacherDept);
                                    JSONObject data=mesData.getJSONObject("data");
                                    data.put("delFlag",0);
                                    mesData.put("data", data);
                                    Message teacherDeptMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacherDept, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
                                    defaultMQProducer.send(teacherDeptMsg);
                                } catch (Exception e) {
                                    log.info("推送教职工部门关系信息失败，错误信息:" + e.getMessage());
                                    e.printStackTrace();
                                }
                            }
                        });
            }
            //将修改的信息推送至rocketMQ
            if (teaResult && baseUserResult) {
                JSONObject mesData = new JSONObject();
                mesData.put("tableName", "t_teacher");
                mesData.put("data", teacher);
                JSONObject data=mesData.getJSONObject("data");
                data.put("delFlag",0);
                mesData.put("data", data);
                Message teacherMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacher, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
                defaultMQProducer.send(teacherMsg);
                mesData.put("tableName", "t_base_user");
                mesData.put("data", baseUser);
                JSONObject data1=mesData.getJSONObject("data");
                data1.put("delFlag",0);
                mesData.put("data", data1);
                Message baseUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_baseUser, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
                defaultMQProducer.send(baseUserMsg);
            }
        } catch (Exception e) {
            log.info("推送教职工信息失败，错误信息:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public TeacherVo selectTeacherInfo(String schoolCode, String cardNumber) {
        Teacher teacher = teacherMapper.selectTeacherDetails(schoolCode, cardNumber);
        TeacherVo teacherVo = BeanMapUtils.map(teacher, TeacherVo.class);
        List<TeacherDeptVo> teacherDeptVo = teacherDeptMapper.selectTeacherDeptDetailsInfo(schoolCode, cardNumber);
        teacherVo.setTeacherDeptVos(teacherDeptVo);
        return teacherVo;
    }

    @Override
    @Transactional
    public void updateTeacherInfo(UpdateTeacherDto updateTeacherDto) {
        try {
            Teacher teacher = BeanMapUtils.map(updateTeacherDto, Teacher.class);
            Boolean teaResult = teacherMapper.updateTeacher(teacher) > 0;
            BaseUser updateBaseUserDto = BeanMapUtils.map(updateTeacherDto, BaseUser.class);
            //如果改了手机号码就进行修改
            if(!updateBaseUserDto.getPhone().equals(updateTeacherDto.getPhone())){
                try {
                    baseUserUnqiueMapper.updateUserPhoneByUserId(updateBaseUserDto.getId(),updateBaseUserDto.getPhone());
                }catch (Exception e){
                    String message=e.getMessage();
                    if (e instanceof DuplicateKeyException) {
                        Preconditions.checkArgument(false,"当前手机号码已重复请重新填写");
                    }
                }
            }
            Boolean baseUserResult = baseUserMapper.updateBaseUserInfo(updateBaseUserDto) > 0;
            TeacherDept teacherDepts = teacherDeptMapper.findTeacherBySchoolCodeAndCardNumber(updateTeacherDto.getSchoolCode(), updateTeacherDto.getCardNumber());
            teacherDeptMapper.deleteTeacherDept(updateTeacherDto.getSchoolCode(), updateTeacherDto.getCardNumber(), 0);
            for (int i = 0; i < updateTeacherDto.getTeacherDeptDtoList().size(); i++) {
                TeacherDeptDto teacherDeptDto = new TeacherDeptDto();
                teacherDeptDto.setId(teacherDepts.getId());
                teacherDeptDto.setSchoolCode(updateTeacherDto.getSchoolCode());
                teacherDeptDto.setCardNumber(updateTeacherDto.getCardNumber());
                teacherDeptDto.setTeacherId(updateTeacherDto.getId());
                teacherDeptDto.setOperator(updateTeacherDto.getOperator());
                teacherDeptDto.setOperatorName(updateTeacherDto.getOperatorName());
                String[] ids = updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptIds().split(",");
                String[] names = updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptNames().split("\\/");
                teacherDeptDto.setDeptId(Long.parseLong(ids[ids.length - 1]));
                teacherDeptDto.setDeptName(names[names.length - 1]);
                teacherDeptDto.setDeptIds(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptIds());
                teacherDeptDto.setDeptNames(updateTeacherDto.getTeacherDeptDtoList().get(i).getDeptNames().trim());
                TeacherDept teacherDept = BeanMapUtils.map(teacherDeptDto, TeacherDept.class);
                Boolean teaDeptResult = teacherDeptMapper.insert(teacherDept) > 0;
                //推送消息至MQ
                if (teaDeptResult) {
                    JSONObject mesData = new JSONObject();
                    mesData.put("tableName", "t_teacher_dept");
                    mesData.put("data", teacherDept);
                    JSONObject data=mesData.getJSONObject("data");
                    data.put("del_flag","0");
                    mesData.put("data", data);
                    Message teacherDeptMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacherDept, String.valueOf(System.currentTimeMillis()), mesData.toJSONString().getBytes());
                    defaultMQProducer.send(teacherDeptMsg);
                }
            }
            //修改时判断用户是否已经激活
            if (updateTeacherDto.getActivate().equals(Byte.parseByte("2"))) {
                SynUserInfoRequest synUserInfoRequest = new SynUserInfoRequest();
                synUserInfoRequest.setSchool_code(updateTeacherDto.getSchoolCode());
                synUserInfoRequest.setCard_number(updateTeacherDto.getCardNumber());
                synUserInfoRequest.setName(updateTeacherDto.getName());
                synUserInfoRequest.setGender(updateTeacherDto.getGender() == 1 ? "男" : "女");
                String names[] = updateTeacherDto.getTeacherDeptDtoList().get(0).getDeptNames().split("\\/");
                //判断是K12 还是中高职
                if(names.length>0){
                    if (updateTeacherDto.getSchoolType() >= Byte.parseByte("4")) {
                        synUserInfoRequest.setCollege(names[names.length - 1]);
                    }
                }
                synUserInfoRequest.setOrganization(updateTeacherDto.getTeacherDeptDtoList().get(0).getDeptNames());
                synUserInfoRequest.setTelephone(updateTeacherDto.getPhone());
                synUserInfoRequest.setCard_type("1");
                synUserInfoRequest.setId_card(updateTeacherDto.getIdcard());
                synUserInfoRequest.setHead_image(updateTeacherDto.getImage());
                synUserInfoRequest.setIdentity_type(AuthenticationConstant.TEACHER);
                synUserInfoRequest.setIdentity_title(updateTeacherDto.getPosition());
                synUserInfoRequest.setNation(updateTeacherDto.getNationName());
                synUserInfoRequest.setQq(updateTeacherDto.getQqNumber());
                synUserInfoRequest.setAddress(updateTeacherDto.getAdress());
                synUserInfoRequest.setEmail(updateTeacherDto.getEmail());
                synUserInfoRequest.setPhysical_card_number(updateTeacherDto.getPhysicalNumber());
                synUserInfoRequest.setPhysical_chip_number(updateTeacherDto.getPhysicalChipNumber());
                synUserInfoRequest.setDorm_number(updateTeacherDto.getDormitoryAddress());
                synUserInfoRequest.setCampus(updateTeacherDto.getCampusName());
                String result = AuthenticationUtils.synUserInfo(synUserInfoRequest, updateTeacherDto.getAppKey(), updateTeacherDto.getAppSecret());
                JSONObject jsonObject = JSONObject.parseObject(result);
                if (!jsonObject.get("errcode").equals(0)) {
                    throw new Exception("教职工信息同步失败,返回的错误码" + jsonObject.get("errcode") + "，同步教职工卡号=" + updateTeacherDto.getCardNumber() + "学校名称=" + updateTeacherDto.getSchoolName());
                }
            }
            //将修改的信息推送至rocketMQ
            if (teaResult && baseUserResult) {
                JSONObject mesData = new JSONObject();
                mesData.put("tableName", "t_teacher");
                mesData.put("data", teacher);
                JSONObject data=mesData.getJSONObject("data");
                data.put("delFlag",0);
                mesData.put("data", data);
                Message teacherMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacher, mesData.toJSONString().getBytes());
                defaultMQProducer.send(teacherMsg);
                mesData.put("tableName", "t_base_user");
                mesData.put("data", updateBaseUserDto);
                JSONObject data1=mesData.getJSONObject("data");
                data1.put("delFlag",0);
                mesData.put("data", data1);
                Message baseUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_baseUser, mesData.toJSONString().getBytes());
                defaultMQProducer.send(baseUserMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info("更新教职工信息失败，错误信息:" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void batchSaveTeacherInfo(List<AddTeacherDto> teacherList) {

        List<Teacher> saveTeacherList = BeanMapUtils.mapList(teacherList, Teacher.class);
        List<BaseUser> baseUserList = BeanMapUtils.mapList(teacherList, BaseUser.class);
        for (int i = 0; i < baseUserList.size(); i++) {
            saveTeacherList.get(i).setId(snowflakeIdWorker.nextId());
            baseUserList.get(i).setUserType(2);
            baseUserList.get(i).setUserId(teacherList.get(i).getId());
            baseUserList.get(i).setId(snowflakeIdWorker.nextId());
        }
        Boolean teaResult = teacherMapper.batchSaveTeacherInfo(saveTeacherList) > 0;
        Boolean baseUserResult = baseUserMapper.batchSaveBaseUserInfo(baseUserList) > 0;
        try {
            //推送至MQ
            if (teaResult && baseUserResult) {
                JSONObject mesData = new JSONObject();
                mesData.put("tableName", "t_teacher");
                mesData.put("data", saveTeacherList);
                JSONArray data=mesData.getJSONArray("data");
                Map<String,Object> map=new HashMap<>();
                map.put("delFlag",1);
                data.add(data.size()-1,map);
                mesData.put("data", data);
                Message teacherMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_teacher, mesData.toJSONString().getBytes());
                defaultMQProducer.send(teacherMsg);
                mesData.put("tableName", "t_base_user");
                mesData.put("data", baseUserList);
                JSONArray data1=mesData.getJSONArray("data");
                Map<String,Object> map1=new HashMap<>();
                map1.put("delFlag",1);
                data.add(data.size()-1,map1);
                mesData.put("data", data1);
                Message baseUserMsg = new Message(RocketMqConstrants.Topic.bdxhTopic, RocketMqConstrants.Tags.userInfoTag_baseUser, mesData.toJSONString().getBytes());
                defaultMQProducer.send(baseUserMsg);
            }
        } catch (Exception e) {
            log.info("推送教职工信息失败，错误信息:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<String> queryTeacherCardNumberBySchoolCode(String schoolCode) {
        return teacherMapper.queryTeacherCardNumberBySchoolCode(schoolCode);
    }

    @Override
    public List<Teacher> findTeacherInfoByDeptOrg(String schoolCode, String parentIds) {
        return teacherMapper.findTeacherInfoByDeptOrg(schoolCode, parentIds);
    }

    @Override
    public void updateSchoolName(String schoolCode, String schoolName) {
        teacherMapper.updateSchoolName(schoolCode, schoolName);
    }
}
