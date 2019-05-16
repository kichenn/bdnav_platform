package com.bdxh.user.persistence.mongodb;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.user.dto.VisitLogsQueryDto;
import com.bdxh.user.mongo.VisitLogsMongo;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @description:将学生日志数据存入mongoDB
 * @author: binzh
 * @create: 2019-04-19 15:57
 **/

@Repository
public class VisitLogsMongoMapper {
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有学生浏览网页数据
     * @param visitLogsQueryDto
     * @return
     */
    public PageInfo<VisitLogsVo> getVisitLogsInfos(VisitLogsQueryDto visitLogsQueryDto) {
        Query query=new Query();
        Criteria criteria=new Criteria();
        //模糊匹配
        if(StringUtils.isNotEmpty(visitLogsQueryDto.getSchoolName())){
            Pattern pattern = Pattern.compile("^.*"+visitLogsQueryDto.getSchoolName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("school_name").regex(pattern);
        }
        if(null!=visitLogsQueryDto.getStatus()&&visitLogsQueryDto.getStatus()!=0){
            criteria.and("status").is(visitLogsQueryDto.getStatus());
        }
        if(StringUtils.isNotEmpty(visitLogsQueryDto.getSchoolCode())){
            Pattern pattern = Pattern.compile("^.*"+visitLogsQueryDto.getSchoolCode()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("school_code").regex(pattern);
        }
        if (StringUtils.isNotEmpty(visitLogsQueryDto.getUserName())){
            Pattern pattern = Pattern.compile("^.*"+visitLogsQueryDto.getUserName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("user_name").regex(pattern);
        }
        if (StringUtils.isNotEmpty(visitLogsQueryDto.getCardNumber())){
            Pattern pattern = Pattern.compile("^.*"+visitLogsQueryDto.getCardNumber()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("card_number").regex(pattern);
        }
        query.addCriteria(criteria);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "create_date")));
        int skip = (visitLogsQueryDto.getPageNum() - 1) * visitLogsQueryDto.getPageSize();
        query.skip(skip).limit(visitLogsQueryDto.getPageSize());
        List<VisitLogsMongo> visitLogsMongoList=mongoTemplate.find(query,VisitLogsMongo.class);
        List<VisitLogsVo> visitLogsVos=	BeanMapUtils.mapList(visitLogsMongoList,VisitLogsVo.class);
        PageInfo<VisitLogsVo> pageInfoVisitLogs= new PageInfo<>(visitLogsVos);
        return pageInfoVisitLogs;
    }


    /**
     * 查询单个学生浏览网页数据
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    public VisitLogsVo getVisitLogsInfo(String schoolCode, String cardNumber, String id) {
        Query query=new Query(Criteria.where("school_code").is(schoolCode)
                .and("card_number").is(cardNumber).and("id").is(id));
        VisitLogsMongo visitLogsMongo=mongoTemplate.findOne(query,VisitLogsMongo.class);
        if(null==visitLogsMongo){
            return null;
        }
        VisitLogsVo visitLogsVo=BeanMapUtils.map(visitLogsMongo,VisitLogsVo.class);
        return visitLogsVo;
    }

    /**
     * 修改学生浏览网页数据
     * @param visitLogsMongo
     */
    public void updateVisitLogsInfo(VisitLogsMongo visitLogsMongo) {
        Query query =new Query();
        query.addCriteria(Criteria.where("id").is(visitLogsMongo.getId())
                .and("school_code").is(visitLogsMongo.getSchoolCode())
                .and("card_number").is(visitLogsMongo.getCardNumber()));
        Update update=new Update();
        if(StringUtils.isNotEmpty(visitLogsMongo.getUrl())){
            update.set("url",visitLogsMongo.getUrl());
        }
        if(null!=visitLogsMongo.getStatus()){
            update.set("status",visitLogsMongo.getStatus());
        }
        if(StringUtils.isNotEmpty(visitLogsMongo.getRemark())){
            update.set("remark",visitLogsMongo.getRemark());
        }
        update.set("update_date",visitLogsMongo.getUpdateDate());
        mongoTemplate.upsert(query,update,VisitLogsMongo.class);
    }

    /**
     * 修改学生浏览网页数据
     * @param schoolCode
     * @param schoolName
     */
    public void updateSchoolName(String schoolCode,String schoolName) {
        Query query =new Query();
        query.addCriteria(Criteria.where("school_code").is(schoolCode));
        Update update=new Update();
        if(StringUtils.isNotEmpty(schoolName)){
            update.set("school_name",schoolName);
        }
        mongoTemplate.updateMulti (query,update,VisitLogsMongo.class);
    }
    /**
     * 删除学生浏览网页数据
     * @param schoolCode
     * @param cardNumber
     * @param id
     */
    public void removeVisitLogsInfo(String schoolCode, String cardNumber, String id) {
        Query query=new Query(Criteria.where("school_code").is(schoolCode)
                .and("card_number").is(cardNumber)
                .and("id").is(id));
        mongoTemplate.remove(query,VisitLogsMongo.class);
    }

    /**
     * 批量删除学生浏览网页数据
     * @param schoolCodes
     * @param cardNumbers
     * @param ids
     */
    public void batchRemoveVisitLogsInfo(String schoolCodes, String cardNumbers, String ids) {
        String id[]=ids.split(",");
        String cardNumber[]=cardNumbers.split(",");
        String schoolCode[]=schoolCodes.split(",");
        Query query=new Query();
        for (int i = 0; i < id.length; i++) {
            Criteria criteria=Criteria.where("school_code").is(schoolCode[i])
                    .and("card_number").is(cardNumber[i])
                    .and("id").is(id[i]);
            query.addCriteria(criteria);
            mongoTemplate.remove(query,VisitLogsMongo.class);
        }
    }

    /**
     * 新增学生浏览网页数据
     * @param visitLogsMongo
     */
    public void insertVisitLogsInfo(VisitLogsMongo visitLogsMongo) {
        mongoTemplate.save(visitLogsMongo);
    }

}