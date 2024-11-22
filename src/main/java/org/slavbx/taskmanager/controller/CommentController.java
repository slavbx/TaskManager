package org.slavbx.taskmanager.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.CommentDTO;
import org.slavbx.taskmanager.dto.ResponseDTO;
import org.slavbx.taskmanager.dto.TaskDTO;
import org.slavbx.taskmanager.mapper.CommentMapper;
import org.slavbx.taskmanager.model.Comment;
import org.slavbx.taskmanager.model.Task;
import org.slavbx.taskmanager.repository.specification.CommentSpecifications;
import org.slavbx.taskmanager.repository.specification.TaskSpecifications;
import org.slavbx.taskmanager.service.CommentService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Page<CommentDTO>> getCommentsPage(@RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                            @RequestParam(defaultValue = "10") @Min(0) @Max(100) int pageSize,
                                                            Long authorId) {
        Specification<Comment> spec = Specification.where(CommentSpecifications.hasAuthor(authorId));
        return ResponseEntity.ok(commentMapper.commentsPageToCommentDTOsPage(commentService.getCommentsWithPagingAndFiltering(spec, pageNumber, pageSize)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable @Min(0) Long id) {
        return ResponseEntity.ok(commentMapper.commentToCommentDTO(commentService.getCommentById(id)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskSecurity.hasPerformer(#id)")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createComment(@RequestBody CommentDTO commentDTO) {
        return ResponseEntity.ok(commentService.createComment(commentMapper.commentDTOToComment(commentDTO)));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.delete(id));
    }
}
