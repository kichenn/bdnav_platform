package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletConsumerDto;
import com.bdxh.wallet.dto.ModifyWalletConsumerDto;
import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.fallback.WalletConsumerControllerClientFallback;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**消费记录的feign
 * @author WanMing
 * @date 2019/7/16 11:02
 */
@Service
@FeignClient(value = "wallet-provider-cluster",path = "/walletConsumer",fallback = WalletConsumerControllerClientFallback.class)
public interface WalletConsumerControllerClient {


    /**
     * 添加账户消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 15:06
     */
    @RequestMapping(value = "/addWalletConsumer", method = RequestMethod.POST)
    @ResponseBody
    Wrapper addWalletConsumer(@Validated @RequestBody AddWalletConsumerDto addWalletConsumerDto);

    /**
     * 删除账户消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 16:06
     */
    @RequestMapping(value = "/delWalletConsumer", method = RequestMethod.GET)
    @ResponseBody
    Wrapper delWalletConsumer(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") Long id) ;

    /**
     * 修改账户消费记录状态
     *
     * @Author: WanMing
     * @Date: 2019/7/12 16:22
     */
    @RequestMapping(value = "/modifyWalletConsumer", method = RequestMethod.POST)
    @ResponseBody
    Wrapper modifyWalletConsumer(@Validated @RequestBody ModifyWalletConsumerDto modifyWalletConsumerDto);


    /**
     * 根据条件查询账户的消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 17:22
     */
    @RequestMapping(value = "/findWalletConsumerByCondition", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<WalletConsumerVo>> findWalletConsumerByCondition(@RequestBody QueryWalletConsumerDto queryWalletConsumerDto);

    /**
     * 根据id查询单条消费记录
     *
     * @Author: WanMing
     * @Date: 2019/7/12 16:06
     */
    @RequestMapping(value = "/findWalletConsumerById", method = RequestMethod.GET)
    @ResponseBody
    Wrapper<WalletConsumer> findWalletConsumerById(@RequestParam("schoolCode") String schoolCode, @RequestParam("cardNumber") String cardNumber, @RequestParam("id") String id);
}
