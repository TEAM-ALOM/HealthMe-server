package HealthMe.HealthMe.domain.food.repository;

import HealthMe.HealthMe.domain.exercise.domain.ExerciseList;
import HealthMe.HealthMe.domain.food.domain.IngestionList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngestionListRepository extends JpaRepository<IngestionList, Long> {


}

