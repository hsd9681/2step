package com.example.demo.domain.column.controller;

import com.example.demo.domain.column.dto.RequestColumnDto;
import com.example.demo.domain.column.dto.ResponseColumnDto;
import com.example.demo.domain.column.service.ColumnService;
import com.example.demo.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;


    // 컬럼 생성 (보드에 컬럼 생성)
    @PostMapping("/{id}/col")
    public ResponseEntity<ResponseColumnDto> createColumn(@Valid @RequestBody RequestColumnDto requestColumnDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 컬럼을 생성하는 서비스 메서드 호출
        ResponseColumnDto createdColumn = columnService.createColumn(requestColumnDto, userDetails.getUser());
        return new ResponseEntity<>(createdColumn, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/col/{id}")
    public ResponseEntity<String> deleteColumn(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        columnService.deleteColumn(id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("컬럼이 삭제되었습니다.");
    }

    @PutMapping("/reorder")
    public ResponseEntity<List<ResponseColumnDto>> reorderColumns(@RequestBody List<Long> columnIds, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ResponseColumnDto> reorderedColumns = columnService.reorderColumns(columnIds, userDetails.getUser());
        return new ResponseEntity<>(reorderedColumns, HttpStatus.OK);
    }
}