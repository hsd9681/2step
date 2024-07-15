package com.example.demo.domain.column.controller;

import com.example.demo.domain.column.dto.RequestColumnDto;
import com.example.demo.domain.column.dto.ResponseColumnDto;
import com.example.demo.domain.column.dto.ResponseFindColumnDto;
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
@RequestMapping("/api/board{boardId}")
@RequiredArgsConstructor
public class ColumnController {

    private final ColumnService columnService;


    // 컬럼 생성 (보드에 컬럼 생성)
    @PostMapping("/col")
    public ResponseEntity<ResponseColumnDto> createColumn(@Valid @PathVariable Long boardId,
                                                          @RequestBody RequestColumnDto requestColumnDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 컬럼을 생성하는 서비스 메서드 호출
        ResponseColumnDto createdColumn = columnService.createColumn(boardId, requestColumnDto, userDetails.getUser());
        return new ResponseEntity<>(createdColumn, HttpStatus.CREATED);
    }

    // 컬럼 삭제
    @DeleteMapping("/col/{colId}")
    public ResponseEntity<String> deleteColumn(@PathVariable Long boardId,
                                               @PathVariable Long colId,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        columnService.deleteColumn(boardId, colId, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("컬럼이 삭제되었습니다.");
    }


    // 컬럼 순서 이동
    // "확정" 버튼을 눌렀을 때 동작
    // 보드 ID와 새로운 컬럼 순서(List<Long> columnIds)를 받음.
    @PutMapping("/reorder")
    public ResponseEntity<List<ResponseColumnDto>> reorderColumns(@PathVariable Long boardId,
                                                                  @RequestBody List<Long> columnIds,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ResponseColumnDto> reorderedColumns = columnService.reorderColumns(boardId, columnIds, userDetails.getUser());
        return new ResponseEntity<>(reorderedColumns, HttpStatus.OK);
    }

    @GetMapping("/col")
    public ResponseEntity<List<ResponseFindColumnDto>> findAllColumns(@PathVariable("boardId") Long boardId) {
        List<ResponseFindColumnDto> dtoList = columnService.findAllColumns(boardId);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }
}