package org.slavbx.taskmanager.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Перечисление, представляющее приоритет задачи
 */
@Getter
@AllArgsConstructor
public enum Priority {
    /**
     * Низкий приоритет
     */
    LOW,
    /**
     * Средний приоритет
     */
    MEDIUM,
    /**
     * Высокий приоритет
     */
    HIGH
}
