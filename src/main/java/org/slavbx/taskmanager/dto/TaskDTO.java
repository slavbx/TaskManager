package org.slavbx.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slavbx.taskmanager.model.Priority;
import org.slavbx.taskmanager.model.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private Long id;
    String title;
    String description;
    private Long authorId;
    private Long performerId;
    private Priority priority;
    private Status status;
}

