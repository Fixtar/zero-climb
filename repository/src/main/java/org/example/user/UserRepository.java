package org.example.user;

import org.example.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByMemberId(String memberId);

    Boolean existsByMemberId(String memberId);

}
