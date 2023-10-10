package org.example.controller.post.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class PostReq {

    private String Content;

    private String Difficulty;

    private Long memberId;

}
