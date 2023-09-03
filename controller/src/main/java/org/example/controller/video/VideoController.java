package org.example.controller.video;

import lombok.RequiredArgsConstructor;
import org.example.FileService;
import org.example.exception.FileUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class VideoController {

    private final FileService fileService;

    @GetMapping("/sample/video1")
    public ResponseEntity<StreamingResponseBody> video() {
        File file = new File("/Users/seongha/Desktop/dev/zero-climb/video/video1.mp4");
        if (!file.isFile()) {
            return ResponseEntity.notFound().build();
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

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "video/mp4");
        responseHeaders.add("Content-Length", Long.toString(file.length()));

        return ResponseEntity.ok().headers(responseHeaders).body(streamingResponseBody);
    }


    @PostMapping("/file")
    public void uploadVideo(@RequestParam("file") MultipartFile multipartFile) throws FileUploadException {
        System.out.println("upload controller");
        System.out.println(multipartFile.getOriginalFilename());
        String originFileName = multipartFile.getOriginalFilename();


        fileService.fileUpload(multipartFile);
        System.out.println("upload success" + originFileName);

    }

}
