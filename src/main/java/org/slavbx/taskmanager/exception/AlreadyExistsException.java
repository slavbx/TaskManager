package org.slavbx.taskmanager.exception;

/**
 * Исключение для ситуации, когда пользователь пытается создать или добавить
 * объект (например, задачу или комментарий) с уже существующим уникальным
 * идентификатором (или другим ограничением).
 */
public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message) {
        super(message);
    }
}

