package org.example.post.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Gym;
import org.example.entity.Post;
import org.example.entity.S3Entity;
import org.example.entity.User;
import org.example.exception.Error;
import org.example.location.LocationRepository;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.example.s3entity.S3EntityRepository;
import org.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final S3EntityRepository s3EntityRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Override
    public PostInfoDto getPostByPostId(Long postId) {

        Post post = postRepository.getPostById(postId);
        String locationName = "";
        if (post.getGym() != null) {
            locationName = post.getGym().getName();
        }
        Set<String> videoUrlSet = mappingVideoList(post.getVideoList());
        return PostInfoDto.builder()
                .id(postId)
                .Content(post.getContent())
                .videoList(videoUrlSet)
                .Difficulty(post.getDifficulty())
                .memberId(post.getUser().getMemberId())
                .location(locationName)
                .build();
    }

    private Set<String> mappingVideoList(Set<String> videoList) {
        return videoList.stream()
                .map(filename -> "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com/" + filename)
                .collect(Collectors.toSet());
    }

    @Override
    public Long uploadPost(PostInfoDto postInfoDto) {
        User user = userRepository.findUserByMemberId(postInfoDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(Error.NOT_FOUND_USER_EXCEPTION.getMessage()));
        Gym gym = locationRepository.getLocationByName(postInfoDto.getLocation())
                .orElseThrow(() -> new IllegalArgumentException(Error.NOT_FOUND_GYM_EXCEPTION.getMessage()));

        //업로드시 상태변경으로 삭제 되었다고 변경
        changeIsStoreValue(postInfoDto.getVideoList());

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

    private void changeIsStoreValue(Set<String> videoList) {

        for (String url : videoList) {
            S3Entity s3 = s3EntityRepository.getS3EntityByFileName(url);
            s3.setStore(true);
        }

    }

}
