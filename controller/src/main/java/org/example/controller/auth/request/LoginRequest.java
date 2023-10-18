package org.example.controller.auth.request;

import lombok.Data;

@Data
public class LoginRequest {

    private String memberId;
    private String password;

}
