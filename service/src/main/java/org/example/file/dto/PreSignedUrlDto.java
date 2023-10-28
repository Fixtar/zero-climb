package org.example.file.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PreSignedUrlDto {

    private String preSignedUrl;
    private String fileName;


}
