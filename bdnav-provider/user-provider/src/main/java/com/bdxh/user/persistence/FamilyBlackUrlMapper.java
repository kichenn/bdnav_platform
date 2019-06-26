package com.bdxh.user.persistence;

import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.user.entity.FamilyBlackUrl;



/**
* @Description: Mapper
* @Author WanMing
* @Date 2019-06-25 10:17:12
*/
@Repository
public interface FamilyBlackUrlMapper extends Mapper<FamilyBlackUrl> {

	/**
	 *查询总条数
	 */
	 Integer getFamilyBlackUrlAllCount();

	/**
	 *批量删除方法
	 */
	 Integer delFamilyBlackUrlInIds(@Param("ids") List<Long> ids);

	 /**
	  * 删除单个家长黑名单记录
	  * @Author: WanMing
	  * @Date: 2019/6/25 11:04
	  */
	int delFamilyBlackUrl(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("id") String id);

	/**
	 * 条件查询
	 * @Author: WanMing
	 * @Date: 2019/6/25 12:02
	 */
	List<FamilyBlackUrl> findFamilyBlackUrlByCondition(@Param("familyBlackUrl")FamilyBlackUrl familyBlackUrl);

	/**
	 * 查询家长下指定孩子的黑名单列表
	 * @Author: WanMing
	 * @Date: 2019/6/25 14:39
	 */
    List<FamilyBlackUrl> findFamilyBlackUrlByStudent(@Param("schoolCode")String schoolCode, @Param("cardNumber")String cardNumber, @Param("studentNumber")String studentNumber);


	//查询当前孩子的的黑名单列表
	List<FamilyBlackUrl> findBlackInList(@Param("schoolCode") String schoolCode,@Param("studentNumber")String studentNumber);
}
