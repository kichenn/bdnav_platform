package com.bdxh.user.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.vo.VisitLogsVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-17 17:29:23
*/
@Repository
public interface VisitLogsMapper extends Mapper<VisitLogs> {
	//查询所有
	List<VisitLogsVo> getVisitLogsInfos(VisitLogs visitLogs);

	//查询单个
	VisitLogsVo	getVisitLogsInfo(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("id") String id);

	//修改
	int updateVisitLogsInfo(VisitLogs visitLogs);

	//删除单个
	int removeVisitLogsInfo(@Param("schoolCode") String schoolCode,@Param("cardNumber") String cardNumber,@Param("id") String id);

	//批量删除
	int batchRemoveVisitLogsInfo(@Param("list") List<Map<String,String>> list);
}
