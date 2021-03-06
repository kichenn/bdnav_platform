package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddWalletConsumerDto;
import com.bdxh.wallet.dto.ModifyWalletConsumerDto;
import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.dto.QueryWalletConsumerExcelDto;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.feign.WalletConsumerControllerClient;
import com.bdxh.wallet.vo.BaseEchartsVo;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**账户消费管理的降级服务
 * @author WanMing
 * @date 2019/7/16 11:08
 */
@Component
public class WalletConsumerControllerClientFallback implements WalletConsumerControllerClient {
    @Override
    public Wrapper addWalletConsumer(AddWalletConsumerDto addWalletConsumerDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delWalletConsumer(String schoolCode, String cardNumber, Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyWalletConsumer(ModifyWalletConsumerDto modifyWalletConsumerDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<WalletConsumerVo>> findWalletConsumerByCondition(QueryWalletConsumerDto queryWalletConsumerDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<WalletConsumer> findWalletConsumerByOrderNo(String schoolCode, String cardNumber, String orderNo) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<WalletConsumerVo>> findWalletConsumerList(QueryWalletConsumerExcelDto queryWalletConsumerExcelDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<BaseEchartsVo> queryAllConsumerMoney(String schoolCode) {
        return WrapMapper.error();
    }


}
