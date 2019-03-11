package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.fallback.DictDataControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
@Service
@FeignClient(value = "system-provider-cluster",fallback = DictDataControllerClientFallback.class)
public interface DictDataControllerClient {

    //分页查询全部字典数据
    @RequestMapping("/dictData/findDictDataPage")
    @ResponseBody
    Wrapper findDictDataPage(@RequestParam(name = "pageNum")Integer pageNum,
                                      @RequestParam(name = "pageSize")Integer pageSize);

    //带条件的分页查询
    @RequestMapping("/dictData/queryListPage")
    @ResponseBody
    Wrapper queryListPage(@RequestBody DictDataQueryDto dictDataQueryDto);

    //添加字典数据
    @RequestMapping("/dictData/addDictData")
    @ResponseBody
    Wrapper addDictData(@RequestBody DictDataDto dictDataDto);

    //修改字典数据
    @RequestMapping("/dictData/updateDictData")
    @ResponseBody
    Wrapper updateDictData(@RequestBody UpdateDictDataDto updateDictDataDto);

    //删除单个字典数据
    @RequestMapping("/dictData/delDictData")
    @ResponseBody
    Wrapper delDictData(@RequestParam(name = "id") Long id);


    //批量删除字典
    @RequestMapping("/dictData/delBatchDictData")
    @ResponseBody
    Wrapper delBatchDictData(@RequestParam(name = "ids")String ids);


}
