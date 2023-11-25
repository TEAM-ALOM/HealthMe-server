package HealthMe.HealthMe.domain.user.service;

import HealthMe.HealthMe.domain.user.domain.EmailSession;
import HealthMe.HealthMe.domain.user.domain.User;
import HealthMe.HealthMe.domain.user.dto.EmailDto;
import HealthMe.HealthMe.domain.user.repository.EmailRepositioy;
import HealthMe.HealthMe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
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

    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "Health me 이메일 인증 번호";
        String authCode = this.createCode();
        emailService.sendEmail(toEmail, title, authCode);


        EmailSession byEmail = emailRepositioy.findByEmail(toEmail);
        if(byEmail!=null) {
            byEmail.setVerifyCode(authCode);
        }
        else {
            emailRepositioy.save(EmailDto.builder()
                    .email(toEmail)
                    .verifyCode(authCode)
                    .createdTime(LocalDateTime.now())
                    .build().toEntity());
        }
    }

    private void checkDuplicatedEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user!=null) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new RuntimeException();
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

    public boolean verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
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
