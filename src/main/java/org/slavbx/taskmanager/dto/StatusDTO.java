package org.slavbx.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slavbx.taskmanager.annotation.ValidEnum;
import org.slavbx.taskmanager.model.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatusDTO {
    //@ValidEnum(enumClass = Status.class)
    private Status status;
}