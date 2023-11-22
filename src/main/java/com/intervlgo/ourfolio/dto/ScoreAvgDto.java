package com.intervlgo.ourfolio.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;

@Getter
@Setter
public class ScoreAvgDto {
    private PortfolioDto portfolio;
    private Double avgOfScore;
}
