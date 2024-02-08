package HealthMe.HealthMe.exercise.repository;


import HealthMe.HealthMe.exercise.domain.ExerciseList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<ExerciseList, Long> {
    Optional<ExerciseList> findByName(@Param("exerciseName") String exerciseName);


}