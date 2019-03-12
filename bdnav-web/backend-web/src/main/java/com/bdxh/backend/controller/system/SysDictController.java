package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.feign.DictControllerClient;
import com.bdxh.system.feign.DictDataControllerClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 数据字典交互控制层
 */
@RestController
@RequestMapping("/sysDict")
@Validated
@Slf4j
@Api(value = "数据字典交互API", tags = "数据字典交互API")
public class SysDictController {

    @Autowired
    private DictControllerClient dictControllerClient;

    @Autowired
    private DictDataControllerClient dictDataControllerClient;


    @RequestMapping(value="/findDictListAll",method = RequestMethod.GET)
    @ApiOperation("查询字典列表数据")
    public Object findDictListAll(){
        try {
            Wrapper wrapper = dictControllerClient.findDictListAll();
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/queryList",method = RequestMethod.POST)
    @ApiOperation("根据条件查询字典")
    public Object queryList(@RequestBody DictQueryDto dictQueryDto){
        try {
            Wrapper wrapper = dictControllerClient.queryList(dictQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/addDict",method = RequestMethod.POST)
    @ApiOperation("添加字典目录")
    public Object addDict(@RequestBody DictDto dictDto){
        try {
            Wrapper wrapper = dictControllerClient.addDict(dictDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/updateDict",method = RequestMethod.POST)
    @ApiOperation("修改字典目录")
    public Object updateDict(@RequestBody UpdateDictDto updateDictDto){
        try {
            Wrapper wrapper = dictControllerClient.updateDict(updateDictDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/delDict",method = RequestMethod.POST)
    @ApiOperation("删除字典目录")
    public Object delDict(@RequestParam(name = "id")Long id){
        try {
            Wrapper wrapper = dictControllerClient.delDict(id);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/findDictDataPage",method = RequestMethod.GET)
    @ApiOperation("分页查询全部字典数据")
    public Object findDictDataPage(@RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                    @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        try {
            Wrapper wrapper = dictDataControllerClient.findDictDataPage(pageNum,pageSize);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/queryListPage",method = RequestMethod.POST)
    @ApiOperation("根据条件查询字典数据")
    public Object queryListPage(@RequestBody DictDataQueryDto dictDataQueryDto){
        try {
            Wrapper wrapper = dictDataControllerClient.queryListPage(dictDataQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/addDictData",method = RequestMethod.POST)
    @ApiOperation("/添加字典数据")
    public Object addDictData(@RequestBody DictDataDto dictDataDto){
        try {
            Wrapper wrapper = dictDataControllerClient.addDictData(dictDataDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @RequestMapping(value="/updateDictData",method = RequestMethod.POST)
    @ApiOperation("修改字典数据")
    public Object updateDictData(@RequestBody UpdateDictDataDto updateDictDataDto){
        try {
            Wrapper wrapper = dictDataControllerClient.updateDictData(updateDictDataDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




    @RequestMapping(value="/delDictData",method = RequestMethod.POST)
    @ApiOperation("删除单个字典数据")
    public Object delDictData(@RequestParam(name = "id") Long id){
        try {
            Wrapper wrapper = dictDataControllerClient.delDictData(id);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/delBatchDictData",method = RequestMethod.POST)
    @ApiOperation("批量删除字典数据")
    public Object delBatchDictData(@RequestParam(name = "ids")String ids){
        try {
            Wrapper wrapper = dictDataControllerClient.delBatchDictData(ids);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
