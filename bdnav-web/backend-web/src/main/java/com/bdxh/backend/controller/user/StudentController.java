/**
 * Copyright (C), 2019-2019
 * FileName: StudentController
 * Author:   binzh
 * Date:     2019/3/11 15:54
 * Description: TOOO
 * History:
 */
package com.bdxh.backend.controller.user;

import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolClass;
import com.bdxh.school.feign.SchoolClassControllerClient;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.netflix.discovery.converters.Auto;
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
import java.util.List;
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

    @Autowired
    private RedisTemplate redisTemplate;
/*    @Autowired
    private ValueOperations valueOperations;*/
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
            SchoolClass schoolClass=new SchoolClass();
            String ClassId[]=addStudentDto.getClassIds().split(",");
            for (int i = 0; i < ClassId.length; i++) {
                String s = ClassId[i];
                schoolClass.setSchoolCode(addStudentDto.getSchoolCode());
                schoolClass.setId(Long.parseLong(ClassId[i]));
               SchoolClass schoolClass1=(SchoolClass)schoolClassControllerClient.findSchoolClassBySchoolClass(schoolClass).getResult();
               if(null!=schoolClass1) {
                   if (schoolClass1.getType() == 1) {
                       addStudentDto.setCollegeName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == 2) {
                       addStudentDto.setFacultyName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == 3) {
                       addStudentDto.setProfessionName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == 4) {
                       addStudentDto.setGradeName(schoolClass1.getName());
                   } else if (schoolClass1.getType() == 5) {
                       addStudentDto.setClassName(schoolClass1.getName());
                       addStudentDto.setClassId(schoolClass1.getId());
                   }
               }else{
                   return WrapMapper.error();
               }
            }
            Wrapper wrapper=studentControllerClient.addStudent(addStudentDto);
            return WrapMapper.ok(wrapper.getResult());
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
                               @RequestParam(name = "cardNumber") @NotNull(message="学生微校卡号不能为空")String cardNumber){
        try{
            StudentVo studentVo=(StudentVo)studentControllerClient.queryStudentInfo(schoolCode,cardNumber).getResult();
            //删除腾讯云的信息
            FileOperationUtils.deleteFile(studentVo.getImage(),null);
            Wrapper wrapper=studentControllerClient.removeStudent(schoolCode,cardNumber);
            return WrapMapper.ok(wrapper.getMessage());
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
                                @RequestParam(name = "cardNumbers") @NotNull(message="学生微校卡号不能为空")String cardNumbers){
        try{
            Wrapper wrapper=studentControllerClient.removeStudents(schoolCodes,cardNumbers);
            return WrapMapper.ok(wrapper.getMessage());
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
            StudentVo studentVo=(StudentVo)studentControllerClient.queryStudentInfo(updateStudentDto.getSchoolCode(),updateStudentDto.getCardNumber()).getResult();
            if (!studentVo.getImage().equals(updateStudentDto.getImage())) {
                //删除腾讯云的以前图片
                FileOperationUtils.deleteFile(studentVo.getImage(), null);
            }
            SchoolClass schoolClass=new SchoolClass();
            String ClassId[]=updateStudentDto.getClassIds().split(",");
            for (int i = 0; i < ClassId.length; i++) {
                String s = ClassId[i];
                schoolClass.setSchoolCode(updateStudentDto.getSchoolCode());
                schoolClass.setId(Long.parseLong(ClassId[i]));
                SchoolClass schoolClass1=(SchoolClass)schoolClassControllerClient.findSchoolClassBySchoolClass(schoolClass).getResult();
                if(null!=schoolClass1) {
                    if ( schoolClass1.getType() == 1) {
                        updateStudentDto.setCollegeName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == 2) {
                        updateStudentDto.setFacultyName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == 3) {
                        updateStudentDto.setProfessionName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == 4) {
                        updateStudentDto.setGradeName(schoolClass1.getName());
                    } else if (schoolClass1.getType() == 5) {
                        updateStudentDto.setClassName(schoolClass1.getName());
                        updateStudentDto.setClassId(schoolClass1.getId());
                    }

                }else{
                    return WrapMapper.error();
                }
            }
            studentControllerClient.updateStudent(updateStudentDto);
            return WrapMapper.ok();
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
           List<String[]> studentList= ExcelImportUtil.readExcelNums(file,0);
           for (int i=1;i<studentList.size();i++) {
               String[] columns = studentList.get(i);
               AddStudentDto addStudentDto = new AddStudentDto();
               School school = new School();
               List<SchoolClass> schoolClassList = new ArrayList<>();
               if (StringUtils.isNotBlank(studentList.get(i)[0])) {
                   if (i == 1) {
                       //第一条查询数据存到缓存中
                       Wrapper schoolWrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                       Wrapper schoolClassWrapper = schoolClassControllerClient.queryClassUrlBySchoolCode(columns[0]);
                       schoolClassList = (List<SchoolClass>) schoolClassWrapper.getResult();
                       school = (School) schoolWrapper.getResult();
                       redisTemplate.opsForValue().set("schoolInfoVo", school);
                       redisTemplate.opsForValue().set("schoolClassList", schoolClassList);
                       //判断当前schoolCode是否与上一条相同
                   } else if (studentList.get(i)[0].equals(i - 1 >= studentList.size() ? studentList.get(studentList.size())[0] : studentList.get(i - 1)[0])) {
                       //判断得出在同一个班级直接从缓存中拉取数据
                       school = (School) redisTemplate.opsForValue().get("schoolInfoVo");
                       schoolClassList = (List<SchoolClass>) redisTemplate.opsForValue().get("schoolClassList");
                   } else {
                       //重新查询数据库进行缓存
                       Wrapper schoolWrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                       Wrapper schoolClassWrapper = schoolClassControllerClient.queryClassUrlBySchoolCode(columns[0]);
                       schoolClassList = (List<SchoolClass>) schoolClassWrapper.getResult();
                       school = (School) schoolWrapper.getResult();
                       redisTemplate.opsForValue().set("schoolInfoVo", school);
                       redisTemplate.opsForValue().set("schoolClassList", schoolClassList);
                   }
                   addStudentDto.setSchoolName(school.getSchoolName());
                   addStudentDto.setSchoolId(school.getId() + "");
                   addStudentDto.setSchoolCode(school.getSchoolCode());
                   addStudentDto.setCampusName(columns[1]);
                   addStudentDto.setCollegeName(columns[2]);
                   addStudentDto.setFacultyName(columns[3]);
                   addStudentDto.setProfessionName(columns[4]);
                   addStudentDto.setGradeName(columns[5]);
                   addStudentDto.setClassName(columns[6]);
                   addStudentDto.setAdress(columns[7]);
                   addStudentDto.setName(columns[8]);
                   addStudentDto.setGender(columns[9].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                   addStudentDto.setPhone(columns[10]);
                   addStudentDto.setCardNumber(columns[11]);
                   addStudentDto.setRemark(columns[12]);
                   String classNames = "";

                   if (!("").equals(addStudentDto.getCollegeName()) && null != addStudentDto.getCollegeName()) {
                       classNames += addStudentDto.getCollegeName() + "/";
                   }
                   if (!("").equals(addStudentDto.getFacultyName()) && null != addStudentDto.getFacultyName()) {
                       classNames += addStudentDto.getFacultyName() + "/";
                   }
                   if (!("").equals(addStudentDto.getProfessionName()) && null != addStudentDto.getProfessionName()) {
                       classNames += addStudentDto.getProfessionName() + "/";
                   }
                   if (!("").equals(addStudentDto.getGradeName()) && null != addStudentDto.getGradeName()) {
                       classNames += addStudentDto.getGradeName() + "/";
                   }
                   if (!("").equals(addStudentDto.getClassName()) && null != addStudentDto.getClassName()) {
                       classNames += addStudentDto.getClassName();
                   }
                   String ids = "";
                   int j;
                   for (j = 0; j < schoolClassList.size(); j++) {
                       //String column = columns[j];
                       if (classNames.trim().equals(schoolClassList.get(j).getThisUrl().trim())) {
                           ids += schoolClassList.get(j).getParentIds();
                       }
                   }
                   if (ids.equals("")) {
                       return WrapMapper.error("请检查" + i + "条数据院系组织填写是否正确");
                   }
                   //拼接ClassNames字段
                   addStudentDto.setClassIds(ids);
                   String[] idarr = ids.split(",");
                   addStudentDto.setClassId(Long.parseLong(idarr[idarr.length - 1]));
                   addStudentDto.setClassNames(classNames);
                   studentControllerClient.addStudent(addStudentDto);
               }
           }
           return  WrapMapper.ok("导入完成");
       }catch (Exception e){
           log.error(e.getMessage());
           return WrapMapper.error(e.getMessage());
       }
    }


}
