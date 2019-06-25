package com.bdxh.user.service;

import com.bdxh.common.support.IService;
import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.entity.FamilyBlackUrl;
import com.bdxh.user.vo.FamilyBlackUrlVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 家长端黑名单业务层接口
* @Author WanMing
* @Date 2019-06-25 10:17:12
*/
@Service
public interface FamilyBlackUrlService extends IService<FamilyBlackUrl> {

	/**
	 *查询所有数量
	 */
 	Integer getFamilyBlackUrlAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelFamilyBlackUrlInIds(List<Long> id);

	/**
	 * 添加家长端黑名单
	 * @param familyBlackUrl
	 * @return
	 */
	Boolean addFamilyBlackUrl(FamilyBlackUrl familyBlackUrl);

	/**
	 * 删除家长端黑名单
	 * @param schoolCode
	 * @param cardNumber
	 * @param id
	 * @return
	 */
	Boolean delFamilyBlackUrl(String schoolCode, String cardNumber, String id);

	/**
	 * 修改家长端黑名单
	 * @Author: WanMing
	 * @Date: 2019/6/25 11:41
	 */
	Boolean modifyFamilyBlackUrl(FamilyBlackUrl familyBlackUrl);

	/**
	 * 分页查询家长端黑名单列表
	 * @Author: WanMing
	 * @Date: 2019/6/25 11:58
	 */
	PageInfo<FamilyBlackUrlVo> findFamilyBlackUrlByCondition(FamilyBlackUrlQueryDto familyBlackUrlQueryDto);

	/**
	 * 查询家长指定孩子的黑名单列表
	 * @Author: WanMing
	 * @Date: 2019/6/25 14:37
	 */
    List<FamilyBlackUrlVo> findFamilyBlackUrlByStudent(String schoolCode, String cardNumber, String studentNumber);

	/**
	 * 根据学生卡号查询黑名单列表
	 * @param schoolCode
	 * @param studentNumber
	 * @return
	 */
	List<FamilyBlackUrlVo> findBlackInList(String schoolCode,String studentNumber);
}
