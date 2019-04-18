package com.bdxh.school.contoller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.service.SchoolStrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;



/**
* @Description: 控制器
* @Date 2019-04-18 09:52:43
*/
@RestController
@RequestMapping("/schoolStrategy")
@Slf4j
@Validated
@Api(value = "学校策略交互API", tags = "SchoolStrategy")
public class SchoolStrategyController {

	@Autowired
	private SchoolStrategyService schoolStrategyService;


	/**
	* @Description: 查询列表信息
	* @Date 2019-04-18 09:52:43
	*/
	@RequestMapping(value = "/findDatasInCondition", method = RequestMethod.GET)
	@ApiOperation(value = "根据条件查询列表", response = SchoolStrategy.class)
	@ResponseBody
	public Object findDatasInCondition(SchoolStrategy schoolStrategy) {
		List<SchoolStrategy> datas = schoolStrategyService.findSchoolStrategyInCondition(schoolStrategy);
		return WrapMapper.ok(datas);
	}

	/**
	* @Description: 删除信息
	* @Date 2019-04-18 09:52:43
	*/
	@RequestMapping(value = "/delDataById", method = RequestMethod.POST)
	@ApiOperation(value = "删除信息", response = Boolean.class)
	@ResponseBody
	public Object delDataById(@RequestParam("id")Long id) {
		return WrapMapper.ok(schoolStrategyService.delSchoolStrategyById(id));
	}


	/**
	* @Description: 批量删除
	* @Date 2019-04-18 09:52:43
	*/
	@RequestMapping(value = "/batchDelDataInIds", method = RequestMethod.POST)
	@ApiOperation(value = "批量删除", response = Boolean.class)
	@ResponseBody
	public Object batchDelDataInIds(@RequestParam("ids")List<Long> ids) {
		return WrapMapper.ok(schoolStrategyService.batchDelSchoolStrategyInIds(ids));
	}

}