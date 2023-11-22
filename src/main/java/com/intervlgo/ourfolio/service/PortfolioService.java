package com.intervlgo.ourfolio.service;

import com.intervlgo.ourfolio.auth.PrincipalDetails;
import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.User;
import com.intervlgo.ourfolio.repository.PortfolioRepository;
import com.intervlgo.ourfolio.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final FileService fileService;

    public ResponseEntity<PortfolioDto> postPortfolio(PortfolioDto request, PrincipalDetails principalDetails, MultipartFile pdf, MultipartFile img) {
        HttpStatus status = HttpStatus.OK;

        User user = principalDetails.getUser();

        if (portfolioRepository.existsByUser(user)) {
            return this.updatePortfolio(principalDetails, request.getContent(), request.getPortFolioPageUrl(), pdf, img);
        }
        String portfolioFileName = null;
        String portfolioOriName = null;
        String imgFileName = null;
        String imgOriName = null;

        try {
            portfolioFileName = fileService.uploadFile(pdf);
            portfolioOriName = pdf.getOriginalFilename();
            imgFileName = fileService.uploadFile(img);
            imgOriName = img.getOriginalFilename();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .content(request.getContent())
                .portfolioPageUrl(request.getPortFolioPageUrl())
                .portfolioFileName(portfolioFileName)
                .portfolioOriName(portfolioOriName)
                .imgFileName(imgFileName)
                .imgOriName(imgOriName)
                .viewCnt(0L)
                .build();
        portfolioRepository.save(portfolio);

        PortfolioDto body = portfolio.toDto();

        return new ResponseEntity<>(body, status);
    }

    @Transactional
    public ResponseEntity<PortfolioDto> updatePortfolio(PrincipalDetails principalDetails, String content, String portfolioPageUrl, MultipartFile pdf, MultipartFile img) {
        User user =  principalDetails.getUser();

        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser(user);
        if (optionalPortfolio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Portfolio portfolio = optionalPortfolio.get();
        String portfolioFileName = null;
        String portfolioOriName = null;
        String imgFileName = null;
        String imgOriName = null;

        if (pdf != null) {
            try {
                portfolioFileName = fileService.uploadFile(pdf);
                portfolioOriName = pdf.getOriginalFilename();
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        if (img != null) {
            try {
                imgFileName = fileService.uploadFile(img);
                imgOriName = img.getOriginalFilename();
            } catch (IOException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
        portfolio.update(content, portfolioPageUrl, portfolioFileName, portfolioOriName, imgFileName, imgOriName);

        return ResponseEntity.ok(portfolio.toDto());

    }

    public ResponseEntity<List<PortfolioDto>> getPortfolioes(String username, String userId, String region, String occupation,
                                                             Long viewCnt, LocalDateTime from, LocalDateTime to) {
        HttpStatus status = HttpStatus.OK;

        List<Portfolio> portfolioList = portfolioRepository.searchPortfolio(username, userId, region, occupation, viewCnt, from, to);

        List<PortfolioDto> body = portfolioList.stream().map(Portfolio::toDto).toList();

        return new ResponseEntity<>(body, status);
    }

    @Transactional
    public ResponseEntity<PortfolioDto> getPortfolio(String userId) {
        HttpStatus status = HttpStatus.OK;

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        User user = optionalUser.get();

        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser_UserId(userId);
        if (optionalPortfolio.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        Portfolio portfolio = optionalPortfolio.get();
        portfolio.view();

        PortfolioDto body = portfolio.toDto();

        return new ResponseEntity<>(body, status);
    }

    public ResponseEntity<InputStreamResource> getPDF(String userId) {
        HttpStatus status = HttpStatus.OK;

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        User user = optionalUser.get();

        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser_UserId(userId);
        if (optionalPortfolio.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        Portfolio portfolio = optionalPortfolio.get();

        try {
            InputStreamResource file = new InputStreamResource(fileService.downloadFile(portfolio.getPortfolioFileName()).getInputStream());
            return new ResponseEntity<>(file, status);
        } catch (IOException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(status);
        }
    }

    public ResponseEntity<InputStreamResource> getIMG(String userId) {
        HttpStatus status = HttpStatus.OK;

        Optional<User> optionalUser = userRepository.findByUserId(userId);
        if (optionalUser.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        User user = optionalUser.get();

        Optional<Portfolio> optionalPortfolio = portfolioRepository.findByUser_UserId(userId);
        if (optionalPortfolio.isEmpty()) {
            status = HttpStatus.NOT_FOUND;
            return new ResponseEntity<>(status);
        }
        Portfolio portfolio = optionalPortfolio.get();

        try {
            InputStreamResource file = new InputStreamResource(fileService.downloadFile(portfolio.getImgFileName()).getInputStream());
            return new ResponseEntity<>(file, status);
        } catch (IOException e) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<>(status);
        }
    }
}
