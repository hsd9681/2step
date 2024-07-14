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
    @PostMapping("/{board_id}/col")
    public ResponseEntity<ResponseColumnDto> createColumn(@Valid @PathVariable Long board_id,
                                                          @RequestBody RequestColumnDto requestColumnDto,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 컬럼을 생성하는 서비스 메서드 호출
        ResponseColumnDto createdColumn = columnService.createColumn(board_id, requestColumnDto, userDetails.getUser());
        return new ResponseEntity<>(createdColumn, HttpStatus.CREATED);
    }

    // 컬럼 삭제
    @DeleteMapping("/{board_id}/col/{col_id}")
    public ResponseEntity<String> deleteColumn(@PathVariable Long board_id,
                                               @PathVariable Long col_id,
                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {

        columnService.deleteColumn(board_id, col_id, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("컬럼이 삭제되었습니다.");
    }


    // 컬럼 순서 이동
    // "확정" 버튼을 눌렀을 때 동작
    // 보드 ID와 새로운 컬럼 순서(List<Long> columnIds)를 받음.
    @PutMapping("/{board_id}/reorder")
    public ResponseEntity<List<ResponseColumnDto>> reorderColumns(@PathVariable Long board_id,
                                                                  @RequestBody List<Long> columnIds,
                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {

        List<ResponseColumnDto> reorderedColumns = columnService.reorderColumns(board_id, columnIds, userDetails.getUser());
        return new ResponseEntity<>(reorderedColumns, HttpStatus.OK);
    }
}