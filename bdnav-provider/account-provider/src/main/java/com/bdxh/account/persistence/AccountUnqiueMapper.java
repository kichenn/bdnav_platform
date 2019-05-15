package com.bdxh.account.persistence;


import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.account.entity.AccountUnqiue;

import java.util.List;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-05-15 10:48:22
 */
@Repository
public interface AccountUnqiueMapper extends Mapper<AccountUnqiue> {

    /**
     * 新增索引字典信息
     *
     * @param accountUnqiue
     * @return
     */
    int addAccountUnqiue(@Param("accountUnqiue") AccountUnqiue accountUnqiue);


    /**
     * 删除索引字典信息
     *
     * @param id
     * @return
     */
    int delAccountUnqiue(@Param("id") String id);



    /**
     * 修改索引字典信息
     *
     * @param accountUnqiue
     * @return
     */
    int modifyAccountUnqiue(@Param("accountUnqiue") AccountUnqiue accountUnqiue);

}
