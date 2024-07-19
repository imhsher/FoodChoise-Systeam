package com.imcys.foodchoice.client.controller.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/static/files")
@Slf4j
public class FileController {
    @Value("${foodchoice.file-upload-path}")
    private String fileUploadPath;

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {

        Resource fileResource = new FileSystemResource(fileUploadPath + filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(fileResource);
    }



}
