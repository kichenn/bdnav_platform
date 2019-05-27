package com.bdxh.account.controller;

import com.bdxh.account.dto.AddUserDevice;
import com.bdxh.account.dto.ModifyUserDevice;
import com.bdxh.account.entity.Account;
import com.bdxh.account.entity.UserDevice;
import com.bdxh.account.service.UserDeviceService;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-24 14:35:27
*/
@RestController
@RequestMapping("/userDevice")
@Slf4j
@Validated
@Api(value = "UserDevice控制器", tags = "UserDevice")
public class UserDeviceController {

	@Autowired
	private UserDeviceService userDeviceService;

	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;


	@ApiOperation(value = "查询手机设备登录信息", response = Account.class)
	@RequestMapping(value = "/getUserDeviceAll", method = RequestMethod.GET)
	public Object getUserDeviceAll(@RequestParam("schoolCode")String schoolCode,@RequestParam("groupId") Long groupId) {
		List<UserDevice> accounts = userDeviceService.getUserDeviceAll(schoolCode,groupId);
		return WrapMapper.ok(accounts);
	}

	/**
	 * @Description: 添加用户设备登录信息
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/addUserDeviceInfo", method = RequestMethod.POST)
	@ApiOperation(value = "添加用户设备登录信息", response = UserDevice.class)
	public Object addUserDeviceInfo(@Valid @RequestBody AddUserDevice addUserDevice, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			UserDevice userDevices=new UserDevice();
			long id = snowflakeIdWorker.nextId();
			userDevices.setId(id);
		/*	SchoolMode schoolMode = schoolModeService.getSchoolModesByName(addSchoolModeDto.getModelName(),addSchoolModeDto.getSchoolId());
			Preconditions.checkArgument(schoolMode == null, "该模式已存在,请更换后添加");*/
			BeanUtils.copyProperties(addUserDevice, userDevices);
			Boolean result = userDeviceService.save(userDevices)>0;
			return WrapMapper.ok(result);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}

	}


	/**
	 * @Description: 修改用户设备登录信息
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/modifyUserDeviceInfo", method = RequestMethod.POST)
	@ApiOperation(value = "修改用户设备登录信息", response = UserDevice.class)
	public Object modifyUserDeviceInfo(@Valid @RequestBody ModifyUserDevice modifyUserDevice, BindingResult bindingResult) {
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try{
			UserDevice userDevices=new UserDevice();
			BeanUtils.copyProperties(modifyUserDevice, userDevices);
			Boolean result =  userDeviceService.update(userDevices)>0;
			return WrapMapper.ok(result);
		} catch (RuntimeException e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}




	/**
	 * @Description: 删除用户登录设备信息
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/delUserDeviceById", method = RequestMethod.GET)
	@ApiOperation(value = "删除用户登录设备信息", response = Boolean.class)
	public Object delUserDeviceById(@RequestParam("id")Long id) {
		try{
			return WrapMapper.ok(userDeviceService.deleteByKey(id));
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}

	}


	/**
	 * @Description: 根据条件查询用户设备信息是否存在
	 * @Date 2019-04-18 09:52:43
	 */
	@RequestMapping(value = "/findUserDeviceByCodeOrCard", method = RequestMethod.GET)
	@ApiOperation(value = "根据条件查询用户设备信息", response = Boolean.class)
	public Object findUserDeviceByCodeOrCard(@RequestParam("schoolCode")String schoolCode,@RequestParam("cardNumber")String cardNumber) {
		try{
			UserDevice ud=userDeviceService.findUserDeviceByCodeOrCard(schoolCode,cardNumber);
			return WrapMapper.ok(ud);
		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}

	}




}