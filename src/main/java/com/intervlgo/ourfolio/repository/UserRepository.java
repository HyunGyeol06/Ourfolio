package com.intervlgo.ourfolio.repository;

import com.intervlgo.ourfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , UserRepositoryCustom{
    boolean existsByUserId(String userId);
    Optional<User> findByUserId(String userId);


}
