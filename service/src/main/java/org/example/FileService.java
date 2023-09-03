package org.example;

import org.example.exception.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private String uploadDir = "/Users/seongha/Desktop/dev/zero-climb/video/";

    public void fileUpload(MultipartFile multipartFile) throws FileUploadException {

        if(multipartFile.isEmpty()){
            System.out.println("file does not exist");
        }

        UUID uuid = new UUID(3,4);

        String type = multipartFile.getOriginalFilename().split("\\.")[1];
        System.out.println(type);

        Path path = Paths.get(uploadDir + uuid + "."+type);

        System.out.println("Service file : "+ multipartFile.getOriginalFilename());

        try {
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            throw new FileUploadException("Could not store file : " + multipartFile.getOriginalFilename());
        }
    }


}
