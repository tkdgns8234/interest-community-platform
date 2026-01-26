package com.findy.user.out.repository.querydsl;

import com.findy.user.out.repository.entity.QUserEntity;
import com.findy.user.out.repository.entity.UserEntity;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<UserEntity> findAll(Long cursor, int size) {
        QUserEntity user = QUserEntity.userEntity;

        BooleanBuilder builder = new BooleanBuilder();
        if (cursor != null) {
            builder.and(user.id.lt(cursor));
        }

        return queryFactory
                .selectFrom(user)
                .where(builder)
                .orderBy(user.id.desc())
                .limit(size + 1)
                .fetch();
    }
}

