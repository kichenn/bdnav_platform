package com.bdxh.client.controller.user;

import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.entity.SinglePermission;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.entity.Family;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:02
 **/
@RestController
@RequestMapping("/familyWeb")
@Validated
@Slf4j
@Api(value = "家长信息交互API", tags = "家长信息交互API")
public class FamilyWebController {

    @Autowired
    private FamilyControllerClient familyControllerClient;
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    @Autowired
    private SinglePermissionControllerClient singlePermissionControllerClient;
    @Autowired
    private BaseUserControllerClient baseUserControllerClient;

    //图片路径
    private static final String IMG_URL="http://bdnav-1258570075-1258570075.cos.ap-guangzhou.myqcloud.com/data/20190416_be0c86bea84d477f814e797d1fa51378.jpg?sign=q-sign-algorithm%3Dsha1%26q-ak%3DAKIDmhZcOvMyaVdNQZoBXw5xZtqVR6SqdIK6%26q-sign-time%3D1555411088%3B1870771088%26q-key-time%3D1555411088%3B1870771088%26q-header-list%3D%26q-url-param-list%3D%26q-signature%3Dbc7a67e7b405390b739288b55f676ab640094649";
    //图片名称
    private static final String IMG_NAME="20190416_be0c86bea84d477f814e797d1fa51378.jpg";
    /**
     * 新增家庭成员信息
     * @param addFamilyDto
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="新增家庭成员信息", response = Boolean.class)
    @RequestMapping(value = "/addFamily",method = RequestMethod.POST)
    public Object addFamily(@RequestBody AddFamilyDto addFamilyDto){
        try {
            SchoolUser user= SecurityUtils.getCurrentUser();
            BaseUser baseUser=baseUserControllerClient.queryBaseUserBySchoolCodeAndCardNumber(addFamilyDto.getSchoolCode(),addFamilyDto.getCardNumber()).getResult();
            if(null!=baseUser){
                return WrapMapper.error("当前学校已存在相同家长卡号");
            }
            if(addFamilyDto.getImage().equals("")||addFamilyDto.getImageName().equals("")){
                addFamilyDto.setImageName(IMG_NAME);
                addFamilyDto.setImage(IMG_URL);
            }
            addFamilyDto.setOperator(user.getId());
            addFamilyDto.setOperatorName(user.getUserName());
            SchoolInfoVo school= schoolControllerClient.findSchoolById(user.getSchoolId()).getResult();
            addFamilyDto.setSchoolCode(school.getSchoolCode());
            addFamilyDto.setSchoolId(school.getId());
            addFamilyDto.setSchoolName(school.getSchoolName());
            Wrapper wrapper=familyControllerClient.addFamily(addFamilyDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    /**
     * 删除家长信息
     * @param cardNumber
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="删除家长信息", response = Boolean.class)
    @RequestMapping(value = "/removeFamily",method = RequestMethod.POST)
    public Object removeFamily(
                               @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber,
                               @RequestParam(name = "image" ) String image){
        try{
           if(null!=image){
               if(!IMG_NAME.equals(image)){
                   FileOperationUtils.deleteFile(image, null);
               }
           }
            SchoolUser user= SecurityUtils.getCurrentUser();
            SinglePermissionQueryDto singlePermissionQueryDto=new SinglePermissionQueryDto();
            singlePermissionQueryDto.setCardNumber(cardNumber);
            singlePermissionQueryDto.setSchoolCode(user.getSchoolCode());
            PageInfo pageInfo= singlePermissionControllerClient.findSinglePermissionInConditionPage(singlePermissionQueryDto).getResult();
           if(pageInfo.getTotal()>0){
               return WrapMapper.error("请先删除卡号为\"+cardNumber+\"的家长门禁单信息");
           }
            Wrapper wrapper=familyControllerClient.removeFamily(user.getSchoolCode(),cardNumber);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除家长信息
     * @param cardNumbers
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="批量删除家长信息", response = Boolean.class)
    @RequestMapping(value = "/removeFamilys",method = RequestMethod.POST)
    public Object removeFamilys(
                                @RequestParam(name = "cardNumbers") @NotNull(message="微校卡号不能为空")String cardNumbers,
                                @RequestParam(name = "images") String images
    ){
        try{
            SchoolUser user= SecurityUtils.getCurrentUser();
            String[]imageAttr =images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if(null!=imageAttr[i]) {
                    if(!IMG_NAME.equals(imageAttr[i])){
                        FileOperationUtils.deleteFile(imageAttr[i], null);
                    }
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
            Wrapper wrapper= familyControllerClient.removeFamilys(schoolCodes,cardNumbers);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改家长信息
     * @param updateFamilyDto
     * @return
     */
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="修改家长信息", response = Boolean.class)
    @RequestMapping(value = "/updateFamily",method = RequestMethod.POST)
    public Object updateFamily(@RequestBody UpdateFamilyDto updateFamilyDto){
        try {
            FamilyVo familyVo=(FamilyVo) familyControllerClient.queryFamilyInfo(updateFamilyDto.getSchoolCode(),updateFamilyDto.getCardNumber()).getResult();
            if(null!=familyVo.getImage()) {
                if (!familyVo.getImage().equals(updateFamilyDto.getImage())) {
                    //删除腾讯云的以前图片
                    FileOperationUtils.deleteFile(familyVo.getImage(), null);
                }
            }
            SchoolUser user= SecurityUtils.getCurrentUser();
            updateFamilyDto.setOperator(user.getId());
            updateFamilyDto.setOperatorName(user.getUserName());
            updateFamilyDto.setSchoolCode(user.getSchoolCode());
            //判断是否已激活 已激活需要同步微校未激活修改不需要同步微校
            if(familyVo.getActivate().equals(Byte.parseByte("2"))){
            updateFamilyDto.setActivate(familyVo.getActivate());
            School school=schoolControllerClient.findSchoolBySchoolCode(familyVo.getSchoolCode()).getResult();
            updateFamilyDto.setAppKey(school.getAppKey());
            updateFamilyDto.setAppSecret(school.getAppSecret());
            updateFamilyDto.setSchoolType(school.getSchoolType());
            }
            Wrapper wrapper=familyControllerClient.updateFamily(updateFamilyDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询家长信息
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="查询家长信息")
    @RequestMapping(value ="/queryFamilyInfo",method = RequestMethod.GET)
    public Object queryFamilyInfo(@RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber) {
        try {
            SchoolUser user= SecurityUtils.getCurrentUser();
            FamilyVo familyVo=familyControllerClient.queryFamilyInfo(user.getSchoolCode(),cardNumber).getResult();
            return WrapMapper.ok(familyVo) ;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param familyQueryDto
     * @return PageInfo<Family>
     */
    @ApiOperation(value="根据条件分页查询家长数据")
    @RequestMapping(value = "/queryFamilyListPage",method = RequestMethod.POST)
    public Object queryFamilyListPage(@RequestBody FamilyQueryDto familyQueryDto) {
        try {
            // 封装分页之后的数据
            SchoolUser user= SecurityUtils.getCurrentUser();
            familyQueryDto.setSchoolCode(user.getSchoolCode());
            Wrapper wrapper=familyControllerClient.queryFamilyListPage(familyQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @RolesAllowed({"ADMIN"})
    @ApiOperation(value="导入家长数据" , response = Boolean.class)
    @RequestMapping(value="/importFamilyInfo",method = RequestMethod.POST)
    public Object importStudentInfo( @RequestParam("familyFile") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return  WrapMapper.error("上传失败，请选择文件");
            }
            List<String[]> familyList= ExcelImportUtil.readExcel(file);
            School school=new School();
            List<AddFamilyDto> families =new ArrayList<>();
            List<String> cardNumberList=new ArrayList<>();

            SchoolUser user=SecurityUtils.getCurrentUser();
            Long uId=user.getId();
            String uName=user.getUserName();
            BaseUserUnqiue baseUserUnqiue=new BaseUserUnqiue();
            baseUserUnqiue.setSchoolCode(user.getSchoolCode());
            List<String> phoneList=baseUserControllerClient.queryAllUserPhone(baseUserUnqiue).getResult();
            Wrapper wrapper = schoolControllerClient.findSchoolBySchoolCode(user.getSchoolCode());
            Wrapper familyWeapper=familyControllerClient.queryFamilyCardNumberBySchoolCode(user.getSchoolCode());
            school = (School) wrapper.getResult();
            cardNumberList=(List<String>)familyWeapper.getResult();
            for (int i=1;i<familyList.size();i++) {
                String[] columns = familyList.get(i);
                if (StringUtils.isNotBlank(familyList.get(i)[0])) {
                    if (school != null) {
                        AddFamilyDto family = new AddFamilyDto();
                        family.setActivate(Byte.valueOf("1"));
                        family.setSchoolCode(school.getSchoolCode());
                        family.setSchoolId(school.getId());
                        family.setSchoolName(school.getSchoolName());
                        family.setName(columns[0]);
                        family.setGender(columns[1].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                        family.setPhone(columns[2]);
                        //判断当前学校是否有重复卡号
                        if(null!=cardNumberList) {
                            for (int j = 0; j < cardNumberList.size(); j++) {
                                if (columns[3].equals(cardNumberList.get(j))) {
                                    return WrapMapper.error("请检查第" + i + "条数据卡号已存在");
                                }
                            }
                        }else{
                            cardNumberList=new ArrayList<>();
                        }
                        //导入时判断手机号是否存在
                        for (String phone : phoneList) {
                            if(columns[2].equals(phone)){
                                return  WrapMapper.error("请检查第" + i + "条手机号已存在或者重复");
                            }
                        }
                        phoneList.add(columns[2]);
                        family.setCardNumber(columns[3]);
                        cardNumberList.add(columns[3]);
                        family.setWxNumber(columns[4]);
                        family.setAdress(columns[5]);
                        family.setBirth(columns[6]);
                        family.setIdcard(columns[7]);
                        family.setRemark(columns[8]);
                        family.setOperator(uId);
                        family.setOperatorName(uName);
                        family.setImageName(IMG_NAME);
                        family.setImage(IMG_URL);
                        families.add(family);
                    } else {
                        return WrapMapper.error("第" + i + "条的学校数据不存在！请检查");
                    }
                }
            }
            familyControllerClient.batchSaveFamilyInfo(families);
            return  WrapMapper.ok("导入完成");
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }
    }
}