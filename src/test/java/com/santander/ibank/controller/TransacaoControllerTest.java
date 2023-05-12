package com.santander.ibank.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.santander.ibank.service.TransacaoService;

@WebMvcTest(TransacaoController.class)
public class TransacaoControllerTest extends BaseControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TransacaoService service;

  @Test
  void whenConsultaFormatoDataInvalidoValidationError() throws Exception {
    mockMvc
        .perform(get("/transacao/consultar?dataIni=2023-01-05&dataFim=2023-06-01")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Failed to convert value of type 'java.lang.String' to required type 'java.util.Date'")));
  }

  @Test
  void whenConsultaDataNullValidationError() throws Exception {
    mockMvc
        .perform(get("/transacao/consultar")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Parametro invalido")));
  }

  @Test
  void testConsultaOk() throws Exception {
    mockMvc
        .perform(get("/transacao/consultar?dataIni=01-05-2023&dataFim=01-06-2023")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
  
  @Test
  void whenConsultaClienteDataInvalidaValidationError() throws Exception {
    mockMvc
        .perform(get("/transacao/consultar/cliente?clienteId=1&dataIni=2023-01-05&dataFim=2023-06-01")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Failed to convert value of type 'java.lang.String' to required type 'java.util.Date'")));
  }

  @Test
  void whenConsultaClienteIdInvalidaValidationError() throws Exception {
    mockMvc
        .perform(get("/transacao/consultar/cliente?clienteId=abc&dataIni=01-05-2023&dataFim=01-06-2023")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'")));
  }
  
  @Test
  void testConsultaClienteOk() throws Exception {
    mockMvc
        .perform(get("/transacao/consultar/cliente?clienteId=1&dataIni=01-05-2023&dataFim=01-06-2023")
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
  
}
