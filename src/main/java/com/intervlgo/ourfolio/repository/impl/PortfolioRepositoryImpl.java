package com.intervlgo.ourfolio.repository.impl;

import com.intervlgo.ourfolio.dto.PortfolioDto;
import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.QPortfolio;
import com.intervlgo.ourfolio.repository.PortfolioRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.intervlgo.ourfolio.entity.QPortfolio.portfolio;

@Repository
public class PortfolioRepositoryImpl extends QuerydslRepositorySupport implements PortfolioRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PortfolioRepositoryImpl (JPAQueryFactory queryFactory) {
        super(QPortfolio.class);
        this.queryFactory = queryFactory;
    }

    @Override
    public List<Portfolio> searchPortfolio( String username, String userId,
                                              String region, String occupation, Long viewCnt,
                                              LocalDateTime from, LocalDateTime to) {
        JPAQuery<Portfolio> query = queryFactory.select(portfolio).from(portfolio)
                .where(eqUsername(username), eqUserId(userId), eqRegion(region), eqOccupation(occupation), goeViewCnt(viewCnt),
                        betweenModifiedAt(from, to));


        return query.fetch();
    }
    
    private BooleanExpression eqUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        return portfolio.user.username.eq(username);
    }

    private BooleanExpression eqUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            return null;
        }
        return portfolio.user.userId.eq(userId);
    }

    private BooleanExpression eqRegion(String region) {
        if (region == null || region.isEmpty()) {
            return null;
        }
        return portfolio.user.region.eq(region);
    }

    private BooleanExpression eqOccupation(String occupation) {
        if (occupation == null || occupation.isEmpty()) {
            return null;
        }
        return portfolio.user.occupation.eq(occupation);
    }

    private BooleanExpression goeViewCnt(Long viewCnt) {
        if (viewCnt == null || viewCnt <= 0) {
            return null;
        }
        return portfolio.viewCnt.goe(viewCnt);
    }

    private BooleanExpression betweenModifiedAt(LocalDateTime from, LocalDateTime to) {
        if (from == null || to == null) {
            return null;
        }
        return portfolio.modifiedAt.between(from, to);
    }

    private Portfolio toPortfolio(Tuple tuple) {
        Portfolio entity = Portfolio.builder()
                .user(tuple.get(portfolio.user))
                .viewCnt(tuple.get(portfolio.viewCnt))
                .build();
        entity.setCreatedAt(tuple.get(portfolio.createdAt));
        entity.setModifiedAt(tuple.get(portfolio.modifiedAt));

        return entity;
    }

}
