package com.bdxh.backend.controller.user;

import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.feign.FenceAlarmControllerClient;
import com.bdxh.user.vo.FenceAlarmVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-18 15:14
 **/
@RestController
@RequestMapping("/fenceAlarmWeb")
@Slf4j
@Validated
@Api(value = "家长围栏警报控制器", tags = "家长围栏警报控制器")
public class FenceAlarmWebController {

    @Autowired
    private FenceAlarmControllerClient fenceAlarmControllerClient;
    /**
     * 查询所有
     * @param fenceAlarmQueryDto
     * @return
     */
    @ApiOperation("查询所有围栏警报接口")
    @RequestMapping(value = "/getAllFenceAlarmInfos",method = RequestMethod.POST)
    public Object getAllFenceAlarmInfos(@Valid @RequestBody FenceAlarmQueryDto fenceAlarmQueryDto, BindingResult bindingResult){
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            log.info(fenceAlarmControllerClient.getAllFenceAlarmInfos(fenceAlarmQueryDto).getResult()+"");
            return WrapMapper.ok(fenceAlarmControllerClient.getAllFenceAlarmInfos(fenceAlarmQueryDto).getResult());
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 查询单个
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @ApiOperation(value = "查询单个围栏警报接口",response = FenceAlarmVo.class)
    @RequestMapping(value="/getFenceAlarmInfo",method = RequestMethod.POST)
    public Object getFenceAlarmInfo(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
                                    @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
                                    @RequestParam(name="id") @NotNull(message = "id不能为空")  String id){
        try {
            return fenceAlarmControllerClient.getFenceAlarmInfo(schoolCode,cardNumber,id).getResult();
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 删除单个
     * @param id
     * @param schoolCode
     * @param cardNumber
     */
    @ApiOperation("删除单个围栏警报接口")
    @RequestMapping(value="/removeFenceAlarmInfo",method = RequestMethod.GET)
    public Object removeFenceAlarmInfo(@RequestParam("id")String id,@RequestParam("schoolCode")String schoolCode,@RequestParam("cardNumber") String  cardNumber){
        try {
            return fenceAlarmControllerClient.removeFenceAlarmInfo(id,schoolCode,cardNumber);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 批量删除
     * @param ids
     * @param schoolCodes
     * @param cardNumbers
     */
    @ApiOperation("批量删除围栏警报接口")
    @RequestMapping(value="/batchRemoveFenceAlarmInfo",method = RequestMethod.POST)
    public Object batchRemoveFenceAlarmInfo(@RequestParam("ids")String ids,@RequestParam("schoolCodes")String schoolCodes,@RequestParam("cardNumbers")String  cardNumbers){
        try {
            return fenceAlarmControllerClient.batchRemoveFenceAlarmInfo(ids,schoolCodes,cardNumbers);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 修改围栏警报接口
     * @param updateFenceAlarmDto
     */
    @ApiOperation("修改围栏警报接口")
    @RequestMapping(value="/updateFenceAlarmInfo",method = RequestMethod.POST)
    public Object updateFenceAlarmInfo(@Valid @RequestBody UpdateFenceAlarmDto updateFenceAlarmDto, BindingResult bindingResult){
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            return  fenceAlarmControllerClient.updateFenceAlarmInfo(updateFenceAlarmDto);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }

    /**
     * 新增围栏警报接口
     * @param addFenceAlarmDto
     */
    @ApiOperation("新增围栏警报接口")
    @RequestMapping(value="/insertFenceAlarmInfo",method = RequestMethod.POST)
    public Object insertFenceAlarmInfo(@Valid @RequestBody AddFenceAlarmDto addFenceAlarmDto, BindingResult bindingResult){
        try {
            //检验参数
            if (bindingResult.hasErrors()) {
                String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
                return WrapMapper.error(errors);
            }
            return fenceAlarmControllerClient.insertFenceAlarmInfo(addFenceAlarmDto);
        }catch (Exception e){
            e.printStackTrace();
            return WrapMapper.error(e.getMessage());
        }
    }
}