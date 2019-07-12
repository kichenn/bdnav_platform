package com.bdxh.wallet.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.wallet.entity.PhysicalCard;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-07-11 09:40:52
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

	/**
	 * 带条件查询账户列表
	 */
	List<PhysicalCard> findPhysicalCardInCondition(Map<String,Object> param);

	/**
	 * 删除相关账户
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	Integer delPhysicalCard(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")Long id);

	/**
	 * 根据id查询详细信息
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	PhysicalCard findPhysicalCardById(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cardNumber,@Param("id")Long id);

}
