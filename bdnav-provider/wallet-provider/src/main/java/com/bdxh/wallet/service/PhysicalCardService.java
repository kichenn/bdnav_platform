package com.bdxh.wallet.service;

import com.bdxh.common.support.IService;
import com.bdxh.wallet.entity.PhysicalCard;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

	/**
	 * 带条件查询
	 * @param param
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	PageInfo<PhysicalCard> findPhysicalCardInCondition(Map<String,Object> param, Integer pageNum, Integer pageSize);

	/**
	 * 删除账户
	 */
	Boolean delPhysicalCard(String schoolCode,String cardNumber, Long id);

	/**
	 * 根据id查询详情
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	PhysicalCard findPhysicalCardById(String schoolCode,String cardNumber, Long id);

}
