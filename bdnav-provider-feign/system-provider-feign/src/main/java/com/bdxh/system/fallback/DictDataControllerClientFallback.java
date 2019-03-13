package com.bdxh.system.fallback;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DictDataDto;
import com.bdxh.system.dto.DictDataQueryDto;
import com.bdxh.system.dto.UpdateDictDataDto;
import com.bdxh.system.feign.DictDataControllerClient;
import org.springframework.stereotype.Component;

@Component
public class DictDataControllerClientFallback implements DictDataControllerClient {


    @Override
    public Wrapper queryListPage(DictDataQueryDto dictDataQueryDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper addDictData(DictDataDto dictDataDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper updateDictData(UpdateDictDataDto updateDictDataDto) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delDictData(Long id) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper delBatchDictData(String ids) {
        return WrapMapper.error();
    }

    @Override
    public Wrapper findDictDataPage(Long dictId, Integer pageNum, Integer pageSize) {
        return WrapMapper.error();
    }
}
