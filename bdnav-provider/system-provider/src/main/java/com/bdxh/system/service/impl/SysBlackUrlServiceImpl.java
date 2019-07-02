package com.bdxh.system.service.impl;

import com.alibaba.fastjson.JSON;
import com.bdxh.common.utils.UrlSecurityValidator;
import com.bdxh.system.dto.SysBlackUrlQueryDto;
import com.bdxh.system.enums.RequestResultEnum;
import com.bdxh.system.enums.SiteTypeEnum;
import com.bdxh.system.enums.VirusLibraryTypeEnum;
import com.bdxh.system.service.SysBlackUrlService;
import com.bdxh.system.vo.KingSoftResultVo;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.system.entity.SysBlackUrl;
import com.bdxh.system.persistence.SysBlackUrlMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 业务层实现
 * @Author wanMing
 * @Date 2019-06-20 11:32:50
 */
@Service
@Slf4j
public class SysBlackUrlServiceImpl extends BaseService<SysBlackUrl> implements SysBlackUrlService {

    @Autowired
    private SysBlackUrlMapper sysBlackUrlMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getSysBlackUrlAllCount() {
        return sysBlackUrlMapper.getSysBlackUrlAllCount();
    }

    /*
     *批量删除方法
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDelSysBlackUrlInIds(List<Long> ids) {
        return sysBlackUrlMapper.delSysBlackUrlInIds(ids) > 0;
    }

    /**
     * 分页+条件查询系统黑名单即病毒库
     *
     * @param sysBlackUrlQueryDto
     * @Author: WanMing
     * @Date: 2019/6/20 12:16
     */
    @Override
    public PageInfo<SysBlackUrlVo> findSysBlackUrlByCondition(SysBlackUrlQueryDto sysBlackUrlQueryDto) {
        Page page = PageHelper.startPage(sysBlackUrlQueryDto.getPageNum(), sysBlackUrlQueryDto.getPageSize());
        SysBlackUrl sysBlackUrl = new SysBlackUrl();
        BeanUtils.copyProperties(sysBlackUrlQueryDto, sysBlackUrl);
        List<SysBlackUrl> sysBlackUrls = sysBlackUrlMapper.findSysBlackUrlByCondition(sysBlackUrl);
        List<SysBlackUrlVo> sysBlackUrlVos = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sysBlackUrls)) {
            sysBlackUrls.forEach(blackUrl -> {
                SysBlackUrlVo sysBlackUrlVo = new SysBlackUrlVo();
                BeanUtils.copyProperties(blackUrl, sysBlackUrlVo);
                sysBlackUrlVos.add(sysBlackUrlVo);
            });
        }
        PageInfo pageInfo = new PageInfo<>(sysBlackUrlVos);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }



    /**
     * 批量检查url的安全性 不安全写入数据库
     * @Author: WanMing
     * @Date: 2019/6/24 18:11
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchCheckSysBlackUrl(List<String> urls) {
        urls.forEach(url->{
            SysBlackUrl sysBlackUrl = sysBlackUrlMapper.querySysBlackUrlByUrl(url);
            if (null != sysBlackUrl) {
            //系统黑名单已存在
                return;
            }
            //系统黑名单不存在,查金山病毒库
            String resultStr = UrlSecurityValidator.checkUrlSecurity(url);
            KingSoftResultVo softResultVo = JSON.parseObject(resultStr, KingSoftResultVo.class);
            if (RequestResultEnum.QUERY_SUCCESS.getKey().equals(softResultVo.getSuccess())) {
                //访问成功 判断是否是有害
                Byte phish = softResultVo.getPhish();
                if (SiteTypeEnum.PHISHING.getKey().equals(phish) || SiteTypeEnum.WEBSITE_HIGH_RISK.getKey().equals(phish)) {
                    //有害网站,写到病毒库
                    SysBlackUrl addSysBlackUrl = new SysBlackUrl();
                    addSysBlackUrl.setIp(url);
                    addSysBlackUrl.setName(url);
                    addSysBlackUrl.setOrigin(VirusLibraryTypeEnum.JINSHAN_VIRUS_LIBRARY.getKey());
                    addSysBlackUrl.setRemark(SiteTypeEnum.getValue(phish));
                    sysBlackUrlMapper.insert(addSysBlackUrl);
                }
            }
        });

    }

}

