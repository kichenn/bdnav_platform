package com.bdxh.system.controller;

import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.DictDataDto;
import com.bdxh.system.dto.DictDataQueryDto;
import com.bdxh.system.dto.DictQueryDto;
import com.bdxh.system.dto.UpdateDictDataDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.entity.DictData;
import com.bdxh.system.service.DictDataService;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Preconditions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.el.parser.BooleanNode;
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
    public Object addDictData(@Valid @RequestBody DictDataDto dictDataDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            DictData dictResult = dictDataService.getByDictDataName(dictDataDto.getDictId(),dictDataDto.getDataName());
                Preconditions.checkArgument(dictResult == null, "该字典数据已存在,请勿重复添加");
               List<DictData> DataList=dictDataService.getDictDataByIdList(dictDataDto.getDictId());
               for (int i = 0; i < DataList.size(); i++) {
                   if (dictDataDto.getDataValue().equals(DataList.get(i).getDataValue())){
                       return WrapMapper.error("该字典下存在该数据值 请勿重复添加");
                   }
               }
            DictData dictData = BeanMapUtils.map(dictDataDto, DictData.class);
            Boolean flag =dictDataService.save(dictData)>0;
            return WrapMapper.ok(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改字典数据信息
     * @param bindingResult
     * @return
     */
    @ApiOperation("修改字典数据信息")
    @RequestMapping(value = "/updateDictData",method = RequestMethod.POST)
    public Object updateDictData(@Valid @RequestBody UpdateDictDataDto updateDictDataDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Boolean result;
            DictData dictData= BeanMapUtils.map(updateDictDataDto, DictData .class);
            DictData dictResult = dictDataService.getByDictDataName(updateDictDataDto.getDictId(),updateDictDataDto.getDataName());
            if (dictResult!=null){
                if(dictResult.getDataName().equals(updateDictDataDto.getDataName())&&!dictResult.getId().equals(updateDictDataDto.getId())){
                    return WrapMapper.error("该字典数据已存在,请重新更换名称");
                }else{
                    List<DictData> DataList=dictDataService.getDictDataByIdList(updateDictDataDto.getDictId());
                    for (int i = 0; i < DataList.size(); i++) {
                        if (updateDictDataDto.getDataValue().equals(DataList.get(i).getDataValue())&&!updateDictDataDto.getId().equals(DataList.get(i).getId())){
                            return WrapMapper.error("该字典下存在该数据值 请重新更换");
                        }
                    }
                    result =dictDataService.update(dictData)>0;
                }
            }else{
                result =dictDataService.update(dictData)>0;
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
    @RequestMapping(value = "/queryDictDataById",method = RequestMethod.GET)
    public Object queryDictDataById(@RequestParam(name = "id") Long id){
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
    public Object queryList(@Valid @RequestBody DictDataQueryDto dictDataQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(dictDataQueryDto);
            List<DictData> dictData = dictDataService.queryList(param);
            return WrapMapper.ok(dictData);
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
    public Object queryListPage(@Valid @RequestBody DictDataQueryDto dictDataQueryDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Map<String, Object> param = BeanToMapUtil.objectToMap(dictDataQueryDto);
            PageInfo<DictData> dictData = dictDataService.findListPage(param, 1,2);
            return WrapMapper.ok(dictData);
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
     * @return
     */
    @ApiOperation("根据id删除字典数据")
    @RequestMapping(value = "/delDictData",method = RequestMethod.GET)
    public Object delDictData(@RequestParam(name = "id") Long id){
        try {
            dictDataService.deleteDictDataById(id);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


    /**
     * 根据dictId查询列表
     * @return
     */
    @ApiOperation("根据字典ID查询数据列表")
    @RequestMapping(value = "/findDictListBydictId",method = RequestMethod.GET)
    public Object findDictListBydictId(@RequestParam(name = "dictId",required = false)Long dictId,
    @RequestParam(name = "pageNum")Integer pageNum, @RequestParam(name = "pageSize")Integer pageSize){
        try {
            PageInfo<DictData> dictDataVos=dictDataService.findDictListBydictId(dictId,pageNum,pageSize);
            return WrapMapper.ok(dictDataVos);
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }


}
