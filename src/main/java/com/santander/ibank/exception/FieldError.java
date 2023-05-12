package com.santander.ibank.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FieldError extends Error {

  private String field;
  
  public FieldError(String field, String message) {
    super(message);
    this.field = field;
  }
}
