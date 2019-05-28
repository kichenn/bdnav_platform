package com.bdxh.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.bdxh.common.helper.baidu.yingyan.FenceUtils;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceConstant;
import com.bdxh.common.helper.baidu.yingyan.constant.FenceErrorEnums;
import com.bdxh.common.helper.baidu.yingyan.request.FindTrackRequest;
import com.bdxh.common.utils.wrapper.WrapMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @description:
 * @author: binzh
 * @create: 2019-05-24 09:49
 **/
@Slf4j
@RequestMapping(value = "/trajectory")
@RestController
@Api(value = "位置管理----鹰眼轨迹", tags = "位置管理----鹰眼轨迹")
@Validated
public class TrajectoryController {
    @RequestMapping(value = "/findTrajectoryInfo",method = RequestMethod.GET)
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的轨迹信息")
    public Object findTrajectoryInfo(@RequestParam("startTime")@NotNull(message = "开始时间不能为空")String startTime,
                                     @RequestParam("endTime")@NotNull(message = "结束时间不能为空")String endTime,
                                     @RequestParam("schoolCode")@NotNull(message = "学校Code不能为空")String schoolCode,
                                     @RequestParam("cardNumber")@NotNull(message = "学生卡号不能为空")String cardNumber){ FindTrackRequest findTrackRequest=new FindTrackRequest();
        findTrackRequest.setAk(FenceConstant.AK);
        findTrackRequest.setService_id(FenceConstant.SERVICE_ID);
        findTrackRequest.setEntity_name(schoolCode+cardNumber);
        findTrackRequest.setStart_time(startTime);
        findTrackRequest.setEnd_time(endTime);
        //打开轨迹纠偏，返回纠偏后轨迹
        findTrackRequest.setIs_processed(1);
        //使用最短步行路线距离补充
        findTrackRequest.setSupplement_mode("walking");
        findTrackRequest.setSort_type("desc");
        findTrackRequest.setPage_index(1);
        findTrackRequest.setPage_size(5000);
        String result=FenceUtils.getTrack(findTrackRequest);
        JSONObject jsonObject=JSONObject.parseObject(result);
        if(jsonObject.get("status").equals(0)){
        return WrapMapper.ok(jsonObject.getString("points"));
        }else{
            /*,状态码" + jsonObject.getInteger("status") + "*/
            return WrapMapper.error("查询轨迹失败，原因:" + jsonObject.getString("message"));
        }
    }


    /**
     * 家长端鹰眼轨迹------查询单个孩子的实时位置信息
     * @param schoolCode 学校Code     用来拼接ENTITY 查询entity的轨迹点
     * @param cardNumber 学生卡号
     * @return
     */
    @RequestMapping(value = "/findLatestPoint", method = RequestMethod.GET)
    @ApiOperation(value = "家长端鹰眼轨迹------查询单个孩子的实时位置信息")
    public Object findLatestPoint (@RequestParam("schoolCode") @NotNull(message = "学校Code不能为空") String schoolCode,
                                   @RequestParam("cardNumber") @NotNull(message = "学生卡号不能为空") String cardNumber) {
        String result=FenceUtils.getLatestPoint(schoolCode+cardNumber);
           System.out.println(result);
           JSONObject jsonObject=JSONObject.parseObject(result);
           if(jsonObject.get("status").equals(0)){
               return WrapMapper.ok(jsonObject.get("latest_point"));
           }else{
               return WrapMapper.error("查询实时位置失败，原因:" + jsonObject.getString("message"));
           }
    }
}