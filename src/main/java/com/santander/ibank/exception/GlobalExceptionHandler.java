package com.santander.ibank.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseError> handleValidationExceptions(MethodArgumentNotValidException ex) {
    log.error("MethodArgumentNotValid: Valor de argumento incorreto");
    ResponseError responseError = new ResponseError();
    responseError.setCode(HttpStatus.BAD_REQUEST.value());
    List<Error> messages = new ArrayList<>();

    ex.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      messages.add(new com.santander.ibank.exception.FieldError(fieldName, errorMessage));
    });

    responseError.setMessages(messages);

    return ResponseEntity.badRequest().body(responseError);
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseError> handleIllegalArgumentException(IllegalArgumentException ex) {
    log.error("IllegalArgument: Valor de parametro incorreto");
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.BAD_REQUEST, ex));
  }

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ResponseError> handleGeneralExceptions(Exception ex) {
    log.error("Exception", ex);
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.INTERNAL_SERVER_ERROR, ex));
  }

  @ExceptionHandler(RuntimeException.class)
  public final ResponseEntity<ResponseError> handleRuntimeExceptions(RuntimeException ex) {
    log.error("RuntimeException", ex);
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.INTERNAL_SERVER_ERROR, ex));
  }
  
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ResponseError> handleMethodArgumentTypeMismatch(
      MethodArgumentTypeMismatchException ex) {
    log.error("MethodArgumentTypeMismatchException", ex);
    return ResponseEntity.badRequest().body(getErrors(HttpStatus.INTERNAL_SERVER_ERROR, ex));
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public final ResponseEntity<ResponseError> handleMissingParameterException(
      MissingServletRequestParameterException ex) {
    log.error("MissingServletRequestParameterException", ex);
    ResponseError responseError = new ResponseError();
    List<Error> messages = new ArrayList<>();

    messages.add(new com.santander.ibank.exception.FieldError(ex.getParameterName(), "Parametro invalido"));
    responseError.setCode(HttpStatus.BAD_REQUEST.value());
    responseError.setMessages(messages);

    return ResponseEntity.badRequest().body(responseError);
  }

  /**
   * Faz o bind das messagens de erro
   * 
   * @param status
   * @param ex
   * @param field
   * @return
   */
  private ResponseError getErrors(HttpStatus status, Exception ex) {
    ResponseError responseError = new ResponseError();
    responseError.setCode(status.value());
    List<Error> messages = new ArrayList<>();
    messages.add(new Error(ex.getMessage()));
    responseError.setMessages(messages);

    return responseError;
  }
}
