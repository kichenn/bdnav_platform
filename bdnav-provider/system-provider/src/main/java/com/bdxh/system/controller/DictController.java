package com.bdxh.system.controller;


import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.DictDto;
import com.bdxh.system.dto.DictQueryDto;
import com.bdxh.system.dto.UpdateDictDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.service.DictService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典控制器
 */
@RestController
@RequestMapping("/dict")
@Validated
@Slf4j
@Api(value = "系统字典相关API", tags = "系统字典管理")
public class DictController {

    @Autowired
    private DictService dictService;


    /**
     * 添加字典目录
     * @param dictDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("添加字典信息")
    @RequestMapping(value = "/addDict",method = RequestMethod.POST)
    public Object addDict(@Valid @RequestBody DictDto dictDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Dict dictDate = dictService.getByDictName(dictDto.getName());
            Preconditions.checkArgument(dictDate == null, "该字典已存在,请勿重复添加");
            Dict dict = BeanMapUtils.map(dictDto, Dict.class);
            boolean result=dictService.save(dict)>0;
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改目录信息
     * @param updateDictDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改字典目录信息")
    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    public Object updateDict(@Valid @RequestBody UpdateDictDto updateDictDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean result;
            Dict dictDate = dictService.getByDictName(updateDictDto.getName());
            Dict dict= BeanMapUtils.map(updateDictDto, Dict.class);
            if (dictDate!=null){
                if(dictDate.getName().equals(updateDictDto.getName())&&!dictDate.getId().equals(updateDictDto.getId())){
                    return WrapMapper.error("该字典已存在,请勿重复添加");
                }else{
                    result =  dictService.update(dict)>0;
                }
            }else{
                result =  dictService.update(dict)>0;
            }
            return WrapMapper.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据id查询对象
     * @param id
     * @return
     */
    @ApiOperation("根据id查询对象")
    @RequestMapping(value = "/queryDactById",method = RequestMethod.GET)
    public Object queryDict(@RequestParam(name = "id") @NotNull(message = "字典id不能为空") Long id){
        try {
            Dict dict = dictService.selectByKey(id);
            return WrapMapper.ok(dict);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件查询列表
     * @param dictQueryDto
     * @return
     */
    @ApiOperation("根据条件查询列表")
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    public Object queryList(@Valid @RequestBody DictQueryDto dictQueryDto,BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(dictQueryDto);
            List<Dict> dicts = dictService.queryList(param);
            return WrapMapper.ok(dicts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param dictQueryDto
     * @return
     */
    @ApiOperation("根据条件分页查找字典")
    @RequestMapping(value = "/queryListPage",method = RequestMethod.POST)
    public Object queryListPage(@Valid @RequestBody DictQueryDto dictQueryDto,BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(dictQueryDto);
            PageInfo<Dict> dicts = dictService.findListPage(param, dictQueryDto.getPageNum(),dictQueryDto.getPageSize());
            return WrapMapper.ok(dicts);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据id删除字典
     * @return
     */
    @ApiOperation("根据id删除字典")
    @RequestMapping(value = "/delDict",method = RequestMethod.POST)
    public Object delDict(@RequestParam(name = "dictId") Long dictId){
        try {
        dictService.delDict(dictId);
           return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     *查询字典列表
     * @return
     */
    @ApiOperation("查询字典列表")
    @RequestMapping(value = "/findDictListAll",method = RequestMethod.GET)
    public Object findDictListAll(){
        try {
            List<Dict> dictVos=dictService.findDictListAll();
            return WrapMapper.ok(dictVos);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 分页查询字典列表
     * @return
     */
    @ApiOperation("分页查询字典列表")
    @RequestMapping(value = "/findPageDectListAll",method = RequestMethod.GET)
    public Object findPageDectListAll(@RequestParam(name = "pageNum")Integer pageNum,
                                      @RequestParam(name = "pageSize")Integer pageSize){
        try {
            DictQueryDto dqd=new DictQueryDto();
            dqd.setPageNum(pageNum);
            dqd.setPageSize(pageSize);
            PageInfo<Dict> dictVos=dictService.findDictsInConditionPaging(dqd.getPageNum(),dqd.getPageSize());
            return WrapMapper.ok(dictVos);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

  /*  *//**
     *查询字典列表
     * @return
     *//*
    @ApiOperation("根据字典名称查询对象")
    @RequestMapping(value = "/getByDictName",method = RequestMethod.GET)
    public Object getByDictName(@RequestParam(name = "dictName")String dictName){
        try {
            Dict dictDate = dictService.getByDictName(dictName);
            Preconditions.checkArgument(dictDate == null, "该字典已存在,请勿重复添加");
*//*            Boolean flag=*//*
            return WrapMapper.ok(dictDate);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
*/


}
