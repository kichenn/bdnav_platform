package com.bdxh.school.contoller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.*;
import com.bdxh.school.entity.SchoolStrategy;
import com.bdxh.school.service.SchoolStrategyService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
	@RequestMapping(value = "/delDataById", method = RequestMethod.GET)
	@ApiOperation(value = "删除信息", response = Boolean.class)
	@ResponseBody
	public Object delDataById(@RequestParam("id")Long id) {
	   try{
		Boolean result=schoolStrategyService.deleteByKey(id)>0;
		return WrapMapper.ok(result);
	   } catch (Exception e) {
		e.printStackTrace();
		return WrapMapper.error(e.getMessage());
	  }
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

	/**
	 * @Description: 带条件分页查询策略列表信息
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/findPolicyInConditionPage", method = RequestMethod.POST)
	@ApiOperation(value = "带条件分页查询列表信息")
	@ResponseBody
	public Object findPolicyInConditionPage(@Validated @RequestBody QuerySchoolStrategy querySchoolStrategy, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			Map<String, Object> param = BeanToMapUtil.objectToMap(querySchoolStrategy);
			PageInfo<QuerySchoolStrategy> policys = schoolStrategyService.findListPage(param,querySchoolStrategy.getPageNum(),querySchoolStrategy.getPageSize());
			return WrapMapper.ok(policys);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * @Description: 增加
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/addPolicyInCondition", method = RequestMethod.POST)
	@ApiOperation(value = "添加策略模式",  response = Boolean.class)
	public Object addPolicyInCondition(@Validated @RequestBody AddPolicyDto addPolicyDto, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			SchoolStrategy schoolStrategy=new SchoolStrategy();
			BeanUtils.copyProperties(addPolicyDto, schoolStrategy);
			schoolStrategy.setRecursionPermission(Integer.valueOf(addPolicyDto.getRecursionPermission().getKey()));
			Boolean result = schoolStrategyService.save(schoolStrategy)>0;
			return WrapMapper.ok(result);
		} catch (RuntimeException e) {
		e.printStackTrace();
		return WrapMapper.error(e.getMessage());
	  }

	}


	/**
	 * @Description: 修改
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/updatePolicyInCondition", method = RequestMethod.POST)
	@ApiOperation(value = "修改策略模式",  response = Boolean.class)
	public Object updatePolicyInCondition(@Validated @RequestBody ModifyPolicyDto modifyPolicyDto, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			SchoolStrategy schoolStrategy=new SchoolStrategy();
			BeanUtils.copyProperties(modifyPolicyDto, schoolStrategy);
			schoolStrategy.setRecursionPermission(Integer.valueOf(modifyPolicyDto.getRecursionPermission().getKey()));
			Boolean result =  schoolStrategyService.update(schoolStrategy)>0;
			return WrapMapper.ok(result);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}

	}


}