package HealthMe.HealthMe.domain.user.repository;

import HealthMe.HealthMe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository <User, Long> {
    User findByEmail(@Param("email") String email);

}
