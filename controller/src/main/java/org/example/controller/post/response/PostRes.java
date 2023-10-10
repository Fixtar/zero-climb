package org.example.controller.post.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class PostRes {

    private Long id;

    private String Content;

    private String Difficulty;

    private Set<String> videoList = new HashSet<>();

    private Long memberId;

}
