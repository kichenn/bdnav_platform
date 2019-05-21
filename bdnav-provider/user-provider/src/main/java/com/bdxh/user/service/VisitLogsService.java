package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.AddVisitLogsDto;
import com.bdxh.user.dto.UpdateVisitLogsDto;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;


/**
* @Description: 业务层接口
* @Author Kang
* @Date 2019-04-17 17:29:24
*/
@Service
public interface VisitLogsService extends IService<VisitLogs> {

	/**
	 * 根据条件查询所有信息
	 * @param visitLogsQueryDto
	 * @return
	 */
	PageInfo<VisitLogsVo> getVisitLogsInfos(VisitLogsQueryDto visitLogsQueryDto);

	/**
	 * 查询单个信息
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	VisitLogsVo	getVisitLogsInfo( String schoolCode, String cardNumber, String id);

	/**
	 * 修改信息
	 * @param updateVisitLogsDto
	 * @return
	 */
	void updateVisitLogsInfo(UpdateVisitLogsDto updateVisitLogsDto);

	/**
	 * 删除单个
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	void removeVisitLogsInfo( String schoolCode,String cardNumber, String id);

	/**
	 * 批量删除
	 * @param schoolCodes
	 * @param cardNumbers
	 * @param ids
	 * @return
	 */
	void batchRemoveVisitLogsInfo(String schoolCodes,String cardNumbers, String ids);


	/**
	 * 新增日志
	 * @param addVisitLogsDto
	 */
	void insertVisitLogsInfo(AddVisitLogsDto addVisitLogsDto);

	/**
	 * 修改学校名字
	 * @param schoolCode
	 * @param schoolName
	 */
	void updateSchoolName(String schoolCode,String schoolName);

	/**
	 * 微校平台--------查询单个孩子的浏览器访问日志
	 * @param schoolCode
	 * @param cardNumber 学生学号
	 * @return
	 */
	PageInfo<VisitLogsVo> queryVisitLogByCardNumber(String schoolCode, String cardNumber);
}
