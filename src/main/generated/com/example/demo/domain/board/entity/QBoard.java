package com.example.demo.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -340161997L;

    public static final QBoard board = new QBoard("board");

    public final StringPath boardName = createString("boardName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath intro = createString("intro");

    public final ListPath<com.example.demo.domain.permission.entity.Permission, com.example.demo.domain.permission.entity.QPermission> permissions = this.<com.example.demo.domain.permission.entity.Permission, com.example.demo.domain.permission.entity.QPermission>createList("permissions", com.example.demo.domain.permission.entity.Permission.class, com.example.demo.domain.permission.entity.QPermission.class, PathInits.DIRECT2);

    public QBoard(String variable) {
        super(Board.class, forVariable(variable));
    }

    public QBoard(Path<? extends Board> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBoard(PathMetadata metadata) {
        super(Board.class, metadata);
    }

}

