package com.bdxh.system.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.system.entity.AppConfigSecret;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @Description: 持久化
* @Author Kang
* @Date 2019-03-21 15:07:41
*/
@Repository
public interface AppConfigSecretMapper extends Mapper<AppConfigSecret> {

	/**
	 *查询总条数
	 */
	Integer getAppConfigSecretAllCount();

	/**
	 *批量删除方法
	 */
	Integer delAppConfigSecretInIds(@Param("ids") List<Long> ids);

	/**
	 * 应用配置秘钥是否存在
	 * @param param
	 * @return
	 */
	Integer isAppConfigSecretExist(Map<String,Object> param);

	/**
	 * 根据条件查询应用秘钥
	 * @param param
	 * @return
	 */
	List<AppConfigSecret> getByCondition(Map<String,Object> param);

}
