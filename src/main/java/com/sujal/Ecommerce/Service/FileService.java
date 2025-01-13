package com.sujal.Ecommerce.Service;

import io.jsonwebtoken.io.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FileService {

    public String uploadImage(String path, MultipartFile file) throws java.io.IOException {
        String fileName = file.getOriginalFilename();

        String filePath = path + File.separator + fileName;

        File files = new File(path);

        if(!files.exists()){
            files.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName;
    }
}
