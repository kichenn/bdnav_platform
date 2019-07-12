package com.bdxh.wallet.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.wallet.dto.AddPhysicalCard;
import com.bdxh.wallet.dto.ModifyPhysicalCard;
import com.bdxh.wallet.dto.QueryPhysicalCard;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.feign.PhysicalCardControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

@Component
public class PhysicalCardControllerFallback implements PhysicalCardControllerClient {
    @Override
    public Wrapper<PageInfo<PhysicalCard>> findPhysicalCardInCondition(QueryPhysicalCard queryPhysicalCard) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper AddPhysicalCard(AddPhysicalCard addPhysicalCard) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyPhysicalCard(ModifyPhysicalCard modifyPhysicalCard) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delPhysicalCard(String schoolCode, String cardNumber, Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PhysicalCard> findPhysicalCardById(String schoolCode, String cardNumber, Long id) {
        return WrapMapper.error();
    }


}
