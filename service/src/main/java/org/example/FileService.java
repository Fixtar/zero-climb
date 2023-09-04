package org.example;

import org.example.dto.FileStreamingDto;
import org.example.exception.FileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {

    private String storeDir = "/Users/seongha/Desktop/dev/zero-climb/video/";

    public void fileUpload(MultipartFile multipartFile) throws FileUploadException {

        if (multipartFile.isEmpty()) {
            System.out.println("file does not exist");
        }
        UUID uuid = new UUID(3, 4);
        String type = multipartFile.getOriginalFilename().split("\\.")[1];
        System.out.println(type);
        Path path = Paths.get(storeDir + uuid + "." + type);
        System.out.println("Service file : " + multipartFile.getOriginalFilename());

        try {
            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new FileUploadException("Could not store file : " + multipartFile.getOriginalFilename());
        }
    }

    public FileStreamingDto loadFile(String id) {

        File file = new File(storeDir + id);
        if (!file.isFile()) {
            throw new RuntimeException("file not found");
        }

        StreamingResponseBody streamingResponseBody = outputStream -> {
            try {
                final InputStream inputStream = new FileInputStream(file);

                byte[] bytes = new byte[1024];
                int length;
                while ((length = inputStream.read(bytes)) >= 0) {
                    outputStream.write(bytes, 0, length);
                }
                inputStream.close();
                outputStream.flush();
            } catch (final Exception e) {
                System.out.println("Exception while reading and streaming data {} ");
            }
        };

        return FileStreamingDto.builder()
                .streamingResponseBody(streamingResponseBody)
                .fileLength(file.length())
                .build();
    }


}
