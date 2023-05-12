package com.santander.ibank.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TransacaoControllerE2eTest {

  private static final String HOST = "http://localhost:";

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void deveExistir5Transacoes() throws Exception {
    List<?> response = this.restTemplate
        .getForObject(HOST + port + "/transacao/consultar?dataIni=01-05-2023&dataFim=01-06-2023", List.class);
    assertThat(response).hasSizeGreaterThanOrEqualTo(7);
  }

  @Test
  public void deveExistir1Transacao() throws Exception {
    List<?> response = this.restTemplate
        .getForObject(HOST + port + "/transacao/consultar?dataIni=01-05-2023&dataFim=01-05-2023", List.class);
    assertThat(response).hasSize(1);
  }

  @Test
  public void deveExistir3TransacoesCliente() throws Exception {
    List<?> response = this.restTemplate.getForObject(
        HOST + port + "/transacao/consultar/cliente?dataIni=01-05-2023&dataFim=01-06-2023&clienteId=1005", List.class);
    assertThat(response).hasSize(3);
  }

}
