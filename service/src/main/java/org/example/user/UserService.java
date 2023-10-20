package org.example.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.auth.JwtTokenProvider;
import org.example.auth.dto.SignUpDto;
import org.example.auth.dto.TokenDto;
import org.example.entity.User;
import org.example.exception.Error;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(String memberId, String password){
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분

        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.generateToken(authentication);
        return tokenDto;
    }

    @Transactional
    public void signUp(SignUpDto signUpDto){

        String memberId = signUpDto.getMemberId();
        String password = signUpDto.getPassword();
        Boolean isExistedMemberId = userRepository.existsByMemberId(memberId);

        if(isExistedMemberId){
            throw new IllegalArgumentException(Error.ALREADY_EXIST_USER_EXCEPTION.getMessage());
        }
        // 패스워드 조건 검증 필요.
        String encryptedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .memberId(memberId)
                .password(encryptedPassword)
                .name(signUpDto.getNickname())
                .build();
        user.addAuthorities("USER");
        userRepository.save(user);

    }

}
