package com.example.demo.column.controller;

import com.example.demo.column.dto.RequestColumnDto;
import com.example.demo.column.dto.ResponseColumnDto;
import com.example.demo.column.service.ColumnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;

    // 컬럼 생성 (보드에 컬럼 생성)
    @PostMapping("/{id}/col")
    public ResponseEntity<ResponseColumnDto> createColumn(@Valid @RequestBody RequestColumnDto requestColumnDto) {
        // TODO: 실제 인증 로직으로 대체해야 함
        // 현재는 권한이 항상 있다고 가정
        boolean hasPermission = true;
        // 컬럼을 생성하는 서비스 메서드 호출
        ResponseColumnDto createdColumn = columnService.createColumn(requestColumnDto, hasPermission);
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
    public ResponseEntity<List<ResponseColumnDto>> reorderColumns(@RequestBody List<Long> columnIds) {
        // TODO: 실제 인증 로직으로 대체해야 함
        // 현재는 권한이 항상 있다고 가정
        boolean hasPermission = true;
        List<ResponseColumnDto> reorderedColumns = columnService.reorderColumns(columnIds, hasPermission);
        return new ResponseEntity<>(reorderedColumns, HttpStatus.OK);
    }
}