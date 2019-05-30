package com.bdxh.app.controller.common;

import com.bdxh.common.helper.qcloud.files.FileOperationUtils;
import com.bdxh.common.helper.qcloud.files.constant.QcloudConstants;
import com.bdxh.common.utils.wrapper.WrapMapper;
import com.qcloud.cos.model.COSObjectInputStream;
import com.qcloud.cos.utils.IOUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Map;

@RestController
@RequestMapping("/fileController")
@Validated
@Slf4j
@Api(value = "文件操作", tags = "文件操作交互API")
public class FileController {

    @PostMapping("/saveFile")
    @ApiOperation(value = "上传文件", response = String.class)
    public Object saveFile(MultipartFile multipartFile) {
        Map<String, String> resultMap = null;
        try {
            resultMap = FileOperationUtils.saveFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
            log.info("----------上传成功-----------");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("----------上传失败-----------");
        }
        return WrapMapper.ok(resultMap);
    }

    @PostMapping("/saveBatchFile")
    @ApiOperation(value = "分块上传文件", response = String.class)
    public Object saveBatchFile(MultipartFile multipartFile) {
        Map<String, String> resultMap = null;
        try {
            //app存储桶
            resultMap = FileOperationUtils.saveBatchFile(multipartFile, QcloudConstants.APP_BUCKET_NAME);
            log.info("----------上传成功-----------");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("----------上传失败-----------");
        }
        return WrapMapper.ok(resultMap);
    }

    @PostMapping("/downloadFile")
    @ApiOperation(value = "下载文件", response = Boolean.class)
    public Object downloadFile(@RequestParam("fileName") String fileName, HttpServletResponse response) {
        try {
            //解决请求乱码问题
            response.setContentType("text/html;charset=utf-8");
            // 获取请求参数(文件名)
            fileName = new String(fileName.getBytes("iso8859-1"), "utf-8");
            fileName = URLEncoder.encode(fileName, "utf-8");
            //设置Content-Disposition头(告诉浏览器弹出下载框)
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //创建一个文件输入流
            //app存储桶
            COSObjectInputStream cis = FileOperationUtils.downloadFile(fileName,  QcloudConstants.APP_BUCKET_NAME);
            //获得输出流
            ServletOutputStream os = response.getOutputStream();
            //流对拷
            IOUtils.copy(cis, os);
            //关闭流
            os.close();
            cis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WrapMapper.ok(true);
    }

    @PostMapping("/deleteFile")
    @ApiOperation(value = "删除文件", response = Boolean.class)
    public Object deleteFile(@RequestParam("fileName") String fileName) {
        //app存储桶
        FileOperationUtils.deleteFile(fileName,  QcloudConstants.APP_BUCKET_NAME);
        return WrapMapper.ok(true);
    }
}
