package org.example.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.posts.Post;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;


    @Override
    public PostInfoDto getPostByPostId(Long postId) {

        Post post = postRepository.getPostById(postId);
        log.info("getPostRepo success");
        return PostInfoDto.builder()
                .id(postId)
                .Content(post.getContent())
                .videoList(post.getVideoList())
                .Difficulty(post.getDifficulty())
                //.memberId(post.getMember().getId())
                .build();
    }

    @Override
    public Long uploadPost(PostInfoDto postInfoDto) {
//        Member member = memberRepository.getMemberById(postInfoDto.getMemberId());
        Post post = Post.builder()
                .content(postInfoDto.getContent())
                .difficulty(postInfoDto.getDifficulty())
                .videoList(postInfoDto.getVideoList())
//                .member()
                .build();

        postRepository.save(post);
        return post.getId();
    }
}
