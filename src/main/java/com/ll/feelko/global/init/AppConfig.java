package com.ll.feelko.global.init;

import com.querydsl.jpa.JPQLQueryFactory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class AppConfig {

    @Bean
    public JPQLQueryFactory jpqlQueryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }

}
