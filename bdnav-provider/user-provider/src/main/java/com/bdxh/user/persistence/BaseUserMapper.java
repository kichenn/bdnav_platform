package com.bdxh.user.persistence;

import java.util.List;

import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.dto.UpdateBaseUserDto;
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

	/**
	 * 根据条件查询所有用户
	 * @param baseUserQueryDto
	 * @return
	 */
	 List<BaseUser> queryBaseUserInfo(@Param("baseUserQueryDto") BaseUserQueryDto baseUserQueryDto);

	/**
	 * 修改用户数据
	 * @param updateBaseUserDto
	 * @return
	 */
	int updateBaseUserInfo(@Param("updateBaseUserDto") UpdateBaseUserDto updateBaseUserDto);

	/**
	 * 删除用户数据
	 * @param schoolCode
	 * @param cadNumber
	 * @return
	 */
	int deleteBaseUserInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cadNumber);

	/**
	 * 查询单个用户信息
	 * @param schoolCode
	 * @param cadNumber
	 * @return
	 */
	BaseUser queryBaseUserBySchoolCodeAndCardNumber(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cadNumber);
}
