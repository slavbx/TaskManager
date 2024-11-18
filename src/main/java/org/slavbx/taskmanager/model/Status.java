package org.slavbx.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    WAIT("В ожидании"),
    PROGRESS("В процессе"),
    COMPLETE("Завершено");

    private final String description;
}
