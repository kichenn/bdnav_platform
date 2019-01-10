package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.entity.WalletXianConfig;
import com.bdxh.wallet.feign.WalletConfigControllerClient;
import java.util.Map;

/**
 * @description: 一卡通配置类hystrix降级
 * @author: xuyuan
 * @create: 2019-01-08 15:09
 **/
public class WalletConfigControllerFallback implements WalletConfigControllerClient {

    @Override
    public Wrapper saveWalletConfig(Map<String,Object> param) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<WalletXianConfig> queryWalletConfigBySchoolCode(String schoolCode) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper queryWalletConfigPage(Integer pageNum, Integer pageSize) {
        return WrapMapper.error();
    }

}
