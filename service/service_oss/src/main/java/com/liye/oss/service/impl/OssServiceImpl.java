package com.liye.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.liye.oss.service.OssService;
import com.liye.oss.utils.ConstandPropertiesUtils;
import com.liye.servicebase.exceptionhandler.SubjectSystemException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.logging.Log;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author liye
 * @create 2021-01-18-13:23
 */

@Service
@CrossOrigin
@Slf4j
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) throws FileNotFoundException {
        
        String endpoint = ConstandPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstandPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstandPropertiesUtils.KEY_SECRET;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


        // 上传文件流。
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new SubjectSystemException(20001,"文件上传出错");
        }


        //产生随机值
        String uuid= UUID.randomUUID().toString().replaceAll("-","");

        //把文件按日期分类
        /**
         * 1.获取当前日期
         * */
        String datePath = new DateTime().toString("yyyy/MM/dd");


        String fileName=datePath+"/"+uuid+file.getOriginalFilename();

        //file.getOriginalFilename():文件名
        ossClient.putObject(ConstandPropertiesUtils.BUCKET_NAME, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();


        //返回上传路径
        //https://edu-gulioarent.oss-cn-beijing.aliyuncs.com/1.png
        String url="https://"+ ConstandPropertiesUtils.BUCKET_NAME+"."+endpoint+"/"+fileName;
        return url;
    }


    @Override
    public boolean deleteFile(String name) {
        OSS ossClient = null;
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = ConstandPropertiesUtils.END_POINT;
            // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
            String accessKeyId = ConstandPropertiesUtils.KEY_ID;
            String accessKeySecret = ConstandPropertiesUtils.KEY_SECRET;
            String bucketName = ConstandPropertiesUtils.BUCKET_NAME;

            // 创建OSSClient实例。
            ossClient= new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            boolean b = ossClient.doesObjectExist(bucketName, name);
            if(!b) {
                log.error("文件不存在");
            }else {
                // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
                ossClient.deleteObject(bucketName,name);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            if(ossClient!=null) {
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }
}
