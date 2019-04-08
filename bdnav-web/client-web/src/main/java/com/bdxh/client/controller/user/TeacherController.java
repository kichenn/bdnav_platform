package com.bdxh.client.controller.user;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolUser;
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
    /**
     * 新增老师信息
     * @param addTeacherDto
     * @return
     */
    @ApiOperation(value="新增老师信息")
    @RequestMapping(value = "/addTeacher",method = RequestMethod.POST)
    public Object addTeacher(@RequestBody AddTeacherDto addTeacherDto){
        try {
            TeacherVo teacherVo=(TeacherVo) teacherControllerClient.queryTeacherInfo(addTeacherDto.getSchoolCode(),addTeacherDto.getCardNumber()).getResult();
           if(null!=teacherVo){
               return WrapMapper.error("当前学校已存在相同工号");
           }
            SchoolUser user= SecurityUtils.getCurrentUser();
            addTeacherDto.setOperator(user.getId());
            addTeacherDto.setOperatorName(user.getUserName());
            addTeacherDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper=teacherControllerClient.addTeacher(addTeacherDto);
            return wrapper;
        }catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除老师信息
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="删除老师信息")
    @RequestMapping(value = "/removeTeacher",method = RequestMethod.POST)
    public Object removeTeacher(
                                @RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumber,
                                @RequestParam(name = "image" ) String image){
        try{
            if(null!=image){
                FileOperationUtils.deleteFile(image, null);
            }
            SchoolUser user= SecurityUtils.getCurrentUser();
            Wrapper wrapper=teacherControllerClient.removeTeacher(user.getSchoolCode(), cardNumber);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除老师信息
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value="批量删除老师信息")
    @RequestMapping(value = "/removeTeachers",method = RequestMethod.POST)
    public Object removeTeachers(@RequestParam(name = "cardNumbers") @NotNull(message="老师微校卡号不能为空")String cardNumbers,
                                 @RequestParam(name = "images" ) String images){
        try{
            SchoolUser user= SecurityUtils.getCurrentUser();
            String[]imageAttr =images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if(null!=imageAttr[i]) {
                    FileOperationUtils.deleteFile(imageAttr[i], null);
                }
            }
            String schoolCodes="";
            int numberLength=cardNumbers.split(",").length;
            for (int i = 0; i < numberLength; i++){
                if(numberLength==(i+1)){
                    schoolCodes+=user.getSchoolCode();
                }else{
                    schoolCodes+=user.getSchoolCode()+",";
                }

            }
            Wrapper wrapper= teacherControllerClient.removeTeachers(schoolCodes, cardNumbers);
            return wrapper;
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
            SchoolUser user= SecurityUtils.getCurrentUser();
            updateTeacherDto.setOperator(user.getId());
            updateTeacherDto.setOperatorName(user.getUserName());
            updateTeacherDto.setSchoolCode(user.getSchoolCode());
            TeacherVo teacherVo=(TeacherVo) teacherControllerClient.queryTeacherInfo(updateTeacherDto.getSchoolCode(),updateTeacherDto.getCardNumber()).getResult();
            if(null!=teacherVo.getImage()) {
                if (!teacherVo.getImage().equals(updateTeacherDto.getImage())) {
                    //删除腾讯云的以前图片
                    FileOperationUtils.deleteFile(teacherVo.getImage(), null);
                }
            }
           Wrapper wrapper=teacherControllerClient.updateTeacher(updateTeacherDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个老师信息
     * @param  cardNumber
     * @return
     */
    @ApiOperation(value="查询单个老师信息")
    @RequestMapping(value ="/queryTeacherInfo",method = RequestMethod.GET)
    public Object queryTeacherInfo(@RequestParam(name = "cardNumber") @NotNull(message="老师微校卡号不能为空")String cardNumber) {
        try {
            SchoolUser user= SecurityUtils.getCurrentUser();
            TeacherVo teacherVo=teacherControllerClient.queryTeacherInfo(user.getSchoolCode(),cardNumber).getResult();
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
            SchoolUser user= SecurityUtils.getCurrentUser();
            teacherQueryDto.setSchoolCode(user.getSchoolCode());
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
            long start=System.currentTimeMillis();
            List<String[]> teacherList= ExcelImportUtil.readExcelNums(teacherFile,0);
            List<Teacher> saveTeacherList=new ArrayList<>();
            SchoolUser user=SecurityUtils.getCurrentUser();
            Long uId=user.getId();
            String uName=user.getUserName();
            Wrapper wrapper=schoolControllerClient.findSchoolBySchoolCode(user.getSchoolCode());
            Wrapper teacherWeapper=teacherControllerClient.queryTeacherCardNumberBySchoolCode(user.getSchoolCode());
            School school=(School)wrapper.getResult();
            List<String> cardNumberList=(List<String>)teacherWeapper.getResult();
            for (int i = 1; i < teacherList.size(); i++) {
                String[] columns= teacherList.get(i);
                if(StringUtils.isNotBlank(teacherList.get(i)[0])){
                if(school!=null){
                Teacher tacher=new Teacher();
                    tacher.setActivate(Byte.valueOf("1"));
                    tacher.setSchoolName(school.getSchoolName());
                    tacher.setSchoolId(school.getId());
                    tacher.setSchoolCode(school.getSchoolCode());
                    tacher.setCampusName(columns[0]);
                    tacher.setName(columns[1]);
                    tacher.setGender(columns[2].trim().equals("男")?Byte.valueOf("1"):Byte.valueOf("2"));
                    tacher.setNationName(columns[3]);
                    tacher.setPhone(columns[4]);
                    //判断当前学校是否有重复卡号
                    if(null!=cardNumberList) {
                        for (int j = 0; j < cardNumberList.size(); j++) {
                            if (columns[5].equals(cardNumberList.get(j))) {
                                return WrapMapper.error("请检查第" + i + "条数据卡号已存在");
                            }
                        }
                    }else{
                        cardNumberList=new ArrayList<>();
                    }
                    tacher.setCardNumber(columns[5]);
                    cardNumberList.add(columns[5]);
                    tacher.setBirth(columns[6]);
                    tacher.setIdcard(columns[7]);
                    tacher.setRemark(columns[8]);
                    tacher.setOperator(uId);
                    tacher.setOperatorName(uName);
                    saveTeacherList.add(tacher);
                }else{
                    return WrapMapper.error("第"+i+"条不存在当前学校Code");
                }
             }
            }
            teacherControllerClient.batchSaveTeacherInfo(saveTeacherList);
            long end=System.currentTimeMillis();
            log.info("导入10条数据总计用时："+(end-start));
            return WrapMapper.ok("导入完成");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}