package org.example.post;

import org.example.entity.Gym;
import org.example.entity.Post;
import org.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post getPostById(Long postId);

    List<Post> getPostsByGym(Gym gym);

    List<Post> getPostsByUser_MemberId(String memberId);
}
