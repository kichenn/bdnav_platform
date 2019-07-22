package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletRechargeDto;
import com.bdxh.wallet.dto.ModifyWalletRechargeDto;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.dto.QueryWalletRechargeExcelDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.fallback.WalletRechargeControllerFallback;
import com.bdxh.wallet.fallback.WalletAccountControllerFallback;
import com.bdxh.wallet.fallback.WalletRechargeControllerFallback;
import com.bdxh.wallet.vo.BaseEchartsVo;
import com.bdxh.wallet.vo.WalletRechargePayVo;
import com.bdxh.wallet.vo.WalletRechargeVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description: 充值记录相关
 * @Author: Kang
 * @Date: 2019/7/15 11:53
 */
@Service
@FeignClient(value = "wallet-provider-cluster", path = "/walletRecharge", fallback = WalletRechargeControllerFallback.class)
public interface WalletRechargeControllerClient {

    /**
     * 添加充值记录信息
     *
     * @param addWalletRechargeDto
     * @return
     */
    @PostMapping("/addWalletRecharge")
    @ResponseBody
    Wrapper<String> addWalletRecharge(@RequestBody @Validated AddWalletRechargeDto addWalletRechargeDto);

    /**
     * 绑定充值记录并修改相关状态
     *
     * @param modifyWalletRechargeDto
     */
    @PostMapping("/modifyWalletRechargeByOrderNo")
    @ResponseBody
    Wrapper<Boolean> modifyWalletRechargeByOrderNo(@RequestBody @Validated ModifyWalletRechargeDto modifyWalletRechargeDto);

    /**
     * 绑定充值记录并修改相关状态
     *
     * @param orderNo
     * @return
     */
    @GetMapping("/findWalletRechargeByOrderNo")
    @ResponseBody
    Wrapper<WalletRechargePayVo> findWalletRechargeByOrderNo(@RequestParam("orderNo") Long orderNo);

    /**
     * 根据条件分页查询充值记录
     * @Author: WanMing
     * @Date: 2019/7/15 11:13
     */
    @RequestMapping(value = "/findWalletRechargeByCondition",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<WalletRechargeVo>> findWalletRechargeByCondition(@RequestBody QueryWalletRechargeDto queryWalletRechargeDto);

    /**
     * 查询不同充值类型下充值成功的总金额
     * @Author: WanMing
     * @Date: 2019/7/15 11:13
     */
    @RequestMapping(value = "/findWalletRechargeTypeMoneySum",method = RequestMethod.GET)
    @ResponseBody
    Wrapper<List<BaseEchartsVo>> findWalletRechargeTypeMoneySum(@RequestParam(value = "schoolCode",required = false) String schoolCode);

    /**
     * 根据excel条件查询充值记录不分页
     *
     * @Author: WanMing
     * @Date: 2019/7/15 11:13
     */
    @RequestMapping(value = "/findWalletRechargeList", method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<WalletRechargeVo>> findWalletRechargeList(@RequestBody QueryWalletRechargeExcelDto queryWalletRechargeExcelDto);
}
