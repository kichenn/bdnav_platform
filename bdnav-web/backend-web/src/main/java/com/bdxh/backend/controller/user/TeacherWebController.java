package com.bdxh.backend.controller.user;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.product.enums.UserNumberStatusEnum;
import com.bdxh.common.base.enums.BaseUserTypeEnum;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.system.entity.User;
import com.bdxh.user.dto.AddTeacherDto;
import com.bdxh.user.dto.TeacherQueryDto;
import com.bdxh.user.dto.UpdateTeacherDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.TeacherControllerClient;
import com.bdxh.user.feign.TeacherDeptControllerClient;
import com.bdxh.user.vo.StudentVo;
import com.bdxh.user.vo.TeacherVo;
import com.bdxh.wallet.dto.CreateWalletDto;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import com.github.pagehelper.PageInfo;
import com.netflix.discovery.converters.Auto;
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
@RequestMapping("/teacherWeb")
@Validated
@Slf4j
@Api(value = "老师信息交互API", tags = "老师信息交互API")
public class TeacherWebController {
    @Autowired
    private TeacherControllerClient teacherControllerClient;
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    @Autowired
    private SinglePermissionControllerClient singlePermissionControllerClient;
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;
    @Autowired
    private WalletAccountControllerClient walletAccountControllerClient;

    //图片路径
    private static final String IMG_URL = "http://bdnav-1258570075-1258570075.cos.ap-guangzhou.myqcloud.com/data/20190416_be0c86bea84d477f814e797d1fa51378.jpg?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDmhZcOvMyaVdNQZoBXw5xZtqVR6SqdIK6%26q-sign-time%3D1555411088%3B1870771088%26q-key-time%3D1555411088%3B1870771088%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3Dbc7a67e7b405390b739288b55f676ab640094649";
    //图片名称
    private static final String IMG_NAME = "20190416_be0c86bea84d477f814e797d1fa51378.jpg";

