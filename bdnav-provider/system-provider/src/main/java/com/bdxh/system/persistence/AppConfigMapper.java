package com.bdxh.system.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.system.entity.AppConfig;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @Description: 应用配置mapper
* @Author xuyuan
* @Date 2019-03-21 15:07:41
*/
@Repository
public interface AppConfigMapper extends Mapper<AppConfig> {

	/**
	 *查询总条数
	 */
	Integer getAppConfigAllCount();

	/**
	 *批量删除方法
	 */
	Integer delAppConfigInIds(@Param("ids") List<Long> ids);

	/**
	 * 根据appName查询应用配置
	 * @param appName
	 * @return
	 */
	AppConfig getByAppConfigName(@Param("appName") String appName);

	/**
	 * 根据条件查询应用配置
	 * @param param
	 * @return
	 */
	List<AppConfig> getByCondition(Map<String,Object> param);

}
