package com.bdxh.onecard.persistence;

import java.util.List;
import com.bdxh.onecard.entity.VirtualCard;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @Description: Mapper
* @Author Kang
* @Date 2019-03-25 15:03:44
*/
@Repository
public interface VirtualCardMapper extends Mapper<VirtualCard> {

	/**
	 *查询总条数
	 */
	 Integer getVirtualCardAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delVirtualCardInIds(@Param("ids") List<Long> ids);

}
