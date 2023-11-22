package com.intervlgo.ourfolio.repository;

import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.entity.Portfolio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface PortfolioRepositoryCustom {
    List<Portfolio> searchPortfolio(String username, String userId,
                                    String region, String occupation, Long viewCnt,
                                    LocalDateTime from, LocalDateTime to);
}
