package com.bdxh.applecontrol.utils;//import com.notnoop.apns.APNS;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import org.apache.commons.codec.binary.Base64;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import sun.misc.BASE64Decoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: PACKAGE_NAME
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/3/16 11:03.
 */
public class mdmUtil {
    /**定义pList中的相关key常量**/
    public static final String MessageType = "MessageType";
    public static final String Topic = "Topic";
    public static final String UDID = "UDID";
    public static final String PushMagic = "PushMagic";
    public static final String UnlockToken = "UnlockToken";
    public static final String Token = "Token";
    public static final String Identifier = "Identifier";

    /**定义checkIn两种请求路径**/
    public static final String Authenticate = "Authenticate";
    public static final String TokenUpdate = "TokenUpdate";
    public static final String CheckOut = "CheckOut";
    public static final String Repay = "Repay";

    /**定义pList字符串解析正则式**/
    public static final String DATA = "\\<data>(.*?)\\</data>";
    public static final String STRING = "\\<string>(.*?)\\</string>";
    public static final String KEY = "\\<key>(.*?)\\</key>";
    public static final String DICT = "\\<dict>(.*?)\\</dict>";
    public static final String ARRAY = "\\<array>(.*?)\\</array>";

    /**定义命令类型**/
    public static final String Lock = "DeviceLock";
    public static final String Erase = "EraseDevice";
    public static final String Info = "DeviceInformation";
    public static final String Apps = "InstalledApplicationList";
    public static final String Clear = "ClearPasscode";
    public static final String Online = "Online";
    public static final String Install = "InstallApplication";
    public static final String Remove = "RemoveApplication";
    public static final String ProfileList = "ProfileList";
    public static final String ProvisioningProfileList = "ProvisioningProfileList";
    public static final String CertificateList = "CertificateList";

    /**MDM请求服务器端状态**/
    public static final String Idle = "Idle";
    public static final String Acknowledged = "Acknowledged";
    public static final String CommandFormatError = "CommandFormatError";
    public static final String Error = "Error";
    public static final String NotNow = "NotNow";
    public static final String QueryResponses = "QueryResponses";
    public static final String InstalledApplicationList = "InstalledApplicationList";

