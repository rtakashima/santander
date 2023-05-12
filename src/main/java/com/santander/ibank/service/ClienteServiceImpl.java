package com.santander.ibank.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.santander.ibank.entity.Cliente;
import com.santander.ibank.entity.Transacao;
import com.santander.ibank.repository.ClienteRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class ClienteServiceImpl implements ClienteService {

  @Autowired
  private ClienteRepository clienteRepository;

  @Override
  public List<Cliente> listar(Pageable pageable) {
    Page<Cliente> clientes = clienteRepository.findAll(pageable);
    return clientes.isEmpty() ? new ArrayList<>() : clientes.getContent();
  }

  @Override
  public Cliente cadastrar(Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @Override
  public BigDecimal sacar(Long clienteId, BigDecimal valor) {
    return movimentar(clienteId, valor, true);
  }

  @Override
  public BigDecimal depositar(Long clienteId, BigDecimal valor) {
    return movimentar(clienteId, valor, false);
  }

  /**
   * Registra movimentacao em conta
   * @param clienteId
   * @param valor
   * @param isSaque
   * @return Saldo atual
   */
  private BigDecimal movimentar(Long clienteId, BigDecimal valor, boolean isSaque) {
    Cliente cliente = clienteRepository.findById(clienteId)
        .orElseThrow(() -> new IllegalArgumentException("ID do cliente invalido"));
    
    // registra transacao
    BigDecimal valorSaque = valor.multiply(BigDecimal.valueOf(isSaque ? -1 : 1));
    Transacao transacao = new Transacao(null, new Date(), valorSaque, cliente);
    cliente.getTransacoes().add(transacao);

    // atualiza saldo da conta-cliente
    BigDecimal novoSaldo = isSaque ? cliente.getSaldo().subtract(valor) : cliente.getSaldo().add(valor);

    // Desconta taxa de saque se nao for cliente exclusive
    if (isSaque && !Boolean.TRUE.equals(cliente.getPlanoExclusive())) {
      novoSaldo = novoSaldo.subtract(getTaxa(valor));
    }

    cliente.setSaldo(novoSaldo);
    
    // persiste dados
    clienteRepository.save(cliente);
    return novoSaldo;
  }

  /**
   * Calcula taxa a ser descontada no saque
   * 
   * @param valor
   * @return taxa
   */
  private BigDecimal getTaxa(BigDecimal valor) {
    // Isento de taxa de saque
    if (valor.compareTo(BigDecimal.valueOf(100)) <= 0) {
      return BigDecimal.ZERO;
    }

    // Taxa de 0.4% 
    if (valor.compareTo(BigDecimal.valueOf(300)) <= 0) {
      return valor.multiply(BigDecimal.valueOf(0.004));
    }

    // Taxa de 1% 
    return valor.multiply(BigDecimal.valueOf(0.01));
  }

}
