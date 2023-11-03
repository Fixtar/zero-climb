package org.example.controller.post.request;

import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostUpdateReq {

    private String Content;

    private String Difficulty;

    private String location;

    private Set<String> videoList;

}
