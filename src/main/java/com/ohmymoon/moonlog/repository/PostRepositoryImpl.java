package com.ohmymoon.moonlog.repository;

import com.ohmymoon.moonlog.domain.Post;
import com.ohmymoon.moonlog.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Post> getList(Pageable pageable) {
        return jpaQueryFactory.selectFrom(QPost.post)
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(QPost.post.id.desc())
                .fetch();
    }
}
