package com.paypay.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.paypay.dto.Response.Response;

public interface UploadImageService {
    Response uploadFile(MultipartFile file, String email) throws Exception;
    Response loadFile(String filename, HttpServletRequest request) throws Exception, MalformedURLException, IOException;
}
