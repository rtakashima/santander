package com.santander.ibank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.santander.ibank.entity.Cliente;
import com.santander.ibank.entity.Transacao;
import com.santander.ibank.repository.TransacaoRepository;

@ExtendWith(MockitoExtension.class)
public class TransacaServiceTest {

  @InjectMocks
  private TransacaoService service = new TransacaoServiceImpl();

  @Mock
  private TransacaoRepository repository;

  @Test
  void consultarTest() {
    //pre-condicao
    when(repository.findByDataBetween(any(Date.class), any(Date.class))).thenReturn(mockTransacoes());
    
    //acao
    final List<Transacao> transacoes = service.consultar(new Date(), new Date());
    
    //verificacao
    assertThat(transacoes).hasSize(3);
  }

  @Test
  void consultarClienteTest() {
    //pre-condicao
    when(repository.findByClienteClienteIdAndDataBetween(anyLong(), any(Date.class), any(Date.class))).thenReturn(mockTransacoesCliente());
    
    //acao
    final List<Transacao> transacoes = service.consultar(1l, new Date(), new Date());
    
    //verificacao
    assertThat(transacoes).hasSize(2);
  }

  /**
   * Cria lista de trancacoes
   * 
   * @return lista de trancacoes
   */
  private List<Transacao> mockTransacoes() {
    List<Transacao> transacoes = new ArrayList<>();
    transacoes.add(new Transacao(1l, new Date(), BigDecimal.valueOf(100), null));
    transacoes.add(new Transacao(2l, new Date(), BigDecimal.valueOf(200), null));
    transacoes.add(new Transacao(3l, new Date(), BigDecimal.valueOf(300), null));
    return transacoes;
  }

  /**
   * Cria lista de trancacoes do cliente
   * 
   * @return lista de trancacoes
   */
  private List<Transacao> mockTransacoesCliente() {
    List<Transacao> transacoes = new ArrayList<>();
    Cliente cliente = new Cliente();
    cliente.setClienteId(1l);
    transacoes.add(new Transacao(1l, new Date(), BigDecimal.valueOf(100), cliente));
    transacoes.add(new Transacao(2l, new Date(), BigDecimal.valueOf(200), cliente));
    return transacoes;
  }

}
