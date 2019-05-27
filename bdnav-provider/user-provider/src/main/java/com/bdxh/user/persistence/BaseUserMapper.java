package com.bdxh.user.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.user.dto.BaseUserQueryDto;
import com.bdxh.user.vo.BaseEchartsVo;
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


	 //查询总条数
	 Integer getBaseUserAllCount();


	 //批量删除方法
	 Integer delBaseUserInIds(@Param("ids") List<Long> ids);

	//根据条件查询所有用户
	 List<BaseUser> queryBaseUserInfo(@Param("baseUserQueryDto") BaseUserQueryDto baseUserQueryDto);

	 //修改用户数据
	int updateBaseUserInfo(@Param("baseUser") BaseUser baseUser);

	// 删除用户数据
	int deleteBaseUserInfo(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cadNumber);

	//查询单个用户信息
	BaseUser queryBaseUserBySchoolCodeAndCardNumber(@Param("schoolCode")String schoolCode,@Param("cardNumber")String cadNumber);

	//批量删除用户信息
	int batchRemoveBaseUserInfo(@Param("list") List<Map<String,String>> list);

	//批量新增用户信息
	int batchSaveBaseUserInfo(List<BaseUser> baseUserList);

	//查询所有用户手机号
	List<String> queryAllUserPhone();

	//根据手机号查询有没有重复的手机号
	Integer queryUserPhoneByPhone(@Param("baseUserQueryDto")BaseUserQueryDto baseUserQueryDto);

	//查询不是家长的用户信息
	List<BaseUser> findAllBaseUserInfo();

	//修改学校名字
	int updateSchoolName(@Param("schoolCode")String schoolCode, @Param("schoolName")String schoolName);

    List<String> findSchoolNumberBySchool(@Param("schoolCode")String schoolCode);

    //统计用户分类的数量
    List<BaseEchartsVo> querySchoolUserCategoryCount(@Param("schoolCode")String schoolCode, @Param("userType")Integer userType);
}
