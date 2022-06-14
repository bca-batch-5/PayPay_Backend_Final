package com.paypay.config.FileStorageProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "file")
public class FileStorage {
    private String uploadDir;
}
