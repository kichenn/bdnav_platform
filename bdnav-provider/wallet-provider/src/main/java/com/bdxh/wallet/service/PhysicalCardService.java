package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.entity.PhysicalCard;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
public interface PhysicalCardService extends IService<PhysicalCard> {

    /**
     * 查询所有数量
     */
    Integer getPhysicalCardAllCount();

    /**
     * 批量删除方法
     */
    Boolean batchDelPhysicalCardInIds(List<Long> id);

    /**
     * 根据实体卡号，修改信息
     */
    Boolean modifyInfoByPhysicalCard(PhysicalCard physicalCard);
}
