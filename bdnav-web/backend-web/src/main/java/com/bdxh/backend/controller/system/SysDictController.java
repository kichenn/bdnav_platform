package com.bdxh.backend.controller.system;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.system.dto.*;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.feign.DictControllerClient;
import com.bdxh.system.feign.DictDataControllerClient;
import com.github.pagehelper.PageInfo;
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
    @ApiOperation(value="查询字典列表数据",response = Dict.class)
    public Object findDictListAll(){
        try {
            Wrapper wrapper = dictControllerClient.findDictListAll();
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/queryDictList",method = RequestMethod.POST)
    @ApiOperation(value="根据条件查询字典",response =PageInfo.class )
    public Object queryDictList(@Validated @RequestBody DictQueryDto dictQueryDto){
        try {
            Wrapper<PageInfo<Dict>> wrapper = dictControllerClient.queryDictList(dictQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/addDict",method = RequestMethod.POST)
    @ApiOperation(value="添加字典目录",response=Boolean.class)
    public Object addDict(@Validated @RequestBody DictDto dictDto){
        try {
            Wrapper wrapper = dictControllerClient.addDict(dictDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/updateDict",method = RequestMethod.POST)
    @ApiOperation(value="修改字典目录",response=Boolean.class)
    public Object updateDict(@Validated @RequestBody UpdateDictDto updateDictDto){
        try {
            Wrapper wrapper = dictControllerClient.updateDict(updateDictDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/delDict",method = RequestMethod.POST)
    @ApiOperation(value="删除字典目录",response=Boolean.class)
    public Object delDict(@RequestParam(name = "dictId")Long dictId){
        try {
            Wrapper wrapper = dictControllerClient.delDict(dictId);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/queryListPage",method = RequestMethod.POST)
    @ApiOperation(value="根据条件查询字典数据",response = DictData.class)
    public Object queryListPage(@Validated @RequestBody DictDataQueryDto dictDataQueryDto){
        try {
            Wrapper wrapper = dictDataControllerClient.queryListPage(dictDataQueryDto);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/addDictData",method = RequestMethod.POST)
    @ApiOperation(value="/添加字典数据",response=Boolean.class)
    public Object addDictData(@Validated @RequestBody DictDataDto dictDataDto){
        try {
            Wrapper wrapper = dictDataControllerClient.addDictData(dictDataDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



    @RequestMapping(value="/updateDictData",method = RequestMethod.POST)
    @ApiOperation(value="修改字典数据",response=Boolean.class)
    public Object updateDictData(@Validated @RequestBody UpdateDictDataDto updateDictDataDto){
        try {
            Wrapper wrapper = dictDataControllerClient.updateDictData(updateDictDataDto);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }




    @RequestMapping(value="/delDictData",method = RequestMethod.GET)
    @ApiOperation(value="删除单个字典数据",response=Boolean.class)
    public Object delDictData(@RequestParam(name = "id") Long id){
        try {
            Wrapper wrapper = dictDataControllerClient.delDictData(id);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    @RequestMapping(value="/delBatchDictData",method = RequestMethod.POST)
    @ApiOperation(value="批量删除字典数据",response=Boolean.class)
    public Object delBatchDictData(@RequestParam(name = "ids")String ids){
        try {
            Wrapper wrapper = dictDataControllerClient.delBatchDictData(ids);
            return wrapper;
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    @RequestMapping(value="/findDictDataPage",method = RequestMethod.GET)
    @ApiOperation(value="根据字典数据查询数据列表",response = DictData.class)
    public Object findDictDataPage(
            @RequestParam(name ="dictId",required = false)Long dictId,
            @RequestParam(name = "pageNum",defaultValue = "1")Integer pageNum,
                                   @RequestParam(name = "pageSize",defaultValue = "10")Integer pageSize){
        try {
            Wrapper wrapper = dictDataControllerClient.findDictDataPage(dictId,pageNum,pageSize);
            return WrapMapper.ok(wrapper.getResult());
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
