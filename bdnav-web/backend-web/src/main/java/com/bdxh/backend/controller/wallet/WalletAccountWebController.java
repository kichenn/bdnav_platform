package com.bdxh.backend.controller.wallet;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.wallet.dto.*;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.feign.WalletAccountControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/walletAccountWeb")
@Slf4j
@Validated
@Api(value = "钱包账户控制器", tags = "钱包账户控制器")
public class WalletAccountWebController {

    @Autowired
    private WalletAccountControllerClient walletAccountControllerClient;



    @ApiOperation(value = "删除钱包账户",response = Boolean.class)
    @RequestMapping(value = "/delWalletAccount", method = RequestMethod.GET)
    public Object delWalletAccount(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) {
        try {
            return walletAccountControllerClient.delWalletAccount(schoolCode,cardNumber,id);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "添加钱包账号",response = Boolean.class)
    @RequestMapping(value = "/addWalletAccount", method = RequestMethod.POST)
    public Object addWalletAccount(@RequestBody AddWalletAccount addWalletAccount) {
        try {
            return walletAccountControllerClient.addWalletAccount(addWalletAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value="修改钱包账号",response = Boolean.class)
    @RequestMapping(value = "/modifyPhysicalCard", method = RequestMethod.POST)
    public Object modifyPhysicalCard(@RequestBody ModifyWalletAccount modifyWalletAccount) {
        try {
            return walletAccountControllerClient.modifyWalletAccount(modifyWalletAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @ApiOperation(value = "分页查询钱包账号",response = WalletAccount.class)
    @RequestMapping(value = "/findWalletAccountInCondition", method = RequestMethod.POST)
    public Object findWalletAccountInCondition(@RequestBody QueryWalletAccount queryWalletAccount) {
        try {
            return walletAccountControllerClient.findWalletAccountInCondition(queryWalletAccount);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}