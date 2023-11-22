package com.intervlgo.ourfolio.controller;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.dto.UserDto;
import com.intervlgo.ourfolio.service.FileService;
import com.intervlgo.ourfolio.service.PortfolioService;
import com.intervlgo.ourfolio.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final PortfolioService portfolioService;

    private final UserService userService;

    @GetMapping("/")
    public String mainPage(Model model) throws IOException {
        List<PortfolioDto> list = portfolioService.getPortfolioes(null, null, null, null, null, LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.now()).getBody();

        model.addAttribute("title1", list.get(0).getUser().getUsername());
        model.addAttribute("img1", "/files/" + list.get(0).getImgName());
        model.addAttribute("desc1", list.get(0).getContent().substring(0, list.get(0).getContent().length()>=30?29:list.get(0).getContent().length()));

        return "main";
    }

    @GetMapping("/main")
    public String loginMain(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        List<PortfolioDto> list = portfolioService.getPortfolioes(null, null, null, null, null, LocalDateTime.of(1999, 1, 1, 1, 1), LocalDateTime.now()).getBody();

        model.addAttribute("title1", list.get(0).getUser().getUsername());
        model.addAttribute("img1", "/files/" + list.get(0).getImgName());
        model.addAttribute("desc1", list.get(0).getContent().substring(0, list.get(0).getContent().length()>=30?29:list.get(0).getContent().length()));

        return "loginMain";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(UserDto user){
        userService.signUp(user);
        log.info(user.toString());

        return "redirect:/loginForm";
    }

    @GetMapping(value="/logout")
    public String logoutMainGET(HttpServletRequest request) throws Exception{
        HttpSession session = request.getSession();
        session.invalidate();

        return "redirect:/";

    }
}
