package com.santander.ibank;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.santander.ibank.controller.ClienteController;
import com.santander.ibank.controller.TransacaoController;

@SpringBootTest
class SantanderApplicationTests {

  @Autowired
  private ClienteController clienteController;
  
  @Autowired
  private TransacaoController transacaoController;
  
	@Test
	void contextLoads() {
	  assertThat(clienteController).isNotNull();
    assertThat(transacaoController).isNotNull();
	}

}
