package com.liye.oss.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;

/**
 * @author liye
 * @create 2021-01-18-13:22
 */
public interface OssService {
    String uploadFileAvatar(MultipartFile file) throws FileNotFoundException;

    boolean deleteFile(String name);
}
