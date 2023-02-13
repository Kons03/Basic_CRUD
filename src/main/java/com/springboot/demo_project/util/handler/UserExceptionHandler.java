package com.springboot.demo_project.util.handler;

import com.springboot.demo_project.models.dto.ErrorDTO;
import com.springboot.demo_project.util.exception.UserAlreadyExistsException;
import com.springboot.demo_project.util.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ErrorDTO> handleCommonHelper(final Exception exception, final HttpStatus status) {
        final var errorDto = new ErrorDTO();
        errorDto.setCode(String.valueOf(status.value()));
        errorDto.setErrorDetails(exception.getMessage());
        return new ResponseEntity<>(errorDto, status);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<ErrorDTO> handleUserNameNotFoundConflict(final UserNotFoundException e) {
        return this.handleCommonHelper(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserAlreadyExistsException.class})
    public ResponseEntity<ErrorDTO> handleUserNameExistsConflict(final UserAlreadyExistsException e) {
        return this.handleCommonHelper(e, HttpStatus.CONFLICT);
    }
}
