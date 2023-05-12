package com.santander.ibank.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.santander.ibank.entity.Cliente;
import com.santander.ibank.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

  @InjectMocks
  private ClienteService service = new ClienteServiceImpl();
  
  @Mock
  private ClienteRepository repository;
  
  @Test
  void cadastrarTest() {
    //pre-condicao
    final Cliente cliente = mockCliente();
    when(repository.save(any(Cliente.class))).thenReturn(cliente);
    
    //acao
    final Cliente clienteDB = service.cadastrar(mockCliente());
    
    //verificacao
    assertThat(clienteDB).usingRecursiveComparison().isEqualTo(cliente);
  }

  @Test
  void listarTest() {
    Pageable pageable = PageRequest.of(0, 10);
    //pre-condicao
    when(repository.findAll(pageable)).thenReturn(mockClientes());
    
    //acao
    final List<Cliente> clientesDB = service.listar(pageable);
    
    //verificacao
    assertThat(clientesDB).hasSize(3);
  }

  @Test
  void sacarPlanoExclusivoTest() {
    //pre-condicao
    Optional<Cliente> cliente = Optional.of(mockClienteExclusivo());//saldo = 1000
    when(repository.findById(anyLong())).thenReturn(cliente);
    
    //acao
    final BigDecimal saldo = service.sacar(1l, BigDecimal.valueOf(800));
    
    //verificacao
    BigDecimal valueOf = BigDecimal.valueOf(200);
    assertEquals(valueOf, saldo);
  }
  
  @Test
  void sacarSemTaxaValorBaixoTest() {
    //pre-condicao
    Optional<Cliente> cliente = Optional.of(mockCliente());//saldo = 1000
    when(repository.findById(anyLong())).thenReturn(cliente);
    
    //acao
    final BigDecimal saldo = service.sacar(1l, BigDecimal.valueOf(100));
    
    //verificacao
    BigDecimal valueOf = BigDecimal.valueOf(900);
    assertEquals(valueOf, saldo);
  }
  
  @Test
  void sacarComTaxa04PorCentoTest() {
    //pre-condicao
    Optional<Cliente> cliente = Optional.of(mockCliente());//saldo = 1000
    when(repository.findById(anyLong())).thenReturn(cliente);
    
    //acao
    final BigDecimal saldo = service.sacar(1l, BigDecimal.valueOf(300));
    
    //verificacao
    BigDecimal valueOf = BigDecimal.valueOf(698.800);
    assertEquals(valueOf.setScale(3), saldo);
  }

  @Test
  void sacarComTaxa1PorCentoTest() {
    //pre-condicao
    Optional<Cliente> cliente = Optional.of(mockCliente());//saldo = 1000
    when(repository.findById(anyLong())).thenReturn(cliente);
    
    //acao
    final BigDecimal saldo = service.sacar(1l, BigDecimal.valueOf(500));
    
    //verificacao
    BigDecimal valueOf = BigDecimal.valueOf(495.00);
    assertEquals(valueOf.setScale(2), saldo);
  }
  
  @Test
  void depositarValorTest() {
    //pre-condicao
    Optional<Cliente> cliente = Optional.of(mockCliente());//saldo = 1000
    when(repository.findById(anyLong())).thenReturn(cliente);
    
    //acao
    final BigDecimal saldo = service.depositar(1l, BigDecimal.valueOf(500));
    
    //verificacao
    BigDecimal valueOf = BigDecimal.valueOf(1500);
    assertEquals(valueOf, saldo);
  }
  
  /**
   * Cria instancia teste do cliente
   * @return
   */
  private Cliente mockCliente() {
    Cliente cliente = new Cliente();
    cliente.setNome("Rafael Takashima");
    cliente.setConta("123456789");
    cliente.setDtNasc(Date.valueOf(LocalDate.of(1982, 6, 1)));
    cliente.setSaldo(BigDecimal.valueOf(1000));
    
    return cliente;
  }
  
  /**
   * Cria cliente com plano exclusivo
   * @return
   */
  private Cliente mockClienteExclusivo() {
    Cliente cliente = mockCliente();
    cliente.setPlanoExclusive(true);
    return cliente;
  }
  
  /**
   * Criar page lista de clientes
   * @return
   */
  private Page<Cliente> mockClientes() {
    List<Cliente> clientes = new ArrayList<>();
    clientes.add(new Cliente(1L, "Joao", false, BigDecimal.valueOf(100), "12000345",
        Date.valueOf(LocalDate.of(1958, 1, 20)), null));
    clientes.add(new Cliente(2L, "Maria", true, BigDecimal.valueOf(500), "04564544",
        Date.valueOf(LocalDate.of(1958, 1, 20)), null));
    clientes.add(new Cliente(3L, "John", false, BigDecimal.valueOf(2500), "9812312",
        Date.valueOf(LocalDate.of(1958, 1, 20)), null));

    return new PageImpl<>(clientes);
  }
  
}
