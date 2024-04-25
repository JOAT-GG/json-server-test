package com.example.demo.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMatchResult is a Querydsl query type for MatchResult
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchResult extends EntityPathBase<MatchResult> {

    private static final long serialVersionUID = 1303803122L;

    public static final QMatchResult matchResult = new QMatchResult("matchResult");

    public final StringPath champion = createString("champion");

    public final StringPath date = createString("date");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath matchId = createString("matchId");

    public final StringPath position = createString("position");

    public final BooleanPath win = createBoolean("win");

    public QMatchResult(String variable) {
        super(MatchResult.class, forVariable(variable));
    }

    public QMatchResult(Path<? extends MatchResult> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMatchResult(PathMetadata metadata) {
        super(MatchResult.class, metadata);
    }

}

