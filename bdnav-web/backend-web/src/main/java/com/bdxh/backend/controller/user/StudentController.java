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
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.system.entity.User;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
@Validated
@Slf4j
@Api(value = "学生信息交互API", tags = "学生信息交互API")
public class StudentController {

    @Autowired
    private StudentControllerClient studentControllerClient;

    @Autowired
    private SchoolControllerClient schoolControllerClient;

    @Autowired
    private SchoolClassControllerClient schoolClassControllerClient;


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
    @ApiOperation(value="新增学生信息")
    @RequestMapping(value = "/addStudent",method = RequestMethod.POST)
    public Object addStudent(@Valid @RequestBody AddStudentDto addStudentDto, BindingResult bindingResult){
        //检验参数
        if (bindingResult.hasErrors()) {

            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user=SecurityUtils.getCurrentUser();
            addStudentDto.setOperator(user.getId());
            addStudentDto.setOperatorName(user.getUserName());
            StudentVo student=(StudentVo)studentControllerClient.queryStudentInfo(addStudentDto.getSchoolCode(),addStudentDto.getCardNumber()).getResult();
            if(null!= student){
                return WrapMapper.error("当前学校已存在相同学生学号");
            }
            SchoolClass schoolClass=new SchoolClass();
            String ClassId[]=addStudentDto.getClassIds().split(",");
            for (int i = 0; i < ClassId.length; i++) {
                String s = ClassId[i];
                schoolClass.setSchoolCode(addStudentDto.getSchoolCode());
                schoolClass.setId(Long.parseLong(ClassId[i]));
               SchoolClass schoolClass1=(SchoolClass)schoolClassControllerClient.findSchoolClassBySchoolClass(schoolClass).getResult();
               if(null!=schoolClass1) {
                   if (schoolClass1.getType() == COLLEGE_TYPE) {
                       addStudentDto.setCollegeName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == FACULTY_TYPE) {
                       addStudentDto.setFacultyName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == PROFESSION_TYPE) {
                       addStudentDto.setProfessionName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == GRADE_TYPE) {
                       addStudentDto.setGradeName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == CLASS_TYPE) {
                       addStudentDto.setClassName(schoolClass1.getName());
                       addStudentDto.setClassId(schoolClass1.getId());
                   }
               }else{
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
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="删除学生信息")
    @RequestMapping(value = "/removeStudent",method = RequestMethod.POST)
    public Object removeStudent(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCode,
                               @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber,
                                @RequestParam(name = "image" ) String image){
        try{

            //删除腾讯云的信息
            if(null!=image){
                FileOperationUtils.deleteFile(image, null);
            }
            Wrapper wrapper=studentControllerClient.removeStudent(schoolCode,cardNumber);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 批量删除学生信息
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @CrossOrigin
    @ApiOperation(value="批量删除学生信息")
    @RequestMapping(value = "/removeStudents",method = RequestMethod.POST)
    public Object removeStudents(@RequestParam(name = "schoolCodes") @NotNull(message="学生学校Code不能为空")String schoolCodes,
                                @RequestParam(name = "cardNumbers") @NotNull(message="学生微校卡号不能为空")String cardNumbers,
                                 @RequestParam(name = "images" ) String images){
        try{
            String[]imageAttr =images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if(null!=imageAttr[i]) {
                    FileOperationUtils.deleteFile(imageAttr[i], null);
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
    @CrossOrigin
    @ApiOperation(value="修改学生信息")
    @RequestMapping(value = "/updateStudent",method = RequestMethod.POST)
    public Object updateStudent(@Valid @RequestBody UpdateStudentDto updateStudentDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user=SecurityUtils.getCurrentUser();
            updateStudentDto.setOperator(user.getId());
            updateStudentDto.setOperatorName(user.getUserName());
            StudentVo studentVo=(StudentVo)studentControllerClient.queryStudentInfo(updateStudentDto.getSchoolCode(),updateStudentDto.getCardNumber()).getResult();
           if(null!=studentVo.getImage()){
            if (!studentVo.getImage().equals(updateStudentDto.getImage())) {
                //删除腾讯云的以前图片
                FileOperationUtils.deleteFile(studentVo.getImage(), null);
            }
           }
            SchoolClass schoolClass=new SchoolClass();
            String ClassId[]=updateStudentDto.getClassIds().split(",");
            for (int i = 0; i < ClassId.length; i++) {
                String s = ClassId[i];
                schoolClass.setSchoolCode(updateStudentDto.getSchoolCode());
                schoolClass.setId(Long.parseLong(ClassId[i]));
                SchoolClass schoolClass1=(SchoolClass)schoolClassControllerClient.findSchoolClassBySchoolClass(schoolClass).getResult();
                if(null!=schoolClass1) {
                    if ( schoolClass1.getType() == COLLEGE_TYPE) {
                        updateStudentDto.setCollegeName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == FACULTY_TYPE) {
                        updateStudentDto.setFacultyName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == PROFESSION_TYPE) {
                        updateStudentDto.setProfessionName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == GRADE_TYPE) {
                        updateStudentDto.setGradeName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == CLASS_TYPE) {
                        updateStudentDto.setClassName(schoolClass1.getName());
                        updateStudentDto.setClassId(schoolClass1.getId());
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
     * @param  schoolCode cardNumber
     * @return family
     */
    @CrossOrigin
    @ApiOperation(value="查询单个学生信息")
    @RequestMapping(value ="/queryStudentInfo",method = RequestMethod.GET)
    public Object queryStudentInfo(@RequestParam(name = "schoolCode") @NotNull(message="学生学校Code不能为空")String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber) {
        try {
            StudentVo studentVo= studentControllerClient.queryStudentInfo(schoolCode,cardNumber).getResult();
            return  WrapMapper.ok(studentVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @CrossOrigin
    @ApiOperation("导入学生数据")
    @RequestMapping(value="/importStudentInfo",method = RequestMethod.POST)
    public Object importStudentInfo(@RequestParam("studentFile") MultipartFile file) throws IOException {
       try {
           if (file.isEmpty()) {
               return  WrapMapper.error("上传失败，请选择文件");
           }
           long start=System.currentTimeMillis();
           List<String[]> studentList= ExcelImportUtil.readExcelNums(file,0);
           List<Student> students=new ArrayList<>();
           List<SchoolClass>   schoolClassList =new ArrayList<>();
           List<String> cardNumberList=new ArrayList<>();
           School school = new School();
           User user=SecurityUtils.getCurrentUser();
           Long uId=user.getId();
           String uName=user.getUserName();
           for (int i=1;i<studentList.size();i++) {
               String[] columns = studentList.get(i);
                   if (!studentList.get(i)[0].equals(i - 1 >= studentList.size() ? studentList.get(studentList.size())[0] : studentList.get(i - 1)[0])||i==1) {
                       //判断得出在同一个班级直接从缓存中拉取数据
                        Wrapper  schoolWrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                        Wrapper  schoolClassWrapper = schoolClassControllerClient.queryClassUrlBySchoolCode(columns[0]);
                        Wrapper  studentWeapper =studentControllerClient.queryCardNumberBySchoolCode(columns[0]);
                       schoolClassList = (List<SchoolClass>) schoolClassWrapper.getResult();
                       school = (School) schoolWrapper.getResult();
                       cardNumberList=(List<String>) studentWeapper.getResult();
                   }
               if (StringUtils.isNotBlank(studentList.get(i)[0])) {
                   //判断当前学校是否有重复学号
                   if(null!=cardNumberList) {
                       for (int j = 0; j < cardNumberList.size(); j++) {
                           if (columns[12].equals(cardNumberList.get(j))) {
                               return WrapMapper.error("请检查第" + i + "条数据学号已存在");
                           }
                       }
                   }else{
                        cardNumberList=new ArrayList<>();
                   }
                   Student student = new Student();
                   student.setCardNumber(columns[12]);
                   cardNumberList.add(columns[12]);
                   student.setSchoolName(school.getSchoolName());
                   student.setSchoolId(school.getId());
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
           log.info("导入一万条数据总计用时："+(end-start));
           return  WrapMapper.ok("导入完成");
       }catch (Exception e){
           log.error(e.getMessage());
           return WrapMapper.error(e.getMessage());
       }
    }


}
