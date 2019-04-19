package com.bdxh.school.contoller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSchoolModeDto;
import com.bdxh.school.dto.ModifySchoolModeDto;
import com.bdxh.school.dto.QuerySchoolMode;
import com.bdxh.school.dto.SchoolModeDto;
import com.bdxh.school.entity.SchoolMode;
import com.bdxh.school.service.SchoolModeService;
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
@RequestMapping("/schoolMode")
@Slf4j
@Validated
@Api(value = "学校模式交互API", tags = "SchoolMode")
public class SchoolModeController {

	@Autowired
	private SchoolModeService schoolModeService;


	/**
	* @Description: 查询列表信息
	* @Date 2019-04-18 09:52:43
	*/
	@RequestMapping(value = "/findModesInCondition", method = RequestMethod.GET)
	@ApiOperation(value = "根据条件查询列表", response = SchoolMode.class)
	public Object findModesInCondition(SchoolModeDto schoolModeDto) {
        SchoolMode schoolMode = new SchoolMode();
        BeanUtils.copyProperties(schoolModeDto, schoolMode);
		List<SchoolMode> datas = schoolModeService.findSchoolModeInCondition(schoolMode);
		return WrapMapper.ok(datas);
	}

    /**
     * @Description: 增加
     * @Date 2019-04-18 09:52:43
     */
    @RequestMapping(value = "/addModesInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "添加应用模式", response = SchoolMode.class)
    public Object addModesInCondition(@Validated @RequestBody AddSchoolModeDto addSchoolModeDto, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
        SchoolMode schoolMode = new SchoolMode();
        BeanUtils.copyProperties(addSchoolModeDto, schoolMode);
		Boolean result = schoolModeService.addSchoolMode(schoolMode);
		return WrapMapper.ok(result);
    }


    /**
     * @Description: 修改
     * @Date 2019-04-18 09:52:43
     */
    @RequestMapping(value = "/updateModesInCondition", method = RequestMethod.POST)
    @ApiOperation(value = "修改应用模式", response = SchoolMode.class)
    public Object updateModesInCondition(@Validated @RequestBody ModifySchoolModeDto modifySchoolModeDto, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
        SchoolMode schoolMode = new SchoolMode();
        BeanUtils.copyProperties(modifySchoolModeDto, schoolMode);
		Boolean result = schoolModeService.modifySchoolMode(schoolMode);
        return WrapMapper.ok(result);
    }


    /**
     * @Description: 带条件分页查询列表信息
     * @Date 2019-04-18 09:52:43
     */
    @RequestMapping(value = "/findModesInConditionPage", method = RequestMethod.POST)
    @ApiOperation(value = "带条件分页查询列表信息", response = SchoolMode.class)
    public Object findModesInConditionPage(@Validated @RequestBody QuerySchoolMode querySchoolMode, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			Map<String, Object> param = BeanToMapUtil.objectToMap(querySchoolMode);
			PageInfo<QuerySchoolMode> Users = schoolModeService.findListPage(param,querySchoolMode.getPageNum(),querySchoolMode.getPageSize());
			return WrapMapper.ok(Users);
	       } catch (Exception e) {
		     e.printStackTrace();
		    return WrapMapper.error(e.getMessage());
	   }
    }

	/**
	* @Description: 删除信息
	* @Date 2019-04-18 09:52:43
	*/
	@RequestMapping(value = "/delModesById", method = RequestMethod.GET)
	@ApiOperation(value = "删除信息", response = Boolean.class)
	public Object delModesById(@RequestParam("id")Long id) {
		return WrapMapper.ok(schoolModeService.delSchoolModeById(id));
	}

	/**
	 * @Description: 根据id查询所需信息
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/getModesById", method = RequestMethod.GET)
	@ApiOperation(value = "根据id查询", response = Boolean.class)
	public Object getModesById(@RequestParam("id")Long id) {
		return WrapMapper.ok(schoolModeService.selectByKey(id));
	}



	/**
	* @Description: 批量删除
	* @Date 2019-04-18 09:52:43
	*/
	@RequestMapping(value = "/batchDelModesInIds", method = RequestMethod.POST)
	@ApiOperation(value = "批量删除", response = Boolean.class)
	public Object batchDelModesInIds(@RequestParam("ids")List<Long> ids) {
		return WrapMapper.ok(schoolModeService.batchDelSchoolModeInIds(ids));
	}

}