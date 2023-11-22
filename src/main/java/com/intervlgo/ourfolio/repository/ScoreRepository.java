package com.intervlgo.ourfolio.repository;

import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.Score;
import com.intervlgo.ourfolio.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> , ScoreRepositoryCustom {
    boolean existsByUserAndPortFolio(User user, Portfolio portFolio);

    List<Score> findByPortFolio(Portfolio portFolio);

}
