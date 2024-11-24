package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "DTO для отправки комментариев")
public record CommentDTO (
    Long id,
    LocalDateTime dateTime,
    String text,
    Long taskId,
    Long authorId
){}