package com.cloudkeeper.leasing.identity.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface FdfsService {

    String uploadFile(MultipartFile file) throws IOException;

    String uploadFile(InputStream inputStream, Long size, String fileName) throws IOException;

    void deleteFile(String fileUrl);
}
