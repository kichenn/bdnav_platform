package com.bdxh.user.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.service.FenceAlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
* @Description: 家长围栏警报控制器
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@RestController
@RequestMapping("/fenceAlarm")
@Slf4j
@Validated
@Api(value = "家长围栏警报控制器", tags = "家长围栏警报控制器")
public class FenceAlarmController {

	@Autowired
	private FenceAlarmService fenceAlarmService;
	/**
	 * 查询所有
	 * @param fenceAlarmQueryDto
	 * @return
	 */
	@ApiOperation("查询所有围栏警报接口")
	@RequestMapping(value = "/getAllFenceAlarmInfos",method = RequestMethod.POST)
	public Object getAllFenceAlarmInfos(@Valid @RequestBody  FenceAlarmQueryDto fenceAlarmQueryDto, BindingResult bindingResult){
		try {
			//检验参数
			if (bindingResult.hasErrors()) {
				String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
				return WrapMapper.error(errors);
			}
			return WrapMapper.ok( fenceAlarmService.getAllFenceAlarmInfos(fenceAlarmQueryDto));
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 查询单个
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	@ApiOperation("查询单个围栏警报接口")
	@RequestMapping(value="/getFenceAlarmInfo",method = RequestMethod.POST)
	public Object getFenceAlarmInfo(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
									@RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
									@RequestParam(name="id") @NotNull(message = "id不能为空")  String id){
		try {
			return WrapMapper.ok(fenceAlarmService.getFenceAlarmInfo(schoolCode,cardNumber,id));
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 查询单个
	 * @param schoolCode
	 * @param cardNumber
	 * @param fenceId
	 * @return
	 */
	@ApiOperation("查询单个围栏警报接口")
	@RequestMapping(value="/getFenceAlarmInfos",method = RequestMethod.POST)
	public Object getFenceAlarmInfos(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
									@RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
									@RequestParam(name="fenceId") @NotNull(message = "围栏Id不能为空")  String fenceId){
		try {
			return WrapMapper.ok(fenceAlarmService.findFenceAlarmInfos(schoolCode,cardNumber,fenceId));
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 删除单个
	 * @param id
	 * @param schoolCode
	 * @param cardNumber
	 */
	@ApiOperation("删除单个围栏警报接口")
	@RequestMapping(value="/removeFenceAlarmInfo",method = RequestMethod.GET)
	public Object removeFenceAlarmInfo(@RequestParam("id")String id,@RequestParam("schoolCode")String schoolCode,@RequestParam("cardNumber") String  cardNumber){
		try {
			fenceAlarmService.removeFenceAlarmInfo(id,schoolCode,cardNumber);
			return WrapMapper.ok();
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 批量删除
	 * @param ids
	 * @param schoolCodes
	 * @param cardNumbers
	 */
	@ApiOperation("批量删除围栏警报接口")
	@RequestMapping(value="/batchRemoveFenceAlarmInfo",method = RequestMethod.POST)
	public Object batchRemoveFenceAlarmInfo(@RequestParam("ids")String ids,@RequestParam("schoolCodes")String schoolCodes,@RequestParam("cardNumbers")String  cardNumbers){
		try {
			fenceAlarmService.batchRemoveFenceAlarmInfo(ids,schoolCodes,cardNumbers);
			return WrapMapper.ok();
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 修改围栏警报接口
	 * @param updateFenceAlarmDto
	 */
	@ApiOperation("修改围栏警报接口")
	@RequestMapping(value="/updateFenceAlarmInfo",method = RequestMethod.POST)
	public Object updateFenceAlarmInfo(@Valid @RequestBody UpdateFenceAlarmDto updateFenceAlarmDto,BindingResult bindingResult){
		try {
			//检验参数
			if (bindingResult.hasErrors()) {
				String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
				return WrapMapper.error(errors);
			}
			fenceAlarmService.updateFenceAlarmInfo(updateFenceAlarmDto);
			return WrapMapper.ok();
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}

	/**
	 * 新增围栏警报接口
	 * @param addFenceAlarmDto
	 */
	@ApiOperation("新增围栏警报接口")
	@RequestMapping(value="/insertFenceAlarmInfo",method = RequestMethod.POST)
	public Object insertFenceAlarmInfo(@Valid @RequestBody AddFenceAlarmDto addFenceAlarmDto,BindingResult bindingResult){
		try {
			//检验参数
			if (bindingResult.hasErrors()) {
				String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
				return WrapMapper.error(errors);
			}
			fenceAlarmService.insertFenceAlarmInfo(addFenceAlarmDto);
			return WrapMapper.ok();
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}
}