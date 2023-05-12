package com.santander.ibank.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MovimentacaoDTO {

  @NotNull(message = "Informe o ID do cliente")
  private Long clienteId;
  
  @NotNull(message = "Informe o valor")
  @Positive(message = "O valor deve ser positivo")
  private BigDecimal valor;
}
