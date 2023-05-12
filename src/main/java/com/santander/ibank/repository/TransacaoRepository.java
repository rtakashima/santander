package com.santander.ibank.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.ibank.entity.Transacao;

public interface TransacaoRepository extends JpaRepository<Transacao, Long>{

  /**
   * Busca transacoes dentro doperiodo informado
   * @param dataIni data de inicio
   * @param dataFim data final
   * @return lista de transacoes
   */
  List<Transacao> findByDataBetween(Date dataIni, Date dataFim);
  
  /**
   * Busca transacoes de um cliente dentro do periodo informado
   * @param clienteId ID do cliente
   * @param dataIni data de inicio
   * @param dataFim data final
   * @return
   */
  List<Transacao> findByClienteClienteIdAndDataBetween(Long clienteId, Date dataIni, Date dataFim);
  
}
