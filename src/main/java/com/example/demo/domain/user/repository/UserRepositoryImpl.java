package com.example.demo.domain.user.repository;

import com.example.demo.domain.user.entity.QUser;
import com.example.demo.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

//    @Override
//    public Optional<User> searchByUsername(String username) {
//        QUser user = QUser.user;
//
//        return Optional.ofNullable(
//                jpaQueryFactory.selectFrom(user)
//                        .where(user.username.eq(username))
//                        .fetchFirst());
//    }
}
