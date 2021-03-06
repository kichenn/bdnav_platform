package com.bdxh.user.controller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddFamilyBlackUrlDto;
import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.dto.ModifyFamilyBlackUrlDto;
import com.bdxh.user.entity.FamilyBlackUrl;
import com.bdxh.user.service.FamilyBlackUrlService;
import com.bdxh.user.vo.FamilyBlackUrlVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 家长端黑名单控制器
* @Author WanMing
* @Date 2019-06-25 10:17:12
*/
@RestController
@RequestMapping("/familyBlackUrl")
@Slf4j
@Validated
@Api(value = "家长黑名单管理", tags = "家长黑名单管理API")
public class FamilyBlackUrlController {

	@Autowired
	private FamilyBlackUrlService familyBlackUrlService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;


	/**
	 * 添加家长端的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/25 10:44
	 */
	@ApiOperation(value = "添加家长端的黑名单",response = Boolean.class)
	@RequestMapping(value = "/addFamilyBlackUrl",method = RequestMethod.POST)
	public Object addFamilyBlackUrl(@Validated @RequestBody AddFamilyBlackUrlDto addFamilyBlackUrlDto){
		//数据拷贝
		FamilyBlackUrl familyBlackUrl = new FamilyBlackUrl();
		BeanUtils.copyProperties(addFamilyBlackUrlDto, familyBlackUrl);
		familyBlackUrl.setId(snowflakeIdWorker.nextId());
		familyBlackUrl.setStatus(addFamilyBlackUrlDto.getStatus().getKey());
		//添加
		try {
			familyBlackUrlService.addFamilyBlackUrl(familyBlackUrl);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				//数据库设置唯一索引 黑名单ip,家长id,学生id
				return WrapMapper.error("该条url已存在黑名单中,请勿重复添加");
			}
			e.printStackTrace();
		}
		return WrapMapper.ok(familyBlackUrl.getId());
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
		return WrapMapper.ok(familyBlackUrlService.delFamilyBlackUrl(schoolCode,cardNumber,id));
	}


	/**
	 * 修改家长端的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/25 10:44
	 */
	@ApiOperation(value = "修改家长端的黑名单",response = String.class)
	@RequestMapping(value = "/modifyFamilyBlackUrl",method = RequestMethod.POST)
	public Object modifyFamilyBlackUrl(@Validated @RequestBody ModifyFamilyBlackUrlDto modifyFamilyBlackUrlDto ){
		//数据拷贝
		FamilyBlackUrl familyBlackUrl = new FamilyBlackUrl();
		BeanUtils.copyProperties(modifyFamilyBlackUrlDto,familyBlackUrl);
		familyBlackUrl.setStatus(modifyFamilyBlackUrlDto.getStatus()==null?null:modifyFamilyBlackUrlDto.getStatus().getKey());
		//修改
		try {
			familyBlackUrlService.modifyFamilyBlackUrl(familyBlackUrl);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				//数据库设置唯一索引 黑名单ip,家长id,学生id
				return WrapMapper.error("该条url已存在黑名单中,请勿重复添加");
			}
			e.printStackTrace();
		}
		return WrapMapper.ok();
	}


/*
	*/
/**
	 * 根据条件分页查询家长端的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/25 10:44
	 *//*

	@ApiOperation(value = "根据条件分页查询家长端的黑名单",response = FamilyBlackUrlVo.class)
	@RequestMapping(value = "/findFamilyBlackUrlByCondition",method = RequestMethod.POST)
	public Object findFamilyBlackUrlByCondition(@RequestBody FamilyBlackUrlQueryDto familyBlackUrlQueryDto ){
		return WrapMapper.ok(familyBlackUrlService.findFamilyBlackUrlByCondition(familyBlackUrlQueryDto));
	}
*/

	/**
	 * 查询家长对应孩子的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/25 10:44
	 */
	@ApiOperation(value = "查询家长对应孩子的黑名单",response = FamilyBlackUrlVo.class)
	@RequestMapping(value = "/findFamilyBlackUrlByStudent",method = RequestMethod.GET)
	public Object findFamilyBlackUrlByStudent(@RequestParam("schoolCode" )String schoolCode,
											  @RequestParam("cardNumber")String cardNumber,
											  @RequestParam("studentNumber")String studentNumber){
		return WrapMapper.ok(familyBlackUrlService.findFamilyBlackUrlByStudent(schoolCode,cardNumber,studentNumber));
	}


	@ApiOperation(value = "查询孩子的黑名单",response = FamilyBlackUrlVo.class)
	@RequestMapping(value = "/findBlackInList",method = RequestMethod.GET)
	public Object findBlackInList(@RequestParam("schoolCode" )String schoolCode,
											  @RequestParam("studentNumber")String studentNumber){
		List<String> urlList=new ArrayList<>();
		List<FamilyBlackUrlVo> bus= familyBlackUrlService.findBlackInList(schoolCode,studentNumber);
		for (int i = 0; i < bus.size(); i++) {
			urlList.add(bus.get(i).getIp());
		}
		return WrapMapper.ok(urlList);
	}


	/**
	 * 根据id查询黑名单详情
	 * @Date: 2019/6/25 10:44
	 */
	@ApiOperation(value = "根据id查询黑名单详情",response = FamilyBlackUrl.class)
	@RequestMapping(value = "/findBlackUrlById",method = RequestMethod.GET)
	public Object findBlackUrlById(@RequestParam("schoolCode" )String schoolCode,
								   @RequestParam("cardNumber")String cardNumber,
											  @RequestParam("id")Long id){
		return WrapMapper.ok(familyBlackUrlService.findBlackUrlById(schoolCode,cardNumber,id));
	}

	/**
	 * @Description: 带条件分页查询列表信息
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/findFamilyBlackUrlByCondition", method = RequestMethod.POST)
	@ApiOperation(value = "带条件分页查询列表信息")
	@ResponseBody
	public Object findFamilyBlackUrlByCondition(@Validated @RequestBody FamilyBlackUrlQueryDto familyBlackUrlQueryDto, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			Map<String, Object> param = BeanToMapUtil.objectToMap(familyBlackUrlQueryDto);
			PageInfo<FamilyBlackUrlVo> FamilyBlackUrls = familyBlackUrlService.findFamilyBlackUrlByCondition(param,familyBlackUrlQueryDto.getPageNum(),familyBlackUrlQueryDto.getPageSize());
			return WrapMapper.ok(FamilyBlackUrls);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


}