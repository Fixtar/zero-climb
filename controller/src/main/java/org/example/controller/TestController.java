package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081/")
public class TestController {
    @GetMapping("/sample")
    public ResponseEntity<SampleRes> test() {
        System.out.println("test controller");

        return ResponseEntity.ok().body(SampleRes.builder()
                .ApiSample("server Sample success")
                .build());
    }


}
