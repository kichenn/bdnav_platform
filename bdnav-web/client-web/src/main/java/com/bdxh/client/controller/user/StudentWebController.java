/**
 * Copyright (C), 2019-2019
 * FileName: StudentController
 * Author:   binzh
 * Date:     2019/3/11 15:54
 * Description: TOOO
 * History:
 */
package com.bdxh.client.controller.user;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/studentWeb")
@Validated
@Slf4j
@Api(value = "学生信息交互API", tags = "学生信息交互API")
public class StudentWebController {

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private SchoolOrgControllerClient schoolOrgControllerClient;
    @Autowired
    private SinglePermissionControllerClient singlePermissionControllerClient;

    @Autowired
    private BaseUserControllerClient baseUserControllerClient;

    //图片路径
    private static final String IMG_URL="http://bdnav-1258570075-1258570075.cos.ap-guangzhou.myqcloud.com/data/20190416_be0c86bea84d477f814e797d1fa51378.jpg?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDmhZcOvMyaVdNQZoBXw5xZtqVR6SqdIK6%26q-sign-time%3D1555411088%3B1870771088%26q-key-time%3D1555411088%3B1870771088%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3Dbc7a67e7b405390b739288b55f676ab640094649";
    //图片名称
    private static final String IMG_NAME="20190416_be0c86bea84d477f814e797d1fa51378.jpg";
    //学院类型
    private static final Byte COLLEGE_TYPE=1;
    //系类型
    private static final Byte FACULTY_TYPE=2;
    //专业类型
    private static final Byte PROFESSION_TYPE=3;
    //年纪类型
    private static final Byte GRADE_TYPE=4;
    //班级类型
    private static final Byte CLASS_TYPE=5;
    /**
     * 根据条件查询所有学生信息接口
     * @param studentQueryDto
     * @return
     */
    @CrossOrigin
    @RequestMapping(value="/queryStudentListPage",method = RequestMethod.POST)
    @ApiOperation("查询所有学生信息接口")
    public Object queryStudentListPage(@RequestBody StudentQueryDto studentQueryDto){
        try {
            SchoolUser user= SecurityUtils.getCurrentUser();
            studentQueryDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper =studentControllerClient.queryStudentListPage(studentQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增学生信息
     * @param addStudentDto
     * @param bindingResult
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="新增学生信息", response = Boolean.class)
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Object addStudent(@Valid @RequestBody AddStudentDto addStudentDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            if(addStudentDto.getImage().equals("")||addStudentDto.getImageName().equals("")){
                addStudentDto.setImageName(IMG_NAME);
                addStudentDto.setImage(IMG_URL);
            }
            SchoolUser user=SecurityUtils.getCurrentUser();
            addStudentDto.setOperator(user.getId());
            addStudentDto.setOperatorName(user.getUserName());
            SchoolInfoVo school= schoolControllerClient.findSchoolById(user.getSchoolId()).getResult();
            addStudentDto.setSchoolCode(user.getSchoolCode());
            addStudentDto.setSchoolId(school.getId()+"");
            addStudentDto.setSchoolName(school.getSchoolName());
            BaseUser baseUser=baseUserControllerClient.queryBaseUserBySchoolCodeAndCardNumber(addStudentDto.getSchoolCode(),addStudentDto.getCardNumber()).getResult();
            if(null!=baseUser){
                return WrapMapper.error("当前学校已存在相同学生学号");
            }
            SchoolOrgQueryDto schoolOrgQueryDto = new SchoolOrgQueryDto();
            String orgIds[] = addStudentDto.getClassIds().split(",");
            for (int i = 0; i < orgIds.length; i++) {
                String orgId = orgIds[i];
                SchoolOrg schoolOrg =schoolOrgControllerClient.findSchoolOrgInfo(Long.parseLong(orgId)).getResult();
                if (null != schoolOrg) {
                    if (schoolOrg.getOrgType() == COLLEGE_TYPE) {
                        addStudentDto.setCollegeName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == FACULTY_TYPE) {
                        addStudentDto.setFacultyName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == PROFESSION_TYPE) {
                        addStudentDto.setProfessionName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == GRADE_TYPE) {
                        addStudentDto.setGradeName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == CLASS_TYPE) {
                        addStudentDto.setClassName(schoolOrg.getOrgName());
                        addStudentDto.setClassId(schoolOrg.getId());
                    }
                } else{
                   return WrapMapper.error();
               }
            }
            Wrapper wrapper=studentControllerClient.addStudent(addStudentDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除学生信息
     * @param cardNumber
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="删除学生信息", response = Boolean.class)
    @RequestMapping(value = "/removeStudent",method = RequestMethod.POST)
    public Object removeStudent(@RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber,
                                @RequestParam(name = "image" ) String image){
        try{
            //删除腾讯云的信息
            if(null!=image){
                FileOperationUtils.deleteFile(image, null);
            }
            SchoolUser user=SecurityUtils.getCurrentUser();
            SinglePermissionQueryDto singlePermissionQueryDto=new SinglePermissionQueryDto();
            singlePermissionQueryDto.setCardNumber(cardNumber);
            singlePermissionQueryDto.setSchoolCode(user.getSchoolCode());
            PageInfo pageInfo= singlePermissionControllerClient.findSinglePermissionInConditionPage(singlePermissionQueryDto).getResult();
            if(pageInfo.getTotal()>0){
                return WrapMapper.error("请先删除卡号为\"+cardNumber+\"的学生门禁单信息");
            }
            Wrapper wrapper=studentControllerClient.removeStudent(user.getSchoolCode(),cardNumber);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 批量删除学生信息
     * @param cardNumbers
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="批量删除学生信息", response = Boolean.class)
    @RequestMapping(value = "/removeStudents",method = RequestMethod.POST)
    public Object removeStudents(@RequestParam(name = "cardNumbers") @NotNull(message="学生微校卡号不能为空")String cardNumbers,
                                 @RequestParam(name = "images" ) String images){
        try{
            String[]imageAttr =images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if(null!=imageAttr[i]) {
                    FileOperationUtils.deleteFile(imageAttr[i], null);
                }
            }
            SchoolUser user=SecurityUtils.getCurrentUser();
            String schoolCodes="";
            int numberLength=cardNumbers.split(",").length;
            for (int i = 0; i < numberLength; i++){
                if(numberLength==(i+1)){
                    schoolCodes+=user.getSchoolCode();
                }else{
                    schoolCodes+=user.getSchoolCode()+",";
                }

            }
            Wrapper wrapper=studentControllerClient.removeStudents(schoolCodes,cardNumbers);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改学生信息
     * @param updateStudentDto
     * @param bindingResult
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="修改学生信息", response = Boolean.class)
    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public Object updateStudent(@Valid @RequestBody UpdateStudentDto updateStudentDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            SchoolUser user= SecurityUtils.getCurrentUser();
            updateStudentDto.setOperator(user.getId());
            updateStudentDto.setOperatorName(user.getUserName());
            updateStudentDto.setSchoolCode(user.getSchoolCode());
            StudentVo studentVo=(StudentVo)studentControllerClient.queryStudentInfo(user.getSchoolCode(),updateStudentDto.getCardNumber()).getResult();
            //判断是否已激活 已激活需要同步微校未激活修改不需要同步微校
            if(studentVo.getActivate().equals(Byte.parseByte("2"))) {
                updateStudentDto.setActivate(studentVo.getActivate());
                School school = schoolControllerClient.findSchoolBySchoolCode(studentVo.getSchoolCode()).getResult();
                updateStudentDto.setAppKey(school.getAppKey());
                updateStudentDto.setAppSecret(school.getAppSecret());
                updateStudentDto.setSchoolType(school.getSchoolType());
            }
            if(null!=studentVo.getImage()){
            if (!studentVo.getImage().equals(updateStudentDto.getImage())) {
                //删除腾讯云的以前图片
                if(!studentVo.getImageName().equals(IMG_NAME)){
                    FileOperationUtils.deleteFile(studentVo.getImageName(), null);
                }
            }
           }
            SchoolOrgQueryDto schoolOrgQueryDto = new SchoolOrgQueryDto();
            String orgIds[] = updateStudentDto.getClassIds().split(",");
            for (int i = 0; i < orgIds.length; i++) {
                String orgId = orgIds[i];
                SchoolOrg schoolOrg =schoolOrgControllerClient.findSchoolOrgInfo(Long.parseLong(orgId)).getResult();
                if (null != schoolOrg) {
                    if (schoolOrg.getOrgType() == COLLEGE_TYPE) {
                        updateStudentDto.setCollegeName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == FACULTY_TYPE) {
                        updateStudentDto.setFacultyName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == PROFESSION_TYPE) {
                        updateStudentDto.setProfessionName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == GRADE_TYPE) {
                        updateStudentDto.setGradeName(schoolOrg.getOrgName());
                    } else if (schoolOrg.getOrgType() == CLASS_TYPE) {
                        updateStudentDto.setClassName(schoolOrg.getOrgName());
                        updateStudentDto.setClassId(schoolOrg.getId());
                    }
                }else{
                    return WrapMapper.error();
                }
            }
            Wrapper  wrapper=studentControllerClient.updateStudent(updateStudentDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个学生信息
     * @param cardNumber
     * @return family
     */
    @CrossOrigin
    @ApiOperation(value="查询单个学生信息")
    @RequestMapping(value ="/queryStudentInfo",method = RequestMethod.GET)
    public Object queryStudentInfo(@RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber) {
        try {
            SchoolUser user= SecurityUtils.getCurrentUser();
            StudentVo studentVo= studentControllerClient.queryStudentInfo(user.getSchoolCode(),cardNumber).getResult();
            return  WrapMapper.ok(studentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="导入学生数据", response = Boolean.class)
    @RequestMapping(value="/importStudentInfo",method = RequestMethod.POST)
    public Object importStudentInfo(@RequestParam("studentFile") MultipartFile file) throws IOException {
       try {
           if (file.isEmpty()) {
               return  WrapMapper.error("上传失败，请选择文件");
           }
           long start=System.currentTimeMillis();
           List<String[]> studentList= ExcelImportUtil.readExcelNums(file,0);
           List<AddStudentDto> students=new ArrayList<>();
           SchoolUser user=SecurityUtils.getCurrentUser();
           Long uId=user.getId();
           String uName=user.getUserName();
           BaseUserUnqiue baseUserUnqiue=new BaseUserUnqiue();
           baseUserUnqiue.setSchoolCode(user.getSchoolCode());
           List<String> phoneList=baseUserControllerClient.queryAllUserPhone(baseUserUnqiue).getResult();
           //判断得出在同一个班级直接从缓存中拉取数据
           Wrapper  schoolWrapper = schoolControllerClient.findSchoolBySchoolCode(user.getSchoolCode());
           Wrapper  schoolOrgWrapper = schoolOrgControllerClient.findClassOrgList(user.getSchoolId());
           Wrapper  studentWeapper =studentControllerClient.queryCardNumberBySchoolCode(user.getSchoolCode());
           List<SchoolOrg> schoolClassList = (List<SchoolOrg>) schoolOrgWrapper.getResult();
           School school = (School) schoolWrapper.getResult();
           List<String>  cardNumberList=(List<String>) studentWeapper.getResult();
           for (int i=1;i<studentList.size();i++) {
               String[] columns = studentList.get(i);
               if (StringUtils.isNotBlank(studentList.get(i)[0])) {
                   //判断当前学校是否有重复学号
                   if(null!=cardNumberList) {
                       for (int j = 0; j < cardNumberList.size(); j++) {
                           if (columns[11].equals(cardNumberList.get(j))) {
                               return WrapMapper.error("请检查第" + i + "条数据学号已存在");
                           }
                       }
                   }else{
                        cardNumberList=new ArrayList<>();
                   }
                   //导入时判断手机号是否存在
                   for (String phone : phoneList) {
                       if(columns[10].equals(phone)){
                           return  WrapMapper.error("请检查第" + i + "条手机号已存在或者重复");
                       }
                   }
                   phoneList.add(columns[10]);
                   AddStudentDto student = new AddStudentDto();
                   student.setCardNumber(columns[11]);
                   cardNumberList.add(columns[11]);
                   student.setSchoolName(school.getSchoolName());
                   student.setSchoolId(school.getId()+"");
                   student.setSchoolCode(school.getSchoolCode());
                   student.setActivate(Byte.valueOf("1"));
                   student.setCampusName(columns[0]);
                   student.setCollegeName(columns[1]);
                   student.setFacultyName(columns[2]);
                   student.setProfessionName(columns[3]);
                   student.setGradeName(columns[4]);
                   student.setClassName(columns[5]);
                   student.setAdress(columns[6]);
                   student.setName(columns[7]);
                   student.setBirth(columns[8]);
                   student.setGender(columns[9].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                   student.setPhone(columns[10]);
                   student.setIdcard(columns[12]);
                   student.setRemark(columns[13]);
                   student.setImageName(IMG_NAME);
                   student.setImage(IMG_URL);
                   student.setOperator(uId);
                   student.setOperatorName(uName);
                   String classNames = "";
                   if (!("").equals(student.getCollegeName()) && null != student.getCollegeName()) {
                       classNames += student.getCollegeName() + "/";
                   }
                   if (!("").equals(student.getFacultyName()) && null != student.getFacultyName()) {
                       classNames += student.getFacultyName() + "/";
                   }
                   if (!("").equals(student.getProfessionName()) && null != student.getProfessionName()) {
                       classNames += student.getProfessionName() + "/";
                   }
                   if (!("").equals(student.getGradeName()) && null != student.getGradeName()) {
                       classNames += student.getGradeName() + "/";
                   }
                   if (!("").equals(student.getClassName()) && null != student.getClassName()) {
                       classNames += student.getClassName();
                   }

                   String ids = "";
                   int j;
                   for (j = 0;j < schoolClassList.size(); j++) {
                       if (classNames.trim().equals(schoolClassList.get(j).getThisUrl().trim())) {
                           ids += schoolClassList.get(j).getParentIds();
                       }
                   }
                   if (ids.equals("")) {
                       return WrapMapper.error("请检查" + i + "条数据院系组织填写是否正确");
                   }
                   //拼接ClassNames字段
                   student.setClassIds(ids);
                   String[] idarr = ids.split(",");
                   student.setClassId(Long.parseLong(idarr[idarr.length - 1]));
                   student.setClassNames(classNames);
                   students.add(student);
                   log.info("已经添加完第"+i+"条");
               }
           }
           studentControllerClient.batchSaveStudentInfo(students);
           long end=System.currentTimeMillis();
           log.info("导入数据总计用时："+(end-start));
           return  WrapMapper.ok("导入完成");
       }catch (Exception e){
           log.error(e.getMessage());
           return WrapMapper.error(e.getMessage());
       }
    }


}
