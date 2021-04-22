package com.leyou.item.controller;

import com.leyou.item.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@Api(tags = {"上传图片接口"})
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 图片上传
     * @param file
     * @return
     */
    @PostMapping("/image")
    @ApiOperation(value = "上传图片")
    public ResponseEntity<String> uploadImage(@RequestParam("file")@RequestBody MultipartFile file){
        String url = uploadService.uploadImage(file);
        if (StringUtils.isBlank(url)){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);
    }

}
