package org.example.entity.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.entity.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Post")
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "post_content")
    private String Content;

    @Column(name = "post_difficulty")
    private String Difficulty;

    @ElementCollection(targetClass = String.class,fetch = FetchType.EAGER)
    @Column(name = "post_video_url_list")
    private Set<String> videoList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Builder
    public Post(String content, String difficulty, Set<String> videoList, User user) {
        Content = content;
        Difficulty = difficulty;
        this.videoList = videoList;
        this.user = user;
    }
}
