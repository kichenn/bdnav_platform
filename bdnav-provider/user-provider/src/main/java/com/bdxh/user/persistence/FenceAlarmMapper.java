package com.bdxh.user.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.entity.FenceAlarm;
import com.bdxh.user.vo.FenceAlarmVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-17 17:29:23
*/
@Repository
public interface FenceAlarmMapper extends Mapper<FenceAlarm> {
/*	//查询所有
	List<FenceAlarmVo> getAllFenceAlarmInfos(@Param("fenceAlarmQueryDto") FenceAlarm fenceAlarm);

	//查询单个
	FenceAlarmVo getFenceAlarmInfo(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("id") String id);

	//删除单个
	int removeFenceAlarmInfo(@Param("id")String id,@Param("schoolCode")String schoolCode,@Param("cardNumber")String  cardNumber);

	//批量删除
	int batchRemoveFenceAlarmInfo(@Param("list") List<Map<String,String>> list);

	//修改数据
	int updateFenceAlarmInfo(FenceAlarm fenceAlarm);*/
}
