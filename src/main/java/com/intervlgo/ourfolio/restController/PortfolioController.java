package com.intervlgo.ourfolio.restController;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.dto.ScoreAvgDto;
import com.intervlgo.ourfolio.dto.ScoreDto;
import com.intervlgo.ourfolio.service.PortfolioService;
import com.intervlgo.ourfolio.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;
    private final ScoreService scoreService;

//    @PostMapping("")
//    public ResponseEntity<PortfolioDto> postPortfolio(
//            @RequestBody PortfolioDto portfolioDto,
//            @AuthenticationPrincipal PrincipalDetails principalDetails
//    ) {
//        return portfolioService.postPortfolio(portfolioDto, principalDetails);
//    }
/*
    @GetMapping("/list")
    public ResponseEntity<Page<PortfolioDto>> getPortfolioList(
            Pageable pageable,
            @RequestParam(name = "writer-username", required = false) String username,
            @RequestParam(name = "writer-userId", required = false) String userId,
            @RequestParam(name ="writer-region", required = false) String region,
            @RequestParam(name = "writer-occupation", required = false) String occupation,
            @RequestParam(name = "view-cnt", required = false) Long viewCnt,
            @RequestParam(name = "from", defaultValue = "2000-01-01 00")String from,
            @RequestParam(name = "to", defaultValue = "3000-12-31 23")String to
    ) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");
        return portfolioService.getPortfolioes(pageable, username, userId,
                region, occupation, viewCnt,
                LocalDateTime.parse(from,formatter), LocalDateTime.parse(to, formatter));
    }
 */

    @GetMapping("/{userId}")
    public ResponseEntity<PortfolioDto> getPortfolio(@PathVariable(name = "userId") String userId) {
        return portfolioService.getPortfolio(userId);
    }


    @PostMapping("/{userId}/score")
    public ResponseEntity<ScoreDto> giveScore(
           @Validated @RequestBody ScoreDto scoreDto,
            @PathVariable(name = "userId") String portfolioWriterId,
           @AuthenticationPrincipal PrincipalDetails principalDetails

    ) {
        return scoreService.giveScore(scoreDto, portfolioWriterId, principalDetails);
    }

    @GetMapping("/{userId}/score")
    public ResponseEntity<ScoreAvgDto> getAvgScore(
            @PathVariable(name = "userId") String portfolioWriterId
    ) {
        return scoreService.getScores(portfolioWriterId);
    }


}
