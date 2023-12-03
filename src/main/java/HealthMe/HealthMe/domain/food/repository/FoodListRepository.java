package HealthMe.HealthMe.domain.food.repository;

import HealthMe.HealthMe.domain.food.domain.FoodList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface FoodListRepository extends JpaRepository<FoodList, Long> {


    FoodList findByName(@Param("name") String name);
}
