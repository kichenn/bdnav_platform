package com.bdxh.school.fallback;


import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.school.dto.AddBlackUrlDto;
import com.bdxh.school.dto.BlackUrlQueryDto;
import com.bdxh.school.dto.ModifyBlackUrlDto;
import com.bdxh.school.entity.BlackUrl;
import com.bdxh.school.feign.BlackUrlControllerClient;
import com.bdxh.school.vo.BlackUrlShowVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
* @Description: 学校Url
* @Author: Kang
* @Date: 2019/4/11 11:37
*/
@Component
public class BlackUrlControllerClientFallback implements BlackUrlControllerClient {

    @Override
    public Wrapper addBlack(AddBlackUrlDto addBlackUrlDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyBlack(ModifyBlackUrlDto modifyBlackUrlDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBlackById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBlackInIds(List<Long> ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<BlackUrl> findBlackById(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<BlackUrlShowVo>>  findBlackInConditionPaging(BlackUrlQueryDto blackUrlQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<String>> findBlackInList(String schoolCode) {
        return WrapMapper.error();
    }
}
