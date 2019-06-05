package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.AddControlConfig;
import com.bdxh.system.dto.ModifyControlConfig;
import com.bdxh.system.dto.QueryControlConfig;
import com.bdxh.system.entity.ControlConfig;
import com.bdxh.system.service.ControlConfigService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @Description: 控制器
* @Author Kang
* @Date 2019-05-30 09:36:37
*/
@RestController
@RequestMapping("/controlConfig")
@Slf4j
@Validated
@Api(value = "app应用黑白名单控制器", tags = "app应用黑白名单控制器")
public class ControlConfigController {

	@Autowired
	private ControlConfigService controlConfigService;

	/**
	 * 根据类型查询应用黑白名单
	 *
	 * @param appType
	 * @return
	 */
	@RequestMapping(value = "/findAppType", method = RequestMethod.GET)
	@ApiOperation("查询应用黑白名单")
	public Object findAppType(@RequestParam(name = "appType") Byte appType) {
		try {
		    List<String> all=new ArrayList<>();
			List<ControlConfig> ccf=controlConfigService.findAppType(appType);
            for (int i = 0; i < ccf.size(); i++) {
                all.add(ccf.get(i).getAppPackage());
            }
			return WrapMapper.ok(all);

		} catch (Exception e) {
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


	/**
	 * 根据条件分页查找
	 * @param queryControlConfig
	 * @return
	 */
	@ApiOperation("根据条件分页查找应用管控信息")
	@RequestMapping(value = "/queryListPage",method = RequestMethod.POST)
	public Object queryListPage(@Valid @RequestBody QueryControlConfig queryControlConfig, BindingResult bindingResult){
		//检验参数
		if(bindingResult.hasErrors()){
			String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
			return WrapMapper.error(errors);
		}
		try {
			Map<String, Object> param = BeanToMapUtil.objectToMap(queryControlConfig);
			PageInfo<ControlConfig> ControlConfigData = controlConfigService.findListPage(param,queryControlConfig.getPageNum(),queryControlConfig.getPageSize());
			return WrapMapper.ok(ControlConfigData);
		}catch (Exception e){
			e.printStackTrace();
			return WrapMapper.error(e.getMessage());
		}
	}


    /**
     * 增加应用管控信息
     * @param addControlConfig
     * @return
     */
    @ApiOperation("增加应用管控信息")
    @RequestMapping(value = "/addControlConfig",method = RequestMethod.POST)
    public Object addControlConfig(@Valid @RequestBody AddControlConfig addControlConfig, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {

        ControlConfig controlConfig= new ControlConfig();
            BeanUtils.copyProperties(addControlConfig, controlConfig);
            Boolean flag=controlConfigService.save(controlConfig)>0;
            return WrapMapper.ok(flag);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 修改应用管控信息
     * @param modifyControlConfig
     * @return
     */
    @ApiOperation("修改应用管控信息")
    @RequestMapping(value = "/modifyControlConfig",method = RequestMethod.POST)
    public Object modifyControlConfig(@Valid @RequestBody ModifyControlConfig modifyControlConfig, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            ControlConfig controlConfig= new ControlConfig();
            BeanUtils.copyProperties(modifyControlConfig, controlConfig);
            Boolean flag=controlConfigService.update(controlConfig)>0;
            return WrapMapper.ok(flag);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除应用管控信息
     * @param
     * @return
     */
    @ApiOperation("删除应用管控信息")
    @RequestMapping(value = "/delControlConfig",method = RequestMethod.GET)
    public Object delControlConfig(@RequestParam(name = "id") Long id){
        try {
            Boolean result=controlConfigService.deleteByKey(id)>0;
            return WrapMapper.ok(result);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}