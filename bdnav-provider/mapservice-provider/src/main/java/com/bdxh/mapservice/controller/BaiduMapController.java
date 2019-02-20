package com.bdxh.mapservice.controller;

import com.bdxh.common.base.constant.MapConstrants;
import com.bdxh.common.utils.BeanToMapUtil;
import com.bdxh.common.utils.HttpClientUtils;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.mapservice.dto.AddFenceDto;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 百度鹰眼轨迹
 */
@Controller
@RequestMapping("/baiduMap")
@Slf4j
public class BaiduMapController {

   private static final String PROCESS_OPTION = "need_denoise=1,need_mapmatch=1,radius_threshold=0,transport_mode=auto";

   @Autowired
   private RestTemplate restTemplate;

   /**
    * 查询实时位置点
    */
    @RequestMapping(value = "/getLatestPoint",method = RequestMethod.GET)
    @ResponseBody
   public Object getLatestPoint(@RequestParam("entityName") String entityName) {
       try {
          Preconditions.checkArgument(StringUtils.isNotEmpty(entityName), "监控名称不能为空");
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append(MapConstrants.BaiDuMap.getLatestPointURL).append("?");
          stringBuilder.append("ak=").append(MapConstrants.BaiDuMap.AK).append("&service_id=")
                  .append(MapConstrants.BaiDuMap.SERVICE_ID).append("&mcode=").append(MapConstrants.BaiDuMap.MCODE);
          stringBuilder.append("&entity_name=").append(entityName).append("&process_option=").append(PROCESS_OPTION);
          String url = stringBuilder.toString();
          String result=restTemplate.getForObject(url,String.class);
          return WrapMapper.ok(result);
       } catch (Exception e) {
          return WrapMapper.error(e.getMessage());
       }
    }

   /**
    * 轨迹路程查询
    */
   @RequestMapping("/getTrack")
   @ResponseBody
   public Object getTrack(@RequestParam("entityName") String entityName,@RequestParam("startTime") String startTime, @RequestParam("endTime") String endTime) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(entityName), "监控名称不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(startTime), "起始时间不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(endTime), "结束时间不能为空");
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append(MapConstrants.BaiDuMap.getTrackURL).append("?");
         stringBuilder.append("ak=").append(MapConstrants.BaiDuMap.AK).append("&service_id=")
                 .append(MapConstrants.BaiDuMap.SERVICE_ID).append("&mcode=").append(MapConstrants.BaiDuMap.MCODE);
         stringBuilder.append("&entity_name=").append(entityName).append("&start_time=").append(startTime).append("&end_time=").append(endTime)
                 .append("&is_processed=1").append("&process_option=").append(PROCESS_OPTION).append("&supplement_mode=").append("walking");
         String url = stringBuilder.toString();
         String result=restTemplate.getForObject(url,String.class);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }

   }



   /**
    * 创建圆形围栏
    */
   @RequestMapping(value = "/addFence", method = RequestMethod.POST)
   @ResponseBody
   public Object addFence(@Valid AddFenceDto addFenceDto,BindingResult bindingResult) {
      //检验参数
      if(bindingResult.hasErrors()){
         String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
         return WrapMapper.error(errors);
      }
      try {
         Map<String, Object> param = BeanToMapUtil.objectToMap(addFenceDto);
         param.put("ak",MapConstrants.BaiDuMap.AK);
         param.put("service_id",MapConstrants.BaiDuMap.SERVICE_ID);
         param.put("mcode",MapConstrants.BaiDuMap.MCODE);
         String result= HttpClientUtils.doPost(MapConstrants.BaiDuMap.createcirclefenceURL, param);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }


   /**
    * 删除圆形围栏或监控对象
    */
   @RequestMapping(value = "/delFence", method = RequestMethod.POST)
   @ResponseBody
   public Object delFence(@RequestParam("fenceId") String fenceId, @RequestParam(value = "monitoredPerson",required = false) String monitoredPerson) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(monitoredPerson), "监控对象不能为空");
         Map<String,Object> param = new HashMap<>();
         param.put("ak",MapConstrants.BaiDuMap.AK);
         param.put("service_id",MapConstrants.BaiDuMap.SERVICE_ID);
         param.put("mcode",MapConstrants.BaiDuMap.MCODE);
         param.put("monitored_person",monitoredPerson);
         param.put("fence_ids",fenceId);
         String result=HttpClientUtils.doPost(MapConstrants.BaiDuMap.delCreatecirclefenceURL,param);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }


   /**
    * 查询围栏的状态(内外)
    */
   @RequestMapping(value = "/queryStatus", method = RequestMethod.GET)
   @ResponseBody
   public Object queryStatus(@RequestParam("fenceId") String fenceId, @RequestParam("monitoredPerson") String monitoredPerson) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(fenceId), "围栏ID不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(monitoredPerson), "监控对象不能为空");
         StringBuilder stringBuilder = new StringBuilder();
         stringBuilder.append(MapConstrants.BaiDuMap.queryStatusURl).append("?");
         stringBuilder.append("ak=").append(MapConstrants.BaiDuMap.AK).append("&service_id=")
                 .append(MapConstrants.BaiDuMap.SERVICE_ID).append("&mcode=").append(MapConstrants.BaiDuMap.MCODE);
         stringBuilder.append("&fence_ids=").append(fenceId).append("&monitored_person=").append(monitoredPerson);
         String url = stringBuilder.toString();
         String result=restTemplate.getForObject(url,String.class);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }

   /**
    * 增加围栏监控对象
    */
   @RequestMapping("/addMonitoredPerson")
   @ResponseBody
   public Object addMonitoredPerson(@RequestParam("fenceId") String fenceId, @RequestParam("monitoredPerson") String monitoredPerson) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(monitoredPerson), "监控对象不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(fenceId), "围栏ID不能为空");
         Map<String,Object> param = new HashMap<>();
         param.put("ak",MapConstrants.BaiDuMap.AK);
         param.put("service_id",MapConstrants.BaiDuMap.SERVICE_ID);
         param.put("mcode",MapConstrants.BaiDuMap.MCODE);
         param.put("monitored_person",monitoredPerson);
         param.put("fence_id",fenceId);
         String result=HttpClientUtils.doPost(MapConstrants.BaiDuMap.addMonitoredPersonURL,param);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }


}
