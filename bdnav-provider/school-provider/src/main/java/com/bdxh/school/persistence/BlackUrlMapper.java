package com.bdxh.school.persistence;

import java.util.List;
import java.util.Map;

import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.vo.BlackUrlShowVo;
import tk.mybatis.mapper.common.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.bdxh.school.entity.BlackUrl;


/**
 * @Description: Mapper
 * @Author Kang
 * @Date 2019-04-11 09:56:14
 */
@Repository
public interface BlackUrlMapper extends Mapper<BlackUrl> {

    /**
     * 查询总条数
     */
    Integer getBlackUrlAllCount();

    /**
     * 批量删除方法
     */
    Integer delBlackUrlInIds(@Param("ids") List<Long> ids);

    /**
     * 条件分页查询
     */
    List<BlackUrlShowVo> findBlackInConditionPaging(@Param("blackUrl") BlackUrl blackUrl);

    //查询当前学校的黑名单列表
    List<BlackUrl> findBlackInList(@Param("schoolCode") String schoolCode,@Param("urlType")Long urlType);

    //添加黑名单列表
    Integer addBlackUrl(BlackUrl blackUrl);

    //查询当前学生的的黑名单列表
    List<BlackUrl> findBlackInListByCard(@Param("cardNumber")String cardNumber,@Param("urlType")Long urlType);

}
