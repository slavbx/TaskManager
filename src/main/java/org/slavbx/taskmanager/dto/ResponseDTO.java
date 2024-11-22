package org.slavbx.taskmanager.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slavbx.taskmanager.model.Status;

@Builder
@Schema(description = "DTO для ответа на запрос")
public record ResponseDTO (
    @JsonInclude(JsonInclude.Include.NON_NULL)
    Long entityId,
    String message
){}