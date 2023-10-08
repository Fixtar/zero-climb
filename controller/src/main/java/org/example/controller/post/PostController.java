package org.example.controller.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.post.response.PostRes;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/{post-id}")
    public PostRes getPost(@PathVariable(value = "post-id") Long postId){

        log.info("getPost Controller");
        PostInfoDto postInfoDto = postService.getPostByPostId(postId);

        return PostRes.builder()
                .id(postInfoDto.getId())
                .Content(postInfoDto.getContent())
                .Difficulty(postInfoDto.getDifficulty())
                .videoList(postInfoDto.getVideoList())
                .memberId(postInfoDto.getMemberId())
                .build();
    }


}
