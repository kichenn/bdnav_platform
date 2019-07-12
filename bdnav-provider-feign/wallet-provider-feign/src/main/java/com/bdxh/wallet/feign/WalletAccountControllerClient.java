package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletAccount;
import com.bdxh.wallet.dto.ModifyWalletAccount;
import com.bdxh.wallet.dto.QueryWalletAccount;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.fallback.WalletAccountControllerFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "wallet-provider-cluster",fallback = WalletAccountControllerFallback.class)
public interface WalletAccountControllerClient {

    /**
     * 分页查询
     * @param queryWalletAccount
     * @return
     */
    @RequestMapping(value = "/walletAccount/findWalletAccountInCondition",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<WalletAccount>> findWalletAccountInCondition(@RequestBody QueryWalletAccount queryWalletAccount);


    /**
     * 添加
     * @param addWalletAccount
     * @return
     */
    @RequestMapping(value = "/walletAccount/addWalletAccount",method = RequestMethod.POST)
    @ResponseBody
    Wrapper addWalletAccount(@RequestBody AddWalletAccount addWalletAccount);


    /**
     * 修改
     * @param modifyWalletAccount
     * @return
     */
    @RequestMapping(value = "/walletAccount/modifyWalletAccount",method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyWalletAccount(@RequestBody ModifyWalletAccount modifyWalletAccount);



    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/walletAccount/delWalletAccount",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delWalletAccount(@RequestParam("schoolCode") String schoolCode,@RequestParam("cardNumber") String cardNumber,@RequestParam("id") Long id);




}
