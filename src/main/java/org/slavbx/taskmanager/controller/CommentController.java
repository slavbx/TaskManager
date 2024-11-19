package org.slavbx.taskmanager.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.CommentDTO;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.dto.TaskDTO;
import org.slavbx.taskmanager.mapper.CommentMapper;
import org.slavbx.taskmanager.service.CommentService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getCommentsPage(@RequestParam(defaultValue = "0") @Min(0) int page,
                                                            @RequestParam(defaultValue = "10") @Min(0) @Max(100) int size) {
        return ResponseEntity.ok(commentMapper.commentsPageToCommentDTOsPage(commentService.getAllComments(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(commentMapper.commentToCommentDTO(commentService.getCommentById(id)));
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.createComment(commentMapper.commentDTOToComment(commentDTO)));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.delete(id));
    }
}
