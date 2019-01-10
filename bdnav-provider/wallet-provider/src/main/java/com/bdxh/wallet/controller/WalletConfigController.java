package com.bdxh.wallet.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.WalletConfigDto;
import com.bdxh.wallet.entity.WalletXianConfig;
import com.bdxh.wallet.service.WalletXianConfigService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 一卡通配置类控制器
 * @author: xuyuan
 * @create: 2019-01-08 13:14
 **/
@Controller
@RequestMapping("/walletConfig")
@Slf4j
public class WalletConfigController {

    @Autowired
    private WalletXianConfigService walletXianConfigService;

    /**
     * 保存一卡通配置信息
     * @param walletConfigDto
     * @param bindingResult
     * @return
     */
    @RequestMapping("/saveWalletConfig")
    @ResponseBody
    public Object saveWalletConfig(@Valid WalletConfigDto walletConfigDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            WalletXianConfig walletXianConfig = BeanMapUtils.map(walletConfigDto, WalletXianConfig.class);
            int save = walletXianConfigService.save(walletXianConfig);
            Preconditions.checkArgument(save==1,"保存一卡通配置信息失败");
            return WrapMapper.ok();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据学校编码查询一卡通配置信息
     * @param schoolCode
     * @return
     */
    @RequestMapping("/queryWalletConfigBySchoolCode")
    @ResponseBody
    public Object queryWalletConfigBySchoolCode(@RequestParam(name = "schoolCode") String schoolCode){
        try {
            Preconditions.checkArgument(StringUtils.isNotEmpty(schoolCode),"学校编码不能为空");
            WalletXianConfig walletXianConfig=new WalletXianConfig();
            walletXianConfig.setSchoolCode(schoolCode);
            List<WalletXianConfig> list = walletXianConfigService.select(walletXianConfig);
            Preconditions.checkArgument(list!=null&&list.size()==1,"查询一卡通配置信息失败");
            return WrapMapper.ok(list.get(0));
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 分页查询一卡通配置信息
     * @return
     */
    @RequestMapping("/queryWalletConfigPage")
    @ResponseBody
    public Object queryWalletConfigPage(@RequestParam(name = "pageNum", defaultValue = "1") Integer pageNum,
                                        @RequestParam(name = "pageSize", defaultValue = "15") Integer pageSize){
        try {
            PageHelper.startPage(pageNum,pageSize);
            List<WalletXianConfig> list = walletXianConfigService.selectAll();
            PageInfo<WalletXianConfig> pageInfo=new PageInfo<>(list);
            return WrapMapper.ok(pageInfo);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
