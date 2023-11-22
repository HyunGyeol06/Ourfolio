package com.intervlgo.ourfolio.repository.impl;

import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.QScore;
import com.intervlgo.ourfolio.entity.QUser;
import com.intervlgo.ourfolio.repository.PortfolioRepositoryCustom;
import com.intervlgo.ourfolio.repository.ScoreRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.intervlgo.ourfolio.entity.QScore.score1;

@Repository
public class ScoreRepositoryImpl extends QuerydslRepositorySupport implements ScoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ScoreRepositoryImpl (JPAQueryFactory queryFactory) {
        super(QScore.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public Double getAverageOfScore(Portfolio portfolio) {
        JPAQuery<Double> query = queryFactory.select(score1.score.avg()).from(score1).where(eqPortfolio(portfolio));
        Double averageOfScore = query.fetch().get(0);

        return averageOfScore;
    }

    private BooleanExpression eqPortfolio(Portfolio portfolio) {
        if (portfolio == null) {
            return null;
        }
        return score1.portFolio.eq(portfolio);
    }
}
