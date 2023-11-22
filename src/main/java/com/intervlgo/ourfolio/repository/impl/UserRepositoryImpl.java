package com.intervlgo.ourfolio.repository.impl;

import com.intervlgo.ourfolio.entity.Portfolio;
import com.intervlgo.ourfolio.entity.QPortfolio;
import com.intervlgo.ourfolio.entity.QUser;
import com.intervlgo.ourfolio.entity.User;
import com.intervlgo.ourfolio.repository.PortfolioRepositoryCustom;
import com.intervlgo.ourfolio.repository.UserRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.intervlgo.ourfolio.entity.QUser.user;

@Repository
@Slf4j
public class UserRepositoryImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl (JPAQueryFactory queryFactory) {
        super(QUser.class);
        this.queryFactory = queryFactory;
    }
    @Override
    public Page<User> searchUser(Pageable pageable, String username, String region, String occupation) {
        JPAQuery<User> query = queryFactory.selectFrom(user)
                .where(containsUsername(username), eqRegion(region), eqOccupation(occupation));
        int totalCount = query.fetch().size();

        List<User> userList = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<User>(userList, pageable, totalCount);
    }

    private BooleanExpression containsUsername(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        return user.username.contains(username);
    }

    private BooleanExpression eqRegion(String region) {
        if (region == null || region.isEmpty()) {
            return null;
        }
        return user.region.eq(region);
    }

    private BooleanExpression eqOccupation(String occupation) {
        if (occupation == null || occupation.isEmpty()) {
            return null;
        }
        return user.occupation.eq(occupation);
    }
}
