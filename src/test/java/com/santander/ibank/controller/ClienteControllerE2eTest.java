package com.santander.ibank.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.santander.ibank.dto.ClienteDTO;
import com.santander.ibank.dto.MovimentacaoDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClienteControllerE2eTest {

  private static final String HOST = "http://localhost:";

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void deveCadastrarCliente() throws Exception {
    ResponseEntity<Void> response = this.restTemplate.postForEntity(HOST + port + "/cliente/cadastrar", mockCliente(),
        Void.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  public void deveListar5Clientes() throws Exception {
    List<?> clientes = this.restTemplate.getForObject(HOST + port + "/cliente/listar?page=1", List.class);
    assertThat(clientes).hasSize(5);
  }

  @Test
  public void deveSacarComTaxa04PorCento() throws Exception {
    MovimentacaoDTO movimentacao = new MovimentacaoDTO();
    movimentacao.setClienteId(1002l);
    movimentacao.setValor(BigDecimal.valueOf(300));

    ResponseEntity<String> response = this.restTemplate.postForEntity(HOST + port + "/cliente/sacar", movimentacao,
        String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("1698.800");
  }

  @Test
  public void deveSacarComTaxa1PorCento() throws Exception {
    MovimentacaoDTO movimentacao = new MovimentacaoDTO();
    movimentacao.setClienteId(1003l);
    movimentacao.setValor(BigDecimal.valueOf(500));

    ResponseEntity<String> response = this.restTemplate.postForEntity(HOST + port + "/cliente/sacar", movimentacao,
        String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("4495.00");
  }

  @Test
  public void deveDepositarAumentandoSaldo() throws Exception {
    MovimentacaoDTO movimentacao = new MovimentacaoDTO();
    movimentacao.setClienteId(1001l);
    movimentacao.setValor(BigDecimal.valueOf(500));

    ResponseEntity<String> response = this.restTemplate.postForEntity(HOST + port + "/cliente/depositar", movimentacao,
        String.class);
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody()).isEqualTo("1500.00");
  }

  /**
   * Cria instancia teste do cliente
   * 
   * @return
   */
  private ClienteDTO mockCliente() {
    ClienteDTO cliente = new ClienteDTO();
    cliente.setNome("Charles Xavier");
    cliente.setConta("8555588");
    cliente.setDtNasc(Date.valueOf(LocalDate.of(1982, 6, 1)));
    cliente.setSaldo(BigDecimal.valueOf(1000));

    return cliente;
  }

}
