package com.bdxh.servicepermit.contoller;

import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.servicepermit.dto.AddServiceUserDto;
import com.bdxh.servicepermit.dto.ModifyServiceUserDto;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.service.ServiceUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 控制器
* @Date 2019-04-26 10:26:58
*/
@RestController
@RequestMapping("/serviceUsed")
@Slf4j
@Validated
@Api(value = "用户服务认证控制器", tags = "用户服务认证交互API")
public class ServiceUserController {

	@Autowired
	private ServiceUserService serviceUserService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;

	//带条件分页所有
	@ApiOperation("带条件分页查询")
	@RequestMapping(value = "/queryUserOrder", method = RequestMethod.POST)
	public Object queryUserOrder(@Valid @RequestBody QueryServiceUserDto queryServiceUsedDto, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			Map<String, Object> param = BeanToMapUtil.objectToMap(queryServiceUsedDto);
			PageInfo<ServiceUser> qrders = serviceUserService.getServiceByCondition(param, queryServiceUsedDto.getPageNum(),queryServiceUsedDto.getPageSize());
			return WrapMapper.ok(qrders);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	/**
	 * 添加用户服务许可
	 * @param addServiceUsedDto
	 * @param bindingResult
	 * @return
	 */
	@ApiOperation("创建用户服务许可")
	@RequestMapping(value = "/createService", method = RequestMethod.POST)
	public Object createService(@Valid @RequestBody AddServiceUserDto addServiceUsedDto, BindingResult bindingResult) {
		//检验参数
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			ServiceUser Su=new ServiceUser();
			Su.setId(snowflakeIdWorker.nextId());
			BeanUtils.copyProperties(addServiceUsedDto, Su);
			Boolean flag =serviceUserService.save(Su)>0;
			return WrapMapper.ok(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 修改用户服务许可
	 * @param modifyServiceUsedDto
	 * @param bindingResult
	 * @return
	 */
	@ApiOperation("修改用户服务许可")
	@RequestMapping(value = "/updateService", method = RequestMethod.POST)
	public Object updateService(@Valid @RequestBody ModifyServiceUserDto modifyServiceUsedDto, BindingResult bindingResult) {
		//检验参数
		if (bindingResult.hasErrors()) {
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			ServiceUser Su=new ServiceUser();
			BeanUtils.copyProperties(modifyServiceUsedDto, Su);
			Boolean flag =serviceUserService.update(Su)>0;
			return WrapMapper.ok(flag);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}



	@ApiOperation("删除用户许可")
	@RequestMapping(value = "/deleteService", method = RequestMethod.GET)
	public Object deleteService(@RequestParam("schoolCode") @NotNull(message = "schoolCode不能为空") String schoolCode,
							  @RequestParam("cardNumber") @NotNull(message = "家长号不能为空") Long cardNumber,
							  @RequestParam(name = "id") @NotNull(message = "服务id不能为空") Long id) {
		try {
			Boolean result = serviceUserService.deleteByServiceId(schoolCode,cardNumber,id);
			return WrapMapper.ok(result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e.getStackTrace());
			return WrapMapper.error(e.getMessage());
		}
	}

}