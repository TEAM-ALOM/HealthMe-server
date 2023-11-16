package HealthMe.HealthMe.domain.exercise.repository;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseProgressRepository extends JpaRepository<ExerciseProgressList, Long> {
}
