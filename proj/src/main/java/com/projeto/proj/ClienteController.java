package com.projeto.proj;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.proj.database.RepositorioCliente;
import com.projeto.proj.entidade.Cliente;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
     @Autowired
    private RepositorioCliente repositorioCliente;

    @GetMapping
    public List<Cliente> listarClientes() {
        return repositorioCliente.findAll();
    }

    @PostMapping
    public Cliente criarCliente(@RequestBody Cliente cliente) {
        return repositorioCliente.save(cliente);
    }

    @PutMapping("/{id}")
    public Cliente atualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteAtualizado) {
        Cliente cliente = repositorioCliente.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));
        cliente.setNome(clienteAtualizado.getNome());
        cliente.setCpf(clienteAtualizado.getCpf());
        cliente.setData_nasc(clienteAtualizado.getData_nasc());
        cliente.setEndereco(clienteAtualizado.getEndereco());
        return repositorioCliente.save(cliente);
    }

    @DeleteMapping("/{id}")
    public void deletarCliente(@PathVariable Long id) {
        repositorioCliente.deleteById(id);
    }
}