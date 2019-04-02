package com.bdxh.applecontrol.controller;

import org.apache.log4j.PropertyConfigurator;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URLEncoder;

import static com.bdxh.applecontrol.utils.downLoadUtils.*;

/**
 * @ClassName: com.bdxh.applecontrol.controller
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/3/16 10:24.
 */

@Controller
public class DownLoadController {


    @RequestMapping("/down")
   public void downConfig(HttpServletRequest request, HttpServletResponse response) throws Exception{


       PropertyConfigurator.configure("classpath：log4j.properties");
//        PropertyConfigurator.configure("./src/log4j.properties");


       String uuid ="B02478DE-8AEE-4E67-95CF-8256D1123456";

       String Lt = getXmlStr(uuid);
       Document st = StringTOXml(Lt);
//        Writer out = new FileWriter("/usr/local/tomcats/tomcat13/webapps/mdm/file/"+uuid+".mobileconfig");
       Writer out = new FileWriter("/home/unsigned.mobileconfig");
       out.write(Lt);
       out.close();

       createMobileconfig(uuid);  //动态签名

       try {
           //处理请求
           //读取要下载的文件

           File f = new File("/home/"+uuid+".mobileconfig");

           if(f.exists()){
               FileInputStream fis = new FileInputStream(f);
               String filename=URLEncoder.encode(f.getName(),"utf-8"); //解决中文文件名下载后乱码的问题
               byte[] b = new byte[fis.available()];
               fis.read(b);
               response.setCharacterEncoding("utf-8");
               response.setHeader("Content-Disposition","attachment; filename="+filename+"");
               //获取响应报文输出流对象
               ServletOutputStream outs =response.getOutputStream();
               //输出
               outs.write(b);
               outs.flush();
               outs.close();
           }
       }catch (Exception e){
       };
   }


}
