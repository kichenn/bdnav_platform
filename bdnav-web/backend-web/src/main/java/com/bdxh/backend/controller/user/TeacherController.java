package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.TeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:02
 **/
@RestController
@RequestMapping("/teacher")
@Validated
@Slf4j
@Api(value = "老师信息交互API", tags = "老师信息交互API")
public class TeacherController {
    @Autowired
    private TeacherControllerClient teacherControllerClient;

    /**
     * 新增老师信息
     * @param addTeacherDto
     * @return
     */
    @ApiOperation(value="新增老师信息")
    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    public Object addTeacher(@RequestBody AddTeacherDto addTeacherDto){
        try {
            teacherControllerClient.addTeacher(addTeacherDto);
            return WrapMapper.ok();
        }catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除老师信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="删除老师信息")
    @RequestMapping(value = "/removeTeacher",method = RequestMethod.POST)
    public Object removeTeacher(@RequestParam(name = "schoolCode") @NotNull(message="老师学校Code不能为空")String schoolCode,
                                @RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumber){
        try{
            teacherControllerClient.removeTeacher(schoolCode, cardNumber);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除老师信息
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value="批量删除老师信息")
    @RequestMapping(value = "/removeTeachers",method = RequestMethod.POST)
    public Object removeTeachers(@RequestParam(name = "schoolCodes") @NotNull(message="老师学校Code不能为空")String schoolCodes,
                                 @RequestParam(name = "cardNumbers") @NotNull(message="老师微校卡号不能为空")String cardNumbers){
        try{
            teacherControllerClient.removeTeachers(schoolCodes, cardNumbers);
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改老师信息
     * @param updateTeacherDto
     * @return
     */
    @ApiOperation(value="修改老师信息")
    @RequestMapping(value = "/updateTeacher",method = RequestMethod.POST)
    public Object updateTeacher(@RequestBody UpdateTeacherDto updateTeacherDto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            teacherControllerClient.updateTeacher(updateTeacherDto);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个老师信息
     * @param schoolCode cardNumber
     * @return
     */
    @ApiOperation(value="查询单个老师信息")
    @RequestMapping(value ="/queryTeacherInfo",method = RequestMethod.GET)
    public Object queryTeacherInfo(@RequestParam(name = "schoolCode") @NotNull(message="老师学校Code不能为空")String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumber) {
        try {
            TeacherVo teacherVo=teacherControllerClient.queryTeacherInfo(schoolCode,cardNumber).getResult();
            return WrapMapper.ok(teacherVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 分页查询老师数据
     * @param teacherQueryDto
     * @return
     */
    @ApiOperation(value="分页查询老师数据")
    @RequestMapping(value = "/queryTeacherListPage",method = RequestMethod.POST)
    public Object queryTeacherListPage(@RequestBody TeacherQueryDto teacherQueryDto) {
        try {
            // 封装分页之后的数据
            Wrapper wrapper=teacherControllerClient.queryTeacherListPage(teacherQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}