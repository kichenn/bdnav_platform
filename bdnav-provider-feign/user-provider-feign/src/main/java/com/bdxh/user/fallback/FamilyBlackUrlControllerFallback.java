package com.bdxh.user.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFamilyBlackUrlDto;
import com.bdxh.user.dto.FamilyBlackUrlQueryDto;
import com.bdxh.user.dto.ModifyFamilyBlackUrlDto;
import com.bdxh.user.feign.FamilyBlackUrlControllerClient;
import com.bdxh.user.vo.FamilyBlackUrlVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 家长端黑名单的降级处理
 * @author WanMing
 * @date 2019/6/25 16:36
 */
@Component
public class FamilyBlackUrlControllerFallback implements FamilyBlackUrlControllerClient {
    @Override
    public Wrapper addFamilyBlackUrl(AddFamilyBlackUrlDto addFamilyBlackUrlDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delFamilyBlackUrl(String schoolCode, String cardNumber, String id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper modifyFamilyBlackUrl(ModifyFamilyBlackUrlDto modifyFamilyBlackUrlDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<FamilyBlackUrlVo>> findFamilyBlackUrlByCondition(FamilyBlackUrlQueryDto familyBlackUrlQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<FamilyBlackUrlVo>> findFamilyBlackUrlByStudent(String schoolCode, String cardNumber, String studentNumber) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<List<String>> findBlackInList(String schoolCode, String studentNumber) {
        return WrapMapper.error();
    }
}
