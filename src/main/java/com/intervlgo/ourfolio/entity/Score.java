package com.intervlgo.ourfolio.entity;

import com.intervlgo.ourfolio.dto.ScoreDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Table(name = "score")
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portFolio;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_pk")
    private User user;

    private Byte score;

    public ScoreDto toDto() {
        ScoreDto scoreDto = new ScoreDto();
        scoreDto.setUser(user.toDto());
        scoreDto.setPortfolio(portFolio.toDto());
        scoreDto.setScore(score);
        return scoreDto;
    }
}
