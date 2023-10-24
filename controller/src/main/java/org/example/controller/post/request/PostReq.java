package org.example.controller.post.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostReq {

    private String Content;

    private String Difficulty;

    private String memberId;

    private String location;

}
