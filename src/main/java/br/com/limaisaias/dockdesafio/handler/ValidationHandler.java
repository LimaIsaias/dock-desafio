package br.com.limaisaias.dockdesafio.handler;

import br.com.limaisaias.dockdesafio.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ValidationHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Arrays.asList(ex.getMessage()));
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(MethodArgumentNotValidException ex) {

        List<String> erros = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> erros.add(((FieldError) error).getField() + " : " + error.getDefaultMessage()));
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), erros);

        return ResponseEntity.badRequest().body(errorResponse);
    }

    @Getter
    @AllArgsConstructor
    class ErrorResponse {
        private int statusCode;
        private List<String> erros;

    }

}
