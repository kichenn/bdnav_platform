package com.bdxh.appburied.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.appburied.configration.utils.GeTuiUtils;
import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.FamilyQueryApplyLogDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.service.ApplyLogService;
import com.bdxh.appburied.vo.informationVo;
import com.bdxh.common.helper.getui.constant.GeTuiConstant;
import com.bdxh.common.utils.BeanMapUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdxh.common.support.BaseService;
import lombok.extern.slf4j.Slf4j;
import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.persistence.ApplyLogMapper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description: 业务层实现
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
@Slf4j
public class ApplyLogServiceImpl extends BaseService<ApplyLog> implements ApplyLogService {

    @Autowired
    private ApplyLogMapper applyLogMapper;

    /*
     *查询总条数
     */
    @Override
    public Integer getApplyLogAllCount() {
        return applyLogMapper.getApplyLogAllCount();
    }

    /**
     * 分页条件查询 上报App状态日志
     */
    @Override
    public PageInfo<ApplyLog> findApplyLogInConationPaging(ApplyLogQueryDto applyLogQueryDto) {
        ApplyLog applyLog = new ApplyLog();
        BeanUtils.copyProperties(applyLogQueryDto, applyLog);
        //设置状态值
        if (applyLogQueryDto.getInstallAppsPlatformEnum() != null) {
            applyLog.setPlatform(applyLogQueryDto.getInstallAppsPlatformEnum().getKey());
        }
        if (applyLogQueryDto.getApplyLogModelEnum() != null) {
            applyLog.setModel(applyLogQueryDto.getApplyLogModelEnum().getKey());
        }
        if (applyLogQueryDto.getApplyLogOperatorStatusEnum() != null) {
            applyLog.setOperatorStatus(applyLogQueryDto.getApplyLogOperatorStatusEnum().getKey());
        }
        PageHelper.startPage(applyLogQueryDto.getPageNum(), applyLogQueryDto.getPageSize());
        List<ApplyLog> appStatuses = applyLogMapper.findApplyLogInConationPaging(applyLog);
        PageInfo<ApplyLog> pageInfoFamily = new PageInfo<>(appStatuses);
        return pageInfoFamily;
    }

    @Override
    public List<ApplyLog> familyFindApplyLogInfo(String schoolCode, String cardNumber) {
        ApplyLog applyLog = new ApplyLog();
        applyLog.setSchoolCode(schoolCode);
        applyLog.setCardNumber(cardNumber);
        return applyLogMapper.findApplyLogInConationPaging(applyLog);
    }

    @Override
    public void modifyVerifyApplyLog(ModifyApplyLogDto modifyApplyLogDto) {
        ApplyLog applyLog = BeanMapUtils.map(modifyApplyLogDto, ApplyLog.class);
        if (modifyApplyLogDto.getInstallAppsPlatformEnum() != null) {
            applyLog.setPlatform(modifyApplyLogDto.getInstallAppsPlatformEnum().getKey());
        }
        if (modifyApplyLogDto.getApplyLogModelEnum() != null) {
            applyLog.setModel(modifyApplyLogDto.getApplyLogModelEnum().getKey());
        }
        if (modifyApplyLogDto.getApplyLogOperatorStatusEnum() != null) {
            applyLog.setOperatorStatus(modifyApplyLogDto.getApplyLogOperatorStatusEnum().getKey());
        }
        Boolean result = applyLogMapper.modifyVerifyApplyLog(applyLog) > 0;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("data", applyLog);
        jsonObject.put("key", GeTuiConstant.ALL_APP_CONTROLL);
        log.info("---------------家长审批畅玩推送数据格式{}", jsonObject.toJSONString());
        if (result) {
            Boolean pushResult = GeTuiUtils.pushMove(modifyApplyLogDto.getClientId(), "家长审批", jsonObject.toString());
            if (!pushResult) {
                Preconditions.checkArgument(pushResult, "推送至安卓端失败");
            }
        }
    }

    @Override
    public List<informationVo> checkMymessages(String schoolCode, String cardNumber) {
        return applyLogMapper.checkMymessages(schoolCode, cardNumber);
    }

    /**
     * 分页查询家长对应孩子的畅玩记录
     *
     * @param familyQueryApplyLogDto
     */
    @Override
    public PageInfo<ApplyLog> findApplyLogInfoByFamily(FamilyQueryApplyLogDto familyQueryApplyLogDto) {
        Integer pageSize = familyQueryApplyLogDto.getPageSize();
        Integer pageNum = familyQueryApplyLogDto.getPageNum();
//        Page page = PageHelper.startPage(familyQueryApplyLogDto.getPageNum(), familyQueryApplyLogDto.getPageSize());
        List<ApplyLog> applyLogs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(familyQueryApplyLogDto.getStudentCardNumbers())) {
            //查询开通服务的孩子的申请畅玩记录
            familyQueryApplyLogDto.getStudentCardNumbers().forEach(cardNumber -> {
                List<ApplyLog> family = applyLogMapper.findApplyLogInfoByFamily(familyQueryApplyLogDto.getSchoolCode(), cardNumber);
                applyLogs.addAll(family);
            });
        }
        //时间排序并分页
        List<ApplyLog> collect = applyLogs.stream().sorted(Comparator.comparing(ApplyLog::getCreateDate).reversed())
                .skip((pageNum - 1) * pageSize).limit(pageSize).collect(Collectors.toList());
        PageInfo<ApplyLog> pageInfo = new PageInfo<ApplyLog>(Optional.ofNullable(collect).get());
        pageInfo.setTotal(applyLogs.size());
        return pageInfo;
    }
}
