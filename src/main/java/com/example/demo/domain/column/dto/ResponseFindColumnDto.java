package com.example.demo.domain.column.dto;

import com.example.demo.domain.column.entity.BoardColumn;
import lombok.Getter;

@Getter
public class ResponseFindColumnDto {
    private final Long id;
    private final String name;
    private final Long order;

    public ResponseFindColumnDto(BoardColumn boardColumn) {
        this.id = boardColumn.getId();
        this.name = boardColumn.getName();
        this.order = boardColumn.getOrders();
    }
}
