package org.example.post.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.posts.Post;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostInfoDto getPostByPostId(Long postId) {

        Post post = postRepository.getPostById(postId);

        return PostInfoDto.builder()
                .id(postId)
                .Content(post.getContent())
                .videoList(post.getVideoList())
                .Difficulty(post.getDifficulty())
                .memberId(post.getMember().getId())
                .build();
    }
}
