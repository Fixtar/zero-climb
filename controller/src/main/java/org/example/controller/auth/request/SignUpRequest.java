package org.example.controller.auth.request;

import lombok.Data;

@Data
public class SignUpRequest {

    private String memberId;
    private String password;
    private String nickname;

}
