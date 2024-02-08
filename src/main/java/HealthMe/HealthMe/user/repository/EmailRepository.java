package HealthMe.HealthMe.user.repository;

import HealthMe.HealthMe.user.domain.EmailSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

// 11/25 추가 : 이메일 인증코드 저장용 리포지토리
public interface EmailRepository extends JpaRepository<EmailSession, Long> {
    Optional<EmailSession> findByEmail(@Param("email") String email);

}
