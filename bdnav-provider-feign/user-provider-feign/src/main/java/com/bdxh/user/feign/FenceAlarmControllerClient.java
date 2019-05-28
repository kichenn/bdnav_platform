package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.AddFenceAlarmDto;
import com.bdxh.user.dto.FenceAlarmQueryDto;
import com.bdxh.user.dto.UpdateFenceAlarmDto;
import com.bdxh.user.fallback.FenceAlarmControllerFallback;
import com.bdxh.user.vo.FenceAlarmVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-18 15:02
 **/
@Service
@FeignClient(value = "user-provider-cluster",fallback = FenceAlarmControllerFallback.class)
public interface FenceAlarmControllerClient {
    /**
     * 查询所有
     * @param fenceAlarmQueryDto
     * @return
     */
    @RequestMapping(value = "/fenceAlarm/getAllFenceAlarmInfos",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<FenceAlarmVo>> getAllFenceAlarmInfos(@RequestBody FenceAlarmQueryDto fenceAlarmQueryDto);

    /**
     * 查询单个
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @RequestMapping(value="/fenceAlarm/getFenceAlarmInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<FenceAlarmVo> getFenceAlarmInfo(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
                                            @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
                                            @RequestParam(name="id") @NotNull(message = "id不能为空")  String id);

    /**
     * 查询单个
     * @param schoolCode
     * @param cardNumber
     * @param fenceId
     * @return
     */
    @RequestMapping(value="/fenceAlarm/getFenceAlarmInfos",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<List<FenceAlarmVo>> getFenceAlarmInfos(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
                                                   @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
                                                   @RequestParam(name="fenceId") @NotNull(message = "围栏Id不能为空")  String fenceId);

    /**
     * 删除单个
     * @param id
     * @param schoolCode
     * @param cardNumber
     */
    @RequestMapping(value="/fenceAlarm/removeFenceAlarmInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper removeFenceAlarmInfo(@RequestParam("id")String id,
                                 @RequestParam("schoolCode")String schoolCode,
                                 @RequestParam("cardNumber") String  cardNumber);

    /**
     * 批量删除
     * @param ids
     * @param schoolCodes
     * @param cardNumbers
     */
    @RequestMapping(value="/fenceAlarm/batchRemoveFenceAlarmInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchRemoveFenceAlarmInfo(@RequestParam("ids")String ids,@RequestParam("schoolCodes")String schoolCodes,@RequestParam("cardNumbers")String  cardNumbers);
    /**
     * 修改围栏警报接口
     * @param updateFenceAlarmDto
     */
    @RequestMapping(value="/fenceAlarm/updateFenceAlarmInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateFenceAlarmInfo( @RequestBody UpdateFenceAlarmDto updateFenceAlarmDto);

    /**
     * 新增围栏警报接口
     * @param addFenceAlarmDto
     */
    @RequestMapping(value="/fenceAlarm/insertFenceAlarmInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper insertFenceAlarmInfo( @RequestBody AddFenceAlarmDto addFenceAlarmDto);
}