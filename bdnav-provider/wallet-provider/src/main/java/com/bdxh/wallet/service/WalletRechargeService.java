package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.dto.QueryWalletRechargeDto;
import com.bdxh.wallet.dto.QueryWalletRechargeExcelDto;
import com.bdxh.wallet.entity.WalletRecharge;
import com.bdxh.wallet.vo.BaseEchartsVo;
import com.bdxh.wallet.vo.WalletRechargeVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
public interface WalletRechargeService extends IService<WalletRecharge> {

    /**
     * 查询所有数量
     */
    Integer getWalletRechargeAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelWalletRechargeInIds(List<Long> id);

    /**
     * 根据我方订单号，修改相关信息
     *
     * @param walletRecharge
     * @return
     */
    Boolean modifyWalletRechargeByOrderNo(WalletRecharge walletRecharge);

    /**
     * 我方订单号，查询充值记录信息
     *
     * @param orderNo
     * @return
     */
    WalletRecharge findWalletRechargeByOrderNo(Long orderNo);

    /**
     * 根据cardNumber 和 schoolcode 查询充值记录信息
     */
    List<WalletRecharge> findWalletRechargeByCardNumberAndSchoolCode(String schoolCode, String cardNumber);

    /**
     * 删除充值记录
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    Boolean delWalletRecharge(String schoolCode, String cardNumber, Long id);

    /**
     * 根据条件分页查询充值记录
     *
     * @param queryWalletRechargeDto
     * @return
     */
    PageInfo<WalletRechargeVo> findWalletRechargeByCondition(QueryWalletRechargeDto queryWalletRechargeDto);

    /**
     * 根据id查询单条记录
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    WalletRecharge findWalletRechargeById(String schoolCode, String cardNumber, Long id);

    /**
     * 查询不同充值类型下充值成功的总金额
     * @param schoolCode
     * @return
     */
    List<BaseEchartsVo> findWalletRechargeTypeMoneySum(String schoolCode);

    /**
     * 根据excel条件查询充值记录不分页
     * @Author: WanMing
     * @Date: 2019/7/22 14:52
     */
    List<WalletRechargeVo> findWalletRechargeList(QueryWalletRechargeExcelDto queryWalletRechargeExcelDto);
}
