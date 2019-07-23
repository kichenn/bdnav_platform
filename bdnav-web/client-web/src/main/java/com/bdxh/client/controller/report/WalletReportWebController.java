package com.bdxh.client.controller.report;

import com.bdxh.school.feign.SchoolChargeDeptControllerClient;
import com.bdxh.wallet.feign.WalletConsumerControllerClient;
import com.bdxh.wallet.feign.WalletRechargeControllerClient;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**账户钱包的报表Web
 * @author WanMing
 * @date 2019/7/23 12:07
 */
@RestController
@RequestMapping("/clientWalletReportWeb")
@Slf4j
@Validated
@Api(value = "校园钱包--报表信息", tags = "校园钱包--报表信息API")
public class WalletReportWebController {

    @Autowired
    private WalletRechargeControllerClient walletRechargeControllerClient;

    @Autowired
    private WalletConsumerControllerClient walletConsumerControllerClient;

    @Autowired
    private SchoolChargeDeptControllerClient schoolChargeDeptControllerClient;




}
