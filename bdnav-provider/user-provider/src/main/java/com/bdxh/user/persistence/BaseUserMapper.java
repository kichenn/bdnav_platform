package com.bdxh.user.persistence;

import java.util.List;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.user.entity.BaseUser;

/**
* @Description: Mapper
* @Author xuyuan
* @Date 2019-03-26 18:27:42
*/
@Repository
public interface BaseUserMapper extends Mapper<BaseUser> {

	/**
	 *查询总条数
	 */
	 Integer getBaseUserAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delBaseUserInIds(@Param("ids") List<Long> ids);

}
