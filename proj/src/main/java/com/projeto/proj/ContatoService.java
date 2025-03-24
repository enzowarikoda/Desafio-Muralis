package com.projeto.proj;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.projeto.proj.database.*;
import com.projeto.proj.entidade.*;

@Service
public class ContatoService {

    @Autowired
    private RepositorioContato contatoRepository;

    @Autowired
    private RepositorioCliente clienteRepository;

    public Contato addContato(Long clienteId, Contato contato) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        contato.setCliente(cliente);
        return contatoRepository.save(contato);
    }
}
