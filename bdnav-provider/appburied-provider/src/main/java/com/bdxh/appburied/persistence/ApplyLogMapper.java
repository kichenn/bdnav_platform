package com.bdxh.appburied.persistence;

import java.util.List;

import com.bdxh.appburied.entity.ApplyLog;
import com.bdxh.appburied.vo.informationVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-04-11 16:39:55
 */
@Repository
public interface ApplyLogMapper extends Mapper<ApplyLog> {

    /**
     * 查询总条数
     */
    Integer getApplyLogAllCount();

    /**
     * 分页条件查询 上报App状态日志
     */
    List<ApplyLog> findApplyLogInConationPaging(@Param("applyLog") ApplyLog applyLog);

    int modifyVerifyApplyLog(ApplyLog applyLog);

    //查询当前用户的申请消息
    List<informationVo> checkMymessages(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber);

    //查询家长所有孩子
    List<ApplyLog> findApplyLogInfoByFamily(@Param("schoolCode")String schoolCode ,@Param("cardNumber")String cardNumber);
}
