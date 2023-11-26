package HealthMe.HealthMe.domain.user.repository;

import HealthMe.HealthMe.domain.user.domain.EmailSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

// 11/25 추가 : 이메일 인증코드 저장용 리포지토리
public interface EmailRepositioy extends JpaRepository<EmailSession, Long> {
    EmailSession findByEmail(@Param("email") String email);
    EmailSession deleteByEmail(@Param("email") String email);
}
