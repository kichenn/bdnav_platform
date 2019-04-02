package com.bdxh.applecontrol.controller;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

import static com.bdxh.applecontrol.utils.mdmUtil.*;

/**
 * @ClassName: com.bdxh.applecontrol.controller
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/3/16 10:24.
 */
@Controller
@RequestMapping("/mdm")
public class mdmController {

    @RequestMapping(value="/mdmserverUrl",method=RequestMethod.PUT)
    public void mdmserverUrl(HttpServletRequest request, HttpServletResponse response) throws Exception{

//      日志手动加载配置  2 ios设备主动连接 返回自身状态 发送操作指令
        PropertyConfigurator.configure("classpath：log4j.properties");

        ServletInputStream iosmdm = request.getInputStream();
        String info = inputStream2String(iosmdm);

        try {
            if (info.contains("Idle")){

                 //各类指令
                 //安装app
                String installApp = getInstallApplication("InstallApplication","07a6c20e-5e35-4f79-8680-10dee8460098","iTunesStoreID","766695512");   //1
                 //查询安装了的app
                String queryInstalledAppPlist = prepareQueryInstalledAppPlist("InstalledApplicationList","com.youdao.note.iphone","07a6c20e-5e35-4f79-8680-10dee846000");   //1
                 //抹除数据(慎用  结尾删了个字母t)
//               String eraseDevice = prepareErasePlis();
                 //查询设备数据
                String queryDevice = prepareQueryPlist("DeviceInformation","07a6c20e-5e35-4f79-8680-10dee8460098");
                 //开启丢失模式（）
                String openLostMode = prepareLostModePlist("EnableLostMode","把我的手机给我","07a6c20e-5e35-4f79-8680-10dee8460098");
                 //播放警报声（）
                String showWarningVoice = prepareWarningVoicePlist();
                 //设备锁屏
                String prepareDeviceLockPlist = prepareDeviceLockPlist("DeviceLock","byebye","15603019583","07a6c20e-5e35-4f79-8680-10dee8460099");     //1
                 //设备信息
                String prepareQueryPlist = prepareQueryPlist("DeviceInformation","07a6c20e-5e35-4f79-8680-10dee8460098");
                 //删除app
                String removeApp = getRemoveApplication("RemoveApplication","07a6c20e-5e35-4f79-8680-10dee8460098","com.ttplus.ios");  //1
                 //关机  重启     RestartDevice  ShutDownDevice    未完成
                String operationApp = getShutRestartDevices("RestartDevice", "07a6c20e-5e35-4f79-8680-10dee8460098");
                 //获取16进制xml字符串
                 //转换配置文件为base64格式模拟data数据
                String feXml = encodeStr(getXmlString("/usr/local/tomcats/tomcat13/webapps/mdm/file/config2.mobileconfig"));   //禁用app  合并配置文件
                 //发送安装配置文件
                String installProfile = getInstallProfile("InstallProfile",feXml,"07a6c20e-5e35-4f79-8680-10dee8460098");
                 //移出配置文件
                String removeProfile = getRemoveProfile("RemoveProfile","SXFdeiMac.575EACF3-74FA-467D-8B02-EAB803E7544C", "07a6c20e-5e35-4f79-8680-10dee8460098");
                 //ProfileList   查询ProfileList
                String ProfileList = getProfileList("ProfileList","07a6c20e-5e35-4f79-8680-10dee8460098");

//                 String ProvisioningProfileList = getProfileList("ProvisioningProfileList","07a6c20e-5e35-4f79-8680-10dee8460098");
                //移除RemoveProvisioningProfile
                 String RemoveProvisioningProfile = RemoveProvisioningProfile("RemoveProvisioningProfile","e6217a36-9a0e-452e-a149-ddcec40aaf63","07a6c20e-5e35-4f79-8680-10dee8460098");

                //ManagedApplicationFeedback   托管的Application反馈
                String ManagedApplicationFeedback = prepareQueryInstalledAppPlist("ManagedApplicationFeedback","com.ballpure.News","07a6c20e-5e35-4f79-8680-10dee8460098");
                //被管理的app
                String ManagedApplicationList = prepareQueryInstalledAppPlist("ManagedApplicationList","com.ballpure.News","07a6c20e-5e35-4f79-8680-10dee8460098");

                response.setHeader("content-type", "application/xml;charset=UTF-8");
                response.setCharacterEncoding("UTF-8");
                String configTitle = "Mbaike_DeviceLock";
                response.setHeader("Content-Disposition", "attachment; filename=" + configTitle + ".plist");
                PrintWriter sos = response.getWriter();
//                    sos.write(prepareDeviceLockPlist);     // 锁屏
//                    sos.write(queryInstalledAppPlist);     //查询安装app
//                    sos.write(ManagedApplicationFeedback);     //ManagedApplicationFeedback
                   sos.write(ManagedApplicationList);     //ManagedApplicationList  被管理的app
//                    sos.write(installApp);                 //安装app
//                    sos.write(removeApp);                  //删除app
//                    sos.write(operationApp);               //关机 重启              刷机
//                    sos.write(openLostMode);               //丢失模式   服务器将设备置于MDM lost模式，并提供消息、电话号码和脚注
//                    sos.write(showWarningVoice);           //  警报
//                    sos.write(installProfile);             //  安装配置文件
//                    sos.write(removeProfile);              //  移出配置文件
//                    sos.write(prepareQueryPlist);          //  设备信息
//                    sos.write(ProfileList);                //  ProfileList
//                    sos.write(ProvisioningProfileList);              //  ProvisioningProfileList
//                    sos.write(RemoveProvisioningProfile);            //  RemoveProvisioningProfile

                   sos.flush();
                   sos.close();
            }
            else if (info.contains("InstalledApplicationList")){
//                        Map<String, String> plistMap = parseInformation(info);
//                        plistMap.get("InstalledApplicationList");
//                        plistMap.get("CommandUUID");
//                        plistMap.get("Status");
//                        plistMap.get("UDID");

                List plistList = parseInformation(info,"Name");
            }
            else {
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
