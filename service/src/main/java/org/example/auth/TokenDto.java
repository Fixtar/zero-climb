package org.example.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TokenDto {

    private String type;
    private String accessToken;
    private String refreshToken;
}
