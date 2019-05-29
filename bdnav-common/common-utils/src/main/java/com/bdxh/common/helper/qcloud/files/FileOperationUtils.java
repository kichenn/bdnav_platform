package com.bdxh.common.helper.qcloud.files;

import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.DateUtil;
import com.bdxh.common.utils.ObjectUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import com.qcloud.cos.transfer.Upload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 腾讯云文件操作
 * @Author: Kang
 * @Date: 2019/3/12 16:15
 */
public class FileOperationUtils {

    // 1 初始化用户身份信息(secretId, secretKey)
    private static COSCredentials cred = new BasicCOSCredentials(QcloudConstants.SECRET_ID, QcloudConstants.SERCRET_KEY);

    private static ClientConfig clientConfig = new ClientConfig(new Region(QcloudConstants.REGION_NAME));
    // 3 生成cos客户端
    private static COSClient cosClient = new COSClient(cred, clientConfig);

    /**
     * @Description: 文件上传  buckentName 存储桶名 不传使用默认的
     * @Author: Kang
     * @Date: 2019/3/12 18:17
     */
    public static Map<String, String> saveFile(MultipartFile multipartFile, String buckentName) throws Exception {
        //设置存储桶名称，不传使用默认存储名
        String buckentNameFinal = QcloudConstants.BUCKET_NAME;
        if (StringUtils.isNotEmpty(buckentName)) {
            buckentNameFinal = buckentName;
        }
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(QcloudConstants.N_THREADS);
        // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        // 文件后缀
        String extName = FilenameUtils.getExtension(fileName);
        String key = getDestPath(extName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getcontentType(extName));
        objectMetadata.setContentDisposition("inline;filename=" + key);
        //图片存储到data里，apk存储到files里
        String finalKey = QcloudConstants.RESOURCES_PREFIX + key;
        if (extName.equals("apk")) {
            finalKey = QcloudConstants.RESOURCES_PREFIX1 + key;
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest(buckentNameFinal, finalKey, multipartFile.getInputStream(), objectMetadata);
        putObjectRequest.setMetadata(objectMetadata);
        // putobjectResult会返回文件的etag
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // 获取文件的 etag
        String etag = putObjectResult.getETag();
        // 关闭 TransferManger
        transferManager.shutdownNow();

        if (StringUtils.isNotEmpty(etag)) {
            Map<String, String> map = new HashMap<>();
            map.put("name", key);
            map.put("url", getUrl(key, buckentName));
            return map;
        }
        return null;
    }

    /**
     * @Description: 分块上传
     * @Author: Kang
     * @Date: 2019/4/23 16:13
     */
    public static Map<String, String> saveBatchFile(MultipartFile multipartFile, String buckentName) throws Exception {
        //设置存储桶名称，不传使用默认存储名
        String buckentNameFinal = QcloudConstants.BUCKET_NAME;
        if (StringUtils.isNotEmpty(buckentName)) {
            buckentNameFinal = buckentName;
        }
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        // 文件后缀
        String extName = FilenameUtils.getExtension(fileName);
        String key = getDestPath(extName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getcontentType(extName));
        objectMetadata.setContentDisposition("inline;filename=" + key);
        //图片存储到data里，apk存储到files里
        String finalKey = QcloudConstants.RESOURCES_PREFIX + key;
        if (extName.equals("apk")) {
            finalKey = QcloudConstants.RESOURCES_PREFIX1 + key;
        }
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(QcloudConstants.N_THREADS);
        // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
        PutObjectRequest putObjectRequest = new PutObjectRequest(buckentNameFinal, finalKey, multipartFile.getInputStream(), objectMetadata);
        // 本地文件上传
        Upload upload = transferManager.upload(putObjectRequest);
        // 等待传输结束（如果想同步的等待上传结束，则调用 waitForCompletion）
        UploadResult uploadResult = upload.waitForUploadResult();

        if (StringUtils.isNotEmpty(uploadResult.getETag())) {
            Map<String, String> map = new HashMap<>();
            map.put("name", key);
            map.put("url", getUrl(key, buckentName));
            return map;
        }
        return null;
    }

    /**
     * @Description: 文件下载 fileName文件名 buckentName 存储桶名
     * @Param: 文件名(阿里存储的文件名)
     * @Author: Kang
     * @Date: 2019/3/12 18:23
     */
    public static COSObjectInputStream downloadFile(String fileName, String buckentName) {
        //设置存储桶名称，不传使用默认存储名
        String buckentNameFinal = QcloudConstants.BUCKET_NAME;
        if (StringUtils.isNotEmpty(buckentName)) {
            buckentNameFinal = buckentName;
        }
        // 文件后缀
        String extName = FilenameUtils.getExtension(fileName);
        //图片存储到data里，apk存储到files里
        String finalKey = QcloudConstants.RESOURCES_PREFIX + fileName;
        if (extName.equals("apk")) {
            finalKey = QcloudConstants.RESOURCES_PREFIX1 + fileName;
        }
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(buckentNameFinal, finalKey);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        return cosObjectInput;
    }


    /**
     * @Description: 文件删除 fileName文件名 buckentName 存储桶名
     * @Author: Kang
     * @Date: 2019/3/19 10:53
     */
    public static void deleteFile(String fileName, String buckentName) {
        //设置存储桶名称，不传使用默认存储名
        String buckentNameFinal = QcloudConstants.BUCKET_NAME;
        if (StringUtils.isNotEmpty(buckentName)) {
            buckentNameFinal = buckentName;
        }
        String key = QcloudConstants.RESOURCES_PREFIX + fileName;
        try {
            cosClient.deleteObject(buckentNameFinal, key);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cosClient.shutdown();
    }


    /**
     * @Description: 获得url链接 fileName文件名 buckentName 存储桶名
     * @Author: Kang
     * @Date: 2019/3/19 16:49
     */
    private static String getUrl(String fileName, String buckentName) {
        //设置存储桶名称，不传使用默认存储名
        String buckentNameFinal = QcloudConstants.BUCKET_NAME;
        if (StringUtils.isNotEmpty(buckentName)) {
            buckentNameFinal = buckentName;
        }
        // 文件后缀
        String extName = FilenameUtils.getExtension(fileName);
        //图片存储到data里，apk存储到files里
        String finalKey = QcloudConstants.RESOURCES_PREFIX + fileName;
        if (extName.equals("apk")) {
            finalKey = QcloudConstants.RESOURCES_PREFIX1 + fileName;
        }
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(buckentNameFinal, finalKey, HttpMethodName.GET);
        // 设置签名过期时间(可选), 过期时间不做限制，只需比当前时间大, 若未进行设置, 则默认使用ClientConfig中的签名过期时间(5分钟)
        //30L * 60L * 1000L  半小时过期
        //30L * 60L * 1000L * 2L * 24L 一天
        //  30L * 60L * 1000L * 2L * 24L * 365L * 10L 10年
        Long year = 30L * 60L * 1000L * 2L * 24L * 365L * 10L;
        Date expirationDate = new Date(System.currentTimeMillis() + year);
        System.out.println("-------------------:" + DateUtil.format(expirationDate, "yyyy-MM-dd HH:mm:ss"));
        req.setExpiration(expirationDate);
        URL url = cosClient.generatePresignedUrl(req);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

    /**
     * @Description: 生成文件名
     * @param: extName 文件后缀
     * @Author: Kang
     * @Date: 2019/3/12 17:31
     */
    private static String getDestPath(String extName) {
        //规则：  年月日_随机数.后缀名
        String sb = DateUtil.format(new Date(), DateUtil.DATE_FORMAT_SHORT)
                + "_" + ObjectUtil.getUuid() + "." + extName;
        return sb;
    }

    /**
     * @Description: 判断OSS服务文件上传时文件的contentType
     * @param: FilenameExtension 文件后缀
     * @Author: Kang
     * @Date: 2019/3/12 17:32
     */
    private static String getcontentType(String FilenameExtension) {
        if (".bmp".equalsIgnoreCase(FilenameExtension)) {
            return "image/bmp";
        } else if (".gif".equalsIgnoreCase(FilenameExtension)) {
            return "image/gif";
        } else if (".jpeg".equalsIgnoreCase(FilenameExtension) || ".jpg".equalsIgnoreCase(FilenameExtension) || ".png".equalsIgnoreCase(FilenameExtension)) {
            return "image/jpeg";
        } else if (".html".equalsIgnoreCase(FilenameExtension)) {
            return "text/html";
        } else if (".txt".equalsIgnoreCase(FilenameExtension)) {
            return "text/plain";
        } else if (".vsd".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.visio";
        } else if (".pptx".equalsIgnoreCase(FilenameExtension) || ".ppt".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.ms-powerpoint";
        } else if (".docx".equalsIgnoreCase(FilenameExtension) || ".doc".equalsIgnoreCase(FilenameExtension)) {
            return "application/msword";
        } else if (".xml".equalsIgnoreCase(FilenameExtension)) {
            return "text/xml";
        } else if (".apk".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.android.package-archive";
        }
        return "image/jpeg";
    }


    /**
     * @Description: 随机数
     * @Author: Kang
     * @Date: 2019/3/12 17:42
     */
    private static String getRandomNum(int len) {
        String str = "0123456789";
        StringBuilder sb = new StringBuilder(6);
        for (int i = 0; i < len; i++) {
            char ch = str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }
        return sb.toString();
    }
}
