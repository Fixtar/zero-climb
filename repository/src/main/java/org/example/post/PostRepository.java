package org.example.post;

import org.example.entity.posts.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post getPostById(Long postId);

}
