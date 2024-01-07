package HealthMe.HealthMe.domain.user.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.token.dto.AuthToken;
import HealthMe.HealthMe.domain.user.dto.LoginDto;
import HealthMe.HealthMe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/user")
public class LoginController {
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity logIn(@RequestBody LoginDto user) throws CustomException {
        AuthToken authToken = userService.signIn(user);
        return new ResponseEntity(authToken, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody LoginDto loginDto) throws CustomException {
        return new ResponseEntity(userService.logout(loginDto), HttpStatus.OK);
    }
}
