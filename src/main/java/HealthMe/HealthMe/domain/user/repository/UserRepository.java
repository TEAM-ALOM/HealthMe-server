package HealthMe.HealthMe.domain.user.repository;

import HealthMe.HealthMe.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {



}
