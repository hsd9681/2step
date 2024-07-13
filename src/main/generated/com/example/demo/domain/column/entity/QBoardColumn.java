package com.example.demo.domain.column.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBoardColumn is a Querydsl query type for BoardColumn
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoardColumn extends EntityPathBase<BoardColumn> {

    private static final long serialVersionUID = -1251865447L;

    public static final QBoardColumn boardColumn = new QBoardColumn("boardColumn");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> order = createNumber("order", Long.class);

    public QBoardColumn(String variable) {
        super(BoardColumn.class, forVariable(variable));
    }

    public QBoardColumn(Path<? extends BoardColumn> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoardColumn(PathMetadata metadata) {
        super(BoardColumn.class, metadata);
    }

}

