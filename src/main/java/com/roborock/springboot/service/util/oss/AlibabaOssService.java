package com.roborock.springboot.service.util.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.roborock.springboot.service.config.OSSConfiguration;
import com.roborock.springboot.service.util.StringUtils;
import com.roborock.springboot.service.util.uuid.IdUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * @Author BoHanZhang
 * @Date Create in 2021/2/22 16:19
 * @Description oss工具类
 */

@Component
public class AlibabaOssService {

    private static final Logger log = LoggerFactory.getLogger(AlibabaOssService.class);

    @Autowired
    private OSS ossClient;

    @Autowired
    private OSSConfiguration configuration;

    /**
     * 文件上传
     *
     * @param file         上传的文件
     * @param storagePath 文件存储路径
     *                      例如：/user/Administrator/Desktop
     * @return fileName
     */
    public String uploadFile(MultipartFile file, String storagePath) {
        String fileName = "";
        try {
            fileName = IdUtils.simpleUUID();
            InputStream inputStream = file.getInputStream();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(inputStream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            fileName = storagePath + "/" + fileName;
            // 上传文件
            ossClient.putObject(configuration.getBucketName(), fileName, inputStream, objectMetadata);
        } catch (IOException e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
        return fileName;
    }

    /**
     * 查看文件是否存在
     *
     * @param fileName  文件名
     * @return boolean
     */
    public boolean doesObjectExist(String fileName) {
        try {
            if (StringUtils.isEmpty(fileName)) {
                log.error("文件名不能为空");
                return false;
            } else {
                return ossClient.doesObjectExist(configuration.getBucketName(), fileName);
            }
        } catch (OSSException | ClientException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 文件下载
     *
     * @param os         输入流
     * @param objectName 文件名称
     *
     */
    public void exportFile(OutputStream os, String objectName) {
        OSSObject ossObject = ossClient.getObject(configuration.getBucketName(), objectName);
        // 读取文件内容
        BufferedInputStream in = new BufferedInputStream(ossObject.getObjectContent());
        BufferedOutputStream out = new BufferedOutputStream(os);
        byte[] buffer = new byte[1024];
        int lenght;
        try {
            while ((lenght = in.read(buffer)) != -1) {
                out.write(buffer, 0, lenght);
            }
            out.flush();
        } catch (IOException e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
    }

    /**
     * 文件删除
     *
     * @param fileName 文件名称
     *
     */
    public void deleteFile(String fileName) {
        try {
            ossClient.deleteObject(configuration.getBucketName(), fileName);
        } catch (Exception e) {
            log.error("Error occurred: {}", e.getMessage(), e);
        }
    }

    /**
     * 文件url获取
     *
     * @param fileName 文件名称
     * @param expSeconds 过期时间（单位s）
     * @return url
     */
    public String getSingeNatureUrl(String fileName, int expSeconds) {
        Date expiration = new Date(System.currentTimeMillis() + expSeconds * 1000);
        URL url = ossClient.generatePresignedUrl(configuration.getBucketName(), fileName, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

}
