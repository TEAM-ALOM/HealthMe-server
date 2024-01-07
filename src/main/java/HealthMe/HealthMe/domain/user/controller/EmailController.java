package HealthMe.HealthMe.domain.user.controller;
import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.user.dto.EmailDto;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.service.EmailCreateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/email")
public class EmailController {
    private final EmailCreateService emailCreateService;

    @PostMapping("/verification-request")
    public ResponseEntity sendMessage(@RequestBody EmailDto emailDto) throws CustomException, javax.mail.MessagingException {
        UserDto userDto = emailCreateService.sendCodeToEmail(emailDto.getEmail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
    @PostMapping("/verification")
    public ResponseEntity verificationEmail(@RequestBody EmailDto emailDto) throws CustomException{
        return new ResponseEntity<>(emailCreateService.verifiedCode(emailDto.getEmail(), emailDto.getVerifyCode(), 0), HttpStatus.OK);
    }

    @PostMapping("/password-change-verification-request")
    public ResponseEntity sendPasswordChangeMessage(@RequestBody EmailDto emailDto) throws CustomException, javax.mail.MessagingException {
        return new ResponseEntity(emailCreateService.sendPasswordResetEmail(emailDto.getEmail()),HttpStatus.OK);
    }

    @PostMapping("/password-change-verification")
    public ResponseEntity verificationCode(@RequestBody EmailDto emailDto) throws CustomException{
        return new ResponseEntity(emailCreateService.verifiedCode(emailDto.getEmail(), emailDto.getVerifyCode(), 1), HttpStatus.OK);
    }
}
