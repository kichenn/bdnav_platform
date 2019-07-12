package com.bdxh.backend.controller.wallet;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.AddPhysicalCard;
import com.bdxh.wallet.dto.ModifyPhysicalCard;
import com.bdxh.wallet.dto.QueryPhysicalCard;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.feign.PhysicalCardControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/physicalCardWeb")
@Slf4j
@Validated
@Api(value = "物理卡控制器", tags = "物理卡控制器")
public class PhysicalCardWebController {

    @Autowired
    private PhysicalCardControllerClient physicalCardControllerClient;



    @ApiOperation(value = "删除物理卡号",response = Boolean.class)
    @RequestMapping(value = "/removePhysicalCard", method = RequestMethod.GET)
    public Object removePhysicalCard(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id) {
        try {
            return physicalCardControllerClient.delPhysicalCard(schoolCode,cardNumber,id);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "添加物理卡号",response = Boolean.class)
    @RequestMapping(value = "/addPhysicalCard", method = RequestMethod.POST)
    public Object addPhysicalCard(@RequestBody AddPhysicalCard addPhysicalCard) {
        try {
            return physicalCardControllerClient.AddPhysicalCard(addPhysicalCard);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value="修改物理卡号",response = Boolean.class)
    @RequestMapping(value = "/modifyPhysicalCard", method = RequestMethod.POST)
    public Object modifyPhysicalCard(@RequestBody ModifyPhysicalCard modifyPhysicalCard) {
        try {
            return physicalCardControllerClient.modifyPhysicalCard(modifyPhysicalCard);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "分页查询物理卡号",response = PhysicalCard.class)
    @RequestMapping(value = "/findPhysicalCardInCondition", method = RequestMethod.POST)
    public Object findPhysicalCardInCondition(@RequestBody QueryPhysicalCard queryPhysicalCard) {
        try {
            return physicalCardControllerClient.findPhysicalCardInCondition(queryPhysicalCard);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @ApiOperation(value = "根据id查询物理卡号",response = PhysicalCard.class)
    @RequestMapping(value = "/findPhysicalCardById", method = RequestMethod.GET)
    public Object findPhysicalCardById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id) {
        try {
            return physicalCardControllerClient.findPhysicalCardById(schoolCode,cardNumber,id);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}
