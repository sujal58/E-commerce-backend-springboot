package com.sujal.Ecommerce.Service;

import com.sujal.Ecommerce.Exceptions.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class FileService {

    public String uploadImage(String path, MultipartFile file) throws java.io.IOException {

        //extract the original name of the uploaded file
        String fileName = file.getOriginalFilename();

        //modified file name
        String modifiedFileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")).concat(fileName);
        //full path of the file including file name to be uploaded
        String filePath = path + File.separator + modifiedFileName;

        //creating new object of File representing directory where file is going to store
        File files = new File(path);

        //checking if directory exist or not
        //if not exist then creating directory
        if(!files.exists()){
            files.mkdir();
        }

        //copying the uploaded multipart file into the specified filepath
        Files.copy(file.getInputStream(), Paths.get(filePath));


        //returning the file name after copying that file
        return modifiedFileName;
    }

    public void deleteImage(String path, String imageUrl){
        String imagePath = path + File.separator + imageUrl;

        File image = new File(imagePath);

        if(image.exists()) {
            if (!image.delete()) {
                throw new CustomException("Error while deleting existing image!!");
            }
        }
    }
}
