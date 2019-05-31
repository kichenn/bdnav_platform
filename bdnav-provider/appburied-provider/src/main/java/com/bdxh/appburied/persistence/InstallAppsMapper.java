package com.bdxh.appburied.persistence;

import java.util.List;

import com.bdxh.appburied.entity.InstallApps;
import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Repository
public interface InstallAppsMapper extends Mapper<InstallApps> {

    /**
     * 查询总条数
     */
    Integer getInstallAppsAllCount();

    /**
     * 条件查询上报App信息 分页
     * @param installApps
     * @return
     */
    List<InstallApps> findInstallAppsInContionPaging(@Param("installApps") InstallApps installApps);

    /**
     * 批量上报应用信息
     * @param appList
     * @return
     */
    Integer batchSaveInstallAppsInfo(List<InstallApps> appList);

    //根据包名删除上报应用
    Integer delByAppPackage(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("appPackage") String appPackage);

    //查询同一账户里是否存在相同应用
    //InstallApps findRepeatApplications(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("appPackage") String appPackage);

    //批量删除上报应用
    Integer batchDelInstallApps(@Param("ids") List<Long> ids);

    //查询同一账户下的上报应用列表
    List<InstallApps> getAccountApplication(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("accountId") Long accountId);

}
