package com.bdxh.appburied.persistence;

import java.util.List;

import com.bdxh.appburied.entity.ApplyLog;
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
	 *查询总条数
	 */
	 Integer getApplyLogAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delApplyLogInIds(@Param("ids") List<Long> ids);
}
