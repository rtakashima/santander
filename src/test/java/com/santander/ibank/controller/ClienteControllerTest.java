package com.santander.ibank.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.santander.ibank.dto.ClienteDTO;
import com.santander.ibank.dto.MovimentacaoDTO;
import com.santander.ibank.entity.Cliente;
import com.santander.ibank.service.ClienteService;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest extends BaseControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClienteService service;

  @Test
  void whenNomeNullValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/cadastrar").content(asJsonString(mockClienteSemNome()))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Informe o nome")));
  }

  @Test
  void whenNomeSizeSmallValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/cadastrar").content(asJsonString(mockClienteNome("r")))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Quantidade invalida de caracteres")));
  }

  @Test
  void whenContaNullValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/cadastrar").content(asJsonString(mockClienteSemConta()))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Informe a conta")));
  }

  @Test
  void whenContaSizeSmallValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/cadastrar").content(asJsonString(mockClienteConta("12")))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().string(containsString("Quantidade invalida de digitos")));
  }

  @Test
  void whenDataNascNullValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/cadastrar").content(asJsonString(mockClienteSemDataNasc()))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Informe a data de nascimento")));
  }

  @Test
  void cadastrarClienteOk() throws Exception {
    mockMvc
        .perform(post("/cliente/cadastrar").content(asJsonString(mockClienteNome("Rafael Takashima")))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
  
  @Test
  void testPageIndexOk() throws Exception {
    when(service.listar(PageRequest.of(0, 10))).thenReturn(mockClientes());
    MvcResult res = mockMvc.perform(
        get("/cliente/listar?page=1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
    ).andExpect(status().isOk()).andReturn();
    
    @SuppressWarnings("unchecked")
    List<Cliente> clientes = jsonToObj(res.getResponse().getContentAsString(), List.class);
    assertThat(clientes).hasSize(3);
  }

  @Test
  void whenSacarDataIsNullValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/sacar").content(asJsonString(new MovimentacaoDTO()))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Informe o ID do cliente")))
        .andExpect(content().string(containsString("Informe o valor")));
  }

  @Test
  void whenDepositarDataIsNullValidationError() throws Exception {
    mockMvc
        .perform(post("/cliente/depositar").content(asJsonString(new MovimentacaoDTO()))
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest()).andExpect(content().string(containsString("Informe o ID do cliente")))
        .andExpect(content().string(containsString("Informe o valor")));
  }
  
  /**
   * Criar mock objeto do cliente sem nome
   * @return
   */
  private ClienteDTO mockClienteSemNome() {
    ClienteDTO cliente = new ClienteDTO();
    cliente.setConta("502050");
    cliente.setSaldo(BigDecimal.valueOf(200000));
    cliente.setDtNasc(Date.valueOf(LocalDate.of(1980, 6, 1)));
    return cliente;
  }

  /**
   * Criar mock objeto do cliente sem conta
   * @return
   */
  private ClienteDTO mockClienteSemConta() {
    ClienteDTO cliente = new ClienteDTO();
    cliente.setNome("Rafael");
    cliente.setSaldo(BigDecimal.valueOf(200000));
    cliente.setDtNasc(Date.valueOf(LocalDate.of(1980, 6, 1)));
    return cliente;
  }

  /**
   * Criar mock objeto do cliente sem data de nascimento
   * @return
   */
  private ClienteDTO mockClienteSemDataNasc() {
    ClienteDTO cliente = new ClienteDTO();
    cliente.setNome("Rafael");
    cliente.setConta("502050");
    cliente.setSaldo(BigDecimal.valueOf(200000));
    return cliente;
  }

  /**
   * Criar mock objeto do cliente com nome informado
   * @param nome
   * @return
   */
  private ClienteDTO mockClienteNome(String nome) {
    ClienteDTO cliente = mockClienteSemNome();
    cliente.setNome(nome);
    return cliente;
  }

  /**
   * Criar mock objeto do cliente com conta informada
   * @param conta
   * @return
   */
  private ClienteDTO mockClienteConta(String conta) {
    ClienteDTO cliente = mockClienteSemConta();
    cliente.setConta(conta);
    return cliente;
  }

  /**
   * Criar lista de clientes
   * @return
   */
  private List<Cliente> mockClientes() {
    List<Cliente> clientes = new ArrayList<>();
    clientes.add(new Cliente(1L, "Joao", false, BigDecimal.valueOf(100), "12000345",
        Date.valueOf(LocalDate.of(1958, 1, 20)), null));
    clientes.add(new Cliente(2L, "Maria", true, BigDecimal.valueOf(500), "04564544",
        Date.valueOf(LocalDate.of(1958, 1, 20)), null));
    clientes.add(new Cliente(3L, "John", false, BigDecimal.valueOf(2500), "9812312",
        Date.valueOf(LocalDate.of(1958, 1, 20)), null));

    return clientes;
  }

}