    /**
     * 新增老师信息
     *
     * @param addTeacherDto
     * @return
     */
    @ApiOperation(value = "新增老师信息")
    @RequestMapping(value = "/addTeacher", method = RequestMethod.POST)
    public Object addTeacher(@RequestBody AddTeacherDto addTeacherDto) {
        try {
            BaseUser baseUser = baseUserControllerClient.queryBaseUserBySchoolCodeAndCardNumber(addTeacherDto.getSchoolCode(), addTeacherDto.getCardNumber()).getResult();
            if (null != baseUser) {
                return WrapMapper.error("当前学校已存在相同教师工号");
            }
            if (addTeacherDto.getImage().equals("") || addTeacherDto.getImageName().equals("")) {
                addTeacherDto.setImageName(IMG_NAME);
                addTeacherDto.setImage(IMG_URL);
            }
            User user = SecurityUtils.getCurrentUser();
            addTeacherDto.setOperator(user.getId());
            addTeacherDto.setOperatorName(user.getUserName());
            Wrapper wrapper = teacherControllerClient.addTeacher(addTeacherDto);
            if (wrapper.getCode() == 200) {
                schoolControllerClient.updateSchoolUserNum(Integer.valueOf(BaseUserTypeEnum.TEACHER.getCode()), Integer.valueOf(UserNumberStatusEnum.ADD.getCode()), 1, addTeacherDto.getSchoolCode());
                //新增老师账户
                //查询钱包信息
                WalletAccount walletAccount = walletAccountControllerClient.findWalletByCardNumberAndSchoolCode(addTeacherDto.getCardNumber(), addTeacherDto.getSchoolCode()).getResult();
                if (walletAccount == null) {
                    TeacherVo teacherVo = teacherControllerClient.queryTeacherInfo(addTeacherDto.getSchoolCode(), addTeacherDto.getCardNumber()).getResult();
                    //钱包不存在，新增钱包信息
                    CreateWalletDto createWalletDto = new CreateWalletDto();
                    createWalletDto.setSchoolId(addTeacherDto.getSchoolId().toString());
                    createWalletDto.setSchoolCode(addTeacherDto.getSchoolCode());
                    createWalletDto.setSchoolName(addTeacherDto.getSchoolName());
                    createWalletDto.setUserId(teacherVo.getId());
                    createWalletDto.setCardNumber(addTeacherDto.getCardNumber());
                    createWalletDto.setUserName(teacherVo.getName());
                    createWalletDto.setUserType(new Byte("3"));
                    createWalletDto.setOrgId("");
                    walletAccountControllerClient.createWallet(createWalletDto);
                }
            }
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除老师信息
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value = "删除老师信息")
    @RequestMapping(value = "/removeTeacher", method = RequestMethod.POST)
    public Object removeTeacher(@RequestParam(name = "schoolCode") @NotNull(message = "老师学校Code不能为空") String schoolCode,
                                @RequestParam(name = "cardNumber") @NotNull(message = "老师微校卡号不能为空") String cardNumber,
                                @RequestParam(name = "image") String image) {
        try {
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
                return WrapMapper.error("请先删除卡号为" + cardNumber + "的老师门禁单信息");
            }
            Wrapper wrapper = teacherControllerClient.removeTeacher(schoolCode, cardNumber);
            if (wrapper.getCode() == 200) {
                schoolControllerClient.updateSchoolUserNum(Integer.parseInt(BaseUserTypeEnum.TEACHER.getCode().toString()), Integer.parseInt(UserNumberStatusEnum.REMOVE.getCode().toString()), 1, schoolCode);
            }
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除老师信息
     *
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value = "批量删除老师信息")
    @RequestMapping(value = "/removeTeachers", method = RequestMethod.POST)
    public Object removeTeachers(@RequestParam(name = "schoolCodes") @NotNull(message = "老师学校Code不能为空") String schoolCodes,
                                 @RequestParam(name = "cardNumbers") @NotNull(message = "老师微校卡号不能为空") String cardNumbers,
                                 @RequestParam(name = "images") String images) {
        try {
            String[] imageAttr = images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if (null != imageAttr[i]) {
                    if (!IMG_NAME.equals(imageAttr[i])) {
                        FileOperationUtils.deleteFile(imageAttr[i], null);
                    }
                }
            }
            Wrapper wrapper = teacherControllerClient.removeTeachers(schoolCodes, cardNumbers);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改老师信息
     *
     * @param updateTeacherDto
     * @return
     */
    @ApiOperation(value = "修改老师信息")
    @RequestMapping(value = "/updateTeacher", method = RequestMethod.POST)
    public Object updateTeacher(@RequestBody UpdateTeacherDto updateTeacherDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            User user = SecurityUtils.getCurrentUser();
            updateTeacherDto.setOperator(user.getId());
            updateTeacherDto.setOperatorName(user.getUserName());
            TeacherVo teacherVo = (TeacherVo) teacherControllerClient.queryTeacherInfo(updateTeacherDto.getSchoolCode(), updateTeacherDto.getCardNumber()).getResult();
            //判断是否已激活 已激活需要同步微校未激活修改不需要同步微校
            if (teacherVo.getActivate().equals(Byte.parseByte("2"))) {
                School school = schoolControllerClient.findSchoolBySchoolCode(teacherVo.getSchoolCode()).getResult();
                updateTeacherDto.setAppKey(school.getSchoolKey());
                updateTeacherDto.setAppSecret(school.getSchoolSecret());
                updateTeacherDto.setSchoolType(school.getSchoolType());
                updateTeacherDto.setActivate(teacherVo.getActivate());
            }
            if (null != teacherVo.getImage()) {
                if (!updateTeacherDto.getImage().equals(teacherVo.getImage())) {
                    //删除腾讯云的以前图片
                    if (!teacherVo.getImageName().equals(IMG_NAME)) {
                        FileOperationUtils.deleteFile(teacherVo.getImageName(), null);
                    }
                }
            }

            Wrapper wrapper = teacherControllerClient.updateTeacher(updateTeacherDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个老师信息
     *
     * @param schoolCode cardNumber
     * @return
     */
    @ApiOperation(value = "查询单个老师信息")
    @RequestMapping(value = "/queryTeacherInfo", method = RequestMethod.GET)
    public Object queryTeacherInfo(@RequestParam(name = "schoolCode") @NotNull(message = "老师学校Code不能为空") String schoolCode,
                                   @RequestParam(name = "cardNumber") @NotNull(message = "老师微校卡号不能为空") String cardNumber) {
        try {
            TeacherVo teacherVo = teacherControllerClient.queryTeacherInfo(schoolCode, cardNumber).getResult();
            return WrapMapper.ok(teacherVo);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 分页查询老师数据
     *
     * @param teacherQueryDto
     * @return
     */
    @ApiOperation(value = "分页查询老师数据")
    @RequestMapping(value = "/queryTeacherListPage", method = RequestMethod.POST)
    public Object queryTeacherListPage(@RequestBody TeacherQueryDto teacherQueryDto) {
        try {
            // 封装分页之后的数据
            Wrapper wrapper = teacherControllerClient.queryTeacherListPage(teacherQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 导入老师信息
     *
     * @param teacherFile
     * @return
     */
    @CrossOrigin
    @ApiOperation(value = "导入老师信息")
    @RequestMapping(value = "/importTeacherInfo", method = RequestMethod.POST)
    public Object importTeacherInfo(@RequestParam("teacherFile") MultipartFile teacherFile) {
        try {
            long start = System.currentTimeMillis();
            List<String[]> teacherList = ExcelImportUtil.readExcelNums(teacherFile, 0);
            School school = new School();
            List<AddTeacherDto> saveTeacherList = new ArrayList<>();
            List<String> cardNumberList = new ArrayList<>();
            List<String> phoneList = new ArrayList<>();
            User user = SecurityUtils.getCurrentUser();
            Long uId = user.getId();
            String uName = user.getUserName();
            for (int i = 1; i < teacherList.size(); i++) {
                String[] columns = teacherList.get(i);
                if (StringUtils.isNotBlank(teacherList.get(i)[0])) {
                    if (!teacherList.get(i)[0].equals(i - 1 >= teacherList.size() ? teacherList.get(teacherList.size() - 1)[0] : teacherList.get(i - 1)[0]) || i == 1) {
                        Wrapper wrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                        Wrapper teacherWeapper = baseUserControllerClient.findSchoolNumberBySchool(columns[0]);
                        school = (School) wrapper.getResult();
                        cardNumberList = (List<String>) teacherWeapper.getResult();
                        BaseUserUnqiue baseUserUnqiue = new BaseUserUnqiue();
                        baseUserUnqiue.setSchoolCode(columns[0]);
                        phoneList = baseUserControllerClient.queryAllUserPhone(baseUserUnqiue).getResult();
                    }
                    if (school != null) {
                        AddTeacherDto tacher = new AddTeacherDto();
                        tacher.setActivate(Byte.valueOf("1"));
                        tacher.setSchoolName(school.getSchoolName());
                        tacher.setSchoolId(school.getId());
                        tacher.setSchoolCode(school.getSchoolCode());
                        tacher.setCampusName(columns[1]);
                        tacher.setName(columns[2]);
                        tacher.setGender(columns[3].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                        tacher.setPosition(columns[4]);
                        tacher.setPhone(columns[5]);
                        //判断当前学校是否有重复卡号
                        if (null != cardNumberList) {
                            for (int j = 0; j < cardNumberList.size(); j++) {
                                if (columns[6].equals(cardNumberList.get(j))) {
                                    return WrapMapper.error("请检查第" + i + "条数据卡号已存在");
                                }
                            }
                        } else {
                            cardNumberList = new ArrayList<>();
                        }
                        //导入时判断手机号是否存在
                        for (String phone : phoneList) {
                            if (columns[5].equals(phone)) {
                                return WrapMapper.error("请检查第" + i + "条手机号已存在");
                            }
                        }
                        phoneList.add(columns[5]);
                        tacher.setCardNumber(columns[6]);
                        cardNumberList.add(columns[6]);
                        tacher.setBirth(columns[7]);
                        tacher.setIdcard(columns[8]);
                        tacher.setRemark(columns[9]);
                        tacher.setOperator(uId);
                        tacher.setOperatorName(uName);
                        tacher.setImageName(IMG_NAME);
                        tacher.setImage(IMG_URL);
                        saveTeacherList.add(tacher);
                    } else {
                        return WrapMapper.error("第" + i + "条不存在当前学校Code");
                    }
                }
            }
            teacherControllerClient.batchSaveTeacherInfo(saveTeacherList);
            long end = System.currentTimeMillis();
            log.info("总计用时：" + (end - start) + "+毫秒");
            return WrapMapper.ok("导入完成");
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}