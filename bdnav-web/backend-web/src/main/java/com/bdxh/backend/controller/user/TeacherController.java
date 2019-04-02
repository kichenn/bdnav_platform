package com.bdxh.backend.controller.user;

import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.Teacher;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.vo.TeacherVo;
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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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

            Wrapper wrapper=teacherControllerClient.addTeacher(addTeacherDto);

            return WrapMapper.ok(wrapper.getMessage());
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
            TeacherVo teacherVo=(TeacherVo) teacherControllerClient.queryTeacherInfo(schoolCode,cardNumber).getResult();
            FileOperationUtils.deleteFile(teacherVo.getImage(), null);
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
            TeacherVo teacherVo=(TeacherVo) teacherControllerClient.queryTeacherInfo(updateTeacherDto.getSchoolCode(),updateTeacherDto.getCardNumber()).getResult();
            if (!teacherVo.getImage().equals(updateTeacherDto.getImage())) {
                //删除腾讯云的以前图片
                FileOperationUtils.deleteFile(teacherVo.getImage(), null);
            }
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
            List<Teacher> saveTeacherList=new ArrayList<>();
            List<String> cardNumberList=new ArrayList<>();
            for (int i = 1; i < teacherList.size(); i++) {
                String[] columns= teacherList.get(i);
                if(StringUtils.isNotBlank(teacherList.get(i)[0])){
                if(i==1){
                    //第一条查询数据存到缓存中
                    Wrapper wrapper=schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                    Wrapper teacherWeapper=teacherControllerClient.queryTeacherCardNumberBySchoolCode(columns[0]);
                    school=(School)wrapper.getResult();
                    cardNumberList=(List<String>)teacherWeapper.getResult();
                    redisTemplate.opsForValue().set("schoolInfoVo",school);
                    redisTemplate.opsForValue().set("teacherCardNumberList",cardNumberList);
                    //判断得出在同一个班级直接从缓存中拉取数据
                }else if(teacherList.get(i)[0].equals(i-1>=teacherList.size()?teacherList.get(teacherList.size()-1)[0]:teacherList.get(i-1)[0])){
                    school=(School)redisTemplate.opsForValue().get("schoolInfoVo");
                    cardNumberList=(List<String>)redisTemplate.opsForValue().get("teacherCardNumberList");
                }else{
                    Wrapper wrapper=schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                    Wrapper teacherWeapper=teacherControllerClient.queryTeacherCardNumberBySchoolCode(columns[0]);
                    school=(School)wrapper.getResult();
                    cardNumberList=(List<String>)teacherWeapper.getResult();
                    redisTemplate.opsForValue().set("schoolInfoVo",school);
                    redisTemplate.opsForValue().set("teacherCardNumberList",cardNumberList);
                }
                if(school!=null){
                Teacher tacher=new Teacher();
                    tacher.setActivate(Byte.valueOf("1"));
                    tacher.setSchoolName(school.getSchoolName());
                    tacher.setSchoolId(school.getId());
                    tacher.setSchoolCode(school.getSchoolCode());
                    tacher.setCampusName(columns[1]);
                    tacher.setName(columns[2]);
                    tacher.setGender(columns[3].trim().equals("男")?Byte.valueOf("1"):Byte.valueOf("2"));
                    tacher.setNationName(columns[4]);
                    tacher.setPhone(columns[5]);
                    //判断当前学校是否有重复卡号
                    if(null!=cardNumberList) {
                        for (int j = 0; j < cardNumberList.size(); j++) {
                            if (columns[6].equals(cardNumberList.get(j))) {
                                return WrapMapper.error("请检查" + i + "条数据卡号已存在");
                            }
                        }
                    }else{
                        cardNumberList=new ArrayList<>();
                    }
                    tacher.setCardNumber(columns[6]);
                    cardNumberList.add(columns[6]);
                    redisTemplate.opsForValue().set("teacherCardNumberList", cardNumberList);
                    tacher.setBirth(columns[7]);
                    tacher.setIdcard(columns[8]);
                    tacher.setRemark(columns[9]);
                    saveTeacherList.add(tacher);
                }else{
                    return WrapMapper.error("第"+i+"条不存在当前学校Code");
                }
             }
            }
            teacherControllerClient.batchSaveTeacherInfo(saveTeacherList);
            return WrapMapper.ok("导入完成");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}