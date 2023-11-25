package HealthMe.HealthMe.domain.user.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.UserSignUpDto;
import HealthMe.HealthMe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody UserSignUpDto user) throws CustomException{
        return new ResponseEntity(userService.signUp(user), HttpStatus.OK);
    }

    @GetMapping("/signIn")
    public ResponseEntity signIn(@RequestBody UserSignUpDto user) throws CustomException{
        return new ResponseEntity(userService.signIn(user), HttpStatus.OK);
    }

}
