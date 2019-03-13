package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.fallback.DictDataControllerClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Service
@FeignClient(value = "system-provider-cluster",fallback = DictDataControllerClientFallback.class)
public interface DictDataControllerClient {

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
    @RequestMapping(value = "/dictData/delDictData",method = RequestMethod.GET)
    @ResponseBody
    Wrapper delDictData(@RequestParam(name = "id") Long id);


    //批量删除字典
    @RequestMapping("/dictData/delBatchDictData")
    @ResponseBody
    Wrapper delBatchDictData(@RequestParam(name = "ids")String ids);

    //根据字典ID查询数据列表或查询全部
    @RequestMapping(value = "/dictData/findDictListBydictId")
    @ResponseBody
    Wrapper findDictDataPage(@RequestParam(name ="dictId")Long dictId,@RequestParam(name = "pageNum")Integer pageNum,
                                 @RequestParam(name = "pageSize")Integer pageSize);


}
