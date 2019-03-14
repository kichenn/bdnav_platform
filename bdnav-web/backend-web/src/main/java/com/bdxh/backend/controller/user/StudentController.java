/**
 * Copyright (C), 2019-2019
 * FileName: StudentController
 * Author:   binzh
 * Date:     2019/3/11 15:54
 * Description: TOOO
 * History:
 */
package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.POIUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddStudentDto;
import com.bdxh.user.dto.StudentQueryDto;
import com.bdxh.user.dto.UpdateStudentDto;
import com.bdxh.user.entity.Student;
import com.bdxh.user.feign.StudentControllerClient;
import com.bdxh.user.vo.StudentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Arrays;
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
            studentControllerClient.addStudent(addStudentDto);
                return WrapMapper.ok();
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
            studentControllerClient.removeStudent(schoolCode,cardNumber);
            return WrapMapper.ok();
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
            studentControllerClient.removeStudents(schoolCodes,cardNumbers);
            return WrapMapper.ok();
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
    @ApiOperation("导入学生数据")
    @RequestMapping(value="/importStudentInfo",method = RequestMethod.POST)
    public Object importStudentInfo(@RequestParam("studentFile") MultipartFile file,@RequestParam("schoolCode") String schoolCode) throws IOException {
        if (file.isEmpty()) {
            return  WrapMapper.error("上传失败，请选择文件");
        }
        if(schoolCode.isEmpty()){
            return  WrapMapper.error("请先选择学校");
        }
        List<String[]> studentList= POIUtil.readExcelNums(file,0);
        for (int i=1;i<studentList.size();i++){
            String column= Arrays.toString(studentList.get(i));
            String [] columns=column.split(",");
            Student student=new Student();
            student.getSchoolName();
            System.out.println("++++++++++"+column);
        }
        return null;
    }
}
