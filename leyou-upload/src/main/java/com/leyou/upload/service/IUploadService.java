package com.leyou.upload.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date 2020/3/20
 */
public interface IUploadService {

    /**
     * 上传文件
     * @param file
     */
    String uploadImage(HttpServletRequest request, MultipartFile file);
}
