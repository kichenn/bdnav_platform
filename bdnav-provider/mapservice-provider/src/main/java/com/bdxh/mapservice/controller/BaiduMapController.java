package com.bdxh.mapservice.controller;

import com.bdxh.BDMap.dto.AddFenceDto;
import com.bdxh.common.base.constant.BaiduConstrants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.bdxh.mapservice.configration.common.HttpClientConfig;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;


/**
 * 百度鹰眼轨迹
 */
@Controller
@Slf4j
@RequestMapping("/BaiduMap")
public class BaiduMapController {

   @Autowired
   private RestTemplate restTemplate;

   private String addKeyParam(String queryString) {
      queryString += "&ak=" + BaiduConstrants.BaiDu.AK;
      queryString += "&service_id=" + BaiduConstrants.BaiDu.SERVICE_ID;
      queryString += "&mcode=" + BaiduConstrants.BaiDu.MCODE;
      return queryString;
   }

   /**
    * 查询实时位置点
    */
    @RequestMapping("/getLatestPoint")
    @ResponseBody
   public Object getLatestPoint(@RequestParam("entityName") String entityName, HttpServletRequest request) {
       try {
          Preconditions.checkArgument(StringUtils.isNotEmpty(entityName), "监控名称不能为空");
          String queryString = addKeyParam(request.getQueryString());
          String process_option="need_denoise=1,need_mapmatch=1,radius_threshold=0,transport_mode=auto";
          String url=BaiduConstrants.BaiDu.getLatestPointURL+"?"+queryString+"&entity_name="+entityName+"&process_option="+process_option;
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
   public Object getTrack(@RequestParam("entityName") String entityName,@RequestParam("startTime") String startTime,
                          @RequestParam("endTime") String endTime,
                          HttpServletRequest request) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(entityName), "监控名称不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(startTime), "起始时间不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(endTime), "结束时间不能为空");
         String queryString = addKeyParam(request.getQueryString());
         String supplement_mode= "walking";
         String process_option="need_denoise=1,need_mapmatch=1,radius_threshold=0,transport_mode=auto";
         String url=BaiduConstrants.BaiDu.getTrackURL+"?"+queryString+"&entity_name="+entityName+"&start_time="+startTime+"&end_time="+endTime
                 +"&is_processed="+1+"&process_option="+process_option+"&supplement_mode="+supplement_mode;
         String result=restTemplate.getForObject(url,String.class);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }

   }



   /**
    * 创建圆形围栏
    */
   @RequestMapping("/addFence")
   @ResponseBody
   public Object createcirclefence(@Valid AddFenceDto addFenceDto,BindingResult bindingResult,HttpServletRequest request) {
      //检验参数
      if(bindingResult.hasErrors()){
         String errors = bindingResult.getFieldErrors().stream().map(u -> u.getDefaultMessage()).collect(Collectors.joining(","));
         return WrapMapper.error(errors);
      }
      try {
         String url=BaiduConstrants.BaiDu.createcirclefenceURL;
         String longitude =String.valueOf(addFenceDto.getLongitude());
         String latitude =String.valueOf(addFenceDto.getLatitude());
         String radius =String.valueOf(addFenceDto.getRadius());
         String queryString = addKeyParam(request.getQueryString());
         String result=HttpClientConfig.sendPost(url,queryString+"&monitored_person="+addFenceDto.getMonitored_person()+"&longitude="+longitude+"&latitude="+latitude+"&radius="+radius+"&coord_type="+addFenceDto.getCoord_type());
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }


   /**
    * 删除圆形围栏
    */
   @RequestMapping("/delFence")
   @ResponseBody
   public Object delFence(@RequestParam("fenceIds") String fenceIds,
                          @RequestParam("monitoredPerson") String monitoredPerson, HttpServletRequest request) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(monitoredPerson), "监控对象不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(fenceIds), "围栏ID不能为空");
         String queryString = addKeyParam(request.getQueryString());
         String url=BaiduConstrants.BaiDu.delCreatecirclefenceURL;
         String result=HttpClientConfig.sendPost(url,queryString+ "&monitored_person=" + monitoredPerson +"&fence_ids="+fenceIds);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }


   /**
    * 查询围栏的状态(内外)
    */
   @RequestMapping("/queryStatus")
   @ResponseBody
   public Object queryStatus(@RequestParam("monitoredPerson") String monitoredPerson,
           @RequestParam("fenceIds") String fenceIds,HttpServletRequest request) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(monitoredPerson), "监控对象不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(fenceIds), "围栏ID不能为空");
         String queryString = addKeyParam(request.getQueryString());
         String url=BaiduConstrants.BaiDu.queryStatusURl+"?"+queryString+"&monitored_person="+monitoredPerson+"&fence_ids="+fenceIds;
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
   public Object addMonitoredPerson(@RequestParam("monitoredPerson") String monitoredPerson,
                             @RequestParam("fenceIds") String fenceIds,HttpServletRequest request) {
      try {
         Preconditions.checkArgument(StringUtils.isNotEmpty(monitoredPerson), "监控对象不能为空");
         Preconditions.checkArgument(StringUtils.isNotEmpty(fenceIds), "围栏ID不能为空");
         String queryString = addKeyParam(request.getQueryString());
         String url=BaiduConstrants.BaiDu.addMonitoredPersonURL;
         String result=HttpClientConfig.sendPost(url,queryString+ "&monitored_person=" + monitoredPerson +"&fence_ids="+fenceIds);
         return WrapMapper.ok(result);
      } catch (Exception e) {
         return WrapMapper.error(e.getMessage());
      }
   }


}
