package com.santander.ibank.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.santander.ibank.entity.Transacao;
import com.santander.ibank.repository.TransacaoRepository;

@Service
public class TransacaoServiceImpl implements TransacaoService {

  @Autowired
  private TransacaoRepository transacaoRepository;
  
  @Override
  public List<Transacao> consultar(Date dataIni, Date dataFim) {
    return transacaoRepository.findByDataBetween(dataIni, dataFim);
  }

  @Override
  public List<Transacao> consultar(Long clienteId, Date dataIni, Date dataFim) {
    return transacaoRepository.findByClienteClienteIdAndDataBetween(clienteId, dataIni, dataFim);
  }

}
