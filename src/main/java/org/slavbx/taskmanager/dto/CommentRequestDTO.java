package org.slavbx.taskmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
@Schema(description = "DTO для получения комментариев")
public record CommentRequestDTO(
        @Schema(description = "Текст комментария", example = "posted text")
        @NotBlank(message = "Text cannot be empty")
        String text,
        @Schema(description = "Идентификатор комментируемой", example = "1")
        @Min(value = 1, message = "Id must be greater than 0")
        Long taskId
){}