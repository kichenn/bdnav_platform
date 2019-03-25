package com.bdxh.backend.controller.user;

import com.bdxh.common.helper.excel.ExcelImportUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.entity.School;
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
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

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
    public Object importStudentInfo( @RequestParam("familyFile") MultipartFile file) throws IOException {
        try {
            if (file.isEmpty()) {
                return  WrapMapper.error("上传失败，请选择文件");
            }
            List<String[]> familyList= ExcelImportUtil.readExcel(file);
            School school=new School();
            for (int i=1;i<familyList.size();i++) {
                String[] columns = familyList.get(i);
                if (StringUtils.isNotBlank(familyList.get(i)[0])) {
                    if (i == 1) {
                        //第一条查询数据存到缓存中
                        Wrapper wrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                        school = (School) wrapper.getResult();
                        redisTemplate.opsForValue().set("schoolInfoVo", school);
                        //判断得出在同一个班级直接从缓存中拉取数据
                    } else if (familyList.get(i)[0].equals(i - 1 >= familyList.size() ? familyList.get(familyList.size() - 1)[0] : familyList.get(i - 1)[0])) {
                        school = (School) redisTemplate.opsForValue().get("schoolInfoVo");
                    } else {
                        Wrapper wrapper = schoolControllerClient.findSchoolBySchoolCode(columns[0]);
                        school = (School) wrapper.getResult();
                        redisTemplate.opsForValue().set("schoolInfoVo", school);
                    }
                    if (school != null) {
                        AddFamilyDto addFamilyDto = new AddFamilyDto();
                        addFamilyDto.setSchoolCode(school.getSchoolCode());
                        addFamilyDto.setSchoolId(school.getId());
                        addFamilyDto.setSchoolName(school.getSchoolName());
                        addFamilyDto.setName(columns[1]);
                        addFamilyDto.setGender(columns[2].trim().equals("男") ? Byte.valueOf("1") : Byte.valueOf("2"));
                        addFamilyDto.setPhone(columns[3]);
                        addFamilyDto.setCardNumber(columns[4]);
                        addFamilyDto.setWxNumber(columns[5]);
                        addFamilyDto.setAdress(columns[6]);
                        addFamilyDto.setRemark(columns[7]);
                        familyControllerClient.addFamily(addFamilyDto);
                    } else {
                        return WrapMapper.error("第" + i + "条的学校数据不存在！请检查");
                    }
                }
            }
            return  WrapMapper.ok();
        }catch (Exception e){
            return WrapMapper.error(e.getMessage());
        }
    }
}