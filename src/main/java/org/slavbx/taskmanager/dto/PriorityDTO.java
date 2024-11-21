package org.slavbx.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slavbx.taskmanager.model.Priority;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PriorityDTO {
    private Priority priority;
}
