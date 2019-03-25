package com.bdxh.backend.controller.user;

import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.TeacherVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.List;
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
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
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

    /**
     * 导入老师信息
     * @param teacherFile
     * @return
     */
    @CrossOrigin
    @ApiOperation(value="导入老师信息")
    @RequestMapping(value = "/importTeacherInfo",method = RequestMethod.POST)
    public Object importTeacherInfo(@RequestParam("teacherFile")MultipartFile teacherFile) {
        try {
            List<String[]> teacherList= ExcelImportUtil.readExcelNums(teacherFile,0);
            School school=new School();
            for (int i = 1; i < teacherList.size(); i++) {
                String[] columns= teacherList.get(i);
                if(i==1){
                    //第一条查询数据存到缓存中
                    Wrapper wrapper=schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                    school=(School)wrapper.getResult();
                    redisTemplate.opsForValue().set("schoolInfoVo",school);
                    //判断得出在同一个班级直接从缓存中拉取数据
                }else if(teacherList.get(i)[0].equals(i-1>=teacherList.size()?teacherList.get(teacherList.size()-1)[0]:teacherList.get(i-1)[0])){
                    school=(School)redisTemplate.opsForValue().get("schoolInfoVo");
                }else{
                    Wrapper wrapper=schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                    school=(School)wrapper.getResult();
                    redisTemplate.opsForValue().set("schoolInfoVo",school);
                }
                if(school!=null){
                AddTeacherDto addTeacherDto=new AddTeacherDto();
                addTeacherDto.setSchoolName(school.getSchoolName());
                addTeacherDto.setSchoolId(school.getId());
                addTeacherDto.setSchoolCode(school.getSchoolCode());
                addTeacherDto.setCampusName(columns[1]);
                addTeacherDto.setName(columns[2]);
                addTeacherDto.setGender(columns[3].trim().equals("男")?Byte.valueOf("1"):Byte.valueOf("2"));
                addTeacherDto.setNationName(columns[4]);
                addTeacherDto.setPhone(columns[5]);
                addTeacherDto.setCardNumber(columns[6]);
                addTeacherDto.setRemark(columns[7]);
            }else{
                    return WrapMapper.error("第"+i+"条不存在当前学校Code");
                }
            }
            return WrapMapper.ok("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}