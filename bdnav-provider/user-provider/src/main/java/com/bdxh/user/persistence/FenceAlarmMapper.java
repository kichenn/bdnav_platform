package com.bdxh.user.persistence;

import java.util.List;

import com.bdxh.user.entity.FenceAlarm;
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

	/**
	 *查询总条数
	 */
	 Integer getFenceAlarmAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delFenceAlarmInIds(@Param("ids") List<Long> ids);
}
