package com.bdxh.system.service;

import com.bdxh.common.support.IService;
import com.bdxh.system.dto.SysBlackUrlQueryDto;
import com.bdxh.system.entity.SysBlackUrl;
import com.bdxh.system.vo.SysBlackUrlVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import java.util.List;

/**
* @Description: 系统黑名单即病毒库的业务层接口
* @Author wanMing
* @Date 2019-06-20 11:32:50
*/
@Service
public interface SysBlackUrlService extends IService<SysBlackUrl> {

	/**
	 *查询所有数量
	 */
 	Integer getSysBlackUrlAllCount();

	/**
	 *批量删除方法
	 */
 	Boolean batchDelSysBlackUrlInIds(List<Long> ids);

 	/**
 	 * 分页+添加查询系统黑名单即病毒库
 	 * @Author: WanMing
 	 * @Date: 2019/6/20 12:16
 	 */
	PageInfo<SysBlackUrlVo> findSysBlackUrlByCondition(SysBlackUrlQueryDto sysBlackUrlQueryDto);


	/**
	 * 批量处理url的安全性检查
	 * @Author: WanMing
	 * @Date: 2019/6/24 16:25
	 */
	void batchCheckSysBlackUrl(List<String> urls);
}
