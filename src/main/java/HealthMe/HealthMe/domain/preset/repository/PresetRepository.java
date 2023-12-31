package HealthMe.HealthMe.domain.preset.repository;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PresetRepository extends JpaRepository<Preset, Long> {
    @Query("SELECT p FROM PRESET p LEFT JOIN p.user u WHERE u.email = :email")
    List<Preset> findByUserEmail(@Param("email") String email);

}
