package com.bdxh.client.controller.wallet;

import ch.qos.logback.core.util.StringCollectionUtil;
import com.bdxh.client.configration.security.utils.SecurityUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.school.entity.SchoolUser;
import com.bdxh.school.feign.SchoolDeviceControllerClient;
import com.bdxh.school.vo.ChargeDeptAndDeviceVo;
import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.feign.WalletConsumerControllerClient;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.PageInfo;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WanMing
 * @date 2019/7/16 10:58
 */
@RestController
@RequestMapping("/walletConsumerWeb")
@Slf4j
@Validated
@Api(value = "账户消费记录管理", tags = "账户消费记录管理API")
public class WalletConsumerWebController {

    @Autowired
    private WalletConsumerControllerClient walletConsumerControllerClient;

    @Autowired
    private SchoolDeviceControllerClient schoolDeviceControllerClient;

    /**
     * 根据条件查询账户的消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 17:22
     */
    @RequestMapping(value = "/findWalletConsumerByCondition", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询账户的消费记录", response = WalletConsumerVo.class)
    public Object findWalletConsumerByCondition(@RequestBody QueryWalletConsumerDto queryWalletConsumerDto) {
        SchoolUser user = SecurityUtils.getCurrentUser();
        queryWalletConsumerDto.setSchoolCode(/*user.getSchoolCode()*/"1013371381");
        List<WalletConsumerVo> walletConsumerVos = walletConsumerControllerClient.findWalletConsumerByCondition(queryWalletConsumerDto).getResult().getList();
        if (CollectionUtils.isNotEmpty(walletConsumerVos)) {
            //查询pos机及相关信息
            List<ChargeDeptAndDeviceVo> result = schoolDeviceControllerClient.findChargeDeptAndDeviceRelation(/*user.getSchoolCode()*/"1013371381").getResult();
            if (CollectionUtils.isNotEmpty(result)) {
                Map<String, ChargeDeptAndDeviceVo> deviceVoMap = result.stream().collect(Collectors.toMap(ChargeDeptAndDeviceVo::getDeviceId, Function.identity()));
                walletConsumerVos.forEach(walletConsumerVo -> {
                    ChargeDeptAndDeviceVo chargeDeptAndDeviceVo = deviceVoMap.get(walletConsumerVo.getDeviceNumber());
                    if (null != chargeDeptAndDeviceVo) {
                        walletConsumerVo.setChargeDeptName(chargeDeptAndDeviceVo.getChargeDeptName());
                        walletConsumerVo.setDeviceName(chargeDeptAndDeviceVo.getDeviceName());
                    }

                });
            }

        }
        return WrapMapper.ok(walletConsumerVos);
    }
}
