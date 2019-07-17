package com.bdxh.client.controller.wallet;


import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolChargeDeptControllerClient;
import com.bdxh.school.feign.SchoolDeviceControllerClient;
import com.bdxh.school.vo.ChargeDeptAndDeviceVo;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import com.bdxh.wallet.vo.WalletRechargeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author WanMing
 * @date 2019/7/17 11:12
 */
@RestController
@RequestMapping("/walletRechargeWeb")
@Slf4j
@Validated
@Api(value = "账户充值记录管理", tags = "账户充值记录管理API")
public class WalletRechargeWebController {


    @Autowired
    private WalletRechargeControllerClient walletRechargeControllerClient;

    @Autowired
    private SchoolDeviceControllerClient schoolDeviceControllerClient;


    /**
     * 根据条件分页查询充值记录
     *
     * @Author: WanMing
     * @Date: 2019/7/15 11:13
     */
    @RequestMapping(value = "/findWalletRechargeByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件分页查询充值记录", response = Boolean.class)
    public Object findWalletRechargeByCondition(@RequestBody QueryWalletRechargeDto queryWalletRechargeDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        queryWalletRechargeDto.setSchoolCode(/*user.getSchoolCode()*/"1013371381");
        List<WalletRechargeVo> walletRechargeVos = walletRechargeControllerClient.findWalletRechargeByCondition(queryWalletRechargeDto).getResult().getList();
        if (CollectionUtils.isEmpty(walletRechargeVos)) {
            //无数据
            return WrapMapper.ok(walletRechargeVos);
        }
        List<ChargeDeptAndDeviceVo> deptAndDeviceVos = schoolDeviceControllerClient.findChargeDeptAndDeviceRelation(/*user.getSchoolCode()*/"1013371381").getResult();
        if (CollectionUtils.isNotEmpty(deptAndDeviceVos)) {
            Map<String, String> deptAndDeviceVoMap = deptAndDeviceVos.stream().collect(Collectors.toMap(ChargeDeptAndDeviceVo::getDeviceId, ChargeDeptAndDeviceVo::getChargeDeptName));
            walletRechargeVos.forEach(walletRechargeVo -> {
                walletRechargeVo.setWindowName(deptAndDeviceVoMap.get(walletRechargeVo.getDeviceNumber()));
            });
        }

        return WrapMapper.ok(walletRechargeVos);
    }
}
