package org.slavbx.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление, представляющее статус задачи
 */
@Getter
@AllArgsConstructor
public enum Status {
    /**
     * Задача в ожидании
     */
    WAIT,
    /**
     * Задача на выполнении
     */
    PROGRESS,
    /**
     * Задача выполнена
     */
    COMPLETE
}
