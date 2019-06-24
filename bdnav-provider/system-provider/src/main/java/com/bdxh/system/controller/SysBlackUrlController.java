package com.bdxh.system.controller;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.SysBlackUrlQueryDto;
import com.bdxh.system.dto.AddSysBlackUrlDto;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.bdxh.system.entity.SysBlackUrl;
import com.bdxh.system.service.SysBlackUrlService;

import java.util.List;

/**
* @Description: 系统黑名单(包括第三方金山等)控制器
* @Author wanMing
* @Date 2019-06-20 11:32:50
*/
@RestController
@RequestMapping("/sysBlackUrl")
@Slf4j
@Validated
@Api(value = "系统黑名单(包括第三方金山)", tags = "系统黑名单(包括第三方金山)API")
public class SysBlackUrlController {

	@Autowired
	private SysBlackUrlService sysBlackUrlService;

	/**
	 * 添加系统的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/20 11:52
	 */
	@RequestMapping(value = "/addSysBlackUrl",method = RequestMethod.POST)
	@ApiOperation(value = "添加系统的黑名单",response = Boolean.class)
	public Object addSysBlackUrl(@Validated @RequestBody AddSysBlackUrlDto addSysBlackUrlDto){
		//数据拷贝
		SysBlackUrl sysBlackUrl = new SysBlackUrl();
		BeanUtils.copyProperties(addSysBlackUrlDto,sysBlackUrl);
		try {
			return WrapMapper.ok(sysBlackUrlService.save(sysBlackUrl)>0);
		} catch (Exception e) {
			if(e instanceof DuplicateKeyException){
				return WrapMapper.error("此url已存在");
			}
			e.printStackTrace();
		}
		return WrapMapper.ok();
	}

	/**
	 * 删除系统的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/20 11:52
	 */
	@RequestMapping(value = "/delSysBlackUrl",method = RequestMethod.GET)
	@ApiOperation(value = "删除系统的黑名单",response = Boolean.class)
	public Object delSysBlackUrl(@RequestParam("id") Long id){
		return WrapMapper.ok(sysBlackUrlService.deleteByKey(id)>0);
	}

	/**
	 * 批量删除系统的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/20 11:52
	 */
	@RequestMapping(value = "/batchDelSysBlackUrl",method = RequestMethod.GET)
	@ApiOperation(value = "批量删除系统的黑名单",response = Boolean.class)
	public Object batchDelSysBlackUrl(@RequestParam("ids") List<Long> ids){
		return WrapMapper.ok(sysBlackUrlService.batchDelSysBlackUrlInIds(ids));
	}


	/**
	 * 根据条件分页查询系统的黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/20 11:52
	 */
	@RequestMapping(value = "/findSysBlackUrlByCondition",method = RequestMethod.POST)
	@ApiOperation(value = "根据条件分页查询系统的黑名单",response = SysBlackUrlVo.class)
	public Object findSysBlackUrlByCondition(@Validated @RequestBody SysBlackUrlQueryDto sysBlackUrlQueryDto){
		PageInfo pageInfo = sysBlackUrlService.findSysBlackUrlByCondition(sysBlackUrlQueryDto);
		return WrapMapper.ok(pageInfo);
	}


	/**
	 * 查询所有本地病毒库数据
	 * @Author: WanMing
	 * @Date: 2019/6/24 15:02
	 */
	@RequestMapping(value = "/queryAllSysBlackUrl",method = RequestMethod.GET)
	@ApiOperation(value = "查询所有本地病毒库数据",response = SysBlackUrlVo.class)
	public Object queryAllSysBlackUrl(){
	    return WrapMapper.ok(sysBlackUrlService.selectAll());
    }


    /**
     * 批量判断url的安全性
     * @Author: WanMing
     * @Date: 2019/6/24 16:19
     */
	@RequestMapping(value = "/batchCheckSysBlackUrl",method = RequestMethod.POST)
	@ApiOperation(value = "批量判断url的安全性")
    public Object batchCheckSysBlackUrl(@RequestParam("urls") List<String> urls){
		sysBlackUrlService.batchCheckSysBlackUrl(urls);
		return WrapMapper.ok();
	}



}