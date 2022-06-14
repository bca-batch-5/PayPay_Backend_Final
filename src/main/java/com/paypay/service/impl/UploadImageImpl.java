package com.paypay.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.paypay.Exception.BadRequestException;
import com.paypay.config.FileStorageProperties.FileStorage;
import com.paypay.dto.Response.FileInfo;
import com.paypay.dto.Response.Response;
import com.paypay.model.User;
import com.paypay.model.UserDetail;
import com.paypay.repository.UserDetailRepo;
import com.paypay.repository.UserRepo;
import com.paypay.service.UploadImageService;
import com.paypay.validation.UserValidation;

@Service
@EnableConfigurationProperties(value = FileStorage.class)
public class UploadImageImpl implements UploadImageService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserDetailRepo userDetailRepo;

    @Autowired
    private UserValidation userValidation;

    private Path fileStorageLocation;
    private Map<String, Object> data;

    @Autowired
    public void fileService(FileStorage fileStorage) throws Exception {
        this.fileStorageLocation = Paths.get(fileStorage.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception e) {
            throw new BadRequestException("Could not create the directory where the uploaded files will be stored.");
        }
    }

    @Override
    public Response uploadFile(MultipartFile file, String email) throws Exception {
        User userFind = userRepo.findByEmail(email);
        userValidation.checkingUserByEmail(userFind);
        User user = userFind;
        UserDetail userDetail = userDetailRepo.findByUser(user);
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetPath = this.fileStorageLocation.resolve(fileName);
            // update: mengganti filename menjadi user{id}
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            // save filename to db
            userDetail.setImage(fileName);
            userDetailRepo.save(userDetail);

            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users").path("/{email}/")
                    .path(fileName)
                    .build(email).toString();

            FileInfo fileInfo = new FileInfo(fileName, url, file.getContentType());

            Response responseData = new Response(200, "Success", fileInfo);

            return responseData;
        } catch (Exception e) {
            throw new BadRequestException("Could not store file " + fileName + ". Please try again!");
        }
    }

    @Override
    public Response loadFile(String email, HttpServletRequest request) throws Exception, MalformedURLException, IOException {
        User userDb = userRepo.findByEmail(email);
        UserDetail userDetail = userDetailRepo.findByUser(userDb);
        Path filePath = this.fileStorageLocation.resolve(userDetail.getImage());
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (contentType.isEmpty()) {
                contentType = "application/octet-stream";
            }

            data = new HashMap<>();
            data.put("resource", resource);
            data.put("contentType", contentType);

            Response responseData = new Response(200, "success", data);

            return responseData;
        } else {
            throw new BadRequestException("File not found " + userDetail.getImage());
        }
    }
}
