package org.example.post.service;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Gym;
import org.example.entity.Post;
import org.example.entity.S3Entity;
import org.example.entity.User;
import org.example.exception.Error;
import org.example.file.FileService;
import org.example.location.LocationRepository;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.example.post.dto.PostUpdateDto;
import org.example.s3entity.S3EntityRepository;
import org.example.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final S3EntityRepository s3EntityRepository;
    private final FileService fileService;

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
                .createdAt(post.getCreated_at())
                .lastModifiedAt(post.getUpdated_at())
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

    @Override
    @Transactional
    public Long updatePost(PostUpdateDto postUpdateDto) {
        Post post = postRepository.getPostById(postUpdateDto.getPostId());
        String postMemberId = post.getUser().getMemberId();
        throwIfDoesNotEqualsMemberId(postUpdateDto.getMemberId(), postMemberId);
        Gym gym = locationRepository.getLocationByName(postUpdateDto.getLocation())
                .orElseThrow(() -> new IllegalArgumentException(Error.NOT_FOUND_GYM_EXCEPTION.getMessage()));

        updateS3Entity(post, postUpdateDto);
        post.updatePost(postUpdateDto.getContent(), gym, postUpdateDto.getDifficulty(), postUpdateDto.getVideoList());
        postRepository.save(post);
        return post.getId();
    }

    private void updateS3Entity(Post post, PostUpdateDto postUpdateDto) {
        for (String filename : post.getVideoList()) {
            S3Entity s3Entity = s3EntityRepository.getS3EntityByFileName(filename);
            s3Entity.setStore(false);
        }
        for (String filename : postUpdateDto.getVideoList()) {
            try {
                S3Entity s3Entity = s3EntityRepository.getS3EntityByFileName(filename);
                s3Entity.setStore(true);
            } catch (NullPointerException e) {
                throw new IllegalArgumentException(Error.NOT_FOUND_S3ENTITY_EXCEPTION.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public void deletePostByPostId(Long postId, String memberId) {
        Post post = postRepository.getPostById(postId);
        String postMemberId = post.getUser().getMemberId();
        throwIfDoesNotEqualsMemberId(memberId, postMemberId);
        Set<String> videoList = post.getVideoList();
        for (String filename : videoList) {
            fileService.deleteS3Post(filename);
            s3EntityRepository.deleteByFileName(filename);
        }
        postRepository.delete(post);
    }

    void throwIfDoesNotEqualsMemberId(String tokenMemberId, String postMemberId) {
        if (!postMemberId.equals(tokenMemberId)) {
            throw new RuntimeException(Error.REQUEST_VALIDATION_EXCEPTION.getMessage());
        }
    }

}
