package HealthMe.HealthMe.domain.user.controller;


import HealthMe.HealthMe.common.dto.ErrorResponseDto;
import HealthMe.HealthMe.domain.user.service.EmailCreateService;
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
    private final EmailCreateService userService;

    @PostMapping("/verification-request")
    public ResponseEntity sendMessage(@RequestParam("email") String email) {
        userService.sendCodeToEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/verification")
    public ResponseEntity verificationEmail(@RequestParam("email") String email,
                                            @RequestParam("code") String authCode) {
        return new ResponseEntity<>(userService.verifiedCode(email, authCode), HttpStatus.OK);
    }
}
