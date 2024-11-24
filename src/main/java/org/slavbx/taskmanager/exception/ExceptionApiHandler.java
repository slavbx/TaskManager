package org.slavbx.taskmanager.exception;

import jakarta.validation.ConstraintViolationException;
import org.slavbx.taskmanager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * Обработчик исключений для REST API.
 * Обеспечивает централизованное управление обработкой исключений
 * и возврат соответствующих ответов клиенту.
 */
@RestControllerAdvice
public class ExceptionApiHandler {

    /**
     * Обрабатывает исключение NotFoundException.
     * Возвращает ответ с кодом 404 (Not Found).
     *
     * @param exception Исключение NotFoundException
     * @return ResponseEntity с сообщением об ошибке
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> notFoundException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.builder().message(exception.getMessage()).build());
    }

    /**
     * Обрабатывает исключение AlreadyExistsException.
     * Возвращает ответ с кодом 409 (Conflict).
     *
     * @param exception Исключение AlreadyExistsException
     * @return ResponseEntity с сообщением об ошибке
     */
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> alreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorResponseDTO.builder().message(exception.getMessage()).build());
    }

    /**
     * Обрабатывает исключение MethodArgumentNotValidException.
     * Возвращает ответ с кодом 400 (Bad Request) и собранными сообщениями об ошибках полей.
     *
     * @param exception Исключение MethodArgumentNotValidException
     * @return ResponseEntity с сообщением об ошибке
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> notValidException(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        String errorMessage = String.join("; ", errorMessages);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder().message(errorMessage).build());
    }

    /**
     * Обрабатывает исключение ConstraintViolationException.
     * Возвращает ответ с кодом 400 (Bad Request).
     *
     * @param exception Исключение ConstraintViolationException
     * @return ResponseEntity с сообщением об ошибке
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> constraintException(ConstraintViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder().message(exception.getMessage()).build());
    }

    /**
     * Обрабатывает исключение HttpMessageNotReadableException.
     * Возвращает ответ с кодом 400 (Bad Request).
     *
     * @param exception Исключение HttpMessageNotReadableException
     * @return ResponseEntity с сообщением об ошибке
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponseDTO> httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.builder().message(exception.getMessage()).build());
    }
}
