package org.example.post;

import org.example.post.dto.PostInfoDto;
import org.example.post.dto.PostUpdateDto;

public interface PostService {

    PostInfoDto getPostByPostId(Long postId);

    Long uploadPost(PostInfoDto postInfoDto);

    Long updatePost(PostUpdateDto postUpdateDto);

}
