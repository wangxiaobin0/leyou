package com.leyou.upload.controller;

import com.leyou.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date 2020/3/20
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    @Autowired
    IUploadService uploadService;

    @PostMapping("/image")
    public ResponseEntity uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        String url = uploadService.uploadImage(request, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }
}
