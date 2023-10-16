package org.example.controller.auth;

import lombok.RequiredArgsConstructor;
import org.example.auth.dto.TokenDto;
import org.example.controller.auth.request.LoginRequest;
import org.example.controller.auth.request.SignUpRequest;
import org.example.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginRequest loginRequest) {
        String memberId = loginRequest.getMemberId();
        String password = loginRequest.getPassword();
        TokenDto tokenDto = userService.login(memberId, password);
        return tokenDto;
    }

    @PostMapping("/test")
    public String test() {
        return "success";
    }

    @PostMapping("/signup")
    public String signup(@RequestBody SignUpRequest signUpRequest){



    }

}
