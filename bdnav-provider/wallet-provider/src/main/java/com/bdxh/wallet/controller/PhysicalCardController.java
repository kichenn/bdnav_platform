package com.bdxh.wallet.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
@Api(value = "物理卡控制器", tags = "物理卡控制器")
public class PhysicalCardController {

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	@Autowired
	private PhysicalCardService physicalCardService;


	@RequestMapping(value = "/AddPhysicalCard", method = RequestMethod.POST)
	@ApiOperation(value = "添加物理卡号信息",response = Boolean.class)
	public Object AddPhysicalCard(@RequestBody AddPhysicalCard addPhysicalCard) {
		try {
			PhysicalCard physicalCard=new PhysicalCard();
			physicalCard.setId(snowflakeIdWorker.nextId());
			BeanUtils.copyProperties(addPhysicalCard, physicalCard);
			Boolean result=physicalCardService.save(physicalCard)>0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	@RequestMapping(value = "findPhysicalCardInCondition", method = RequestMethod.POST)
	@ApiOperation(value = "带条件分页查询列表信息",response = PhysicalCard.class)
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
	@ApiOperation(value = "修改物理卡号信息",response = Boolean.class)
	public Object modifyPhysicalCard(@RequestBody ModifyPhysicalCard modifyPhysicalCard) {
		try {
			PhysicalCard physicalCard = new PhysicalCard();
			BeanMapUtils.copy(modifyPhysicalCard, physicalCard);
			Boolean result=physicalCardService.update(physicalCard)>0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	@RequestMapping(value = "/delPhysicalCard", method = RequestMethod.GET)
	@ApiOperation(value = "删除物理卡号信息",response = Boolean.class)
	public Object delPhysicalCard(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id) {
		try {
			Boolean result=physicalCardService.delPhysicalCard(schoolCode,cardNumber,id);
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/findPhysicalCardByid", method = RequestMethod.GET)
	@ApiOperation(value = "根据id查询物理卡号信息",response = PhysicalCard.class)
	public Object findPhysicalCardByid(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id) {
		try {
			return WrapMapper.ok(physicalCardService.findPhysicalCardById(schoolCode,cardNumber,id));
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}



}