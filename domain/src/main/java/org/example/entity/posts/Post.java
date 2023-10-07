package org.example.entity.posts;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.user.Member;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @JoinColumn(name = "member_id")
    private Member member;

}
