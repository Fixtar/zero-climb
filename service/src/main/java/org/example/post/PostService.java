package org.example.post;

import org.example.post.dto.PostInfoDto;

public interface PostService {

    PostInfoDto getPostByPostId(Long postId);


}
