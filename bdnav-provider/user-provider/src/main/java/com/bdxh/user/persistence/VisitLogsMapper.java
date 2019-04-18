package com.bdxh.user.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.vo.VisitLogsVo;
import org.springframework.data.mongodb.repository.MongoRepository;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;



/**
* @Description: Mapper
* @Author Kang
* @Date 2019-04-17 17:29:23
*/
@Repository
public interface VisitLogsMapper extends MongoRepository<String,String> {

}
