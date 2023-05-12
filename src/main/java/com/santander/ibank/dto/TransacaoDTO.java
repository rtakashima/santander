package com.santander.ibank.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransacaoDTO {
  
  private Long id;
  private Date data;
  private BigDecimal valor;
  @JsonProperty("conta")
  private String clienteConta;

}
