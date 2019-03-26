package com.bdxh.onecard.persistence;

import java.util.List;
import com.bdxh.onecard.entity.RechargeRecord;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @Description: Mapper
* @Author Kang
* @Date 2019-03-25 15:03:44
*/
@Repository
public interface RechargeRecordMapper extends Mapper<RechargeRecord> {

	/**
	 *查询总条数
	 */
	 Integer getRechargeRecordAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delRechargeRecordInIds(@Param("ids") List<Long> ids);

}
