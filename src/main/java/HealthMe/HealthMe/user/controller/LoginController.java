package HealthMe.HealthMe.user.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.token.dto.AuthToken;
import HealthMe.HealthMe.user.dto.LoginDto;
import HealthMe.HealthMe.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/user")
@Tag(name="로그인 관련 컨트롤러")
public class LoginController {
    private final UserService userService;
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "해당 url로 요청시 로그인 기능 수행")
    public ResponseEntity logIn(@RequestBody LoginDto user) throws CustomException {
        AuthToken authToken = userService.signIn(user);
        return new ResponseEntity(authToken, HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "해당 url로 요청시 로그아웃 기능 수행")
    public ResponseEntity logout(@RequestBody LoginDto loginDto) throws CustomException {
        return new ResponseEntity(userService.logout(loginDto), HttpStatus.OK);
    }
}
