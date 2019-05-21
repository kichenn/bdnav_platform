package com.bdxh.user.feign;

import com.bdxh.common.utils.wrapper.Wrapper;
import com.bdxh.user.dto.*;
import com.bdxh.user.entity.VisitLogs;
import com.bdxh.user.fallback.VisitLogsControllerFallback;
import com.bdxh.user.vo.VisitLogsVo;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @description:
 * @author: binzh
 * @create: 2019-04-19 16:11
 **/
@Service
@FeignClient(value = "user-provider-cluster",fallback = VisitLogsControllerFallback.class)
public interface VisitLogsControllerClient {

    /**
     * 查询所有
     * @param visitLogsQueryDto
     * @return
     */
    @RequestMapping(value = "/visitLogs/getVisitLogsInfos",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<VisitLogsVo>> getVisitLogsInfos(@RequestBody VisitLogsQueryDto visitLogsQueryDto);

    /**
     * 查询单个
     * @param schoolCode
     * @param cardNumber
     * @param id
     * @return
     */
    @RequestMapping(value="/visitLogs/getVisitLogsInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<VisitLogsVo> getVisitLogsInfo(@RequestParam(name="schoolCode")@NotNull(message = "schoolCode不能为空") String schoolCode,
                                          @RequestParam(name="cardNumber")@NotNull(message = "cardNumber不能为空")  String cardNumber,
                                          @RequestParam(name="id") @NotNull(message = "id不能为空")  String id);

    /**
     * 删除单个
     * @param id
     * @param schoolCode
     * @param cardNumber
     */
    @RequestMapping(value="/visitLogs/removeVisitLogsInfo",method = RequestMethod.GET)
    @ResponseBody
    Wrapper removeVisitLogsInfo(@RequestParam("id")String id,
                                 @RequestParam("schoolCode")String schoolCode,
                                 @RequestParam("cardNumber") String  cardNumber);

    /**
     * 批量删除
     * @param ids
     * @param schoolCodes
     * @param cardNumbers
     */
    @RequestMapping(value="/visitLogs/batchRemoveVisitLogsInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper batchRemoveVisitLogsInfo(@RequestParam("ids")String ids,@RequestParam("schoolCodes")String schoolCodes,@RequestParam("cardNumbers")String  cardNumbers);

    /**
     * 修改
     * @param updateVisitLogsDto
     */
    @RequestMapping(value="/visitLogs/updateVisitLogsInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper updateVisitLogsInfo( @RequestBody UpdateVisitLogsDto updateVisitLogsDto);

    /**
     * 新增
     * @param addVisitLogsDto
     */
    @RequestMapping(value="/visitLogs/insertVisitLogsInfo",method = RequestMethod.POST)
    @ResponseBody
    Wrapper insertVisitLogsInfo( @RequestBody AddVisitLogsDto addVisitLogsDto);


    @RequestMapping(value="/visitLogs/queryVisitLogByCardNumber",method = RequestMethod.POST)
    @ResponseBody
    Wrapper<PageInfo<VisitLogsVo>> queryVisitLogByCardNumber(@RequestParam(name="schoolCode") String schoolCode,
                                                       @RequestParam(name="cardNumber") String cardNumber);
}