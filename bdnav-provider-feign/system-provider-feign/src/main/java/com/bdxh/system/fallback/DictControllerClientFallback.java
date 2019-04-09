package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DictDto;
import com.bdxh.system.dto.DictQueryDto;
import com.bdxh.system.dto.UpdateDictDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.feign.DictControllerClient;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class DictControllerClientFallback implements DictControllerClient {


    @Override
    public Wrapper<List<Dict>> findDictListAll() {
        return WrapMapper.error();
    }

    @Override
    public Wrapper<PageInfo<Dict>> queryDictList(DictQueryDto dictQueryDto) {
        return WrapMapper.error();
    }

  /*  @Override
    public Wrapper<List<Dict>> queryList(DictQueryDto dictQueryDto) {
        return WrapMapper.error();
    }*/



    @Override
    public Wrapper addDict(DictDto dictDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateDict(UpdateDictDto updateDictDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delDict(Long dictId) {
        return WrapMapper.error();
    }




}
