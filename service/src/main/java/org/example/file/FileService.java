package org.example.file;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.example.file.dto.FileStreamingDto;
import org.example.file.exception.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class FileService {

    private String storeDir = "/Users/seongha/Desktop/dev/zero-climb/video/";


    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String fileUpload(MultipartFile multipartFile) throws FileUploadException {

        try {
            String originalFilename = multipartFile.getOriginalFilename();
            String uploadFileName = "videos/" + originalFilename;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            amazonS3Client.putObject(bucket, uploadFileName, multipartFile.getInputStream(), objectMetadata);
            return amazonS3Client.getUrl(bucket, uploadFileName).toString();
        } catch (IOException e) {
            throw new FileUploadException("upload error");
        }

    }

    public String generatePreSignedUrl(){
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 180; //180분 추가
        expiration.setTime(expTimeMillis);

        URL preSignedUrl = amazonS3Client.generatePresignedUrl(bucket,"videos/11", expiration, HttpMethod.PUT);
        return preSignedUrl.toString();
    }


    //로컬에 파일 업로드
//    public void fileUpload(MultipartFile multipartFile) throws FileUploadException {
//
//        if (multipartFile.isEmpty()) {
//            System.out.println("file does not exist");
//        }
//        UUID uuid = new UUID(3, 4);
//        String type = multipartFile.getOriginalFilename().split("\\.")[1];
//        System.out.println(type);
//        Path path = Paths.get(storeDir + uuid + "." + type);
//        System.out.println("Service file : " + multipartFile.getOriginalFilename());
//
//        try {
//            Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
//        } catch (Exception e) {
//            throw new FileUploadException("Could not store file : " + multipartFile.getOriginalFilename());
//        }
//    }

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
