package com.santander.ibank.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.santander.ibank.dto.TransacaoDTO;
import com.santander.ibank.entity.Transacao;
import com.santander.ibank.service.TransacaoService;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

  private static final String FORMATO_DATA = "dd-MM-yyyy";

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private TransacaoService transacaoService;

  @GetMapping("/consultar")
  public List<TransacaoDTO> consultar(@RequestParam("dataIni") @DateTimeFormat(pattern = FORMATO_DATA) Date dataIni,
      @RequestParam("dataFim") @DateTimeFormat(pattern = FORMATO_DATA) Date dataFim) {
    log.info("Consultando transacoes por periodo");
    List<Transacao> transacoes = transacaoService.consultar(dataIni, dataFim);
    return transacoes.stream().map(e -> mapper.map(e, TransacaoDTO.class)).collect(Collectors.toList());
  }

  @GetMapping("/consultar/cliente")
  public List<TransacaoDTO> consultarCliente(@RequestParam("clienteId") @NotNull Long clienteId,
      @RequestParam("dataIni") @DateTimeFormat(pattern = FORMATO_DATA) Date dataIni,
      @RequestParam("dataFim") @DateTimeFormat(pattern = FORMATO_DATA) Date dataFim) {
    log.info("Consultando transacoes de um cliente por periodo");
    List<Transacao> transacoes = transacaoService.consultar(clienteId, dataIni, dataFim);
    return transacoes.stream().map(e -> mapper.map(e, TransacaoDTO.class)).collect(Collectors.toList());
  }

}
