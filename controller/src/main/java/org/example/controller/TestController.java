package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080/")
public class TestController {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/sample")
    public ResponseEntity<SampleRes> test() {
        System.out.println("test controller");

        System.out.println(passwordEncoder.encode("1234"));

        return ResponseEntity.ok().body(SampleRes.builder()
                .ApiSample("server Sample success")
                .build());
    }


}
