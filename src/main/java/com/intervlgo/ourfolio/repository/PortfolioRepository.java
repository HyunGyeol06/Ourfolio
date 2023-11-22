package com.intervlgo.ourfolio.repository;

import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>, PortfolioRepositoryCustom {
    Optional<Portfolio> findByUser(User user);
    boolean existsByUser(User user);

    Optional<Portfolio> findByUser_UserId(String userId);


}
