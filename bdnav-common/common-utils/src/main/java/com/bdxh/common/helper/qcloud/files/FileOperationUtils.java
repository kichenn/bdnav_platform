package com.bdxh.common.helper.qcloud.files;

import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.DateUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.qcloud.cos.transfer.TransferManager;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Random;
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

    /**
     * @Description: 文件上传
     * @Author: Kang
     * @Date: 2019/3/12 18:17
     */
    public static String saveFile(MultipartFile multipartFile) throws Exception {
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 对于使用公网传输且网络带宽质量不高的情况，建议减小该值，避免因网速过慢，造成请求超时。
        ExecutorService threadPool = Executors.newFixedThreadPool(QcloudConstants.N_THREADS);
        // 传入一个 threadpool, 若不传入线程池, 默认 TransferManager 中会生成一个单线程的线程池。
        TransferManager transferManager = new TransferManager(cosClient, threadPool);
//        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
//        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
//
//        File f = fi.getStoreLocation();
        // 文件名
        String fileName = multipartFile.getOriginalFilename();
        // 文件后缀
        String extName = FilenameUtils.getExtension(fileName);
//        File localFile = (File) multipartFile;//multipartFileToFile(multipartFile);
        String key = getDestPath(extName);
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getcontentType(extName));
        objectMetadata.setContentDisposition("inline;filename=" + key);
        PutObjectRequest putObjectRequest = new PutObjectRequest(QcloudConstants.BUCKET_NAME, QcloudConstants.RESOURCES_PREFIX + key, multipartFile.getInputStream(),objectMetadata);
        putObjectRequest.setMetadata(objectMetadata);
        // putobjectResult会返回文件的etag
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        // 获取文件的 etag
        String etag = putObjectResult.getETag();
        // 关闭 TransferManger
        transferManager.shutdownNow();
        return etag != null ? key : "error";
    }

    /**
     * @Description: 文件下载
     * @Param: 文件名(阿里存储的文件名)
     * @Author: Kang
     * @Date: 2019/3/12 18:23
     */
    public static COSObjectInputStream downloadFile(String fileName) {
        String key = QcloudConstants.RESOURCES_PREFIX + fileName;
        COSClient cosClient = new COSClient(cred, clientConfig);
        // 获取下载输入流
        GetObjectRequest getObjectRequest = new GetObjectRequest(QcloudConstants.BUCKET_NAME, key);
        COSObject cosObject = cosClient.getObject(getObjectRequest);
        COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
        return cosObjectInput;
//        //下载到本地
//        File downFile = new File("C:\\用户\\Administrator\\Desktop\\1.jpg");
//        GetObjectRequest getObjectRequest = new GetObjectRequest(QcloudConstants.BUCKET_NAME, key);
//        ObjectMetadata downObjectMeta = cosClient.getObject(getObjectRequest, downFile);
    }


    /**
     * @Description: 文件下载
     * @Author: Kang
     * @Date: 2019/3/19 10:53
     */
    public static void deleteFile(String fileName) {
        String key = QcloudConstants.RESOURCES_PREFIX + fileName;
        // 3 生成cos客户端
        COSClient cosclient = new COSClient(cred, clientConfig);
        try {
            cosclient.deleteObject(QcloudConstants.BUCKET_NAME, key);
        } catch (CosServiceException e) {
            e.printStackTrace();
        } catch (CosClientException e) {
            e.printStackTrace();
        }
        // 关闭客户端
        cosclient.shutdown();
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
                + "_" + getRandomNum(6)
                + "." + extName;
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
        }
        if (".gif".equalsIgnoreCase(FilenameExtension)) {
            return "image/gif";
        }
        if (".jpeg".equalsIgnoreCase(FilenameExtension) || ".jpg".equalsIgnoreCase(FilenameExtension) || ".png".equalsIgnoreCase(FilenameExtension)) {
            return "image/jpeg";
        }
        if (".html".equalsIgnoreCase(FilenameExtension)) {
            return "text/html";
        }
        if (".txt".equalsIgnoreCase(FilenameExtension)) {
            return "text/plain";
        }
        if (".vsd".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.visio";
        }
        if (".pptx".equalsIgnoreCase(FilenameExtension) || ".ppt".equalsIgnoreCase(FilenameExtension)) {
            return "application/vnd.ms-powerpoint";
        }
        if (".docx".equalsIgnoreCase(FilenameExtension) || ".doc".equalsIgnoreCase(FilenameExtension)) {
            return "application/msword";
        }
        if (".xml".equalsIgnoreCase(FilenameExtension)) {
            return "text/xml";
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

    /**
     * MultipartFile 转 File
     *
     * @param multipartFile
     * @throws Exception
     */
    public static File multipartFileToFile(MultipartFile multipartFile) throws Exception {

        File file = null;
        if (multipartFile.equals("") || multipartFile.getSize() <= 0) {
            multipartFile = null;
        } else {
            InputStream ins = null;
            ins = multipartFile.getInputStream();
            String orgiFile = multipartFile.getOriginalFilename();
            file = new File(orgiFile);
            inputStreamToFile(ins, file);
            ins.close();
        }
        return file;
    }

    /**
     * @Description: File  Multipart 互相转换
     * @Author: Kang
     * @Date: 2019/3/12 18:06
     */
    public static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
