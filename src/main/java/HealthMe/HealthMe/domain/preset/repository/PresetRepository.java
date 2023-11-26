package HealthMe.HealthMe.domain.preset.repository;

import HealthMe.HealthMe.domain.preset.domain.Preset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresetRepository extends JpaRepository<Preset, Long> {


}
