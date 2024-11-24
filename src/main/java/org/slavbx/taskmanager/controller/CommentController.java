package org.slavbx.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.slavbx.taskmanager.dto.*;
import org.slavbx.taskmanager.mapper.CommentMapper;
import org.slavbx.taskmanager.model.Comment;
import org.slavbx.taskmanager.repository.specification.CommentSpecifications;
import org.slavbx.taskmanager.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Управление комментариями")
@SecurityRequirement(name = "bearerAuth")
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/comments", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @Operation(summary = "Получение списка комментариев постранично")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Получен список комментариев",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Комментарии не найдены",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<Page<CommentDTO>> getCommentsPage(@RequestParam(defaultValue = "0") @Min(0) int pageNumber,
                                                            @RequestParam(defaultValue = "10") @Min(0) @Max(100) int pageSize,
                                                            Long authorId) {
        Specification<Comment> spec = Specification.where(CommentSpecifications.hasAuthor(authorId));
        return ResponseEntity.ok(commentMapper.commentsPageToCommentDTOsPage(commentService.getCommentsWithPagingAndFiltering(spec, pageNumber, pageSize)));
    }

    @Operation(summary = "Получение комментария по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий получен",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return ResponseEntity.ok(commentMapper.commentToCommentDTO(commentService.getCommentById(id)));
    }

    @Operation(summary = "Создание комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий создан",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = CommentDTO.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN') or @taskSecurity.hasPerformer(#commentRequestDTO.taskId)")
    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        return ResponseEntity.ok(commentService.createComment(commentMapper.commentRequestDTOToComment(commentRequestDTO)));
    }

    @Operation(summary = "Удаление комментария")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Комментарий удалён",
                    content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Комментарий не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.delete(id));
    }
}
