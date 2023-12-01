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

    List<Preset> findByUserEmail(@Param("email") String email); // preset 다 받아오기 되나?
    // Preset findByUserEmail(@Param("email") String email); 이렇게 해도 오류 안생기던데
    //PRESET에 Email 없음 -> join 으로  USER 테이블로부터 정보 받아와야 하나?
}
