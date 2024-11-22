package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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