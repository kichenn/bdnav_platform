package com.bdxh.appburied.service.impl;

import com.bdxh.appburied.dto.AddInstallAppsDto;
import com.bdxh.appburied.dto.InstallAppsQueryDto;
import com.bdxh.appburied.service.InstallAppsService;
import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.ConvertMultipartUtil;
import com.bdxh.common.utils.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.InstallApps;
import com.bdxh.appburied.persistence.InstallAppsMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
public class InstallAppsServiceImpl extends BaseService<InstallApps> implements InstallAppsService {

    @Autowired
    private InstallAppsMapper installAppsMapper;

    @Autowired
    private SnowflakeIdWorker snowflakeIdWorker;

    /*
     *查询总条数
     */
    @Override
    public Integer getInstallAppsAllCount() {
        return installAppsMapper.getInstallAppsAllCount();
    }


    /**
     * 条件分页查询上报app应用
     */
    @Override
    public PageInfo<InstallApps> findInstallAppsInConationPaging(InstallAppsQueryDto installAppsQueryDto) {
        InstallApps installApps = new InstallApps();
        BeanUtils.copyProperties(installAppsQueryDto, installApps);
        if (installAppsQueryDto.getInstallAppsPlatformEnum() != null) {
            installApps.setPlatform(installAppsQueryDto.getInstallAppsPlatformEnum().getKey());
        }
        PageHelper.startPage(installAppsQueryDto.getPageNum(), installAppsQueryDto.getPageSize());
        List<InstallApps> appStatuses = installAppsMapper.findInstallAppsInContionPaging(installApps);
        PageInfo<InstallApps> pageInfoFamily = new PageInfo<>(appStatuses);
        return pageInfoFamily;
    }

    @Override
    public List<InstallApps> findInstallAppsInConation(String schoolCode, String cardNumber) {
        InstallApps installApps=new InstallApps();
        installApps.setSchoolCode(schoolCode);
        installApps.setCardNumber(cardNumber);
        return installAppsMapper.findInstallAppsInContionPaging(installApps);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSaveInstallAppsInfo(List<AddInstallAppsDto> appInstallList) {
      //先删除原有应用在进行增加
        List<Long> ids=new ArrayList();
        List<InstallApps> iAs=installAppsMapper.getAccountApplication(appInstallList.get(0).getSchoolCode(),appInstallList.get(0).getCardNumber(),appInstallList.get(0).getAccountId());
        for (int i = 0; i < iAs.size(); i++) {

            ids.add(iAs.get(i).getId());
        }
        Boolean falg=installAppsMapper.batchDelInstallApps(ids)>0;
        List<InstallApps> appslist= BeanMapUtils.mapList(appInstallList, InstallApps.class);
        for (int i = 0; i < appslist.size(); i++) {
            appslist.get(i).setId(snowflakeIdWorker.nextId());
            appslist.get(i).setPlatform(appInstallList.get(0).getInstallAppsPlatformEnum().getKey());
            MultipartFile multipartFile = ConvertMultipartUtil.base64ToMultipart(appInstallList.get(i).getIconUrl());
            Map<String, String> result = null;
            try {
                result = FileOperationUtils.saveBatchFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
            } catch (Exception e) {
                e.printStackTrace();
            }
            appslist.get(i).setIconUrl(result.get("url"));
            appslist.get(i).setIconName(result.get("name"));
        }
        return installAppsMapper.batchSaveInstallAppsInfo(appslist)>0;
    }

    @Override
    public Boolean delByAppPackage(String schoolCode, String cardNumber, String appPackage) {
        return installAppsMapper.delByAppPackage(schoolCode,cardNumber,appPackage)>0;
    }


    @Override
    public Boolean batchDelInstallApps(List<Long> ids) {
        return installAppsMapper.batchDelInstallApps(ids)>0;
    }

    @Override
    public List<InstallApps> getAccountApplication(String schoolCode, String cardNumber, Long accountId) {
        return installAppsMapper.getAccountApplication(schoolCode,cardNumber,accountId);
    }
}
