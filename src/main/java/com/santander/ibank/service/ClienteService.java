package com.santander.ibank.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Pageable;

import com.santander.ibank.entity.Cliente;

public interface ClienteService {

  /**
   * Lista os clientes por pagina
   * @param pageable
   * @return lista de clientes
   */
  List<Cliente> listar(Pageable pageable);
  
  /**
   * Cadastra um cliente com os atributos
   * Nome, Plano, Saldo, Número da conta e Data de nascimento
   * @param cliente
   */
  Cliente cadastrar(Cliente cliente);
  
  /**
   * Efetua o saque na conta de um cliente,
   * descontando as taxas quando for aplicavel
   * @param clienteId
   * @param valor
   * @return saldo atual
   */
  BigDecimal sacar(Long clienteId, BigDecimal valor);
  
  /**
   * Realiza o deposito na conta de um cliente
   * @param clienteId
   * @param valor
   * @return saldo atual
   */
  BigDecimal depositar(Long clienteId, BigDecimal valor);

}