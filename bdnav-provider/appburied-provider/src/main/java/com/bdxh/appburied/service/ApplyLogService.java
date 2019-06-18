package com.bdxh.appburied.service;

import com.bdxh.appburied.dto.ApplyLogQueryDto;
import com.bdxh.appburied.dto.ModifyApplyLogDto;
import com.bdxh.appburied.vo.informationVo;
import com.bdxh.common.support.IService;
import com.bdxh.appburied.entity.ApplyLog;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Service
public interface ApplyLogService extends IService<ApplyLog> {

    /**
     * 查询所有数量
     */
    Integer getApplyLogAllCount();

    /**
     * 分页条件查询 上报App状态日志
     */
    PageInfo<ApplyLog> findApplyLogInConationPaging(ApplyLogQueryDto applyLogQueryDto);


    List<ApplyLog> familyFindApplyLogInfo(String schoolCode,String cardNumber);

    //修改应用审核状态
    void modifyVerifyApplyLog(ModifyApplyLogDto modifyApplyLogDto);

    //查询当前用户申请消息
    List<informationVo> checkMymessages(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber);
}
