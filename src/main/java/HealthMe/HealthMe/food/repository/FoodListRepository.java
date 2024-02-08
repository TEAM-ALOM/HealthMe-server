package HealthMe.HealthMe.food.repository;

import HealthMe.HealthMe.food.domain.FoodList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FoodListRepository extends JpaRepository<FoodList, Long> {
    Optional<FoodList> findByName(@Param("name") String name);
    Page<FoodList> findAll(Pageable pageable);

}
