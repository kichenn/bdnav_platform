package com.bdxh.applecontrol.utils;//import org.dom4j.Document;
import org.dom4j.Document;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.MessageFormat;

/**
 * @ClassName: PACKAGE_NAME
 * @Description: 描述该类或者接口
 * @Company Autofly
 * @DateTime 2019/3/7 15:13.
 */
public class downLoadUtils {

    //签名
    public static void createMobileconfig(String uuid){
        try {

            //签名
            String oldPath="/home/unsigned.mobileconfig";
            String newPath="/home/"+uuid+".mobileconfig";
            String crtPath="/home/mbaike.crt";
            String keyPath="/home/mbaike.key";
            String pemPath="/home/ca-bundle.pem";

            String oldCmd = "openssl smime -sign -in {0} -out {1} -signer {2} -inkey {3} -certfile {4} -outform der -nodetach";
            String newCmd = MessageFormat.format(oldCmd,oldPath,newPath,crtPath,keyPath,pemPath);
            Runtime.getRuntime().exec(newCmd);
//                FileUtils.copyFile(oldPath, newPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取xml文件流对象
    public static InputStream fileToInput(String file) throws FileNotFoundException {
        InputStream in = new FileInputStream(file);
        return in;
    }

    //    InputStream --> String
    public static String inputToStr(InputStream is) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null){
            buffer.append(line);
        }
        return buffer.toString();
    }

//    //    转换xml为字符串
//    public static String getXmlString (String fileUrl){
//        SAXReader saxReader=new SAXReader();
//        Document document;
//        String xmlString="";
//        try {
//            document = saxReader.read(new File(fileUrl));
//            xmlString=document.asXML();//将xml内容转化为字符串
//        } catch (Exception e) {
//            e.printStackTrace();
//            xmlString="";
//        }
//        return xmlString;
//    }




