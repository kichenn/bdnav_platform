package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.DictDataDto;
import com.bdxh.system.dto.DictDataQueryDto;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.service.DictDataService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典数据控制器
 */
@RestController
@RequestMapping("/dictData")
@Validated
@Slf4j
@Api(value = "字典数据相关API", tags = "系统字典数据")
public class DictDataController {


    @Autowired
    private  DictDataService dictDataService;

    /**
     * 添加字典目录数据
     * @param dictDataDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("添加字典数据信息")
    @RequestMapping(value = "/addDictData",method = RequestMethod.POST)
    public Object addDictData(@Valid @RequestBody DictDataDto dictDataDto, BindingResult bindingResult){   //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            DictData dictData = BeanMapUtils.map(dictDataDto, DictData.class);
            dictDataService.save(dictData);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改字典数据信息
     * @param dictDataDto
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改字典数据信息")
    @RequestMapping(value = "/updateDictData",method = RequestMethod.POST)
    public Object updateDictData(@Valid @RequestBody DictDataDto dictDataDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            DictData dictData= BeanMapUtils.map(dictDataDto, DictData .class);
            dictDataService.update(dictData);
            return WrapMapper.ok();
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
    @RequestMapping(value = "/queryDactDataById",method = RequestMethod.GET)
    public Object queryDictData(@RequestParam(name = "id") @NotNull(message = "字典id不能为空") Long id){
        try {
            DictData dictData = dictDataService.selectByKey(id);
            return WrapMapper.ok(dictData);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件查询列表
     * @param dictDataQueryDto
     * @return
     */
    @ApiOperation("根据条件查询列表")
    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    public Object queryList(@Valid @RequestBody DictDataQueryDto dictDataQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(dictDataQueryDto);
            List<DictData> dictDatas = dictDataService.queryList(param);
            return WrapMapper.ok(dictDatas);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据条件分页查找
     * @param dictDataQueryDto
     * @return
     */
    @ApiOperation("根据条件分页查找字典数据")
    @RequestMapping(value = "/queryListPage",method = RequestMethod.POST)
    public Object queryListPage(@Valid @RequestBody DictDataQueryDto dictDataQueryDto){
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(dictDataQueryDto);
            PageInfo<DictData> dictDatas = dictDataService.findListPage(param, dictDataQueryDto.getPageNum(),dictDataQueryDto.getPageSize());
            return WrapMapper.ok(dictDatas);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 根据ids批量删除角色
     * @param ids
     * @return
     */
    @ApiOperation("根据ids批量删除字典数据")
    @RequestMapping(value = "/delBatchDictData",method = RequestMethod.POST)
    @Transactional
    public Object delBatchDictData(@RequestParam(name = "ids") @NotEmpty(message = "角色id不能为空") String ids){
        try {
            String[] idsArr = StringUtils.split(ids,",");
            List<Long> idsLongArr = new ArrayList<>(15);
            if (idsArr!=null&&idsArr.length>0){
                for (int i=0;i<idsArr.length;i++){
                    String id = idsArr[i];
                    if (StringUtils.isNotEmpty(id)){
                        idsLongArr.add(Long.valueOf(id));
                    }
                }
            }
            dictDataService.delBatchDictData(idsLongArr);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据id删除角色
     * @param id
     * @return
     */
    @ApiOperation("根据id删除字典数据")
    @RequestMapping(value = "/delDictData",method = RequestMethod.POST)
    public Object delDictData(@RequestParam(name = "id") @NotNull(message = "角色id不能为空") Long id){
        try {
            dictDataService.deleteByKey(id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

}
