package HealthMe.HealthMe.domain.user.controller;


import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.domain.user.service.EmailCreateService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("api/v1/email")
public class EmailController {
    private final EmailCreateService emailCreateService;

    @PostMapping("/verification-request")
    public ResponseEntity sendMessage(@RequestParam("email") String email) throws CustomException, MessagingException {
        emailCreateService.sendCodeToEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/verification")
    public ResponseEntity verificationEmail(@RequestParam("email") String email,
                                            @RequestParam("code") String authCode) throws CustomException{
        return new ResponseEntity<>(emailCreateService.verifiedCode(email, authCode, 0), HttpStatus.OK);
    }

    @PostMapping("/password-change-verification-request")
    public ResponseEntity sendPasswordChangeMessage(@RequestParam("email") String email) throws CustomException, MessagingException {
        emailCreateService.sendPasswordResetEmail(email);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/password-change-verification")
    public ResponseEntity verificationCode(@RequestParam("email") String email, @RequestParam("code") String autoCode) throws CustomException{
        return new ResponseEntity(emailCreateService.verifiedCode(email, autoCode, 1), HttpStatus.OK);
    }
}
