package com.bdxh.weixiao.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.ali.sms.constant.AliyunSmsConstants;
import com.bdxh.common.helper.ali.sms.enums.SmsTempletEnum;
import com.bdxh.common.helper.ali.sms.utils.SmsUtil;
import com.bdxh.common.utils.RandomUtil;
import com.bdxh.common.utils.ValidatorUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyStudentDto;
import com.bdxh.user.dto.FamilyStudentQueryDto;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.FamilyStudentControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.FamilyStudentVo;
import com.bdxh.user.vo.FamilyVo;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.weixiao.configration.redis.RedisUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
/**
 * @description:
 * @author: binzh
 * @create: 2019-04-22 15:57
 **/
@RestController
@RequestMapping("/familyStudent")
@Validated
@Slf4j
@Api(value = "子女关系----微校家长学生关系API", tags = "子女关系----微校家长学生关系API")
public class FamilyStudentWebController {

    @Autowired
    private FamilyStudentControllerClient familyStudentControllerClient;

    @Autowired
    private FamilyControllerClient familyControllerClient;

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private RedisUtil redisUtil;
    /**
     * 子女关系----家长绑定孩子
     *
     * @param studentName
     * @param studentCardNumber
     * @param relation
     * @param phone
     * @return
     */
    @ApiOperation(value = "子女关系----家长绑定孩子接口")
    @RequestMapping(value = "/bindingStudent", method = RequestMethod.POST)
    public Object bindingStudent(@RequestParam("studentName")String  studentName,
                                 @RequestParam("studentCardNumber")String studentCardNumber,
                                 @RequestParam("relation")String relation,
                                 @RequestParam("phone")String phone,
                                 @RequestParam("schoolCode")String schoolCode,
                                 @RequestParam("code")String code) {
        try {
            //判断手机验证码是否正确
            String saveCode=redisUtil.get(AliyunSmsConstants.CodeConstants.CAPTCHA_PREFIX +phone);
            if(!code.equals(saveCode)){
                return WrapMapper.error("手机验证码错误");
            }
            String cardNumber="20190516002";
            FamilyVo familyVo=familyControllerClient.queryFamilyInfo(schoolCode, cardNumber).getResult();
            //判断是否存在对呀学号的学生
            StudentVo studentVo=studentControllerClient.queryStudentInfo(schoolCode,studentCardNumber).getResult();
            if(null==studentVo){
                return WrapMapper.error("不存在该学生");
            }
            //判断当前学生是否已存在绑定关系
            FamilyStudentQueryDto familyStudentQueryDto = new FamilyStudentQueryDto();
            familyStudentQueryDto.setStudentNumber(studentCardNumber);
            familyStudentQueryDto.setSchoolCode(schoolCode);
            Wrapper wrapper = familyStudentControllerClient.queryAllFamilyStudent(familyStudentQueryDto);
            PageInfo pageInfo = (PageInfo) wrapper.getResult();
            if (pageInfo.getTotal() != 0) {
                return WrapMapper.error(studentName + "已存在绑定关系");
            }
            AddFamilyStudentDto addFamilyStudentDto=new AddFamilyStudentDto();
            addFamilyStudentDto.setSchoolId(Long.parseLong(familyVo.getSchoolId()));
            addFamilyStudentDto.setSchoolCode(schoolCode);
            addFamilyStudentDto.setCardNumber(cardNumber);
            addFamilyStudentDto.setFamilyId(Long.parseLong(familyVo.getId()));
            addFamilyStudentDto.setStudentId(studentVo.getSId());
            addFamilyStudentDto.setStudentName(studentName);
            addFamilyStudentDto.setStudentNumber(studentCardNumber);
            addFamilyStudentDto.setRelation(relation);
            FamilyVo family=familyControllerClient.queryFamilyInfo(schoolCode,cardNumber).getResult();
            addFamilyStudentDto.setOperator(Long.parseLong(family.getId()));
            addFamilyStudentDto.setOperatorName(family.getName());
            Wrapper wrappers = familyStudentControllerClient.bindingStudent(addFamilyStudentDto);
            return wrappers;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 子女关系----删除学生家长绑定关系
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation(value = "子女关系----删除学生家长绑定关系")
    @RequestMapping(value = "/removeFamilyOrStudent", method = RequestMethod.GET)
    public Object removeFamilyOrStudent(@RequestParam(name = "schoolCode") @NotNull(message = "学校Code不能为空") String schoolCode,
                                        @RequestParam(name = "cardNumber") @NotNull(message = "微校卡号不能为空") String cardNumber,
                                        @RequestParam(name = "id") @NotNull(message = "id不能为空") String id) {
        try {
            Wrapper wrapper = familyStudentControllerClient.removeFamilyOrStudent(schoolCode, cardNumber, id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 子女关系----家长查询孩子列表
     *
     * @param schoolCode
     * @return
     */
    @ApiOperation(value = "子女关系----家长查询孩子列表")
    @RequestMapping(value = "/familyFindStudentList", method = RequestMethod.POST)
    public Object familyFindStudentList(@RequestParam(name = "schoolCode", required = false)String schoolCode) {
        try {
            schoolCode="20110329";
            String cardNumber="20190516002";
            JSONObject jsonObject=new JSONObject();
            FamilyVo family=familyControllerClient.queryFamilyInfo(schoolCode,cardNumber).getResult();
            for (FamilyStudentVo s : family.getStudents()) {
                StudentVo student=studentControllerClient.queryStudentInfo(schoolCode,s.getSCardNumber()).getResult();
                s.setImage(student.getImage());
                s.setImageName(student.getImageName());
            }
            return WrapMapper.ok(family);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 子女关系----查询家长与孩子关系详细
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "子女关系----查询家长与孩子关系详细")
    @RequestMapping(value = "/queryFamilyStudentDetails", method = RequestMethod.POST)
    public Object queryFamilyStudentDetails(@RequestParam(name = "schoolCode", required = false)String schoolCode,
                                            @RequestParam(name = "cardNumber", required = false)String cardNumber) {
        try {
            StudentVo studentVo = studentControllerClient.queryStudentInfo(schoolCode, cardNumber).getResult();
            return WrapMapper.ok(studentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation(value = "子女关系----手机获取短信验证码")
    @RequestMapping(value = "/getPhoneCode",method = RequestMethod.POST)
    public Object getPhoneCode(@RequestParam(name="phone")@NotNull(message = "手机号码不能为空") String phone){
        return familyStudentControllerClient.getPhoneCode(phone);
    }

}