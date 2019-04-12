package com.bdxh.appburied.persistence;

import java.util.List;

import com.bdxh.appburied.entity.AppStatus;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Repository
public interface AppStatusMapper extends Mapper<AppStatus> {

	/**
	 *查询总条数
	 */
	 Integer getAppStatusAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delAppStatusInIds(@Param("ids") List<Long> ids);
}
