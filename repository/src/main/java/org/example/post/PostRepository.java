package org.example.post;

import org.example.entity.Gym;
import org.example.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    Post getPostById(Long postId);

    Slice<Post> findSliceByGym_Name(String gymName, Pageable pageable);
    List<Post> getPostsByUser_MemberId(String memberId);
}
