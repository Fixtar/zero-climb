package org.example.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SignUpDto {

    private String memberId;
    private String password;
    private String nickname;

}
