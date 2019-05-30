package com.bdxh.school.contoller;

import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.dto.AddSchoolServicePermitDto;
import com.bdxh.school.dto.ModifySchoolServicePermitDto;
import com.bdxh.school.dto.SchoolServicePermitQueryDto;
import com.bdxh.school.entity.School;
import com.bdxh.school.entity.SchoolServicePermit;
import com.bdxh.school.service.SchoolService;
import com.bdxh.school.service.SchoolServicePermitService;
import com.bdxh.school.vo.SchoolServicePermitShowVo;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Supplier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.stream.Collectors;


/**
* @Description: 学校服务的许可权限控制器
* @Author wanMing
* @Date 2019-05-28 10:24:19
*/
@RestController
@RequestMapping("/schoolServicePermit")
@Slf4j
@Validated
@Api(value = "学校服务的许可权限控制器", tags = "学校服务的许可权限控制器API")
public class SchoolServicePermitController {


	@Autowired
	private SchoolServicePermitService schoolServicePermitService;



	/**
	 * 增加学校服务许可权限
	 * @Author: WanMing
	 * @Date: 2019/5/28 11:11
	 */
	@RequestMapping(value = "/addSchoolServicePermit",method = RequestMethod.POST)
	@ApiOperation(value = "增加学校服务的许可权限",response = Boolean.class)
	public Object addSchoolServicePermit(@Validated @RequestBody AddSchoolServicePermitDto addSchoolServicePermitDto){

		//由于只能存在一条学校记录
		Integer count = schoolServicePermitService.querySchoolServicePermitCountBySchoolCode(addSchoolServicePermitDto.getSchoolCode());
		if(count>0){
			return WrapMapper.error("此学校许可记录已存在");
		}
		//数据拷贝
		SchoolServicePermit schoolServicePermit = new SchoolServicePermit();
		BeanUtils.copyProperties(addSchoolServicePermitDto, schoolServicePermit);
		schoolServicePermit.setStatus(addSchoolServicePermitDto.getStatus().getKey());
		Date nowTime = DateUtil.getNowTime();
		schoolServicePermit.setCreateDate(nowTime);
		schoolServicePermit.setUpdateDate(nowTime);
		//添加
		try {
			Boolean result = schoolServicePermitService.save(schoolServicePermit) > 0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}

	}

	/**
	 * 修改学校服务许可权限
	 * @Author: WanMing
	 * @Date: 2019/5/28 14:26
	 */
	@RequestMapping(value = "/modifySchoolServicePermit",method = RequestMethod.POST)
	@ApiOperation(value = "修改学校服务的许可权限",response = Boolean.class)
	public Object modifySchoolServicePermit(@Validated @RequestBody ModifySchoolServicePermitDto modifySchoolServicePermitDto){
		//信息拷贝
		SchoolServicePermit schoolServicePermit = new SchoolServicePermit();
		BeanUtils.copyProperties(modifySchoolServicePermitDto, schoolServicePermit);
		schoolServicePermit.setStatus(modifySchoolServicePermitDto.getStatus()!=null?modifySchoolServicePermitDto.getStatus().getKey():null);
		schoolServicePermit.setUpdateDate(DateUtil.getNowTime());
		//修改
		try {
			Boolean result = schoolServicePermitService.update(schoolServicePermit)>0;
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}

	}



	/**
	 * 分页查询学校服务许可权限信息
	 * @Author: WanMing
	 * @Date: 2019/5/28 17:07
	 */
	@RequestMapping(value = "/findSchoolServicePermitInConditionPage",method = RequestMethod.POST)
	@ApiOperation(value = "分页查询学校服务许可权限信息",response = SchoolServicePermitShowVo.class)
	public Object findSchoolServicePermitInConditionPage(@Validated @RequestBody SchoolServicePermitQueryDto schoolServicePermitQueryDto){
		//分页查询
		PageInfo<SchoolServicePermitShowVo> SchoolServicePermitShowVos  = schoolServicePermitService.findSchoolServicePermitInConditionPage(schoolServicePermitQueryDto);
		return WrapMapper.ok(SchoolServicePermitShowVos);
	}


	/**
	 * 根据id查询学校服务许可权限信息
	 * @Author: WanMing
	 * @Date: 2019/5/28 17:13
	 */
	@RequestMapping(value = "/findSchoolServicePermitById",method = RequestMethod.GET)
	@ApiOperation(value = "根据id查询学校服务许可权限信息",response = SchoolServicePermit.class)
	public Object findSchoolServicePermitById(@RequestParam(name = "id") Long id){
		SchoolServicePermit schoolServicePermit = schoolServicePermitService.findSchoolServicePermitById(id);
		return WrapMapper.ok(schoolServicePermit);
	}

}