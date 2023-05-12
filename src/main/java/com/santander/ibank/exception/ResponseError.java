package com.santander.ibank.exception;

import java.util.List;

import lombok.Data;

@Data
public class ResponseError {

  private Integer code;
  private List<Error> messages;
}
