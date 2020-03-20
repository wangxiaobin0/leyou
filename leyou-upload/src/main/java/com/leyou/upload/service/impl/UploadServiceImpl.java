package com.leyou.upload.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.upload.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.management.relation.RoleUnresolved;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @date 2020/3/20
 */
@Service
public class UploadServiceImpl implements IUploadService {
    public static final List<String> IMAGE_TYPE = Arrays.asList("image/jpeg", "image/gif");

    @Autowired
    FastFileStorageClient storageClient;
    @Override
    public String uploadImage(HttpServletRequest request, MultipartFile file) {
        String uploadPath = "/upload/images/";
        String imgPath = request.getServletContext().getRealPath(uploadPath);
        File imgUploadPath = new File(imgPath);
        if (!imgUploadPath.exists()) {
            imgUploadPath.mkdirs();
        }
        //文件类型. images/jpeg
        String contentType = file.getContentType();
        if (!IMAGE_TYPE.contains(contentType)) {
            throw new RuntimeException("文件格式不正确");
        }
        //文件本名
        String originalFilename = file.getOriginalFilename();

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                throw new RuntimeException("文件内容不合法");
            }
            file.transferTo(new File(imgUploadPath , originalFilename));
            StringBuffer url = new StringBuffer();
            url.append(request.getScheme())
                    .append("://")
                    .append(request.getServerName())
                    .append(":")
                    .append(request.getServerPort())
                    .append(request.getContextPath())
                    .append(uploadPath);
            String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
            System.out.println(storePath.getFullPath());
            System.out.println(storePath.getGroup());
            System.out.println(storePath.getPath());
            return url.append(storePath.getFullPath()).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
