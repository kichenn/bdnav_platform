package com.bdxh.account.service;

import com.bdxh.common.support.IService;
import com.bdxh.account.entity.AccountUnqiue;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Description: 业务层接口
 * @Author Kang
 * @Date 2019-05-15 10:48:22
 */
@Service
public interface AccountUnqiueService extends IService<AccountUnqiue> {

    /**
     * 新增索引字典信息
     *
     * @param accountUnqiue
     * @return
     */
    boolean addAccountUnqiue(AccountUnqiue accountUnqiue);


    /**
     * 删除索引字典信息
     *
     * @param id
     * @return
     */
    boolean delAccountUnqiue(String id);

    /**
     * 修改索引字典信息
     *
     * @param accountUnqiue
     * @return
     */
    boolean modifyAccountUnqiue(AccountUnqiue accountUnqiue);



}
