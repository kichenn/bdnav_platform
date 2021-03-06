package com.bdxh.wallet.service.impl;

import com.bdxh.wallet.dto.QueryWalletConsumerDto;
import com.bdxh.wallet.dto.QueryWalletConsumerExcelDto;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.persistence.WalletAccountMapper;
import com.bdxh.wallet.service.WalletConsumerService;
import com.bdxh.wallet.vo.BaseEchartsVo;
import com.bdxh.wallet.vo.WalletConsumerVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.WalletConsumer;
import com.bdxh.wallet.persistence.WalletConsumerMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
@Slf4j
public class WalletConsumerServiceImpl extends BaseService<WalletConsumer> implements WalletConsumerService {

    /**
     * 消费总金额
     */
    private static final String CONSUMER_TOTAL_MONEY = "消费总金额";

    @Autowired
    private WalletConsumerMapper walletConsumerMapper;

    @Autowired
    private WalletAccountMapper walletAccountMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getWalletConsumerAllCount() {
        return walletConsumerMapper.getWalletConsumerAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelWalletConsumerInIds(List<Long> ids) {
        return walletConsumerMapper.delWalletConsumerInIds(ids) > 0;
    }

    /**
     * 删除消费记录
     *
     * @param schoolCode
     * @param cardNumber
     * @param id
     */
    @Override
    public Boolean delWalletConsumer(String schoolCode, String cardNumber, Long id) {
        WalletConsumer walletConsumer = new WalletConsumer();
        walletConsumer.setSchoolCode(schoolCode);
        walletConsumer.setCardNumber(cardNumber);
        walletConsumer.setId(id);
        return walletConsumerMapper.delete(walletConsumer) > 0;
    }

    /**
     * 条件分页查询消费记录
     *
     * @param queryWalletConsumerDto
     * @return
     */
    @Override
    public PageInfo<WalletConsumerVo> findWalletConsumerByCondition(QueryWalletConsumerDto queryWalletConsumerDto) {
        Page page = PageHelper.startPage(queryWalletConsumerDto.getPageNum(), queryWalletConsumerDto.getPageSize());
        List<WalletConsumer> walletConsumers = walletConsumerMapper.findWalletConsumerByCondition(queryWalletConsumerDto);
        List<WalletConsumerVo> walletConsumerVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(walletConsumers)) {
            walletConsumers.forEach(consumerDetail -> {
                WalletConsumerVo walletConsumerVo = new WalletConsumerVo();
                BeanUtils.copyProperties(consumerDetail, walletConsumerVo);
                WalletAccount walletAccount = walletAccountMapper.findWalletAccountBySchool(consumerDetail.getSchoolCode(), consumerDetail.getCardNumber());
                if(Objects.nonNull(walletAccount)){
                    walletConsumerVo.setPhysicalNumber(walletAccount.getPhysicalNumber());
                    walletConsumerVo.setUserType(walletAccount.getUserType());
                }
                walletConsumerVos.add(walletConsumerVo);
            });
        }
        PageInfo<WalletConsumerVo> pageInfo = new PageInfo<>(walletConsumerVos);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /**
     * 根据id查询单条消费记录
     *
     * @param schoolCode
     * @param cardNumber
     * @param orderNo
     * @return
     */
    @Override
    public WalletConsumer findWalletConsumerByOrderNo(String schoolCode, String cardNumber, String orderNo) {
        return walletConsumerMapper.findWalletConsumerById(schoolCode, cardNumber, orderNo);
    }

    /**
     * 根据条件查询消费列表不分页
     *
     * @param queryWalletConsumerExcelDto
     * @return
     */
    @Override
    public List<WalletConsumerVo> findWalletConsumerList(QueryWalletConsumerExcelDto queryWalletConsumerExcelDto) {
        List<WalletConsumer> walletConsumers = null;
        List<WalletConsumerVo> walletConsumerVos = new ArrayList<>();
        switch (queryWalletConsumerExcelDto.getExportWay()) {
            case 1:
                //选中导出
                walletConsumers = walletConsumerMapper.findWalletConsumerByOrderNos(queryWalletConsumerExcelDto.getOrderNos());
                break;
//            case 2:
//                //分页导出
//                PageHelper.startPage(queryWalletConsumerExcelDto.getPageNum(), queryWalletConsumerExcelDto.getPageSize());
//                walletConsumers = walletConsumerMapper.findWalletConsumerByCondition(queryWalletConsumerExcelDto);
//                break;
            case 2:
                //条件导出
                walletConsumers = walletConsumerMapper.findWalletConsumerByCondition(queryWalletConsumerExcelDto);
                break;
            default:
                throw new RuntimeException("导出方式错误,请选择正确的导出方式!!!");
        }
        if(CollectionUtils.isNotEmpty(walletConsumers)){
            walletConsumers.forEach(consumerDetail -> {
                WalletConsumerVo walletConsumerVo = new WalletConsumerVo();
                BeanUtils.copyProperties(consumerDetail, walletConsumerVo);
                walletConsumerVos.add(walletConsumerVo);
            });
        }

        return walletConsumerVos;
    }

    /**
     * 查询单个学校或者所有学校消费总金额
     *
     * @param schoolCode
     * @Author: WanMing
     * @Date: 2019/7/23 11:38
     */
    @Override
    public BaseEchartsVo queryAllConsumerMoney(String schoolCode) {
        String totalMoney = walletConsumerMapper.queryAllConsumerMoney(schoolCode);
        BaseEchartsVo baseEchartsVo = new BaseEchartsVo();
        baseEchartsVo.setName(CONSUMER_TOTAL_MONEY);
        baseEchartsVo.setValue(totalMoney==null?"0":totalMoney);
        return baseEchartsVo;
    }
}
