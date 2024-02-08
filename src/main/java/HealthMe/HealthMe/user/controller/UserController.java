package HealthMe.HealthMe.user.controller;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.user.dto.UserDto;
import HealthMe.HealthMe.user.dto.UserPasswordChangeDto;
import HealthMe.HealthMe.user.dto.UserSignUpDto;
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
@Tag(name="사용자 관련 컨트롤러")
public class UserController {
    private final UserService userService;
    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "해당 url로 요청시 회원가입 기능 수행")
    public ResponseEntity signUp(@RequestBody UserSignUpDto user) throws CustomException{
        return new ResponseEntity(userService.signUp(user), HttpStatus.OK);
    }

    @PostMapping("/save-body-information")
    @Operation(summary = "신체 정보 수정", description = "해당 url로 요청시 신체 정보 수정 가능")
    public ResponseEntity setInformation(@RequestBody UserDto userSignUpInformationDto) throws CustomException{
        return new ResponseEntity<>(userService.setBodyInformation(userSignUpInformationDto), HttpStatus.OK);
    }


    @PostMapping("/change-forget-password")
    @Operation(summary = "잊은 비밀번호 변경", description = "해당 url로 요청시 잊은 비밀번호 변경 기능 수행 -> email 발송 후 수행되어야 함")
    public ResponseEntity changeForgetPassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) throws CustomException{
        return new ResponseEntity(userService.changeForgetPassword(userPasswordChangeDto), HttpStatus.OK);
    }

    @PostMapping("/body-information")
    @Operation(summary = "신체 정보 로드", description = "해당 url로 요청시 신체 정보 로드 기능 수행")
    public ResponseEntity getInformation(@RequestBody UserDto userDto) throws CustomException {
        return new ResponseEntity<>(userService.getBodyInformation(userDto), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    @Operation(summary = "비밀번호 변경", description = "해당 url로 요청시 비밀번호 변경 기능 수행 -> 로그인 이후 수행되어야 함")
    public ResponseEntity changePassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) throws CustomException {
        return new ResponseEntity(userService.changePassword(userPasswordChangeDto), HttpStatus.OK);
    }
}
