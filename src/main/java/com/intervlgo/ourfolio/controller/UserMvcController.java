package com.intervlgo.ourfolio.controller;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.CommentDto;
import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.dto.UserDto;
import com.intervlgo.ourfolio.dto.UserIdPasswordDto;
import com.intervlgo.ourfolio.entity.User;
import com.intervlgo.ourfolio.service.CommentService;
import com.intervlgo.ourfolio.service.PortfolioService;
import com.intervlgo.ourfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserMvcController {

    private final UserService userService;

    @GetMapping("/profile")
    public String profile(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();

        model.addAttribute("username", user.getUsername());
        model.addAttribute("userId", user.getUserId());
        model.addAttribute("job", user.getIsHavingJob() ? "취업 축하드려요":"열심히 해봐요");
        model.addAttribute("occupation", user.getOccupation());
        model.addAttribute("region", user.getRegion());

        return "profile";

    }

    @PutMapping("/information")
    public String editUserInfo(UserDto request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        userService.updateUser(request, principalDetails);
        log.info(request.toString());
        return "redirect:/user/profile";
    }

    @GetMapping("/info-edit-form")
    public String editInfoForm(Model model) {
        return "editUserInfo";
    }

    @PutMapping("/auth-information")
    public String editUserInfo(UserIdPasswordDto request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info(request.toString());
        userService.updateIdPassword(request, principalDetails);
        return "redirect:/user/profile";
    }

    @GetMapping("/info-auth-form")
    public String editAuthForm(Model model) {
        return "editAuthInfo";
    }

}
