package com.leyou.item.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadService {
    // 图片类型
    private static final List<String> IMAGE_CONTENT_TYPES = Arrays.asList("image/png", "image/jpeg");
    // 日志
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadService.class);

    @Autowired
    private FastFileStorageClient storageClient;

    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    public String uploadImage(MultipartFile file) {
        // 获取文件名
        String filename = file.getOriginalFilename();
        // 获取文件类型
        String contentType = file.getContentType();

        // 文件类型不合法，直接返回 null
        if (!IMAGE_CONTENT_TYPES.contains(contentType)) {
            LOGGER.info("文件类型不合法：{}", filename);
            return null;
        }

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            // 如果文件内容不合法，直接返回 null
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法：{}", filename);
                return null;
            }

            // 获取文件名后缀
            String type = StringUtils.substringAfterLast(filename, ".");

            // 保存文件到 FastDFS 服务器
            StorePath storePath = this.storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), type, null);

            // 返回 url
            return "http://image.leyou.com/" + storePath.getFullPath();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.info("服务器内部错误：{}", filename);
        }

        return null;
    }
}


