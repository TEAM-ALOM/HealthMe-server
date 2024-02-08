package HealthMe.HealthMe.user.controller;
import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.user.dto.EmailDto;
import HealthMe.HealthMe.user.dto.UserDto;
import HealthMe.HealthMe.user.service.EmailCreateService;
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
@RequestMapping("api/email")
@Tag(name = "이메일 발송 관련 컨트롤러")
public class EmailController {
    private final EmailCreateService emailCreateService;

    @PostMapping("/verification-request")
    @Operation(summary = "회원가입 이메일 전송", description = "해당 url로 요청시 회원가입 이메일 전송")
    public ResponseEntity sendMessage(@RequestBody EmailDto emailDto) throws CustomException, javax.mail.MessagingException {
        UserDto userDto = emailCreateService.sendCodeToEmail(emailDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/verification")
    @Operation(summary = "회원가입 이메일 검증", description = "해당 url로 요청시 회원가입 이메일에 적힌 코드를 검증")
    public ResponseEntity verificationEmail(@RequestBody EmailDto emailDto) throws CustomException{
        return new ResponseEntity<>(emailCreateService.verifiedCode(emailDto.getEmail(), emailDto.getVerifyCode(), 0), HttpStatus.OK);
    }

    @PostMapping("/password-change-verification-request")
    @Operation(summary = "비밀번호 변경 이메일 전송", description = "해당 url로 요청시 비밀번호 변경 이메일 전송")
    public ResponseEntity sendPasswordChangeMessage(@RequestBody EmailDto emailDto) throws CustomException, javax.mail.MessagingException {
        return new ResponseEntity(emailCreateService.sendPasswordResetEmail(emailDto.getEmail()),HttpStatus.OK);
    }

    @PostMapping("/password-change-verification")
    @Operation(summary = "비밀번호 변경 이메일 검증", description = "해당 url로 요청시 비밀번호 변경 이메일에 적힌 코드를 검증")
    public ResponseEntity verificationCode(@RequestBody EmailDto emailDto) throws CustomException{
        return new ResponseEntity(emailCreateService.verifiedCode(emailDto.getEmail(), emailDto.getVerifyCode(), 1), HttpStatus.OK);
    }
}
