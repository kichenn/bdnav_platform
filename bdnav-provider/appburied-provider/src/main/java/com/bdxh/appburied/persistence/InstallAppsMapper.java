package com.bdxh.appburied.persistence;

import java.util.List;

import com.bdxh.appburied.entity.InstallApps;
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

}
