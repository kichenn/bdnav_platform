package com.bdxh.system.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.DictDto;
import com.bdxh.system.dto.DictQueryDto;
import com.bdxh.system.dto.UpdateDictDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.fallback.DictControllerClientFallback;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@FeignClient(value = "system-provider-cluster",fallback = DictControllerClientFallback.class)
public interface DictControllerClient {


    //查询全部字典列表
    @RequestMapping("/dict/findDictListAll")
    @ResponseBody
    Wrapper<List<Dict>> findDictListAll();

/*    //带条件的查询
    @RequestMapping("/dict/queryList")
    @ResponseBody
    Wrapper<List<Dict>> queryList(@RequestBody DictQueryDto dictQueryDto);*/

    //带条件的分页查询
    @RequestMapping("/dict/queryListPage")
    @ResponseBody
    Wrapper<PageInfo<Dict>> queryDictList(@RequestBody DictQueryDto dictQueryDto);

    //添加字典目录
    @RequestMapping("/dict/addDict")
    @ResponseBody
    Wrapper addDict(@RequestBody DictDto dictDto);

    //修改字典目录
    @RequestMapping("/dict/updateDict")
    @ResponseBody
    Wrapper updateDict(@RequestBody UpdateDictDto updateDictDto);

    //删除字典目录
    @RequestMapping(value = "/dict/delDict",method = RequestMethod.POST)
    @ResponseBody
    Wrapper delDict(@RequestParam(name ="dictId")Long dictId);


}
