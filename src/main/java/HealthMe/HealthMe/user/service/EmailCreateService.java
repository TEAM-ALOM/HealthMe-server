package HealthMe.HealthMe.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.user.domain.EmailSession;
import HealthMe.HealthMe.user.dto.EmailDto;
import HealthMe.HealthMe.user.dto.UserDto;
import HealthMe.HealthMe.user.repository.EmailRepository;
import HealthMe.HealthMe.user.repository.UserRepository;
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
import java.util.Random;

// 11/25 추가 : 이메일 전송 기능
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailCreateService {
    private final UserRepository userRepository;
    private final EmailSendService emailService;
    private final EmailRepository emailRepository;
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

        EmailSession byEmail = emailRepository.findByEmail(toEmail)
                .orElseGet(() -> emailRepository.save(EmailDto.builder()
                        .email(toEmail)
                        .verifyCode(authCode)
                        .createdTime(LocalDateTime.now())
                        .build().toEntity()));

        byEmail.reSetVerifyCode(authCode, LocalDateTime.now());
        return user;
    }
    public UserDto sendPasswordResetEmail(String toEmail) throws CustomException, MessagingException {
        if(userRepository.findByEmail(toEmail).isEmpty()){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        String title = "Health Me 이메일 인증";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode, 1);

        EmailSession byEmail = emailRepository.findByEmail(toEmail)
                .orElseGet(()-> emailRepository.save(EmailDto.builder()
                .email(toEmail)
                .verifyCode(authCode)
                .createdTime(LocalDateTime.now())
                .build().toEntity()));


        byEmail.reSetVerifyCode(authCode, LocalDateTime.now());
        return UserDto.builder()
                .email(toEmail)
                .build();
    }
    private void checkDuplicatedEmail(String email) throws CustomException {
        if(userRepository.findByEmail(email).isPresent()){
            throw new CustomException(ErrorCode.DUPLICATED_EMAIL);
        }
    }
    private String createCode() {
        int length = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
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
        EmailSession authInfo = emailRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        String AuthCode = authInfo.getVerifyCode();

        LocalDateTime createdTime = authInfo.getCreatedTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdTime, now);

        boolean authResult = (duration.toMillis() <= authCodeExpirationMillis) && AuthCode.equals(authCode);
        if(duration.toMillis() > authCodeExpirationMillis){
            throw new CustomException(ErrorCode.EMAIL_VERIFY_TIME_OVER);
        }
        else if(!AuthCode.equals(authCode)){
            throw new CustomException(ErrorCode.VERIFY_NOT_ALLOWED);
        }

        if(authResult == true){
            emailRepository.delete(authInfo);
        }

        EmailDto emailDto = EmailDto.builder()
                .email(email)
                .authResult(authResult)
                .build();
        return emailDto;
    }
}
