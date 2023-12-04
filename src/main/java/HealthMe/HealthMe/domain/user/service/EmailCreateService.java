package HealthMe.HealthMe.domain.user.service;

import HealthMe.HealthMe.common.exception.CustomException;
import HealthMe.HealthMe.common.exception.ErrorCode;
import HealthMe.HealthMe.domain.user.domain.EmailSession;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.EmailDto;
import HealthMe.HealthMe.domain.user.repository.EmailRepositioy;
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
import java.util.Random;

// 11/25 추가 : 이메일 전송 기능
@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class EmailCreateService {
    private final UserRepository userRepository;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final EmailSendService emailService;
    private final EmailRepositioy emailRepositioy;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;

    public void sendCodeToEmail(String toEmail) throws CustomException, MessagingException {
        this.checkDuplicatedEmail(toEmail);
        String title = "Health Me 이메일 인증";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode, 0);

        EmailSession byEmail = emailRepositioy.findByEmail(toEmail);
        // 더티 체킹을 위한 엔티티 수정
        if(byEmail!=null) {
            byEmail.reSetVerifyCode(authCode, LocalDateTime.now());

        }
        else {
            emailRepositioy.save(EmailDto.builder()
                    .email(toEmail)
                    .verifyCode(authCode)
                    .createdTime(LocalDateTime.now())
                    .build().toEntity());
        }
    }
    public void sendPasswordResetEmail(String toEmail) throws CustomException, MessagingException {
        if(userRepository.findByEmail(toEmail)==null){
            throw new CustomException(ErrorCode.ACCOUNT_NOT_FOUND);
        }

        String title = "Health Me 이메일 인증";
        String authCode = this.createCode();

        emailService.sendEmail(toEmail, title, authCode, 1);

        EmailSession byEmail = emailRepositioy.findByEmail(toEmail);
        // 더티 체킹을 위한 엔티티 수정
        if(byEmail!=null) {
            byEmail.reSetVerifyCode(authCode, LocalDateTime.now());
        }
        else {
            emailRepositioy.save(EmailDto.builder()
                    .email(toEmail)
                    .verifyCode(authCode)
                    .createdTime(LocalDateTime.now())
                    .build().toEntity());
        }

    }
    private void checkDuplicatedEmail(String email) throws CustomException {
        User user = userRepository.findByEmail(email);
        if (user!=null) {
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
    public boolean verifiedCode(String email, String authCode, int flag) throws CustomException {
        if (flag == 0) {
            this.checkDuplicatedEmail(email);
        }
        EmailSession authInfo = emailRepositioy.findByEmail(email);
        String AuthCode = authInfo.getVerifyCode();

        LocalDateTime createdTime = authInfo.getCreatedTime();
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdTime, now);
        log.info("Duration = {}", duration.toMillis());
        boolean authResult = (duration.toMillis() <= authCodeExpirationMillis) && AuthCode.equals(authCode);
        if(authResult == true){
            emailRepositioy.delete(authInfo);
        }
        return authResult;
    }
}
