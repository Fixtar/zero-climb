package org.example.post.dto;

import java.util.HashSet;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostUpdateDto {

    private Long postId;

    private String memberId;

    private String Content;

    private String Difficulty;

    private Set<String> videoList = new HashSet<>();

    private String location;

}
