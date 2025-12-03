package com.blog.blogger.service.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.blogger.service.FileService;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String originalName = file.getOriginalFilename();
        String randomId = UUID.randomUUID().toString();
        String fileName = randomId.concat(originalName.substring(originalName.lastIndexOf(".")));

        String filePath = path + File.separator + fileName;

        File folder = new File(path);
        if (!folder.exists()) {
            folder.mkdirs(); // create folder if not exists
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName; // return generated unique file name
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        return new FileInputStream(fullPath);
    }

}
