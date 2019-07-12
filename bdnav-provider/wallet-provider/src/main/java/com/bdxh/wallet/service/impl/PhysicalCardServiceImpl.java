package com.bdxh.wallet.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.wallet.dto.AddPhysicalCard;
import com.bdxh.wallet.entity.WalletAccount;
import com.bdxh.wallet.service.PhysicalCardService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.wallet.entity.PhysicalCard;
import com.bdxh.wallet.persistence.PhysicalCardMapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-07-11 09:40:52
 */
@Service
@Slf4j
public class PhysicalCardServiceImpl extends BaseService<PhysicalCard> implements PhysicalCardService {

    @Autowired
    private PhysicalCardMapper physicalCardMapper;


    /*
     *查询总条数
     */
    @Override
    public Integer getPhysicalCardAllCount() {
        return physicalCardMapper.getPhysicalCardAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelPhysicalCardInIds(List<Long> ids) {
        return physicalCardMapper.delPhysicalCardInIds(ids) > 0;
    }

    /**
     * 根据实体卡号，修改信息
     */
    @Override
    public Boolean modifyInfoByPhysicalCard(PhysicalCard physicalCard) {
        return null;
    }

	@Override
	public PageInfo<PhysicalCard> findPhysicalCardInCondition(Map<String, Object> param, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<PhysicalCard> physicalCardLogs = physicalCardMapper.findPhysicalCardInCondition(param);
		return new PageInfo(physicalCardLogs);
	}


	@Override
	public Boolean delPhysicalCard(String schoolCode,String cardNumber, Long id) {
		return physicalCardMapper.delPhysicalCard(schoolCode,cardNumber,id)>0;
	}

	@Override
	public PhysicalCard findPhysicalCardById(String schoolCode, String cardNumber, Long id) {
		return physicalCardMapper.findPhysicalCardById(schoolCode,cardNumber,id);
	}
}
