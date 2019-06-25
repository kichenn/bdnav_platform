package com.bdxh.backend.controller.user;

import com.bdxh.user.dto.AddFamilyBlackUrlDto;
import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.dto.ModifyFamilyBlackUrlDto;
import com.bdxh.user.feign.FamilyBlackUrlControllerClient;
import com.bdxh.user.vo.FamilyBlackUrlVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 家长端黑名单web层
 * @author WanMing
 * @date 2019/6/25 16:39
 */
@RestController
@RequestMapping("/familyBlackUrlWeb")
@Slf4j
@Validated
@Api(value = "家长黑名单管理", tags = "家长黑名单管理API")
public class FamilyBlackUrlWebController {


    @Autowired
    private FamilyBlackUrlControllerClient familyBlackUrlControllerClient;


    /**
     * 添加家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @ApiOperation(value = "添加家长端的黑名单",response = String.class)
    @RequestMapping(value = "/addFamilyBlackUrl",method = RequestMethod.POST)
    public Object addFamilyBlackUrl(@Validated @RequestBody AddFamilyBlackUrlDto addFamilyBlackUrlDto){
        return familyBlackUrlControllerClient.addFamilyBlackUrl(addFamilyBlackUrlDto);
    }


    /**
     * 删除家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @ApiOperation(value = "删除家长端的黑名单",response = Boolean.class)
    @RequestMapping(value = "/delFamilyBlackUrl",method = RequestMethod.GET)
    public Object delFamilyBlackUrl(@RequestParam(name = "schoolCode")  String schoolCode,
                                    @RequestParam(name = "cardNumber") String cardNumber,
                                    @RequestParam(name = "id") String id){
        return familyBlackUrlControllerClient.delFamilyBlackUrl(schoolCode, cardNumber, id);
    }


    /**
     * 修改家长端的黑名单
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @ApiOperation(value = "修改家长端的黑名单",response = String.class)
    @RequestMapping(value = "/modifyFamilyBlackUrl",method = RequestMethod.POST)
    public Object modifyFamilyBlackUrl(@Validated @RequestBody ModifyFamilyBlackUrlDto modifyFamilyBlackUrlDto ){
        return familyBlackUrlControllerClient.modifyFamilyBlackUrl(modifyFamilyBlackUrlDto);
    }


    /**
     * 根据条件分页查询家长端的黑名单列表
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @ApiOperation(value = "根据条件分页查询家长端的黑名单列表",response = FamilyBlackUrlVo.class)
    @RequestMapping(value = "/findFamilyBlackUrlByCondition",method = RequestMethod.POST)
    public Object findFamilyBlackUrlByCondition(@RequestBody FamilyBlackUrlQueryDto familyBlackUrlQueryDto ){
        return familyBlackUrlControllerClient.findFamilyBlackUrlByCondition(familyBlackUrlQueryDto);
    }

    /**
     * 查询家长对应孩子的黑名单列表
     * @Author: WanMing
     * @Date: 2019/6/25 10:44
     */
    @ApiOperation(value = "查询家长对应孩子的黑名单列表",response = FamilyBlackUrlVo.class)
    @RequestMapping(value = "/findFamilyBlackUrlByStudent",method = RequestMethod.GET)
    public Object findFamilyBlackUrlByStudent(@RequestParam("schoolCode" )String schoolCode,
                                              @RequestParam("cardNumber")String cardNumber,
                                              @RequestParam("studentNumber")String studentNumber){
        return familyBlackUrlControllerClient.findFamilyBlackUrlByStudent(schoolCode, cardNumber, studentNumber);
    }
}
