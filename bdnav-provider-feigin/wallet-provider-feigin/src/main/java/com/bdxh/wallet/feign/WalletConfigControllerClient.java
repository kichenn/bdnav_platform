package com.bdxh.wallet.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.entity.WalletXianConfig;
import com.bdxh.wallet.fallback.WalletConfigControllerFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Map;

/**
 * @description: 一卡通配置类feigin客户端
 * @author: xuyuan
 * @create: 2019-01-08 14:43
 **/
@Service
@FeignClient(value = "wallet-provider-cluster",fallback= WalletConfigControllerFallback.class)
public interface WalletConfigControllerClient {

    /**
     * 保存一卡通配置信息
     * @param param
     * @return
     */
    @RequestMapping("/walletConfig/saveWalletConfig")
    @ResponseBody
    Wrapper saveWalletConfig(@RequestParam Map<String,Object> param);

    /**
     * 根据学校编码查询一卡通配置信息
     * @param schoolCode
     * @return
     */
    @RequestMapping("/walletConfig/queryWalletConfigBySchoolCode")
    @ResponseBody
    Wrapper<WalletXianConfig> queryWalletConfigBySchoolCode(@RequestParam(name = "schoolCode") String schoolCode);

    /**
     * 分页查询一卡通配置信息
     * @return
     */
    @RequestMapping("/walletConfig/queryWalletConfigPage")
    @ResponseBody
    Wrapper queryWalletConfigPage(@RequestParam(name = "pageNum") Integer pageNum, @RequestParam(name = "pageSize") Integer pageSize);

}
