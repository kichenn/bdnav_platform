package com.bdxh.applecontrol.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import static com.bdxh.applecontrol.utils.mdmUtil.*;

/**
 * @ClassName: com.bdxh.applecontrol.controller
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/3/16 10:23.
 */

@Controller
@Slf4j
@RequestMapping("/mdm")
public class CheckinController {
  @RequestMapping(value="/checkin",method=RequestMethod.PUT)
  public void checkIn(HttpServletRequest req, HttpServletResponse res) throws Exception{

    //apns服务器 连接 返回空 获取uuid等数据
    //日志手动加载配置    1

    res.setContentType("text/html;charset=UTF-8");
    String deviceId = req.getParameter("deviceId")==null?"":req.getParameter("deviceId");
    String info = inputStream2String(req.getInputStream());
    /**认证方法调用、Device回传Token方法调用**/
    if (info.toString().contains(Authenticate)) {
      Map<String, String> plistMap = parseAuthenticate(info.toString());
      String MessageType = plistMap.get("MessageType");
      String Topic = plistMap.get("Topic");
      String UDID = plistMap.get("UDID");
      log.info(MessageType);
//      logCheck.info("状态"+"Authenticate");
//      logCheck.info(Topic+"Topic");
//      logCheck.info(UDID+"UDID");
      /**返回一个空的pList格式的文件**/
      String blankPList = getBlankPList();
      res.setHeader("content-type", "application/xml;charset=UTF-8");
      res.setCharacterEncoding("UTF-8");
      String configTitle = "MDMApp_EraseDevice";
      res.setHeader("Content-Disposition", "attachment; filename=" + configTitle + ".plist");
      PrintWriter sos = res.getWriter();
      System.out.println("返回空文件");
      sos.write(blankPList);
      sos.flush();
      sos.close();
    }else if (info.toString().contains(TokenUpdate)) {
      Map<String, String> plistMap = parseTokenUpdate(info.toString());
      String UnlockToken = parseUnlockToken(info.toString());
      String UDID = plistMap.get("UDID");
      String Topic = plistMap.get("Topic");
      String OriToken = plistMap.get("Token");
      String PushMagic = plistMap.get("PushMagic");
//      logCheck.info("状态"+"TokenUpdate");
//      logCheck.info(UDID+"UDID");
//      logCheck.info(Topic+"Topic");
//      logCheck.info(OriToken+"OriToken");
//      logCheck.info(PushMagic+"PushMagic");
      res.setContentType("text/plain;charset=UTF-8");
      res.setCharacterEncoding("UTF-8");
      res.setHeader("Cache-Control", "no-cache");
      PrintWriter out;
      try {
        out = res.getWriter();
        out.print("");
      } catch (IOException e) {
        e.printStackTrace();
      }
    };
  }
}
