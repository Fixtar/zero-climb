package org.example.controller.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.post.request.PostReq;
import org.example.controller.post.request.PostUpdateReq;
import org.example.controller.post.response.PostRes;
import org.example.entity.Post;
import org.example.exception.Error;
import org.example.file.FileService;
import org.example.file.exception.FileUploadException;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
import org.example.post.dto.PostUpdateDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping("/{post-id}")
    public PostRes getPost(@PathVariable(value = "post-id") Long postId) {
        PostInfoDto postInfoDto = postService.getPostByPostId(postId);
        return postInfoDtoToPostRes(postInfoDto);
    }

    @PostMapping("")
    public Long uploadPost(@RequestPart("info") PostReq postReq, Authentication authentication) {
        String memberId = authentication.getName();
        PostInfoDto postInfoDto = PostInfoDto.builder()
                .Content(postReq.getContent())
                .Difficulty(postReq.getDifficulty())
                .memberId(memberId)
                .videoList(postReq.getVideoList())
                .location(postReq.getLocation())
                .build();

        return postService.uploadPost(postInfoDto);
    }

    @PutMapping("/{post-id}")
    public Long updatePost(@PathVariable(value = "post-id") Long postId,
                           @RequestBody PostUpdateReq postUpdateReq,
                           Authentication authentication) {
        String tokenMemberId = authentication.getName();

        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .postId(postId)
                .Content(postUpdateReq.getContent())
                .Difficulty(postUpdateReq.getDifficulty())
                .memberId(tokenMemberId)
                .videoList(postUpdateReq.getVideoList())
                .location(postUpdateReq.getLocation())
                .build();

        return postService.updatePost(postUpdateDto);
    }

    @DeleteMapping("/{post-id}")
    public void deletePost(@PathVariable(value = "post-id") Long postId,
                           Authentication authentication) {
        String tokenMemberId = authentication.getName();

        postService.deletePostByPostId(postId, tokenMemberId);
    }


    @GetMapping("/user")
    public List<PostRes> getPostListByUserId(@RequestParam(name = "user") String memberId) {
        List<Post> postList = postRepository.getPostsByUser_MemberId(memberId);
        return postListToPostResList(postList);
    }

    @GetMapping("/gym")
    public Slice<PostRes> getPostListByGym(@RequestParam(name = "gym") String gymName) {
        PageRequest pageRequest = PageRequest.of(0, 9, Sort.by(Direction.DESC, "created_at"));
        Slice<Post> postSlice = postRepository.findSliceByGym_Name(gymName, pageRequest);
        return postSlice.map(this::postToPostRes);
    }

    @GetMapping("")
    public Slice<PostRes> getPostListAll() {
        PageRequest pageRequest = PageRequest.of(0, 9, Sort.by(Direction.DESC, "created_at"));
        Slice<Post> postSlice = postRepository.findAll(pageRequest);
        return postSlice.map(this::postToPostRes);
    }

    private List<PostRes> postListToPostResList(List<Post> postList) {
        List<PostRes> postResList = new ArrayList<>();
        for (Post p : postList) {
            postResList.add(postToPostRes(p));
        }
        return postResList;
    }

    private PostRes postToPostRes(Post post) {
        return PostRes.builder()
                .id(post.getId())
                .Content(post.getContent())
                .Difficulty(post.getDifficulty())
                .videoList(post.getVideoList())
                .memberId(post.getUser().getMemberId())
                .location(post.getGym().getName())
                .createdAt(post.getCreated_at())
                .lastModifiedAt(post.getUpdated_at())
                .build();
    }

    private PostRes postInfoDtoToPostRes(PostInfoDto postInfoDto) {
        return PostRes.builder()
                .id(postInfoDto.getId())
                .Content(postInfoDto.getContent())
                .Difficulty(postInfoDto.getDifficulty())
                .videoList(postInfoDto.getVideoList())
                .memberId(postInfoDto.getMemberId())
                .location(postInfoDto.getLocation())
                .createdAt(postInfoDto.getCreatedAt())
                .lastModifiedAt(postInfoDto.getLastModifiedAt())
                .build();
    }


}
