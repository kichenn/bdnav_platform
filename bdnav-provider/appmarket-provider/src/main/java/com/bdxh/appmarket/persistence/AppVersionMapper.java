package com.bdxh.appmarket.persistence;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.appmarket.entity.AppVersion;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-05-14 12:15:05
*/
@Repository
public interface AppVersionMapper extends Mapper<AppVersion> {

	/**
	 *查询总条数
	 */
	 Integer getAppVersionAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delAppVersionInIds(@Param("ids") List<Long> ids);
}
