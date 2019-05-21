package com.bdxh.appburied.persistence;

import java.util.List;

import com.bdxh.appburied.entity.AppStatus;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-11 16:39:55
*/
@Repository
public interface AppStatusMapper extends Mapper<AppStatus> {

	/**
	 *查询总条数
	 */
	 Integer getAppStatusAllCount();

	List<AppStatus> findAppStatusInConationPaging(@Param("appStatus") AppStatus appStatus);

	//根据学校Code和学生卡号查询APP状态信息
	List<AppStatus> findAppStatusInfoBySchoolCodeAndCardNumber(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber);

	//查询单个应用的管控状态
	AppStatus finAppStatusInfoByPackage(@Param("schoolCode") String schoolCode,
										@Param("cardNumber") String cardNumber,
										@Param("appPackage")String appPackage);

	//修改应用状态
	Integer updateAppStatus(AppStatus appStatus);

	//新增应用状态
	Integer addAppStatus(AppStatus appStatus);
}
