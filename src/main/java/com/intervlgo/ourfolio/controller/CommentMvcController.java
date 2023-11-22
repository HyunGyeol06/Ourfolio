package com.intervlgo.ourfolio.controller;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.CommentDto;
import com.intervlgo.ourfolio.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
@Slf4j
public class CommentMvcController {

    private final CommentService commentService;

    @PostMapping("/{userId}")
    public String writeComment(
            @PathVariable String userId,
            CommentDto commentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails
    ) {

        log.info(userId);
        commentService.postComment(commentDto,principalDetails, userId);

        return "redirect:/portfolio/"+userId;
    }
}
