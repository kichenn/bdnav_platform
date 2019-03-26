package com.bdxh.onecard.persistence;

import com.bdxh.onecard.entity.PhysicalCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;
import java.util.List;

/**
* @Description: Mapper
* @Author Kang
* @Date 2019-03-25 15:03:44
*/
@Repository
public interface PhysicalCardMapper extends Mapper<PhysicalCard> {

	/**
	 *查询总条数
	 */
	 Integer getPhysicalCardAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delPhysicalCardInIds(@Param("ids") List<Long> ids);

}
