package com.intervlgo.ourfolio.service;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.CommentDto;
import com.intervlgo.ourfolio.entity.Comment;
import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.User;
import com.intervlgo.ourfolio.repository.CommentRepository;
import com.intervlgo.ourfolio.repository.PortfolioRepository;
import com.intervlgo.ourfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final CommentRepository commentRepository;

    public ResponseEntity<CommentDto> postComment(CommentDto request, PrincipalDetails principalDetails, String portfolioWriterId) {
        User portfolioWriter = userRepository.findByUserId(portfolioWriterId).get();
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(portfolioWriter);
        if (optionalPortfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Portfolio portfolio = optionalPortfolio.get();

        User commentWriter = principalDetails.getUser();

        Comment comment = Comment.builder()
                .user(commentWriter)
                .portfolio(portfolio)
                .content(request.getContent())
                .build();
        commentRepository.save(comment);

        CommentDto body = comment.toDto();

        return ResponseEntity.ok(body);
    }

    public ResponseEntity<List<CommentDto>> getCommentList(String portfolioWriterId) {
        User portfolioWriter = userRepository.findByUserId(portfolioWriterId).get();
        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(portfolioWriter);
        if (optionalPortfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Portfolio portfolio = optionalPortfolio.get();

        List<Comment> commentPage = commentRepository.findByPortfolio(portfolio);

        List<CommentDto> body = commentPage.stream().map(Comment::toDto).toList();

        return ResponseEntity.ok(body);
    }

    @Transactional
    public ResponseEntity<CommentDto> updateComment(CommentDto request, PrincipalDetails principalDetails, Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        User requester = principalDetails.getUser();
        if(comment.getUser().equals(requester)) {
            comment.update(request.getContent());
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        CommentDto body = comment.toDto();
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<CommentDto> deleteComment(PrincipalDetails principalDetails, Long commentId) {
        Comment comment = commentRepository.findById(commentId).get();
        User requester = principalDetails.getUser();
        if(comment.getUser().equals(requester)) {
            commentRepository.delete(comment);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        CommentDto body = comment.toDto();
        return ResponseEntity.ok(body);
    }
}
