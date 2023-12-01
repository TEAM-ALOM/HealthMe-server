package HealthMe.HealthMe.domain.user.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.user.dto.UserBodyInformationDto;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.dto.UserPasswordChangeDto;
import HealthMe.HealthMe.domain.user.dto.UserSignUpDto;
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
public class UserController {
    private final UserService userService;
    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody UserSignUpDto user) throws CustomException{
        return new ResponseEntity(userService.signUp(user), HttpStatus.OK);
    }

    @PostMapping("/information")
    public ResponseEntity setInformation(@RequestBody UserBodyInformationDto userSignUpInformationDto) throws CustomException{
        return new ResponseEntity<>(userService.enterBodyInformation(userSignUpInformationDto), HttpStatus.OK);
    }

    @GetMapping("/checkPassword")
    public ResponseEntity checkPassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) throws CustomException{
        return new ResponseEntity(userService.checkPassword(userPasswordChangeDto), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity changePassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) throws CustomException{
        return new ResponseEntity(userService.changePassword(userPasswordChangeDto), HttpStatus.OK);
    }

    @GetMapping("/bodyInformation")
    public ResponseEntity getInformation(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity<>(userService.getUserBodyInformation(userDto), HttpStatus.OK);
    }
}
