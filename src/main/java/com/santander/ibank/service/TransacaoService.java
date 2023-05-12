package com.santander.ibank.service;

import java.util.Date;
import java.util.List;

import com.santander.ibank.entity.Transacao;

public interface TransacaoService {

  /**
   * Realiza a consulta de transacoes feitas por periodo
   * @param dataIni data de inicio
   * @param dataFim datat final
   * @return lista de transacoes
   */
  List<Transacao> consultar(Date dataIni, Date dataFim);
  
  /**
   * Realiza a consulta de transacoes feitas por periodo
   * de um determinado cliente
   * @param clienteId
   * @param dataIni data de inicio
   * @param dataFim datat final
   * @return lista de transacoes
   */
  List<Transacao> consultar(Long clienteId, Date dataIni, Date dataFim);
  
}
