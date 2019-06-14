/**
 * Copyright (C), 2019-2019
 * FileName: StudentController
 * Author:   binzh
 * Date:     2019/3/11 15:54
 * Description: TOOO
 * History:
 */
package com.bdxh.backend.controller.user;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelExportUtils;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.excel.bean.StudentExcelReportBean;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SchoolOrgQueryDto;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.entity.SchoolOrg;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SchoolOrgControllerClient;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.system.entity.User;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentExacleQueryDto;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
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
    private static final String IMG_URL = "http://bdnav-1258570075-1258570075.cos.ap-guangzhou.myqcloud.com/data/20190416_be0c86bea84d477f814e797d1fa51378.jpg?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDmhZcOvMyaVdNQZoBXw5xZtqVR6SqdIK6%26q-sign-time%3D1555411088%3B1870771088%26q-key-time%3D1555411088%3B1870771088%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3Dbc7a67e7b405390b739288b55f676ab640094649";
    //图片名称
    private static final String IMG_NAME = "20190416_be0c86bea84d477f814e797d1fa51378.jpg";
    //学院类型
    private static final Byte COLLEGE_TYPE = 1;
    //系类型
    private static final Byte FACULTY_TYPE = 2;
    //专业类型
    private static final Byte PROFESSION_TYPE = 3;
    //年纪类型
    private static final Byte GRADE_TYPE = 4;
    //班级类型
    private static final Byte CLASS_TYPE = 5;

    /**
     * 根据条件查询所有学生信息接口
     *
     * @param studentQueryDto
     * @return
     */
    @CrossOrigin
    @RequestMapping(value = "/queryStudentListPage", method = RequestMethod.POST)
    @ApiOperation("查询所有学生信息接口")
    public Object queryStudentListPage(@RequestBody StudentQueryDto studentQueryDto) {
        try {
            Wrapper wrapper = studentControllerClient.queryStudentListPage(studentQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增学生信息
     *
     * @param addStudentDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "新增学生信息")
    @RequestMapping(value = "/addStudent", method = RequestMethod.POST)
    public Object addStudent(@Valid @RequestBody AddStudentDto addStudentDto, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            if (addStudentDto.getImage().equals("") || addStudentDto.getImageName().equals("")) {
                addStudentDto.setImageName(IMG_NAME);
                addStudentDto.setImage(IMG_URL);
            }
            User user = SecurityUtils.getCurrentUser();
            addStudentDto.setOperator(user.getId());
            addStudentDto.setOperatorName(user.getUserName());
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
                } else {
                    return WrapMapper.error();
                }
            }
            Wrapper wrapper = studentControllerClient.addStudent(addStudentDto);

            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除学生信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "删除学生信息")
    @RequestMapping(value = "/removeStudent", method = RequestMethod.POST)
    public Object removeStudent(@RequestParam(name = "schoolCode") @NotNull(message = "学生学校Code不能为空") String schoolCode,
                                @RequestParam(name = "cardNumber") @NotNull(message = "学生微校卡号不能为空") String cardNumber,
                                @RequestParam(name = "image") String image) {
        try {
            //删除腾讯云的信息
            if (null != image) {
                if (!image.equals(IMG_NAME)) {
                    FileOperationUtils.deleteFile(image, null);
                }
            }
            SinglePermissionQueryDto singlePermissionQueryDto = new SinglePermissionQueryDto();
            singlePermissionQueryDto.setCardNumber(cardNumber);
            singlePermissionQueryDto.setSchoolCode(schoolCode);
            PageInfo pageInfos = singlePermissionControllerClient.findSinglePermissionInConditionPage(singlePermissionQueryDto).getResult();
            if (pageInfos.getTotal() > 0) {
                return WrapMapper.error("请先删除卡号为" + cardNumber + "的学生门禁单信息");
            }
            Wrapper wrapper = studentControllerClient.removeStudent(schoolCode, cardNumber);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除学生信息
     *
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "批量删除学生信息")
    @RequestMapping(value = "/removeStudents", method = RequestMethod.POST)
    public Object removeStudents(@RequestParam(name = "schoolCodes") @NotNull(message = "学生学校Code不能为空") String schoolCodes,
                                 @RequestParam(name = "cardNumbers") @NotNull(message = "学生微校卡号不能为空") String cardNumbers,
                                 @RequestParam(name = "images") String images) {
        try {
            String[] imageAttr = images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if (null != imageAttr[i]) {
                    if (!imageAttr[i].equals(IMG_NAME)) {
                        FileOperationUtils.deleteFile(imageAttr[i], null);
                    }
                }
            }
            Wrapper wrapper = studentControllerClient.removeStudents(schoolCodes, cardNumbers);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改学生信息
     *
     * @param updateStudentDto
     * @param bindingResult
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "修改学生信息")
    @RequestMapping(value = "/updateStudent", method = RequestMethod.POST)
    public Object updateStudent(@Valid @RequestBody UpdateStudentDto updateStudentDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            //获取操作人信息
            User user = SecurityUtils.getCurrentUser();
            updateStudentDto.setOperator(user.getId());
            updateStudentDto.setOperatorName(user.getUserName());
            //获取当前修改学生原来的信息
            StudentVo studentVo = (StudentVo) studentControllerClient.queryStudentInfo(updateStudentDto.getSchoolCode(), updateStudentDto.getCardNumber()).getResult();
            if (null != studentVo.getImage()) {
                if (!studentVo.getImage().equals(updateStudentDto.getImage())) {
                    //删除腾讯云的以前图片
                    if (!studentVo.getImageName().equals(IMG_NAME)) {
                        FileOperationUtils.deleteFile(studentVo.getImageName(), null);
                    }
                }
            }
            //获取学校信息
            //判断是否已激活 已激活需要同步微校未激活修改不需要同步微校
            if (studentVo.getActivate().equals(Byte.parseByte("2"))) {
                School school = schoolControllerClient.findSchoolBySchoolCode(studentVo.getSchoolCode()).getResult();
                updateStudentDto.setAppKey(school.getSchoolKey());
                updateStudentDto.setAppSecret(school.getSchoolSecret());
                updateStudentDto.setSchoolType(school.getSchoolType());
                updateStudentDto.setActivate(studentVo.getActivate());
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
                } else {
                    return WrapMapper.error();
                }
            }
            Wrapper wrapper = studentControllerClient.updateStudent(updateStudentDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个学生信息
     *
     * @param schoolCode cardNumber
     * @return family
     */
    @CrossOrigin
    @ApiOperation(value = "查询单个学生信息")
    @RequestMapping(value = "/queryStudentInfo", method = RequestMethod.GET)
    public Object queryStudentInfo(@RequestParam(name = "schoolCode") @NotNull(message = "学生学校Code不能为空") String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message = "学生微校卡号不能为空") String cardNumber) {
        try {
            StudentVo studentVo = studentControllerClient.queryStudentInfo(schoolCode, cardNumber).getResult();
            return WrapMapper.ok(studentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation("导入学生数据")
    @RequestMapping(value = "/importStudentInfo", method = RequestMethod.POST)
    public Object importStudentInfo(@RequestParam("studentFile") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return WrapMapper.error("上传失败，请选择文件");
            }
            long start = System.currentTimeMillis();
            List<String[]> studentList = ExcelImportUtil.readExcelNums(file, 0);
            List<AddStudentDto> students = new ArrayList<>();
            List<SchoolOrg> schoolClassList = new ArrayList<>();
            List<String> cardNumberList = new ArrayList<>();
            List<String> phoneList = new ArrayList<>();
            School school = new School();
            User user = SecurityUtils.getCurrentUser();
            Long uId = user.getId();
            String uName = user.getUserName();

            for (int i = 1; i < studentList.size(); i++) {
                String[] columns = studentList.get(i);
                if (!studentList.get(i)[0].equals(i - 1 >= studentList.size() ? studentList.get(studentList.size())[0] : studentList.get(i - 1)[0]) || i == 1) {
                    Wrapper schoolWrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                    school = (School) schoolWrapper.getResult();
                    Wrapper schoolOrgWrapper = schoolOrgControllerClient.findClassOrgList(school.getId());
                    Wrapper studentWeapper = baseUserControllerClient.findSchoolNumberBySchool(columns[0]);
                    schoolClassList = (List<SchoolOrg>) schoolOrgWrapper.getResult();
                    cardNumberList = (List<String>) studentWeapper.getResult();
                    BaseUserUnqiue baseUserUnqiue=new BaseUserUnqiue();
                    baseUserUnqiue.setSchoolCode(columns[0]);
                    phoneList= baseUserControllerClient.queryAllUserPhone(baseUserUnqiue).getResult();
                }
                if (null != school) {
                    if (StringUtils.isNotBlank(studentList.get(i)[0])) {
                        //判断当前学校是否有重复学号
                        if (null != cardNumberList) {
                            for (int j = 0; j < cardNumberList.size(); j++) {
                                if (columns[12].equals(cardNumberList.get(j))) {
                                    return WrapMapper.error("请检查第" + i + "条数据学号已存在");
                                }
                            }
                        } else {
                            cardNumberList = new ArrayList<>();
                        }
                        //导入时判断手机号是否存在
                        for (String phone : phoneList) {
                            if (columns[11].equals(phone)) {
                                return WrapMapper.error("请检查第" + i + "条手机号已存在");
                            }
                        }
                        phoneList.add(columns[11]);
                        AddStudentDto student = new AddStudentDto();
                        student.setCardNumber(columns[12]);
                        cardNumberList.add(columns[12]);
                        student.setSchoolName(school.getSchoolName());
                        student.setSchoolId(school.getId() + "");
                        student.setSchoolCode(school.getSchoolCode());
                        student.setActivate(Byte.valueOf("1"));
                        student.setCampusName(columns[1]);
                        student.setCollegeName(columns[2]);
                        student.setFacultyName(columns[3]);
                        student.setProfessionName(columns[4]);
                        student.setGradeName(columns[5]);
                        student.setClassName(columns[6]);
                        student.setAdress(columns[7]);
                        student.setName(columns[8]);
                        student.setBirth(columns[9]);
                        student.setGender(columns[10].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                        student.setPhone(columns[11]);
                        student.setIdcard(columns[13]);
                        student.setRemark(columns[14]);
                        student.setOperator(uId);
                        student.setOperatorName(uName);
                        student.setImageName(IMG_NAME);
                        student.setImage(IMG_URL);
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
                        for (j = 0; j < schoolClassList.size(); j++) {
                            if (classNames.trim().equals(schoolClassList.get(j).getThisUrl().trim())) {
                                ids += schoolClassList.get(j).getParentIds();
                                break;
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
                        log.info("已经添加完第" + i + "条");
                    } else {
                        return WrapMapper.ok("当前EXACLE文档为NULL，请检查");
                    }
                } else {
                    return WrapMapper.ok("不存在当前学校");
                }
            }
            studentControllerClient.batchSaveStudentInfo(students);
            long end = System.currentTimeMillis();
            log.info("总计用时：" + (end - start) + "毫秒");
            return WrapMapper.ok("导入完成");
        } catch (Exception e) {
            log.error(e.getMessage());
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation("导出学生数据")
    @RequestMapping(value = "/downloadStudentExacle", method = RequestMethod.GET)
    public Object downloadStudentExacle(@ModelAttribute StudentExacleQueryDto studentExacleQueryDto) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        List<Student> students = new ArrayList<>();
        //导出方式(1分页学生信息导出，2：选择条件导出，3：全部选择信息导出)
        StudentQueryDto studentQueryDto = new StudentQueryDto();
        switch (studentExacleQueryDto.getIsBy()) {
            case 1:
                studentQueryDto.setPageSize(studentExacleQueryDto.getPageSize());
                studentQueryDto.setPageNum(studentExacleQueryDto.getPageNum());
                students = BeanMapUtils.mapList((List<StudentVo>) studentControllerClient.queryStudentListPage(studentQueryDto).getResult(), Student.class);
                break;
            case 2:
                students = studentControllerClient.findStudentInfo(studentQueryDto).getResult();
                break;
            case 3:
                students = studentControllerClient.findAllStudent().getResult();
                break;
            default:
                return WrapMapper.error("不存在的选项，请检查");
        }
        if (CollectionUtils.isEmpty(students)) {
            return WrapMapper.error("抱歉，暂无学生数据");
        }
        List<StudentExcelReportBean> studentExcelReportBeanList = students.stream().map(e -> {
            StudentExcelReportBean studentExcelReportBean = BeanMapUtils.map(e, StudentExcelReportBean.class);
            if (e.getGender().equals(Byte.parseByte("1"))) {
                studentExcelReportBean.setGender("男");
            } else {
                studentExcelReportBean.setGender("女");
            }
            if (e.getActivate().equals(Byte.parseByte("1"))) {
                studentExcelReportBean.setActivate("未激活");
            } else {
                studentExcelReportBean.setActivate("已激活");
            }
            return studentExcelReportBean;
        }).collect(Collectors.toList());
        try (OutputStream out = response.getOutputStream()) {
            String fileName = URLEncoder.encode("学生信息表格", StandardCharsets.UTF_8.toString());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"; filename*=utf-8''" + fileName);
            ExcelExportUtils.getInstance().exportObjects2Excel(studentExcelReportBeanList, StudentExcelReportBean.class, true, "北斗星航", true, out);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("导出失败：" + e.getMessage());
        }
        return WrapMapper.ok(true);
    }


}
