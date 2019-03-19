package com.bdxh.backend.controller.user;

import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.feign.SchoolControllerClient;
import com.bdxh.school.vo.SchoolInfoVo;
import com.bdxh.user.dto.AddFamilyDto;
import com.bdxh.user.dto.FamilyQueryDto;
import com.bdxh.user.dto.UpdateFamilyDto;
import com.bdxh.user.feign.FamilyControllerClient;
import com.bdxh.user.vo.FamilyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-03-13 10:02
 **/
@RestController
@RequestMapping("/family")
@Validated
@Slf4j
@Api(value = "家长信息交互API", tags = "家长信息交互API")
public class FamilyController {

    @Autowired
    private FamilyControllerClient familyControllerClient;
    @Autowired
    private SchoolControllerClient schoolControllerClient;
    /**
     * 新增家庭成员信息
     * @param addFamilyDto
     * @return
     */
    @ApiOperation(value="新增家庭成员信息")
    @RequestMapping(value = "/addFamily",method = RequestMethod.POST)
    public Object addFamily(@RequestBody AddFamilyDto addFamilyDto){
        try {
            Wrapper wrapper=familyControllerClient.addFamily(addFamilyDto);
            return WrapMapper.ok(wrapper.getResult());
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
                               @RequestParam(name = "cardNumber") @NotNull(message="微校卡号不能为空")String cardNumber){
        try{
            familyControllerClient.removeFamily(schoolCode,cardNumber);
            return WrapMapper.ok();
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
                                @RequestParam(name = "cardNumbers") @NotNull(message="微校卡号不能为空")String cardNumbers
    ){
        try{
            familyControllerClient.removeFamilys(schoolCodes,cardNumbers);
            return WrapMapper.ok();
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
            familyControllerClient.updateFamily(updateFamilyDto);
            return WrapMapper.ok();
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
    public Object importStudentInfo( @RequestParam("schoolId") Long schoolId,@RequestParam("familyFile") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return  WrapMapper.error("上传失败，请选择文件");
            }
            if(schoolId.equals("")){
                return  WrapMapper.error("请先选择学校");
            }
            Wrapper wrapper=schoolControllerClient.findSchoolById(schoolId);
            SchoolInfoVo schoolInfoVo=(SchoolInfoVo)wrapper.getResult();
            List<String[]> familyList= ExcelImportUtil.readExcel(file);
            for (int i=1;i<familyList.size();i++){
                String[] columns= familyList.get(i);
                AddFamilyDto addFamilyDto=new AddFamilyDto();
                addFamilyDto.setSchoolCode(schoolInfoVo.getSchoolCode());
                addFamilyDto.setSchoolId(schoolInfoVo.getId());
                addFamilyDto.setSchoolName(schoolInfoVo.getSchoolName());
                addFamilyDto.setName(columns[0]);
                addFamilyDto.setGender(columns[1].trim().equals("男")?Byte.valueOf("1"):Byte.valueOf("2"));
                addFamilyDto.setPhone(columns[2]);
                addFamilyDto.setCardNumber(columns[3]);
                addFamilyDto.setWxNumber(columns[4]);
                addFamilyDto.setAdress(columns[5]);
                addFamilyDto.setRemark(columns[6]);
                familyControllerClient.addFamily(addFamilyDto);
            }
            return  WrapMapper.ok();
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }
    }
}