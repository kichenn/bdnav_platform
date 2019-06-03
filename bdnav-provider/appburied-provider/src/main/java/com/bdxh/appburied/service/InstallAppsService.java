package com.bdxh.appburied.service;

import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.dto.InstallAppsQueryDto;
import com.bdxh.common.support.IService;
import com.bdxh.appburied.entity.InstallApps;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
public interface InstallAppsService extends IService<InstallApps> {

    /**
     * 查询所有数量
     */
    Integer getInstallAppsAllCount();

    /**
     * 条件分页查询上报app应用
     */
    PageInfo<InstallApps> findInstallAppsInConationPaging(InstallAppsQueryDto installAppsQueryDto);


    List<InstallApps> findInstallAppsInConation(String schoolCode,String cardNumber);

    /**
     * 批量上报应用信息
     * @param appInstallList
     * @return
     */
    Boolean batchSaveInstallAppsInfo(List<AddInstallAppsDto> appInstallList);

    /**
     * 根据包名删除信息
     * @param schoolCode
     * @param cardNumber
     * @param appPackage
     * @return
     */
    Boolean delByAppPackage(String schoolCode,String cardNumber,String appPackage);

    //批量删除上报应用
    Boolean batchDelInstallApps(List<Long> ids);

    List<InstallApps> getAccountApplication(String schoolCode,String cardNumber,Long accountId);

}
