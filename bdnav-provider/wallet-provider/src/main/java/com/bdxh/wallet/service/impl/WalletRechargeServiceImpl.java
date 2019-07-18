package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.persistence.WalletAccountMapper;
import com.bdxh.wallet.service.WalletRechargeService;
import com.bdxh.wallet.vo.WalletRechargeVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.persistence.WalletRechargeMapper;


import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
@Slf4j
public class WalletRechargeServiceImpl extends BaseService<WalletRecharge> implements WalletRechargeService {

    @Autowired
    private WalletRechargeMapper walletRechargeMapper;


    @Autowired
    private WalletAccountMapper walletAccountMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getWalletRechargeAllCount() {
        return walletRechargeMapper.getWalletRechargeAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelWalletRechargeInIds(List<Long> ids) {
        return walletRechargeMapper.delWalletRechargeInIds(ids) > 0;
    }

    /**
     * 根据我方订单号，修改相关信息
     *
     * @param walletRecharge
     * @return
     */
    @Override
    public Boolean modifyWalletRechargeByOrderNo(WalletRecharge walletRecharge) {
        return walletRechargeMapper.modifyWalletRechargeByOrderNo(walletRecharge) > 0;
    }

    /**
     * 我方订单号，查询充值记录信息
     *
     * @param orderNo
     * @return
     */
    @Override
    public WalletRecharge findWalletRechargeByOrderNo(Long orderNo) {
        WalletRecharge walletRecharge = new WalletRecharge();
        walletRecharge.setOrderNo(orderNo);
        return walletRechargeMapper.selectOne(walletRecharge);
    }

    /**
     * 根据cardNumber 和 schoolcode 查询充值记录信息
     */
    @Override
    public List<WalletRecharge> findWalletRechargeByCardNumberAndSchoolCode(String schoolCode, String cardNumber) {
        WalletRecharge walletRecharge = new WalletRecharge();
        walletRecharge.setCardNumber(cardNumber);
        walletRecharge.setSchoolCode(schoolCode);
        return walletRechargeMapper.select(walletRecharge);
    }

    /**
     * 删除充值记录
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @Override
    public Boolean delWalletRecharge(String schoolCode, String cardNumber, Long id) {
        WalletRecharge walletRecharge = new WalletRecharge();
        walletRecharge.setId(id);
        walletRecharge.setSchoolCode(schoolCode);
        walletRecharge.setCardNumber(cardNumber);
        return walletRechargeMapper.delete(walletRecharge) > 0;
    }

    /**
     * 根据条件分页查询充值记录
     *
     * @param queryWalletRechargeDto
     * @return
     */
    @Override
    public PageInfo<WalletRechargeVo> findWalletRechargeByCondition(QueryWalletRechargeDto queryWalletRechargeDto) {
        PageHelper.startPage(queryWalletRechargeDto.getPageNum(), queryWalletRechargeDto.getPageSize());
        List<WalletRecharge> walletRecharges = walletRechargeMapper.findWalletRechargeByCondition(queryWalletRechargeDto);
        List<WalletRechargeVo> walletRechargeVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(walletRecharges)) {
            walletRecharges.forEach(walletRecharge -> {
                WalletRechargeVo walletRechargeVo = new WalletRechargeVo();
                BeanUtils.copyProperties(walletRecharge, walletRechargeVo);
                WalletAccount walletAccount = walletAccountMapper.findWalletAccountBySchool(walletRechargeVo.getSchoolCode(),walletRechargeVo.getCardNumber());
                walletRechargeVo.setPhysicalNumber(walletAccount.getPhysicalNumber());
                walletRechargeVos.add(walletRechargeVo);
            });
        }
        return new PageInfo<WalletRechargeVo>(walletRechargeVos);
    }

    /**
     * 根据id查询单条记录
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @Override
    public WalletRecharge findWalletRechargeById(String schoolCode, String cardNumber, Long id) {
        WalletRecharge walletRecharge = new WalletRecharge();
        walletRecharge.setSchoolCode(schoolCode);
        walletRecharge.setCardNumber(cardNumber);
        walletRecharge.setId(id);
        return walletRechargeMapper.selectOne(walletRecharge);
    }
}
