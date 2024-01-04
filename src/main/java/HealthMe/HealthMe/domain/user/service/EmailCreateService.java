package HealthMe.HealthMe.domain.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.user.domain.EmailSession;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.EmailDto;
import HealthMe.HealthMe.domain.user.dto.UserDto;
import HealthMe.HealthMe.domain.user.repository.EmailRepository;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import javax.mail.MessagingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

// 11/25 추가 : 이메일 전송 기능
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailCreateService {
    private final UserRepository userRepository;
    private final EmailSendService emailService;
    private final EmailRepository emailRepositioy;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public UserDto sendCodeToEmail(String toEmail) throws CustomException, MessagingException {
        this.checkDuplicatedEmail(toEmail);
        String title = "Health Me 이메일 인증";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode, 0);
        UserDto user = UserDto.builder()
                .email(toEmail)
                .build();

        EmailSession byEmail = emailRepositioy.findByEmail(toEmail)
                .orElseGet(()-> emailRepositioy.save(EmailDto.builder()
                .email(toEmail)
                .verifyCode(authCode)
                .createdTime(LocalDateTime.now())
                .build().toEntity()));

        byEmail.reSetVerifyCode(authCode, LocalDateTime.now());
        return user;
    }
    public void sendPasswordResetEmail(String toEmail) throws CustomException, MessagingException {
        if(userRepository.findByEmail(toEmail).isEmpty()){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        String title = "Health Me 이메일 인증";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode, 1);

        EmailSession byEmail = emailRepositioy.findByEmail(toEmail)
                .orElseGet(()->emailRepositioy.save(EmailDto.builder()
                .email(toEmail)
                .verifyCode(authCode)
                .createdTime(LocalDateTime.now())
                .build().toEntity()));


        byEmail.reSetVerifyCode(authCode, LocalDateTime.now());

    }
    private void checkDuplicatedEmail(String email) throws CustomException {
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new RuntimeException();
        }
    }

    // 이메일 인증
    public EmailDto verifiedCode(String email, String authCode, int flag) throws CustomException {
        if (flag == 0) {
            this.checkDuplicatedEmail(email);
        }
        EmailSession authInfo = emailRepositioy.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
        String AuthCode = authInfo.getVerifyCode();

        LocalDateTime createdTime = authInfo.getCreatedTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdTime, now);

        boolean authResult = (duration.toMillis() <= authCodeExpirationMillis) && AuthCode.equals(authCode);
        if(authResult == true){
            emailRepositioy.delete(authInfo);
        }
        else{
            throw new CustomException(ErrorCode.VERIFY_NOT_ALLOWED);
        }
        EmailDto emailDto = EmailDto.builder()
                .email(email)
                .authResult(authResult)
                .build();
        return emailDto;
    }
}
