package org.example.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Post")
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "gym_id")
    private Gym gym;

    private String difficulty;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @Column(name = "post_video_url_list")
    private Set<String> videoList = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;


    @Builder
    public Post(String content, Gym gym, String difficulty, Set<String> videoList, User user) {
        this.content = content;
        this.gym = gym;
        this.difficulty = difficulty;
        this.videoList = videoList;
        this.user = user;
    }
}