    public static Document StringTOXml(String str) {

        StringBuilder sXML = new StringBuilder();
        sXML.append(str);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc = null;
        try {
            InputStream is = new ByteArrayInputStream(sXML.toString().getBytes("utf-8"));
            doc = (Document) dbf.newDocumentBuilder().parse(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }




    public static String getXmlStr(String uuid){
        String  str = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">\n" +
                "<plist version=\"1.0\">\n" +
                "<dict>\n" +
                "\t<key>ConsentText</key>\n" +
                "\t<dict>\n" +
                "\t\t<key>default</key>\n" +
                "\t\t<string>描述文件安装期间显示的简短信息</string>\n" +
                "\t</dict>\n" +
                "\t<key>PayloadContent</key>\n" +
                "\t<array>\n" +
                "\t\t<dict>\n" +
                "\t\t\t<key>PayloadCertificateFileName</key>\n" +
                "\t\t\t<string>*.bdxht.com</string>\n" +
                "\t\t\t<key>PayloadContent</key>\n" +
                "\t\t\t<data>\n" +
                "\t\t\tMIIM1gIBAzCCDJIGCSqGSIb3DQEHAaCCDIMEggx/MIIMezCCBgQG\n" +
                "\t\t\tCSqGSIb3DQEHAaCCBfUEggXxMIIF7TCCBekGCyqGSIb3DQEMCgEC\n" +
                "\t\t\toIIE9jCCBPIwHAYKKoZIhvcNAQwBAzAOBAgXfkuzLXVvGQICB9AE\n" +
                "\t\t\tggTQHxaTm89KwqduualKUGN9nxIWp+P0/Uw41/3anhG4YXYG+D4h\n" +
                "\t\t\t+DJVz9uDlEmm9ewjXLeIYEsY2HtTXgCp/cqihB4fayngpJf4nmSt\n" +
                "\t\t\tASgKA0SwuoYP8Dqah/POtknTM7grNszoJ/VtnqGKDYPI+O7xk9jb\n" +
                "\t\t\twOcVUkU24wQ7v/dmwWrkBfDd9rlrF0c/q1donqFud5rU5RryMw1b\n" +
                "\t\t\tiIZzuTCBjGjgt8z2VcxXVfTgoRpJ2BnC3IDmaClAbLCPbFAEKcEi\n" +
                "\t\t\tIgChjG2xz99QwajAFj1y0Qq75cHlgvmmbdufpt6cAXvdH5+JBFRL\n" +
                "\t\t\t9N3juRBwDTr3iJJJe5iviSu5785XsafYQtA6k2wJw5jK8ymKiPEH\n" +
                "\t\t\tscPYkvOmkdEZYSNRP7fsl53rozAeZT7PxOjPkeJkymKQoUn8HlzD\n" +
                "\t\t\tJJR3Q8OLzfnIChPFCD+HTBHlGWO3STSwneqvPcJWNdFWVkeHQNAh\n" +
                "\t\t\tQ2Oj8Ez/sMSaxUV86WEpjGBXvjmdoXYNiJErp1CHPdjyWbKSjJkE\n" +
                "\t\t\tIvxoSf5W8hdOWWPRo/0nIe/CAZeXBnOK1zBmjtwGoQnD+yz2/Kjb\n" +
                "\t\t\tuS+E+fIOwaWrbZpvCO6wWB4lPZGgpkj9BdxaBUGZx0EGF2oMCacZ\n" +
                "\t\t\tnw7FoirWEsstYy5W3XhnYtinWXxysGac7tcoJJO5JryEUXyCiFlE\n" +
                "\t\t\tmpQWJuqt+Ke+PKvBSKaO/OKzqINHg47KHDbhsaXSmqDJLaK4Q4on\n" +
                "\t\t\tp6BbMs4IsWMSRSiQL8IXOjzMYD1ZqkBDg2lA7EwARF0ZHarbU2t0\n" +
                "\t\t\t6p9SKoq6skoL3njCkerAkEFowxS6H22foApH0Uzsjy3YScfAAvyX\n" +
                "\t\t\tr0ue2KCRCi18Ql8Mth1cWunUiVZaRI0+deL8N1Rz01K00kLqklS9\n" +
                "\t\t\tU841Q/vdfhARTC5fpWr0xHVCy0At8KSjAbCspRfQFelEPrT84N7b\n" +
                "\t\t\tKtQ0Z4gdv5L+JwDP7asnk+hlt6MZnbmSiCVkFb7d4HugZ9bLKeTg\n" +
                "\t\t\tRXLzbBuTAolcN/d3DLkHM0PJusNetxiilEoB8wKCnyRyTAkRttaL\n" +
                "\t\t\t81yZ9ceHD5/f4AVKC0LeLH5Oryv52/ZjW/ZXJErK/IYst+VmqWOD\n" +
                "\t\t\t1gfyC8oYSHyoG9zs9vXdUcn9hHS7wQlXIGL9D/8HSKwtEoaKk0s9\n" +
                "\t\t\tS0ooem4xUtgmbS7A8869avT/DaUDOdCXxLSc8Q+UrPZDQ61VGf+r\n" +
                "\t\t\tmJUj7STL4gVGHe6o1jfNQfjtRLVAgDXD29UYnhEHtFEYrFZmS78k\n" +
                "\t\t\tcwzUgxo4m33vYDJpk5eA5pwhtwgjX9zPo1Je7y8osLwtClYMfqft\n" +
                "\t\t\t1bEE5njwfzZqyWyMPX6/OJLgBktRr1AQ8WfIMW0rLxYVVUTySp5j\n" +
                "\t\t\tagbo1vdcLZZ+7AiMhjP30ZubF3VccjIWiM1/dNOvq73hU6NKCRaO\n" +
                "\t\t\trcZplgZiL6e9GNAr1OyxcNa60SDwrjG5GxYHKPLiI4+x5GfszojR\n" +
                "\t\t\tbe/U+A3T15VOafoi/Gs0+i8QL4uLQd3OkUAFewrAPk0cRG+FNo05\n" +
                "\t\t\tNrnUDnbBWHFKeplNBJi7o52kdPp7p2/mzV3X0/4ls8iYlvqIqvuy\n" +
                "\t\t\t5gC3uA1hrT65oQ/NKQEE7R8zAC43EQzKdw/gRCK3q23ajH6JFD2r\n" +
                "\t\t\tPUtYL1PhepbpDZ86zWDrGpFzvk4PGQwCro0xgd8wEwYJKoZIhvcN\n" +
                "\t\t\tAQkVMQYEBAEAAAAwWwYJKoZIhvcNAQkUMU4eTAB7ADYAQgA2AEIA\n" +
                "\t\t\tRQAyAEYANQAtADAAQgA0ADIALQA0ADEAOQA4AC0AQQAyAEQANQAt\n" +
                "\t\t\tAEYANgBGAEUANwBBADcARQAxADQARABCAH0wawYJKwYBBAGCNxEB\n" +
                "\t\t\tMV4eXABNAGkAYwByAG8AcwBvAGYAdAAgAEUAbgBoAGEAbgBjAGUA\n" +
                "\t\t\tZAAgAEMAcgB5AHAAdABvAGcAcgBhAHAAaABpAGMAIABQAHIAbwB2\n" +
                "\t\t\tAGkAZABlAHIAIAB2ADEALgAwMIIGbwYJKoZIhvcNAQcGoIIGYDCC\n" +
                "\t\t\tBlwCAQAwggZVBgkqhkiG9w0BBwEwHAYKKoZIhvcNAQwBAzAOBAgt\n" +
                "\t\t\tXAptX/YhYAICB9CAggYo/1pQB+aqhLdywc6MOYh7OS5a+OgD+GsL\n" +
                "\t\t\twpPqIYH2hTia2dTVhrVlREY/IjPn1SHGkqVzrDNxUFG+5XVd1snM\n" +
                "\t\t\tTD61Lc8FYwn51LUECmngG4WF1vP9OYcQJ4qam7WcjbGaJIYl27Zr\n" +
                "\t\t\tF9RwHfKkW/Idr0JY17q1+054mNB/YfpUmvyBPN0KWcaLZzDN0wW/\n" +
                "\t\t\t74u43HwG+s01bnMx0ANktV18N+5WdWZmxG2ZqrhYTWWPtxS64sOi\n" +
                "\t\t\t4XWiqTU9zpsAlYRrP7NGhSmFWpRoezvZFPL5xUjWiAOI2mueNw7u\n" +
                "\t\t\tDNx/1eIjjrdpTOheHQ9MZIeLWtOe3DIGLAY1D+7WwpngFgsB1uOL\n" +
                "\t\t\trg8p03SCJmMnxdWX27JMAbg4Rh5pbo/RdNFhIfNP8BjUT1s/p0vS\n" +
                "\t\t\tUllFLAEcs94mVSXMZfn5aRaALu41tfgDvW5lec5IEgVl+pfWDGNX\n" +
                "\t\t\tzPWseBncaiG0O39fUZj87GO4ES/+O6L0/sjZkxQn6EbA+q8YQ/Cf\n" +
                "\t\t\tceUI/6SYtLpe/E51BmtVJ5f5fRn3KMIvTqqFZ/3Ah2tQ/LEWfk+4\n" +
                "\t\t\t3lBnyj0TteT62JGyK4iOTb8PVA9npcm+qGmmznqZL/DPTfvm4lNS\n" +
                "\t\t\tQhgMLvAAF6jYzUSUfreVXzEMHOUkBEJrjR8W6d2uMndZtH2d6rh9\n" +
                "\t\t\t3iklW8jkFK/TW4L1hlbTYiTUu3zVU2Wditn23g5t1aESq1I8vRAZ\n" +
                "\t\t\t607tPbdJABLCZV3IoZjc7f75laueNN6N5eWXYcuTmZhJmVjNPXPa\n" +
                "\t\t\tr7bDMUQqM5nd0dyD3X0iEot8dtXD5oqEthiKYBowLvEOw0dwPXNt\n" +
                "\t\t\tE6r5ytkzjqCmbwVA3cJ+I3r5VXZCb1btCkXjiMAGIAkjgeQ0dy98\n" +
                "\t\t\tRo+PUXNi98KJcUSGT79vXNJRbPZbNSSUCcX6qeIt3Ie8wio65zIy\n" +
                "\t\t\tB51Ct1/Czn+G9RR7Da8l7XE8F/BZZHcZY4uwr2/4sDxTs1sTzPF7\n" +
                "\t\t\tbzovSIh8ePqYfJnJOdxIcfWn+JtpH0+8LvKAVM6cozZ9wMJHsQSf\n" +
                "\t\t\twVoxQsq3PXoQOWtfpIB1Kq2yPRNFEmgrKxmDf4cDozmZ2rOlowwz\n" +
                "\t\t\tsuergeZ0bvy3yx50b/idI94Oc5kN6ug0xe9kX+8SEqOxIAqMRksh\n" +
                "\t\t\t6IfCyJM+NQjGKvJgrpwfM6PAI4pR3axse7ICS76ILYDb9Zxs5S2n\n" +
                "\t\t\ts9QEge+6leSYo/oKqcAPbIn6uTeDTNe0T2YRW/bdv+ag4m26X4vf\n" +
                "\t\t\t+YrYRvzDaKQWWEQhtFq2bAF6ltEgjHYDny3assJYarDZIhYYKWGM\n" +
                "\t\t\t32bWn/yNpZE2caKBOfqmj0FsAGlrbFfmiT7WuXaR8P9lg5HZ7mCx\n" +
                "\t\t\tq+rTnWfvqixfAZp6oJ6uIbY9r1sw4Qyz6GwztSiW0+e4wWgiYhy6\n" +
                "\t\t\tc6bo5KD9x3VFJU+oVeXzv18sL6ZblgkjACCAX8j4pT3dMXbM8Mbm\n" +
                "\t\t\tnseg84Cd86NsFH0SqjSpwyQQxwxiXfsxKmdALOvG6diTQ/jkgg2I\n" +
                "\t\t\tMfalFheeQ3pZ3+neiIfiO/CeyOpj1MJriwOolLHWAUTDG+VMPIOs\n" +
                "\t\t\tINA7D+fO5ubSumqqPBESXKcA0oLLqarEHPAk0Zk7StMYmtl32mii\n" +
                "\t\t\tyTTlqFf+fsAYSZTUY6Rz6M5azdFUPZZTv5Rp7DFN+5i08jNZXKht\n" +
                "\t\t\tXOeL+rUbN4pCg0y5LISTokhZcy8YgKwYnMnnxnclo0bZ4gqMVMwZ\n" +
                "\t\t\tu+zbeHcBKcjD93rX+Z3Q/CgpLu1N81dpuPFmbzqaR5JBCcsLAluO\n" +
                "\t\t\t3nl03tnaB4b6NnfCbfBt9Dh1t6gQr6d6TKWMOsxdGBEHSV9LswH9\n" +
                "\t\t\tQomkXzCoz1tg81kITr6oEVFfWlL3prX3q4iDfCgqvwuwSCUtZHdU\n" +
                "\t\t\tYNIr7LLNa8K9a4j8O4CpCe2bHf7ubWUuXOKJPaUdE5C4MbtgzFPF\n" +
                "\t\t\tK5WV3vrZxWEMPjmwuYADQqkxEylxv22lRqbLivZFOFW0PP2BPaix\n" +
                "\t\t\t+oP/7Ei2JtCr4ZiwaHpK2e3L3FMSUvGvZBPNH28EYlKzQ1JzlrOb\n" +
                "\t\t\tS1RRWxTOJNtlvzB8UWq7PiGcg8z5f4V4bOGWw0FOJ5PgG1MBoy9K\n" +
                "\t\t\t2TFtDte3Nx5nelQKI4LfU/iKL0pmVk0z9tsKRIx7ITA7MB8wBwYF\n" +
                "\t\t\tKw4DAhoEFGaDNOEzWZK9zZhH6UtTWIA/S3NnBBSCfgVpMax5uGb4\n" +
                "\t\t\tqzpDZDMWUVUVhQICB9A=\n" +
                "\t\t\t</data>\n" +
                "\t\t\t<key>PayloadDescription</key>\n" +
                "\t\t\t<string>Provides device authentication (certificate or identity).</string>\n" +
                "\t\t\t<key>PayloadDisplayName</key>\n" +
                "\t\t\t<string>*.bdxht.com</string>\n" +
                "\t\t\t<key>PayloadIdentifier</key>\n" +
                "\t\t\t<string>com.bdxhtest.profile.credential1</string>\n" +
                "\t\t\t<key>PayloadOrganization</key>\n" +
                "\t\t\t<string>bdxh</string>\n" +
                "\t\t\t<key>PayloadType</key>\n" +
                "\t\t\t<string>com.apple.security.pkcs12</string>\n" +
                "\t\t\t<key>PayloadUUID</key>\n" +
                "\t\t\t<string>2BC2D0BE-A9B3-4196-B592-2E6C68922F30</string>\n" +
                "\t\t\t<key>PayloadVersion</key>\n" +
                "\t\t\t<integer>1</integer>\n" +
                "\t\t</dict>\n" +
                "\t\t<dict>\n" +
                "\t\t\t<key>AccessRights</key>\n" +
                "\t\t\t<integer>8191</integer>\n" +
                "\t\t\t<key>CheckInURL</key>\n" +
                "\t\t\t<string>https://ioscontroll.bdxht.com/mdm/main/java/CheckinServlet</string>\n" +
                "\t\t\t<key>CheckOutWhenRemoved</key>\n" +
                "\t\t\t<true/>\n" +
                "\t\t\t<key>IdentityCertificateUUID</key>\n" +
                "\t\t\t<string>2BC2D0BE-A9B3-4196-B592-2E6C68922F30</string>\n" +
                "\t\t\t<key>PayloadDescription</key>\n" +
                "\t\t\t<string>Configures MobileDeviceManagement.</string>\n" +
                "\t\t\t<key>PayloadIdentifier</key>\n" +
                "\t\t\t<string>com.bdxhtest.profile.mdm2</string>\n" +
                "\t\t\t<key>PayloadOrganization</key>\n" +
                "\t\t\t<string>bdxh</string>\n" +
                "\t\t\t<key>PayloadType</key>\n" +
                "\t\t\t<string>com.apple.mdm</string>\n" +
                "\t\t\t<key>PayloadUUID</key>\n" +
                "\t\t\t<string>DC13F2E4-8664-4CB9-B9F0-29CA299AB26F</string>\n" +
                "\t\t\t<key>PayloadVersion</key>\n" +
                "\t\t\t<integer>1</integer>\n" +
                "\t\t\t<key>ServerURL</key>\n" +
                "\t\t\t<string>https://ioscontroll.bdxht.com/mdm/main/java/mdmServlet</string>\n" +
                "\t\t\t<key>SignMessage</key>\n" +
                "\t\t\t<true/>\n" +
                "\t\t\t<key>Topic</key>\n" +
                "\t\t\t<string>com.apple.mgmt.External.812c121f-8278-4272-939d-4138baae9ff2</string>\n" +
                "\t\t\t<key>UseDevelopmentAPNS</key>\n" +
                "\t\t\t<false/>\n" +
                "\t\t</dict>\n" +
                "\t</array>\n" +
                "\t<key>PayloadDescription</key>\n" +
                "\t<string>描述文件描述,用于简单的解释介绍</string>\n" +
                "\t<key>PayloadDisplayName</key>\n" +
                "\t<string>bdxhtest</string>\n" +
                "\t<key>PayloadIdentifier</key>\n" +
                "\t<string>com.bdxhtest.profile</string>\n" +
                "\t<key>PayloadOrganization</key>\n" +
                "\t<string>bdxh</string>\n" +
                "\t<key>PayloadRemovalDisallowed</key>\n" +
                "\t<false/>\n" +
                "\t<key>PayloadType</key>\n" +
                "\t<string>Configuration</string>\n" +
                "\t<key>PayloadUUID</key>\n" +
                "\t<string>"+uuid+"</string>\n" +
                "\t<key>PayloadVersion</key>\n" +
                "\t<integer>1</integer>\n" +
                "</dict>\n" +
                "</plist>\n";
        return str;
    }



}
