package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@Getter
@Builder
public class FileStreamingDto {
    private StreamingResponseBody streamingResponseBody;
    private Long fileLength;
}
