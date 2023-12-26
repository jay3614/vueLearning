package com.example.vuebackboard.controller;

import com.example.vuebackboard.entity.BoardEntity;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.service.BoardService;
import com.example.vuebackboard.dto.BoardDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@CrossOrigin
@RestController
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board/list")
    public Header<List<BoardDTO>> boardList(@PageableDefault(sort = {"idx"}) Pageable pageable) {
        return boardService.getBoardList(pageable);
    }

    @GetMapping("/board/{id}")
    public BoardDTO getBoard(@PathVariable(name = "id") Long id) {
        return boardService.getBoard(id);
    }

    @PostMapping("/board")
    public BoardEntity create(@RequestBody BoardDTO boardDTO) {
        return boardService.create(boardDTO);
    }

    @PatchMapping("/board")
    public BoardEntity update(@RequestBody BoardDTO boardDTO) {
        return boardService.update(boardDTO);
    }

    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        boardService.delete(id);
    }
}