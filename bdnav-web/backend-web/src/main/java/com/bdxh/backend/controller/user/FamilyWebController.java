package com.bdxh.backend.controller.user;

import com.bdxh.backend.configration.security.utils.SecurityUtils;
import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.SinglePermissionQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.feign.SinglePermissionControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.system.entity.User;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyFenceQueryDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.entity.BaseUser;
import com.bdxh.user.entity.BaseUserUnqiue;
import com.bdxh.user.entity.Family;
import com.bdxh.user.entity.FamilyFence;
import com.bdxh.user.feign.BaseUserControllerClient;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.feign.FamilyFenceControllerClient;
import com.bdxh.user.vo.FamilyVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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
    @ApiOperation(value="新增家庭成员信息")
    @RequestMapping(value = "/addFamily",method = RequestMethod.POST)
    public Object addFamily(@RequestBody AddFamilyDto addFamilyDto){
        try {

            BaseUser baseUser=baseUserControllerClient.queryBaseUserBySchoolCodeAndCardNumber(addFamilyDto.getSchoolCode(),addFamilyDto.getCardNumber()).getResult();
            if(null!=baseUser){
                return WrapMapper.error("当前学校已存在相同家长卡号");
            }

            if(addFamilyDto.getImage().equals("")||addFamilyDto.getImageName().equals("")){
            addFamilyDto.setImageName(IMG_NAME);
            addFamilyDto.setImage(IMG_URL);
            }
            User user= SecurityUtils.getCurrentUser();
            addFamilyDto.setOperator(user.getId());
            addFamilyDto.setOperatorName(user.getUserName());
            Wrapper wrapper=familyControllerClient.addFamily(addFamilyDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除家长信息
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="删除家长信息")
    @RequestMapping(value = "/removeFamily",method = RequestMethod.POST)
    public Object removeFamily(@RequestParam(name = "schoolCode") @NotNull(message="学校Code不能为空")String schoolCode,
                               @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber,
                               @RequestParam(name = "image" ) String image){
        try{
            if(null!=image){
                if(!IMG_NAME.equals(image)){
                    FileOperationUtils.deleteFile(image, null);
                }
           }
            SinglePermissionQueryDto singlePermissionQueryDto=new SinglePermissionQueryDto();
            singlePermissionQueryDto.setCardNumber(cardNumber);
            singlePermissionQueryDto.setSchoolCode(schoolCode);
            PageInfo pageInfos= singlePermissionControllerClient.findSinglePermissionInConditionPage(singlePermissionQueryDto).getResult();
            if(pageInfos.getTotal()>0){
                return WrapMapper.error("请先删除卡号为"+cardNumber+"的家长门禁单信息");
            }
            Wrapper wrapper=familyControllerClient.removeFamily(schoolCode,cardNumber);
            return wrapper;
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除家长信息
     * @param schoolCodes
     * @param cardNumbers
     * @return
     */
    @ApiOperation(value="批量删除家长信息")
    @RequestMapping(value = "/removeFamilys",method = RequestMethod.POST)
    public Object removeFamilys(@RequestParam(name = "schoolCodes") @NotNull(message="学校Code不能为空")String schoolCodes,
                                @RequestParam(name = "cardNumbers") @NotNull(message="微校卡号不能为空")String cardNumbers,
                                @RequestParam(name = "images") String images
    ){
        try{
            String[]imageAttr =images.split(",");
            for (int i = 0; i < imageAttr.length; i++) {
                if(null!=imageAttr[i]) {
                    if(!imageAttr[i].equals(IMG_NAME)){
                        FileOperationUtils.deleteFile(imageAttr[i], null);
                    }
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
    @ApiOperation(value="修改家长信息")
    @RequestMapping(value = "/updateFamily",method = RequestMethod.POST)
    public Object updateFamily(@RequestBody UpdateFamilyDto updateFamilyDto){
        try {
            FamilyVo familyVo=(FamilyVo) familyControllerClient.queryFamilyInfo(updateFamilyDto.getSchoolCode(),updateFamilyDto.getCardNumber()).getResult();
            if(null!=familyVo.getImage()) {
                if (!familyVo.getImage().equals(updateFamilyDto.getImage())) {
                    //删除腾讯云的以前图片
                    if(!familyVo.getImageName().equals(IMG_NAME)){
                        FileOperationUtils.deleteFile(familyVo.getImageName(), null);
                    }
                }
            }
            User user= SecurityUtils.getCurrentUser();
            updateFamilyDto.setOperator(user.getId());
            updateFamilyDto.setOperatorName(user.getUserName());
            //判断是否已激活 已激活需要同步微校未激活修改不需要同步微校
            if(familyVo.getActivate().equals(Byte.parseByte("2"))) {
                updateFamilyDto.setActivate(familyVo.getActivate());
                School school = schoolControllerClient.findSchoolBySchoolCode(familyVo.getSchoolCode()).getResult();
                updateFamilyDto.setAppKey(school.getSchoolKey());
                updateFamilyDto.setAppSecret(school.getSchoolSecret());
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
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    @ApiOperation(value="查询家长信息")
    @RequestMapping(value ="/queryFamilyInfo",method = RequestMethod.GET)
    public Object queryFamilyInfo(@RequestParam(name = "schoolCode") @NotNull(message="学校Code不能为空")String schoolCode,
                                  @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber) {
        try {

            FamilyVo familyVo=familyControllerClient.queryFamilyInfo(schoolCode,cardNumber).getResult();
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
            Wrapper wrapper=familyControllerClient.queryFamilyListPage(familyQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
    @ApiOperation("导入家长数据")
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
            List<String> phoneList=new ArrayList<>();

            User user=SecurityUtils.getCurrentUser();
            Long uId=user.getId();
            String uName=user.getUserName();
            for (int i=1;i<familyList.size();i++) {
                String[] columns = familyList.get(i);
                if (StringUtils.isNotBlank(familyList.get(i)[0])) {
                     if (!familyList.get(i)[0].equals(i - 1 >= familyList.size() ? familyList.get(familyList.size() - 1)[0] : familyList.get(i - 1)[0])|| i==1) {
                        Wrapper wrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                        Wrapper familyWeapper=baseUserControllerClient.findSchoolNumberBySchool(columns[0]);
                         BaseUserUnqiue baseUserUnqiue=new BaseUserUnqiue();
                         baseUserUnqiue.setSchoolCode(columns[0]);
                         phoneList= baseUserControllerClient.queryAllUserPhone(baseUserUnqiue).getResult();
                        school = (School) wrapper.getResult();
                        cardNumberList=(List<String>)familyWeapper.getResult();
                    }
                    if (school != null) {
                        AddFamilyDto family = new AddFamilyDto();
                        family.setActivate(Byte.valueOf("1"));
                        family.setSchoolCode(school.getSchoolCode());
                        family.setSchoolId(school.getId());
                        family.setSchoolName(school.getSchoolName());
                        family.setName(columns[1]);
                        family.setGender(columns[2].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                        family.setPhone(columns[3]);
                        family.setImageName(IMG_NAME);
                        family.setImage(IMG_URL);
                        //导入时判断手机号是否存在
                        for (String phone : phoneList) {
                            if(columns[3].equals(phone)){
                                return  WrapMapper.error("请检查第" + i + "条手机号已存在");
                            }
                        }
                        phoneList.add(columns[3]);
                        //判断当前学校是否有重复卡号
                        if(null!=cardNumberList) {
                            for (int j = 0; j < cardNumberList.size(); j++) {
                                if (columns[4].equals(cardNumberList.get(j))) {
                                    return WrapMapper.error("请检查第" + i + "条数据卡号已存在");
                                }
                            }
                        }else{
                            cardNumberList=new ArrayList<>();
                        }

                        family.setCardNumber(columns[4]);
                        cardNumberList.add(columns[4]);
                        family.setWxNumber(columns[5]);
                        family.setAdress(columns[6]);
                        family.setBirth(columns[7]);
                        family.setIdcard(columns[8]);
                        family.setRemark(columns[9]);
                        family.setOperator(uId);
                        family.setOperatorName(uName);
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