    public static String parseToken(String OriToken) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decodedBytes = decoder.decodeBuffer(OriToken);
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < decodedBytes.length; ++i) {
            buf.append(String.format("%02x", decodedBytes[i]));
        }
        String Token = buf.toString();
        return  Token;
    }

    public void sendMsgToAPNS () throws IOException {
        //唤醒apns服务
///     暂时写死发送通知需要的参数
//      p12文件的本地路径
        String certPath = "/usr/local/tomcats/tomcat13/webapps/mdm/file/MDM_Certificate.p12";
        //p12文件的密码
        String certPassword = "123456";
        String iPhone_SixPushMagic = "75D97916-678C-4772-A45D-070F5CACA68C";
//      i6真机安装描述文件时上传的设备token  (每台机器唯一，但是刷机或者重装系统后会改变。)
        String iPhone_SixToken =  "oRf4+p8tM/skfc8p7UeeZCTHyV7GzV0/Jvfu82hSUjY=";
        //使用方法转换一开始上传上来的token Fix:token位数错误的问题
        String token = parseToken(iPhone_SixToken);
        ApnsService service = APNS.newService().withCert(certPath,certPassword).withProductionDestination().build();
        String payload = APNS.newPayload().customField("mdm",iPhone_SixPushMagic).build();
        //发送通知到APNS
        service.push(token,payload);
    }

    /**
     * 获取Authenticate的pList文件Map数据
     * @param pList
     * @return
     */
    public static Map<String, String> parseAuthenticate(String pList){
        pList = pList.replace("<array/>","<array></array>").
                replaceAll("<true/>", "<string>true</string>").
                replaceAll("<false/>", "<string>false</string>");
        String strBlank = replaceBlank(pList);
        Map<String, String> plistMap = new HashMap<String, String>();
        /**获取key、string列表数据**/
        List<String> keyList = getList(KEY,strBlank);
        List<String> stringList = getList(STRING,strBlank);
        /**组装数据称plistMap**/
        for(int i=0;i<stringList.size();i++){
            plistMap.put(keyList.get(i), stringList.get(i));
        }
        return plistMap;
    }

    /**
     * 空的pList格式的文件（用户checkIn认证时候的返回）
     * @return
     */
    public static String getBlankPList(){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();
    }

    /**
     * 获取TokenUpdate的pList文件Map数据
     * @param pList
     * @return
     */
    public static Map<String, String> parseTokenUpdate(String pList) {
        pList = pList.replace("<array/>","<array></array>").
                replaceAll("<true/>", "<string>true</string>").
                replaceAll("<false/>", "<string>false</string>");
        String strBlank  = replaceBlank(pList);
        Map<String, String> plistMap = new HashMap<String, String>();
        /**获取key、string、data列表数据**/
        List<String> keyList = getList(KEY,strBlank);
        List<String> stringList = getList(STRING,strBlank);
        List<String> dataList = getList(DATA,strBlank);
        /**组装数据称plistMap**/
        int stringNum = 0;
        for(int i=0;i<keyList.size();i++){
            if(keyList.get(i).equals(Token)){
                plistMap.put(Token, dataList.get(0));
            }else if(keyList.get(i).equals(UnlockToken)){
                plistMap.put(UnlockToken, dataList.get(1));
            }else{
                plistMap.put(keyList.get(i), stringList.get(stringNum));stringNum++;
            }
        }
        return plistMap;
    }

    /**
     * 获取Information的pList文件Map数据
     * @param pList
     * @return
     */
    public static String parseUnlockToken(String pList){
        /**组装查询结果中重要的数据（一）**/
        int dataStart = pList.lastIndexOf("<data>")+6;
        int dataEnd = pList.lastIndexOf("</data>");
        String strBlank =  pList.substring(dataStart,dataEnd);
        return strBlank;
    }

    /**
     *
     *TODO 使用Base64加密算法加密字符串
     *return
     */
    public static String encodeStr(String plainText){
        byte[] b=plainText.getBytes();
        Base64 base64=new Base64();
        b=base64.encode(b);
        String s=new String(b);
        return s;
    }

    //解密base64
    public static String decodeStr(String encodeStr){
        byte[] b=encodeStr.getBytes();
        Base64 base64=new Base64();
        b=base64.decode(b);
        String s=new String(b);
        return s;
    }

    //    转换xml为字符串
    public static String getXmlString (String fileUrl){
        SAXReader saxReader=new SAXReader();
        Document document;
        String xmlString="";
        try {
            document = saxReader.read(new File(fileUrl));
            xmlString=document.asXML();//将xml内容转化为字符串
        } catch (Exception e) {
            e.printStackTrace();
            xmlString="";
        }
        return xmlString;
    }

    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 字符串转化成为2进制字符串
     * @param str
     * @return
     */
    public static String strTo2(String str){
        char[] strChar=str.toCharArray();
        String result="";
        for(int i=0;i<strChar.length;i++){
            result +=Integer.toBinaryString(strChar[i])+ " ";
        }
        return result;
    }

