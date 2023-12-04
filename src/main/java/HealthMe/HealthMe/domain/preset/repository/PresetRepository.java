package HealthMe.HealthMe.domain.preset.repository;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import HealthMe.HealthMe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresetRepository extends JpaRepository<Preset, Long> {
    @Query("SELECT p FROM PRESET p WHERE p.user.id = :userId")
        // 쿼리문 공부 현재 preset에 빨간 줄 그어져 있음 수정하기 -> 테이블 다 대문자
    List<Preset> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM PRESET p LEFT JOIN p.user u WHERE u.email = :email")
    List<Preset> findByUserEmail(@Param("email") String email);

}
