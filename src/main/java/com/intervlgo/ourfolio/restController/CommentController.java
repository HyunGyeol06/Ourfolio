package com.intervlgo.ourfolio.restController;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.CommentDto;
import com.intervlgo.ourfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{userId}")
    public ResponseEntity<CommentDto> postComment(
            @RequestBody CommentDto commentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable(name = "userId") String userId
    ) {
        return commentService.postComment(commentDto, principalDetails, userId);
    }

    @GetMapping("/{portfolioWriterId}")
    public ResponseEntity<List<CommentDto>> getComments(
            Pageable pageable,
            @PathVariable(name="portfolioWriterId") String portfolioWriterId
    ) {
        return commentService.getCommentList(portfolioWriterId);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @RequestBody CommentDto commentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable(name = "commentId") Long commentId
    ) {
        return commentService.updateComment(commentDto, principalDetails, commentId);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable(name = "commentId") Long commentId
    ) {
        return commentService.deleteComment(principalDetails, commentId);
    }

}
