package org.example.post.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class PostInfoDto {

    private Long id;

    private String Content;

    private String Difficulty;

    private Set<String> videoList;

    private String memberId;

    private String location;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
