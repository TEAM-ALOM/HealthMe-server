package HealthMe.HealthMe.domain.exercise.repository;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseProgressList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExerciseProgressRepository extends JpaRepository<ExerciseProgressList, Long> {
    @Query("SELECT l FROM EXERCISE_PROGRESS_LIST l LEFT JOIN l.user u WHERE u.email = :userEmail")
    List<ExerciseProgressList> findByEmail(@Param("userEmail") String email);

    List<ExerciseProgressList> findExerciseProgressListsByDate(@Param("date") Date date);

}
