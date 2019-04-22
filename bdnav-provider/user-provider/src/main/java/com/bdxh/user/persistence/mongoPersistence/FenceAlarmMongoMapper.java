package com.bdxh.user.persistence.mongoPersistence;



import com.bdxh.common.helper.excel.utils.DateUtils;
import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.user.dto.*;
import com.bdxh.user.mongo.FenceAlarmMongo;
import com.bdxh.user.mongo.VisitLogsMongo;
import com.bdxh.user.vo.FenceAlarmVo;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;


/**
 * @description:
 * @author: binzh
 * @create: 2019-04-19 15:34
 **/
@Repository
public class FenceAlarmMongoMapper{
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 查询所有学生围栏警报数据
     * @param fenceAlarmQueryDto
     * @return
     */
    public PageInfo<FenceAlarmVo> getFenceAlarmInfos(FenceAlarmQueryDto fenceAlarmQueryDto) {
        //创建条件对象
        Query query=new Query();
        //创建标准查询对象
        Criteria criteria=new Criteria();
        if(StringUtils.isNotEmpty(fenceAlarmQueryDto.getSchoolName())){
            //正则模糊匹配
            Pattern pattern = Pattern.compile("^.*"+fenceAlarmQueryDto.getSchoolName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("school_name").regex(pattern);
        }
        if(StringUtils.isNotEmpty(fenceAlarmQueryDto.getSchoolName())){
            criteria.and("action").is(fenceAlarmQueryDto.getAction());
        }
        if(StringUtils.isNotEmpty(fenceAlarmQueryDto.getSchoolCode())){
            criteria.and("school_code").is(fenceAlarmQueryDto.getSchoolCode());
        }
        if(null!=fenceAlarmQueryDto.getType()){
            criteria.and("type").is(fenceAlarmQueryDto.getType());
        }
        if (StringUtils.isNotEmpty(fenceAlarmQueryDto.getFenceName())){
            Pattern pattern = Pattern.compile("^.*"+fenceAlarmQueryDto.getFenceName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("fence_name").regex(pattern);
        }
        if (StringUtils.isNotEmpty(fenceAlarmQueryDto.getStudentName())){
            Pattern pattern = Pattern.compile("^.*"+fenceAlarmQueryDto.getStudentName()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("student_name").regex(pattern);
        }
        if (StringUtils.isNotEmpty(fenceAlarmQueryDto.getMonitoredPerson())){
            Pattern pattern = Pattern.compile("^.*"+fenceAlarmQueryDto.getMonitoredPerson()+".*$", Pattern.CASE_INSENSITIVE);
            criteria.and("monitored_person").regex(pattern);
        }
        query.addCriteria(criteria);
        query.with(new Sort(new Sort.Order(Sort.Direction.DESC, "create_date")));
        int skip = (fenceAlarmQueryDto.getPageNum() - 1) * fenceAlarmQueryDto.getPageSize();
        query.skip(skip).limit(fenceAlarmQueryDto.getPageSize());
        List<FenceAlarmMongo> fenceAlarmMongoList=mongoTemplate.find(query, FenceAlarmMongo.class);
        List<FenceAlarmVo> fenceAlarmVo=	BeanMapUtils.mapList(fenceAlarmMongoList,FenceAlarmVo.class);
        PageInfo<FenceAlarmVo> pageInfoFenceAlarm= new PageInfo<>(fenceAlarmVo);
        return pageInfoFenceAlarm;
    }


    /**
     * 查询单个学生围栏警报数据
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    public FenceAlarmVo getFenceAlarmInfo(String schoolCode, String cardNumber, String id) {
        Query query=new Query(Criteria.where("school_code").is(schoolCode)
                .and("card_number").is(cardNumber)
                .and("id").is(id));
        FenceAlarmMongo fenceAlarmMongo=mongoTemplate.findOne(query,FenceAlarmMongo.class);
        if(null==fenceAlarmMongo){
            return null;
        }
        FenceAlarmVo fenceAlarmVo=BeanMapUtils.map(fenceAlarmMongo,FenceAlarmVo.class);
        return fenceAlarmVo;
    }

    /**
     * 修改学生围栏警报数据
     * @param fenceAlarmMongo
     */
    public void updateFenceAlarmInfo(FenceAlarmMongo fenceAlarmMongo) {
        Query query =new Query();
        query.addCriteria(Criteria.where("id").is(fenceAlarmMongo.getId())
                .and("school_code").is(fenceAlarmMongo.getSchoolCode())
                .and("card_number").is(fenceAlarmMongo.getCardNumber()));
        Update update=new Update();
        if(StringUtils.isNotEmpty(fenceAlarmMongo.getAction())){
            update.set("url",fenceAlarmMongo.getAction());
        }
        if(StringUtils.isNotEmpty(fenceAlarmMongo.getAlarmPoint())){
            update.set("status",fenceAlarmMongo.getAlarmPoint());
        }
        update.set("update_date",fenceAlarmMongo.getUpdateDate());
        mongoTemplate.updateFirst(query,update,FenceAlarmMongo.class);
    }

    /**
     * 删除学生围栏警报数据
     * @param schoolCode
     * @param cardNumber
     * @param id
     */
    public void removeFenceAlarmInfo(String schoolCode, String cardNumber, String id) {
        Query query=new Query(Criteria.where("school_code").is(schoolCode)
                .and("card_number").is(cardNumber)
                .and("id").is(id));
        mongoTemplate.remove(query,FenceAlarmMongo.class);
    }

    /**
     * 批量删除学生围栏警报数据
     * @param schoolCodes
     * @param cardNumbers
     * @param ids
     */
    public void batchRemoveFenceAlarmInfo(String schoolCodes, String cardNumbers, String ids) {
        String id[]=ids.split(",");
        String cardNumber[]=cardNumbers.split(",");
        String schoolCode[]=schoolCodes.split(",");
        Query query=new Query();
        for (int i = 0; i < id.length; i++) {
            Criteria criteria=Criteria.where("school_code").is(schoolCode[i])
                    .and("card_number").is(cardNumber[i])
                    .and("id").is(id[i]);
            query.addCriteria(criteria);
             mongoTemplate.remove(query,FenceAlarmMongo.class);
        }
    }

    /**
     * 新增学生围栏警报数据
     * @param fenceAlarmMongo
     */
    public void insertFenceAlarmInfo(FenceAlarmMongo fenceAlarmMongo) {
        mongoTemplate.save(fenceAlarmMongo);
    }

}