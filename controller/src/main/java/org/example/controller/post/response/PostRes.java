package org.example.controller.post.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class PostRes {

    private Long id;

    private String Content;

    private String Difficulty;

    private Set<String> videoList = new HashSet<>();

    private String memberId;

    private String location;

    private LocalDateTime createdAt;

    private LocalDateTime lastModifiedAt;

}
