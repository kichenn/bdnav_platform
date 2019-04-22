package com.bdxh.appmarket.service.impl;

import com.bdxh.appmarket.entity.MobileDevice;
import com.bdxh.appmarket.persistence.MobileDeviceMapper;
import com.bdxh.appmarket.service.MobileDeviceService;
import com.bdxh.common.support.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MobileDeviceServiceImpl extends BaseService<MobileDevice> implements MobileDeviceService {

    @Autowired
    private MobileDeviceMapper mobileDeviceMapper;

    //查询列表信息根据条件
    @Override
    public List<MobileDevice> findMobileDeviceInCondition(MobileDevice mobileDevice){
        List<MobileDevice> results=super.select(mobileDevice);
        return results;
    }

    //查询所有数量
    @Override
    public Integer getMobileDeviceAllCount(){
        return mobileDeviceMapper.getMobileDeviceAllCount();
    }

    //新增方法
    @Override
    public Boolean addMobileDevice(MobileDevice mobileDevice){
        return mobileDeviceMapper.insertSelective(mobileDevice) > 0;
    }

    //修改方法
    @Override
    public Boolean modifyMobileDevice(MobileDevice mobileDevice) {
        return mobileDeviceMapper.updateByPrimaryKey(mobileDevice) > 0;
    }


    //删除方法
    @Override
    public Boolean delMobileDeviceById(Long id){
        return mobileDeviceMapper.deleteByPrimaryKey(id) > 0;
    }

    //批量删除方法
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelMobileDeviceInIds(List<Long> ids){
        return mobileDeviceMapper.delMobileDeviceInIds(ids) > 0;
    }

    //根据ID查询对象的方法
    @Override
    public MobileDevice findMobileDeviceById(Long id){
        return mobileDeviceMapper.selectByPrimaryKey(id);
    }

}
