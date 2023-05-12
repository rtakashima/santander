package com.santander.ibank.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santander.ibank.dto.ClienteDTO;
import com.santander.ibank.dto.MovimentacaoDTO;
import com.santander.ibank.entity.Cliente;
import com.santander.ibank.service.ClienteService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/cliente")
public class ClienteController {

  private static final int PAGE_SIZE = 10;

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private ClienteService clienteService;

  @GetMapping("/listar")
  public List<ClienteDTO> listar(@RequestParam("page") @Min(1) int page) {
    log.info("Listando clientes");
    return clienteService.listar(PageRequest.of(--page, PAGE_SIZE)).stream().map(e -> mapper.map(e, ClienteDTO.class))
        .collect(Collectors.toList());
  }

  @PostMapping("/cadastrar")
  public void cadastrar(@Valid @RequestBody ClienteDTO cliente) {
    log.info("Cadastrando cliente");
    clienteService.cadastrar(mapper.map(cliente, Cliente.class));
  }

  @PostMapping("/sacar")
  public BigDecimal sacar(@Valid @RequestBody MovimentacaoDTO movimentacao) {
    log.info("Realizando saque");
    return clienteService.sacar(movimentacao.getClienteId(), movimentacao.getValor());
  }

  @PostMapping("/depositar")
  public BigDecimal depositar(@Valid @RequestBody MovimentacaoDTO movimentacao) {
    log.info("Efetuando deposito");
    return clienteService.depositar(movimentacao.getClienteId(), movimentacao.getValor());
  }

}
