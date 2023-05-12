package com.santander.ibank.entity;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Transacao {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  private Long transacaoId;
  
  @Temporal(TemporalType.TIMESTAMP)
  private Date data;
  
  private BigDecimal valor;
  
  @ManyToOne
  @JoinColumn(name = "cliente_id", referencedColumnName = "clienteId")
  private Cliente cliente;
}
