package com.projeto.proj.database;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.proj.entidade.Contato;
import java.util.List;

public interface RepositorioContato extends JpaRepository<Contato, Long> {
    List<Contato> findByClienteId(Long clienteId);
}