//    /**
//     * 把String的转换成base64码
//     */
//    public static String stringToBase64(String ss) {
//        byte[] bytes = ss.getBytes();
//        String encode = Base64Util.encode(bytes);
//        return encode;
//    }

    /**
     * 发送安装APP的pList格式的模板文件
     * @return
     */
    public static String getInstallApplication(String command,String commandUUID,String ctype,String cvalue){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        backString.append("<plist version=\"1.0\">\n");
        backString.append("<dict>\n");
        backString.append("<key>Command</key>\n" );
        backString.append("<dict>\n");
        backString.append("<key>RequestType</key>\n");
        backString.append("<string>"+command+"</string>\n");
        backString.append("<key>"+ctype+"</key>\n");
        if(ctype.equals("iTunesStoreID")){
            backString.append("<integer>"+cvalue+"</integer>\n");
        }else{
            backString.append("<string>"+cvalue+"</string>\n");
        }
        backString.append("<key>ManagementFlags</key>\n");
        backString.append("<integer>4</integer>\n");
        backString.append("</dict>\n");
        backString.append("<key>CommandUUID</key>\n");
        backString.append("<string>"+commandUUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("</plist>");
        return backString.toString();
    }

//    //删除应用程序
//    public static String prepareRemoveAppPlist() {
//
//        StringBuffer backString = new StringBuffer();
//        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
//        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
//        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
//        backString.append("<plist version=\"1.0\">\n");
//        backString.append("<dict>\n");
//        backString.append("<key>Command</key>\n" );
//        backString.append("<dict>\n");
//        backString.append("<key>RequestType</key>\n");
//        backString.append("<string>RemoveApplication</string>\n");
//        backString.append("<key>Identifier\n</key>");
//        backString.append("<string>com.youdao.note.iphone</string>");
//        backString.append("</dict>\n");
//        backString.append("<key>CommandUUID</key>\n");
//        backString.append("<string>07a6c20e-5e35-4f79-8680-10dee8460098</string>\n");
//        backString.append("</dict>\n");
//        backString.append("</plist>");
//        return backString.toString();
//    }
//    //设备锁屏
//    public static String prepareDeviceLockPlist() {
//
//        StringBuffer backString = new StringBuffer();
//        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
//        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
//        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
//        backString.append("<plist version=\"1.0\"><dict><key>Command</key><dict><key>RequestType</key><string>");
//        backString.append("DeviceLock");
//        backString.append("</string><key>Message</key><string>");
//        backString.append("byebye");
//        backString.append("</string><key>PhoneNumber</key><string>");
//        backString.append("15603019583");
//        backString.append("</string></dict><key>CommandUUID</key><string>");
//        backString.append("07a6c20e-5e35-4f79-8680-10dee8460099");
//        backString.append("</string></dict></plist>");
//        return backString.toString();
//    }

    //设备锁屏
    public static String prepareDeviceLockPlist(String Command,String Message,String PhoneNumber,String CommandUUID) {

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\"><dict><key>Command</key><dict><key>RequestType</key><string>");
        backString.append(Command);
        backString.append("</string><key>Message</key><string>");
        backString.append(Message);
        backString.append("</string><key>PhoneNumber</key><string>");
        backString.append(PhoneNumber);
        backString.append("</string></dict><key>CommandUUID</key><string>");
        backString.append(CommandUUID);
        backString.append("</string></dict></plist>");
        return backString.toString();
    }
    //查询设备已经安装的app列表
    public static String prepareQueryInstalledAppPlist(String RequestType,String Identifiers,String CommandUUID) {

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        backString.append("<plist version=\"1.0\">\n");
        backString.append("<dict>\n");
        backString.append("<key>Command</key>\n" );
        backString.append("<dict>\n");
        backString.append("<key>RequestType</key>\n");
        backString.append("<string>"+RequestType+"</string>\n");
        backString.append("<key>Identifiers\n</key>");
        backString.append("<array>\n");
        backString.append("<string>"+Identifiers+"</string>");
        backString.append("</array>\n");
        backString.append("</dict>\n");
        backString.append("<key>CommandUUID</key>\n");
        backString.append("<string>"+CommandUUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("</plist>");
        return backString.toString();
    }
    //开启丢失模式
    public static String prepareLostModePlist(String Command,String Message,String CommandUUID){

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("<key>Command</key>");
        backString.append("<dict>");
        backString.append("<key>RequestType</key>");
        backString.append("<string>"+Command+"</string>");    //EnableLostMode
        backString.append("<key>Message</key>");
        backString.append("<string>");
        backString.append(Message);             //把我的手机还给我
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("<key>CommandUUID</key>");
        backString.append("<string>");
        backString.append(CommandUUID);
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();
    }
    //发出警报声
    public static String prepareWarningVoicePlist () {


        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("<key>Command</key>");
        backString.append("<dict>");
        backString.append("<key>RequestType</key>");
        backString.append("<string>");
        backString.append("DeviceLocation");
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("<key>CommandUUID</key>");
        backString.append("<string>");
        backString.append("07a6c20e-5e35-4f79-8680-10dee8460099");
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();

    }

    //抹除设备数据
    public static String prepareErasePlist () {

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("<key>Command</key>");
        backString.append("<dict>");
        backString.append("<key>RequestType</key>");
        backString.append("<string>");
        backString.append("EraseDevice");
        backString.append("</string>");
        backString.append("<key>PIN</key>");
        backString.append("<string>");
        backString.append("Wwhosyour123");
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("<key>CommandUUID</key>");
        backString.append("<string>");
        backString.append("07a6c20e-5e35-4f79-8680-10dee8460099");
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();

    }

    //查询相关信息
    public static String prepareQueryPlist (String Command,String CommandUUID) {

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("<key>Command</key>");
        backString.append("<dict>");
        backString.append("<key>RequestType</key>");
        backString.append("<string>");
        backString.append(Command);      //DeviceInformation
        backString.append("</string>");
        backString.append("<key>Queries</key>");
        backString.append("<array>");
        backString.append("<string>ModelName</string>");
        backString.append("<string>Model</string>");
        backString.append("<string>BatteryLevel</string>");
        backString.append("<string>DeviceCapacity</string>");
        backString.append("<string>AvailableDeviceCapacity</string>");
        backString.append("<string>OSVersion</string>");
        backString.append("<string>SerialNumber</string>");
        backString.append("<string>IMEI</string>");
        backString.append("<string>ICCID</string>");
        backString.append("<string>MEID</string>");
        backString.append("<string>IsSupervised</string>");
        backString.append("<string>IsDeviceLocatorServiceEnabled</string>");
        backString.append("<string>IsActivationLockEnabled</string>");
        backString.append("<string>IsCloudBackupEnabled</string>");
        backString.append("<string>WiFiMAC</string>");
        backString.append("<string>BluetoothMAC</string>");

        backString.append("<string>Languages</string>");
        backString.append("<string>Locales</string>");
        backString.append("<string>DeviceID</string>");
        backString.append("<string>OrganizationInfo</string>");
        backString.append("<string>LastCloudBackupDate</string>");
        backString.append("<string>AwaitingConfiguration</string>");
        backString.append("<string>AutoSetupAdminAccounts</string>");

        backString.append("<string>DeviceName</string>");
        backString.append("<string>BuildVersion</string>");
        backString.append("<string>ProductName</string>");
        backString.append("<string>CellularTechnology</string>");
        backString.append("<string>ModemFirmwareVersion</string>");
        backString.append("<string>IsDoNotDisturbInEffect</string>");
        backString.append("<string>EASDeviceIdentifier</string>");
        backString.append("<string>OSUpdateSettings</string>");
        backString.append("<string>LocalHostName</string>");
        backString.append("<string>HostName</string>");
        backString.append("<string>SystemIntegrityProtectionEnabled</string>");
        backString.append("<string>ActiveManagedUsers</string>");
        backString.append("<string>IsMDMLostModeEnabled</string>");
        backString.append("<string>MaximumResidentUsers</string>");

        backString.append("<string>ICCID</string>");
        backString.append("<string>BluetoothMAC</string>");
        backString.append("<string>WiFiMAC</string>");
        backString.append("<string>EthernetMACs</string>");
        backString.append("<string>CurrentCarrierNetwork</string>");
        backString.append("<string>SIMCarrierNetwork</string>");
        backString.append("<string>SubscriberCarrierNetwork</string>");
        backString.append("<string>CarrierSettingsVersion</string>");
        backString.append("<string>PhoneNumber</string>");
        backString.append("<string>VoiceRoamingEnabled</string>");
        backString.append("<string>DataRoamingEnabled</string>");
        backString.append("<string>IsRoaming</string>");
        backString.append("<string>PersonalHotspotEnabled</string>");
        backString.append("<string>SubscriberMCC</string>");
        backString.append("<string>SubscriberMNC</string>");
        backString.append("<string>CurrentMCC</string>");
        backString.append("<string>CurrentMNC</string>");
        backString.append("<string>ServiceSubscriptions</string>");
        backString.append("<string>identifierForVendor</string>");
        backString.append("</array>");
        backString.append("</dict>");
        backString.append("<key>CommandUUID</key>");
        backString.append("<string>");
        backString.append(CommandUUID);
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();

    }


    /**
     * 发送移除APP的pList格式的模板文件
     * @return
     */
    public static String getRemoveApplication(String command,String commandUUID,String Identifier){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        backString.append("<plist version=\"1.0\">\n");
        backString.append("<dict>\n");
        backString.append("<key>Command</key>\n");
        backString.append("<dict>\n");
        backString.append("<key>RequestType</key>\n");
        backString.append("<string>"+command+"</string>\n");
        backString.append("<key>Identifier</key>\n");
        backString.append("<string>"+Identifier+"</string>\n");
        backString.append("</dict>\n");
        backString.append("<key>CommandUUID</key>\n");
        backString.append("<string>"+commandUUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("</plist>");


        return backString.toString();
    }

    /**
     * 发送关机重启命令模板
     * @return
     */

    public static String getShutRestartDevices(String command,String commandUUID){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("<key>Command</key>");
        backString.append("<dict>");
        backString.append("<key>RequestType</key>");
        backString.append("<string>");
        backString.append(command);
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("<key>CommandUUID</key>");
        backString.append("<string>");
        backString.append(commandUUID);
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();
    }



    /**
     * 发送安装配置文件命令
     * @return
     */

    public static String getInstallProfile(String command,String Profile,String commandUUID){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\"");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        backString.append("<plist version=\"1.0\">");
        backString.append("<dict>");
        backString.append("<key>Command</key>");
        backString.append("<dict>");
        backString.append("<key>RequestType</key>");
        backString.append("<string>");
        backString.append(command);
        backString.append("</string>");
        backString.append("<key>Payload</key>");
        backString.append("<data>"+Profile+"</data>");
        backString.append("</dict>");
        backString.append("<key>CommandUUID</key>");
        backString.append("<string>");
        backString.append(commandUUID);
        backString.append("</string>");
        backString.append("</dict>");
        backString.append("</plist>");
        return backString.toString();
    }



    /**
     * 发送移除APP的pList格式的模板文件
     * @return
     */
    public static String getRemoveProfile(String command,String Identifier,String commandUUID){
        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        backString.append("<plist version=\"1.0\">\n");
        backString.append("<dict>\n");
        backString.append("<key>Command</key>\n");
        backString.append("<dict>\n");
        backString.append("<key>RequestType</key>\n");
        backString.append("<string>"+command+"</string>\n");
        backString.append("<key>Identifier</key>\n");
        backString.append("<string>"+Identifier+"</string>\n");
        backString.append("</dict>\n");
        backString.append("<key>CommandUUID</key>\n");
        backString.append("<string>"+commandUUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("</plist>");
        return backString.toString();
    }


    //查询ProfileList
    public static String getProfileList(String command,String commandUUID) {

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        backString.append("<plist version=\"1.0\">\n");
        backString.append("<dict>\n");
        backString.append("<key>Command</key>\n");
        backString.append("<dict>\n");
        backString.append("<key>RequestType</key>\n");
        backString.append("<string>"+command+"</string>\n");
        backString.append("</dict>\n");
        backString.append("<key>CommandUUID</key>\n");
        backString.append("<string>"+commandUUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("</plist>");
        return backString.toString();
    }

    //查询ProfileList
    public static String RemoveProvisioningProfile(String command,String UUID,String commandUUID) {

        StringBuffer backString = new StringBuffer();
        backString.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        backString.append("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \n");
        backString.append("\"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n");
        backString.append("<plist version=\"1.0\">\n");
        backString.append("<dict>\n");
        backString.append("<key>Command</key>\n");
        backString.append("<dict>\n");
        backString.append("<key>RequestType</key>\n");
        backString.append("<string>"+command+"</string>\n");
        backString.append("<key>UUID</key>\n");
        backString.append("<string>"+UUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("<key>CommandUUID</key>\n");
        backString.append("<string>"+commandUUID+"</string>\n");
        backString.append("</dict>\n");
        backString.append("</plist>");
        return backString.toString();
    }


    /**
     * 将inputStream转化成为String
     * @param is
     * @return
     * @throws IOException
     */
    public static String inputStream2String(InputStream is) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        byte[] lens = baos.toByteArray();
        String result = new String(lens,"UTF-8");
        return result;
    }


    /**
     * 获取Information的pList文件Map数据
     * @param pList
     * @return
     */
    public static List parseInformation(String pList,String fields){
        /**组装查询结果中重要的数据（一）**/
        String strBlank = replaceBlank(pList);
        strBlank =  strBlank.replace("<key>QueryResponses</key><dict>","");
        strBlank =  strBlank.replace("</dict><key>Status</key>","<key>Status</key>");
        strBlank =  strBlank.replace("<real>","<string>");
        strBlank =  strBlank.replace("</real>","</string>");
        strBlank =  strBlank.replace("<true/>","<string>true</string>");
        strBlank =  strBlank.replace("<false/>","<string>false</string>");

        strBlank =  strBlank.replace("<integer>","<string>");
        strBlank =  strBlank.replace("</integer>","</string>");
        strBlank =  strBlank.replace("<key>InstalledApplicationList</key>","");

        /**获取key、string列表数据**/
        List<String> keyList = getList(KEY,strBlank);
        List<String> stringList = getList(STRING,strBlank);
        /**组装数据称plistList**/
        List valueNumber = new ArrayList();
        for(int i=0;i<keyList.size();i++){
            if (keyList.get(i).equals(fields)){
                valueNumber.add(stringList.get(i));
            }
        }
        return valueNumber;
    }


    /**
     * java去除字符串中的空格、回车、换行符、制表符
     * @param str
     * @return
     */
    public static String replaceBlank(String str) {
        String strBlank = "";
        if (str!=null) {
            str = str.replace("<false/>", "<string>false</string>");
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            strBlank = m.replaceAll("");
        }
        return strBlank;
    }



    /**
     * 获取字符串列表数据
     * @param pattern
     * @param pList
     * @return
     */
    public static List<String> getList(String pattern,String pList){
        /**获取data列表数据**/
        List<String> dataList = new ArrayList<String>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(pList);
        while(m.find()) {
            dataList.add(m.group(1));
        }
        return dataList;
    }

}
