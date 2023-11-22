package com.intervlgo.ourfolio.repository;

import com.intervlgo.ourfolio.entity.Comment;
import com.intervlgo.ourfolio.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPortfolio(Portfolio portfolio);
}
