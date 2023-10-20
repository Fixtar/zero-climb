package org.example.post.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Gym;
import org.example.entity.Post;
import org.example.entity.User;
import org.example.exception.Error;
import org.example.location.LocationRepository;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.example.user.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Override
    public PostInfoDto getPostByPostId(Long postId) {

        Post post = postRepository.getPostById(postId);
        String locationName ="";
        if(post.getGym() != null){
            locationName = post.getGym().getName();
        }
        return PostInfoDto.builder()
                .id(postId)
                .Content(post.getContent())
                .videoList(post.getVideoList())
                .Difficulty(post.getDifficulty())
                .memberId(post.getUser().getMemberId())
                .location(locationName)
                .build();
    }

    @Override
    public Long uploadPost(PostInfoDto postInfoDto) {
        User user = userRepository.findUserByMemberId(postInfoDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(Error.NOT_FOUND_USER_EXCEPTION.getMessage()));
        Gym gym = locationRepository.getLocationByName(postInfoDto.getLocation())
                .orElse(null);

        Post post = Post.builder()
                .content(postInfoDto.getContent())
                .difficulty(postInfoDto.getDifficulty())
                .videoList(postInfoDto.getVideoList())
                .user(user)
                .gym(gym)
                .build();
        postRepository.save(post);
        return post.getId();
    }
}
