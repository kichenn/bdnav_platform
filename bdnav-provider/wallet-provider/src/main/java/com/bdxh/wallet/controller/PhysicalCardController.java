package com.bdxh.wallet.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.SetPayPwdDto;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.service.PhysicalCardService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.el.parser.BooleanNode;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 控制器
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@RestController
@RequestMapping("/physicalCard")
@Slf4j
@Validated
@Api(value = "物理卡账户相关", tags = "物理卡账户API")
public class PhysicalCardController {

    @Autowired
    private PhysicalCardService physicalCardService;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;


    @PostMapping("/findBillRecord")
    @ApiOperation(value = "查询账单信息", response = Boolean.class)
    public Object findRechargeRecord(@Validated @RequestBody FindBillRecordDto findBillRecordDto) {
        if (findBillRecordDto.getBillType().equals(0)) {
            //查询全部账单(包括充值记录)
        } else {
            //只查询消费账单，按照类型
        }
        return WrapMapper.ok();
    }

    /**
     * @param physicalNumber 实体卡号
     * @param physicalStatus 挂失或者解挂（1 正常 2 挂失）
     * @return
     */
    @PostMapping("/cardStatus")
    @ApiOperation(value = "挂失或者解挂", response = Boolean.class)
    public Object cardStatus(@RequestParam("physicalNumber") String physicalNumber, @RequestParam("physicalStatus") Byte physicalStatus) {
        PhysicalCard physicalCard = new PhysicalCard();
        physicalCard.setPhysicalNumber(physicalNumber);
        physicalCard.setStatus(physicalStatus);
        return WrapMapper.ok(physicalCardService.modifyInfoByPhysicalCard(physicalCard));
    }




    @RequestMapping(value = "/addPhysicalCard", method = RequestMethod.POST)
    @ApiOperation(value = "添加物理卡号信息", response = Boolean.class)
    public Object addPhysicalCard(@RequestBody AddPhysicalCard addPhysicalCard) {
        try {
            PhysicalCard physicalCard = new PhysicalCard();
            physicalCard.setId(snowflakeIdWorker.nextId());
            if (addPhysicalCard.getPhysicalCardStateEnum() != null) {
                physicalCard.setStatus(addPhysicalCard.getPhysicalCardStateEnum().getKey());
            }
            BeanUtils.copyProperties(addPhysicalCard, physicalCard);
            Boolean result = physicalCardService.save(physicalCard) > 0;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/findPhysicalCardInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "带条件分页查询列表信息", response = PhysicalCard.class)
    public Object findPhysicalCardInCondition(@Validated @RequestBody QueryPhysicalCard queryPhysicalCard, BindingResult bindingResult) {
        //检验参数
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(queryPhysicalCard);
            PageInfo<PhysicalCard> physicalCards = physicalCardService.findPhysicalCardInCondition(param, queryPhysicalCard.getPageNum(), queryPhysicalCard.getPageSize());
            return WrapMapper.ok(physicalCards);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/modifyPhysicalCard", method = RequestMethod.POST)
    @ApiOperation(value = "修改物理卡号信息", response = Boolean.class)
    public Object modifyPhysicalCard(@RequestBody ModifyPhysicalCard modifyPhysicalCard) {
        try {
            PhysicalCard physicalCard = new PhysicalCard();
            if (modifyPhysicalCard.getPhysicalCardStateEnum() != null) {
                physicalCard.setStatus(modifyPhysicalCard.getPhysicalCardStateEnum().getKey());
            }
            BeanMapUtils.copy(modifyPhysicalCard, physicalCard);
            Boolean result = physicalCardService.update(physicalCard) > 0;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delPhysicalCard", method = RequestMethod.GET)
    @ApiOperation(value = "删除物理卡号信息", response = Boolean.class)
    public Object delPhysicalCard(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) {
        try {
            Boolean result = physicalCardService.delPhysicalCard(schoolCode, cardNumber, id);
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value = "/findPhysicalCardById", method = RequestMethod.GET)
    @ApiOperation(value = "根据id查询物理卡号信息", response = PhysicalCard.class)
    public Object findPhysicalCardById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) {
        try {
            return WrapMapper.ok(physicalCardService.findPhysicalCardById(schoolCode, cardNumber, id));
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}