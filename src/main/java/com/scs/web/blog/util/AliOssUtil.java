package com.scs.web.blog.util;

import com.aliyun.oss.OSSClient;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.UUID;

/**
 * @author zhao
 * @className AliOssUtil
 * @Description TODO
 * @Date 2019/12/19
 * @Version 1.0
 **/
@Slf4j
public class AliOssUtil {
    public static String upload(File file) {
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4FnnGEXdepaKe265JSB9";
        String accessKeySecret = "TxTGNz8so1AdGxRaA56QlKAjjE9VXN";
        String bucketName = "kkkksslls";
        String filePath = "avatar/";
        String fileName = file.getName();
        String newFileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));
        // 创建OSSClient实例
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        // 上传文件到指定位置，并使用UUID更名
        ossClient.putObject(bucketName, filePath + newFileName, file);
        // 拼接URL
        String url = "https://kkkksslls.oss-cn-beijing.aliyuncs.com/" + filePath + newFileName;
        ossClient.shutdown();
        return url;
    }
}
