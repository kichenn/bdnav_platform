package com.bdxh.appburied.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.appburied.configration.utils.GeTuiUtils;
import com.bdxh.appburied.dto.AppStatusQueryDto;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.service.AppStatusService;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.helper.getui.entity.AppNotificationTemplate;
import com.bdxh.common.helper.getui.request.AppPushRequest;
import com.bdxh.common.helper.getui.utils.GeTuiUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.AppStatus;
import com.bdxh.appburied.persistence.AppStatusMapper;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@Slf4j
public class AppStatusServiceImpl extends BaseService<AppStatus> implements AppStatusService {

    @Autowired
    private AppStatusMapper appStatusMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;
    /*
     *查询总条数
     */
    @Override
    public Integer getAppStatusAllCount() {
        return appStatusMapper.getAppStatusAllCount();
    }


    /**
     * 条件分页查询app状态
     */
    @Override
    public PageInfo<AppStatus> findAppStatusInConationPaging(AppStatusQueryDto appStatusQueryDto) {

        AppStatus appStatus = new AppStatus();
        BeanUtils.copyProperties(appStatusQueryDto, appStatus);
        //设置状态值
        if (appStatusQueryDto.getAppStatusEnum() != null) {
            appStatus.setAppStatus(appStatusQueryDto.getAppStatusEnum().getKey());
        }
        if (appStatusQueryDto.getInstallAppsPlatformEnum() != null) {
            appStatus.setPlatform(appStatusQueryDto.getInstallAppsPlatformEnum().getKey());
        }

        PageHelper.startPage(appStatusQueryDto.getPageNum(), appStatusQueryDto.getPageSize());
        List<AppStatus> appStatuses = appStatusMapper.findAppStatusInConationPaging(appStatus);
        PageInfo<AppStatus> pageInfoFamily = new PageInfo<>(appStatuses);
        return pageInfoFamily;
    }

    @Override
    public List<AppStatus> findAppStatusInfoBySchoolCodeAndCardNumber(String schoolCode, String cardNumber) {
        return appStatusMapper.findAppStatusInfoBySchoolCodeAndCardNumber(schoolCode, cardNumber);
    }

    @Override
    @Transactional
    public Boolean appStatusLockingAndUnlock(AppStatus appStatus) {
        try {
            //查询是否有家长管控记录
            AppStatus oldAppStatus= appStatusMapper.finAppStatusInfoByPackage(appStatus.getSchoolCode(),appStatus.getCardNumber(), appStatus.getAppPackage());
            Boolean result;
            //判断是否存在
            if(null!=oldAppStatus){
                //如果存在修改一条记录然后推送
                appStatus.setId(oldAppStatus.getId());
                result= appStatusMapper.updateAppStatus(appStatus)>0;
            }else{
                //不存在新增一条记录
                appStatus.setId(snowflakeIdWorker.nextId());
                result=appStatusMapper.addAppStatus(appStatus)>0;
            }
            //如果数据库记录插入成功推送个推
            if(result){
                Boolean pushResult;
                //判断状态选择推送模板
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("data",appStatus);
                if(appStatus.getAppStatus().equals(Byte.valueOf("2"))){
                    List<String> clientId = new ArrayList<>();
                    clientId.add("59dc219038fde0484eebcbb6d5476f0c");

                    pushResult= GeTuiUtils.pushMove(clientId,"锁定应用",jsonObject.toString());
                }else{
                    List<String> clientId = new ArrayList<>();
                    clientId.add("59dc219038fde0484eebcbb6d5476f0c");
                    pushResult= GeTuiUtils.pushMove(clientId,"解锁应用",jsonObject.toString());
                }
                Preconditions.checkArgument(pushResult,"推送至安卓端失败");
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}
