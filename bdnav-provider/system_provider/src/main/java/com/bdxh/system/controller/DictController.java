package com.bdxh.system.controller;



import com.bdxh.common.utils.BeanMapUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.system.dto.DictDto;
import com.bdxh.system.dto.RoleDto;
import com.bdxh.system.entity.Dict;
import com.bdxh.system.entity.Role;
import com.bdxh.system.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

/**
 * 字典控制器
 */
@RestController
@RequestMapping("/dict")
@Validated
@Slf4j
public class DictController {

    @Autowired
    private DictService dictService;

    /**
     * 添加字典目录
     * @param dictDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/addDict",method = RequestMethod.POST)
    public Object addDict(@Valid @RequestBody DictDto dictDto, BindingResult bindingResult){   //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
            Dict dict = BeanMapUtils.map(dictDto, Dict.class);
            dictService.save(dict);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改目录信息
     * @param dictDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/updateDict",method = RequestMethod.POST)
    public Object updateDict(@Valid @RequestBody DictDto dictDto, BindingResult bindingResult){
        //检验参数
        if(bindingResult.hasErrors()){
            String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
            return WrapMapper.error(errors);
        }
        try {
          Dict dict= BeanMapUtils.map(dictDto, Dict.class);
            dictService.update(dict);
            return WrapMapper.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }



}
