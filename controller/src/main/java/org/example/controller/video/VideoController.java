package org.example.controller.video;

import lombok.RequiredArgsConstructor;
import org.example.FileService;
import org.example.dto.FileStreamingDto;
import org.example.exception.FileUploadException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class VideoController {

    private final FileService fileService;
    private static String videoRootDir = "/Users/seongha/Desktop/dev/zero-climb/video/";

    @GetMapping("/file/{video-id}")
    public ResponseEntity<StreamingResponseBody> video(@PathVariable("video-id") String videoId) {

        FileStreamingDto fileStreamingDto = fileService.loadFile(videoId);

        final HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "video/mp4");
        responseHeaders.add("Content-Length", Long.toString(fileStreamingDto.getFileLength()));

        return ResponseEntity.ok().headers(responseHeaders).body(fileStreamingDto.getStreamingResponseBody());
    }


    @PostMapping("/file")
    public void uploadFile(@RequestParam("file") MultipartFile multipartFile) throws FileUploadException {
        System.out.println("upload controller");
        System.out.println(multipartFile.getOriginalFilename());
        String originFileName = multipartFile.getOriginalFilename();

        fileService.fileUpload(multipartFile);
        System.out.println("upload success" + originFileName);

    }


}
