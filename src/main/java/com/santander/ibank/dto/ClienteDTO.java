package com.santander.ibank.dto;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ClienteDTO {
  private Long clienteId;
  
  @NotBlank(message = "Informe o nome")
  @Size(min = 2, max = 100, message = "Quantidade invalida de caracteres")
  private String nome;
  private Boolean exclusive;
  private BigDecimal saldo;
  
  @NotBlank(message = "Informe a conta")
  @Size(min = 5, max = 20, message = "Quantidade invalida de digitos")
  private String conta;
  
  @Past
  @NotNull(message = "Informe a data de nascimento")
  private Date dtNasc;
}
