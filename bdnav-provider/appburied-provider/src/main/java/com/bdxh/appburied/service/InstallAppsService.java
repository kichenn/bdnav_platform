package com.bdxh.appburied.service;

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
    PageInfo<InstallApps> findInstallAppsInContionPaging(InstallAppsQueryDto installAppsQueryDto);

}
