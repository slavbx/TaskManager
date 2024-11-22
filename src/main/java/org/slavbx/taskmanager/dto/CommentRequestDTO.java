package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
@Schema(description = "Входной DTO для комментариев")
public record CommentRequestDTO(
        String text,
        @Positive
        @NotEmpty
        Long taskId
){}