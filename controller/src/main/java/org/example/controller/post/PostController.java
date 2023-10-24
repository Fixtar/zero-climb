package org.example.controller.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.controller.post.request.PostReq;
import org.example.controller.post.response.PostRes;
import org.example.entity.Post;
import org.example.file.FileService;
import org.example.file.exception.FileUploadException;
import org.example.post.PostRepository;
import org.example.post.PostService;
import org.example.post.dto.PostInfoDto;
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
    private final FileService fileService;
    private final PostRepository postRepository;

    @GetMapping("/{post-id}")
    public PostRes getPost(@PathVariable(value = "post-id") Long postId) {

        log.info("getPost Controller : " + postId);
        PostInfoDto postInfoDto = postService.getPostByPostId(postId);
        log.info("post Service Success!  " + postId);
        return PostRes.builder()
                .id(postInfoDto.getId())
                .Content(postInfoDto.getContent())
                .Difficulty(postInfoDto.getDifficulty())
                .videoList(postInfoDto.getVideoList())
                .memberId(postInfoDto.getMemberId())
                .location(postInfoDto.getLocation())
                .build();
    }

    @PostMapping("")
    public Long uploadPost(@RequestPart("info") PostReq postReq, @RequestPart("file") MultipartFile multipartFile) throws FileUploadException {

        String fileUrl = fileService.fileUpload(multipartFile);
        Set<String> fileUrlList = new HashSet<>();
        fileUrlList.add(fileUrl);
        PostInfoDto postInfoDto = PostInfoDto.builder()
                .Content(postReq.getContent())
                .Difficulty(postReq.getDifficulty())
                .memberId(postReq.getMemberId())
                .videoList(fileUrlList)
                .location(postReq.getLocation())
                .build();

        return postService.uploadPost(postInfoDto);
    }

    @GetMapping("")
    public List<PostRes> getPostListByUserId(@RequestParam(name = "user") String memberId) {
        List<Post> postList = postRepository.getPostsByUser_MemberId(memberId);
        return postListToPostResList(postList);
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
                .build();
    }


}
