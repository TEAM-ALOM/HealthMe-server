package HealthMe.HealthMe.domain.preset.repository;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresetRepository extends JpaRepository<Preset, Long> {
    @Query("SELECT p FROM PRESET p WHERE p.user.id = :userId")  // 쿼리문 공부 현재 preset에 빨간 줄 그어져 있음 수정하기
    List<Preset> findByUserId(@Param("userId") Long userId);

    // List<Preset> findByUserId(Long userId); userId로 그 유저의 모든 preset 가져오기
    // @Query, @Param 같은 어노테이션 및 SQL 문으로 작성해야할 필요 있나?


}
