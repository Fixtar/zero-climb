package org.example.post.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class PostInfoDto {

    private Long id;

    private String Content;

    private String Difficulty;

    private Set<String> videoList = new HashSet<>();

    private String memberId;

}
