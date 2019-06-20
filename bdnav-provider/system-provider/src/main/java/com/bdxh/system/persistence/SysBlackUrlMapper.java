package com.bdxh.system.persistence;

import java.util.List;

import com.bdxh.system.entity.SysBlackUrl;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: 系统黑名单Mapper
* @Author wanMing
* @Date 2019-06-20 11:32:50
*/
@Repository
public interface SysBlackUrlMapper extends Mapper<SysBlackUrl> {

	/**
	 *查询总条数
	 */
	 Integer getSysBlackUrlAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delSysBlackUrlInIds(@Param("ids") List<Long> ids);

	 /**
	  * 分页+条件查询系统黑名单
	  * @Author: WanMing
	  * @Date: 2019/6/20 12:20
	  */
	List<SysBlackUrl> findSysBlackUrlByCondition(@Param("sysBlackUrl")SysBlackUrl sysBlackUrl);

	/**
	 * 根据ip查询黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/20 15:49
	 */
	SysBlackUrl querySysBlackUrlByUrl(String url);
}
