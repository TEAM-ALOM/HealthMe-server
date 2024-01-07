package HealthMe.HealthMe.domain.food.repository;

import HealthMe.HealthMe.domain.food.domain.IngestionList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IngestionListRepository extends JpaRepository<IngestionList, Long> {
    @Query("SELECT i from INGESTION_LIST i LEFT JOIN i.user u where u.email = :email")
    List<IngestionList> findByEmail(@Param("email") String email);
    List<IngestionList> findByDate(@Param("date") Date date);
}

