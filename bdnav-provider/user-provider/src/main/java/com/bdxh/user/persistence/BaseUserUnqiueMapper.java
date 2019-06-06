package com.bdxh.user.persistence;

import com.bdxh.user.entity.BaseUserUnqiue;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-15 16:14
 **/
@Repository
public interface BaseUserUnqiueMapper extends Mapper<BaseUserUnqiue> {
    //条件查询所有用户手机号
    List<String> queryAllUserPhone(BaseUserUnqiue baseUserUnqiue);

    //判断手机号时候存在
    Integer queryUserPhone(@Param("phone") String phone,@Param("schoolCode")String schoolCode);

    int updateUserPhoneByUserId(@Param("id")Long id, @Param("phone") String phone,@Param("schoolCode")String schoolCode);

    int insertUserPhone(@Param("id")Long id,@Param("phone") String phone,@Param("schoolCode")String schoolCode);

    int batchSaveBaseUserPhone(List<BaseUserUnqiue> baseUserUnqiueList);
}