package com.santander.ibank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.santander.ibank.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
