package com.projeto.proj.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.proj.entidade.Cliente;

public interface RepositorioCliente extends JpaRepository<Cliente, Long> {
    
}
