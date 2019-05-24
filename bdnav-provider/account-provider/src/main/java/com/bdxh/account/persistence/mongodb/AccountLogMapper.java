package com.bdxh.account.persistence.mongodb;

import com.bdxh.account.entity.AccountLog;
import com.bdxh.common.utils.BeanMapUtils;
import com.mongodb.BasicDBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 登录日志mapper
 * @Author: Kang
 * @Date: 2019/5/16 14:28
 */
@Repository
public class AccountLogMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 增加登录日志
     *
     * @param accountLog
     */
    public void addAccountLog(AccountLog accountLog) {
        mongoTemplate.save(accountLog);
    }

    /**
     * 查询用户最近一条登录日志信息 (学校code+学号)
     *
     * @param schoolCode
     * @param cardNumber
     * @return
     */
    public AccountLog findAccountLogBySchoolCodeAndCardNumber(String schoolCode, String cardNumber) {
        Query query = new Query(Criteria.where("school_code").is(schoolCode)
                .and("card_number").is(cardNumber)).limit(1).with(new Sort(Sort.Direction.DESC, "create_date"));
        AccountLog accountLog = mongoTemplate.findOne(query, AccountLog.class);
        if (null == accountLog) {
            return null;
        }
        AccountLog accountLogVo = BeanMapUtils.map(accountLog, AccountLog.class);
        return accountLogVo;
    }

    /**
     * 查询用户最近一条登录日志信息(loginName)
     *
     * @param loginName
     * @return
     */
    public AccountLog findAccountLogByLoginName(String loginName) {
        Query query = new Query(Criteria.where("login_name").is(loginName)).limit(1).with(new Sort(Sort.Direction.DESC, "create_date"));
        AccountLog accountLog = mongoTemplate.findOne(query, AccountLog.class);
        if (null == accountLog) {
            return null;
        }
        AccountLog accountLogVo = BeanMapUtils.map(accountLog, AccountLog.class);
        return accountLogVo;
    }

    /**
     * 查询用户最近一条登录日志信息(userId)
     *
     * @param userId
     * @return
     */
    public AccountLog findAccountLogByUserId(String userId) {
        Query query = new Query(Criteria.where("user_id").is(userId)).limit(1).with(new Sort(Sort.Direction.DESC, "create_date"));
        AccountLog accountLog = mongoTemplate.findOne(query, AccountLog.class);
        if (null == accountLog) {
            return null;
        }
        AccountLog accountLogVo = BeanMapUtils.map(accountLog, AccountLog.class);
        return accountLogVo;
    }


}
