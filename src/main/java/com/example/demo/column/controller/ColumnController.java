package com.example.demo.column.controller;

import com.example.demo.column.dto.ColumnDto;
import com.example.demo.column.service.ColumnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
public class ColumnController {

    private final ColumnService columnService;

    @Autowired
    public ColumnController(ColumnService columnService) {
        this.columnService = columnService;
    }

    // 컬럼 생성 (보드에 컬럼 생성)
    @PostMapping("/{id}/col")
    public ResponseEntity<ColumnDto> createColumn(@Valid @RequestBody ColumnDto columnDto) {
        // TODO: 실제 인증 로직으로 대체해야 함
        // 현재는 권한이 항상 있다고 가정
        boolean hasPermission = true;
        // 컬럼을 생성하는 서비스 메서드 호출
        ColumnDto createdColumn = columnService.createColumn(columnDto, hasPermission);
        return new ResponseEntity<>(createdColumn, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/col")
    public ResponseEntity<Void> deleteColumn(@PathVariable Long id) {
        // TODO: 실제 인증 로직으로 대체해야 함
        // 현재는 권한이 항상 있다고 가정
        boolean hasPermission = true;
        columnService.deleteColumn(id, hasPermission);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/reorder")
    public ResponseEntity<List<ColumnDto>> reorderColumns(@RequestBody List<Long> columnIds) {
        // TODO: 실제 인증 로직으로 대체해야 함
        // 현재는 권한이 항상 있다고 가정
        boolean hasPermission = true;
        List<ColumnDto> reorderedColumns = columnService.reorderColumns(columnIds, hasPermission);
        return new ResponseEntity<>(reorderedColumns, HttpStatus.OK);
    }
}