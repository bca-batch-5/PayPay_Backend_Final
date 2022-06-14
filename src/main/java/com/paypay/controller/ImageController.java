package com.paypay.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.http.HttpHeaders;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paypay.dto.Response.Response;
import com.paypay.service.UploadImageService;

@RestController
@RequestMapping("/paypay")
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    private Response response;
    private static String FILE_PATH_ROOT = "src/main/resources/images/";

    @Autowired
    private UploadImageService uploadImageService;

    @PostMapping("/img/{email}")
    public ResponseEntity<?> updateUserImage(@RequestParam("file") MultipartFile file,
            @PathVariable("email") String email)
            throws Exception {
        response = uploadImageService.uploadFile(file, email);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/img/{email}")
    public ResponseEntity<?> getUserImage(@PathVariable("email") String email,
            HttpServletRequest request) throws MalformedURLException, Exception, IOException {
         response = uploadImageService.loadFile(email, request);
        Map<String, Object> data = (Map<String, Object>) response.getData();
        String contentType = (String) data.get("contentType");
        Resource resource = (Resource) data.get("resource");

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

}
