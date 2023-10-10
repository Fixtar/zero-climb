package org.example.entity.user;

import lombok.Getter;
import lombok.Setter;
import org.example.entity.posts.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_account_id")
    private String accountId;

    @Column(name = "member_account_password")
    private String accountPassword;

    @Column(name = "member_name")
    private String name;

    @OneToMany(mappedBy = "member")
    @Column(name = "member_post_list")
    private List<Post> postList = new ArrayList<>();

}
