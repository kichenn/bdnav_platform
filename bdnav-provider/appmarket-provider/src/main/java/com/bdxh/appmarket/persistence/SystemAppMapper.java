package com.bdxh.appmarket.persistence;

import java.util.List;
import java.util.Map;


import com.bdxh.appmarket.entity.SystemApp;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


/**
* @Description: Mapper
* @Date 2019-06-05 15:05:12
*/
@Repository
public interface SystemAppMapper extends Mapper<SystemApp> {

	/**
	 *查询总条数
	 */
	 Integer getSystemAppAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delSystemAppInIds(@Param("ids") List<Long> ids);

	/**
	 * 带条件查询信息
	 * @param param
	 * @return
	 */
	List<SystemApp> findAppControl(Map<String,Object> param);

	//查询最新版本（版本更新）
	SystemApp versionUpdating();
}
