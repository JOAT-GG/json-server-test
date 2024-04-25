package com.example.demo.repository;

import static com.example.demo.domain.QMatchResult.*;

import com.example.demo.domain.ChampionWinRate;
import com.example.demo.domain.PositionWinRate;
import com.example.demo.domain.TotalWinRate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MatchResultQueryRepository {

    private final JPAQueryFactory query;

    public TotalWinRate getTotalWinRate() {
        return query
                .select(Projections.constructor(
                        TotalWinRate.class,
                        matchResult.win.when(true).then(1).otherwise(0).sum(),
                        matchResult.win.when(false).then(1).otherwise(0).sum()
                )).from(matchResult)
                .fetchOne();
    }

    public List<ChampionWinRate> getChampionWinLoseRate() {
        return query
                .select(Projections.constructor(
                        ChampionWinRate.class,
                        matchResult.champion,
                        matchResult.win.when(true).then(1).otherwise(0).sum(),
                        matchResult.win.count()
                )).from(matchResult)
                .groupBy(matchResult.champion)
                .fetch();
    }

    public List<PositionWinRate> getPositionWinRate() {
        return query
                .select(Projections.constructor(
                        PositionWinRate.class,
                        matchResult.position,
                        matchResult.win.when(true).then(1).otherwise(0).sum(),
                        matchResult.win.count()
                )).from(matchResult)
                .groupBy(matchResult.position)
                .fetch();
    }
}
