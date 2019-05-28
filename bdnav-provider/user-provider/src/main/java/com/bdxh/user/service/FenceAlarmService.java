package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.FenceAlarm;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.vo.FenceAlarmVo;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
public interface FenceAlarmService extends IService<FenceAlarm> {

	/**
	 * 查询所有
	 * @param fenceAlarmQueryDto
	 * @return
	 */
	PageInfo<FenceAlarmVo> getAllFenceAlarmInfos(FenceAlarmQueryDto fenceAlarmQueryDto);

	/**
	 * 查询单个
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	FenceAlarmVo getFenceAlarmInfo( String schoolCode, String cardNumber, String id);

	/**
	 * 删除单个
	 * @param id
	 * @param schoolCode
	 * @param cardNumber
	 */
	void removeFenceAlarmInfo(String id,String schoolCode,String  cardNumber);

	/**
	 * 批量删除
	 * @param ids
	 * @param schoolCodes
	 * @param cardNumbers
	 */
	void batchRemoveFenceAlarmInfo(String ids,String schoolCodes,String  cardNumbers);

	/**
	 * 修改数据
	 * @param updateFenceAlarmDto
	 */
	void updateFenceAlarmInfo(UpdateFenceAlarmDto updateFenceAlarmDto);

	/**
	 * 新增围栏警报
	 * @param addFenceAlarmDto
	 */
	void insertFenceAlarmInfo(AddFenceAlarmDto addFenceAlarmDto);
	/**
	 * 修改学校名字
	 * @param schoolCode
	 * @param schoolName
	 */
	void updateSchoolName(String schoolCode,String schoolName);

	List<FenceAlarmVo> findFenceAlarmInfos(String schoolCode,String  cardNumber,String fenceId);
}
