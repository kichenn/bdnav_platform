package com.bdxh.servicepermit.service.impl;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.servicepermit.dto.QueryServiceUserDto;
import com.bdxh.servicepermit.dto.WeiXiaoAddServiceUserDto;
import com.bdxh.servicepermit.service.ServiceUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.servicepermit.entity.ServiceUser;
import com.bdxh.servicepermit.persistence.ServiceUserMapper;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Date 2019-04-26 10:26:58
 */
@Service
@Slf4j
public class ServiceUserServiceImpl extends BaseService<ServiceUser> implements ServiceUserService {

    @Autowired
    private ServiceUserMapper serviceUserMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getServiceUsedAllCount() {
        return serviceUserMapper.getServiceUserAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelServiceUsedInIds(List<Long> ids) {
        return serviceUserMapper.delServiceUserInIds(ids) > 0;
    }

    @Override
    public PageInfo<ServiceUser> getServiceByCondition(Map<String, Object> param, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ServiceUser> orders = serviceUserMapper.getServiceByCondition(param);
        PageInfo<ServiceUser> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteByServiceId(String SchoolCode, String cardNumber, Long id) {
        return serviceUserMapper.deleteByServiceId(SchoolCode, cardNumber, id) > 0;
    }

    @Override
    public List<ServiceUser> queryAllServiceUser(QueryServiceUserDto queryServiceUsedDto) {
        Map<String, Object> param = BeanToMapUtil.objectToMap(queryServiceUsedDto);
        return serviceUserMapper.getServiceByCondition(param);
    }

    @Override
    public void addServicePermit(WeiXiaoAddServiceUserDto weiXiaoAddServiceUserDto) {
        //试用
        weiXiaoAddServiceUserDto.setType(1);
        QueryServiceUserDto queryServiceUserDto=BeanMapUtils.map(weiXiaoAddServiceUserDto,QueryServiceUserDto.class);
        Map<String, Object> param = BeanToMapUtil.objectToMap(queryServiceUserDto);
        //查询判断是否存在试用记录如果存在直接抛出异常
        List<ServiceUser> serviceUserList=serviceUserMapper.getServiceByCondition(param);
        Preconditions.checkArgument(serviceUserList.size()==0,"您已领取过试用机会请勿重复领取");
        Long nowDate = System.currentTimeMillis();
        //开通时间
        weiXiaoAddServiceUserDto.setStartTime(new Date(nowDate));
        //试用过期时间    240 * 600 * 600 * 7= 7天
        weiXiaoAddServiceUserDto.setEndTime(new Date(nowDate + (240 * 600 * 600 * 7)));
        //正常使用
        weiXiaoAddServiceUserDto.setStatus(1);
        weiXiaoAddServiceUserDto.setCreateDate(new Date(nowDate));
        weiXiaoAddServiceUserDto.setUpdateDate(new Date(nowDate));
        weiXiaoAddServiceUserDto.setOperator(Long.parseLong(weiXiaoAddServiceUserDto.getCardNumber()));
        weiXiaoAddServiceUserDto.setOperatorName(weiXiaoAddServiceUserDto.getFamilyName());
        ServiceUser serviceUser= BeanMapUtils.map(weiXiaoAddServiceUserDto,ServiceUser.class);
        serviceUserMapper.insert(serviceUser);
    }
}
