package com.intervlgo.ourfolio.controller;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.service.CommentService;
import com.intervlgo.ourfolio.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/portfolio")
@Slf4j
public class PortfolioMvcController {

    private final PortfolioService portfolioService;

    private final CommentService commentService;

    @GetMapping("/list")
    public String portfolioList(Model model) {
        List<PortfolioDto> list = portfolioService.getPortfolioes(
                null, null, null, null, null,
                null, null
        ).getBody();

        list =  list.stream().peek(
                portfolioDto -> portfolioDto.setImgName("/files/" +portfolioDto.getImgName())
        ).toList();
        model.addAttribute("portfolios", list);

        return "portfolio";
    }

    @GetMapping("/{userId}")
    public String portfolioDetail(Model model, @PathVariable String userId) {
        PortfolioDto portfolioDto = portfolioService.getPortfolio(userId).getBody();

        log.info(portfolioDto.toString());

        model.addAttribute("title", portfolioDto.getUser().getUsername());
        model.addAttribute("desc", portfolioDto.getContent());
        model.addAttribute("occupation", portfolioDto.getUser().getOccupation());
        model.addAttribute("pdfName", "/files/"+ portfolioDto.getFileName());

        model.addAttribute("comments", commentService.getCommentList(portfolioDto.getUser().getUserId()).getBody());

        model.addAttribute("userId",userId);


        return "portfolioDetail";
    }

    @GetMapping("/uploadForm")
    public String portfolioUploadForm() {
        return "portfolioUploadForm";
    }

    @PostMapping("/upload")
    public String portfolioUpload(
            PortfolioDto portfolioDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            MultipartFile pdf,
            MultipartFile img
            ) {

        portfolioService.postPortfolio(portfolioDto, principalDetails, pdf, img);

        return "redirect:/portfolio/"+principalDetails.getUser().getUserId();
    }
}
