package com.projeto.proj;

import com.projeto.proj.entidade.Contato;
import com.projeto.proj.database.RepositorioContato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projeto.proj.ContatoService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/contatos")
public class ContatoController {
    @Autowired
    private RepositorioContato repositorioContato;

    @Autowired
    private ContatoService contatoService;

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Contato>> getContatosByCliente(@PathVariable Long clienteId) {
        List<Contato> contatos = repositorioContato.findByClienteId(clienteId);
        // Retorna uma lista vazia se não houver contatos
        return ResponseEntity.ok(contatos != null ? contatos : new ArrayList<>());
    }


    @PostMapping("/cliente/{clienteId}")
    public ResponseEntity<Contato> addContato(@PathVariable Long clienteId, @RequestBody Contato contato) {
        // Adiciona o cliente associado
        Contato novoContato = contatoService.addContato(clienteId, contato);
        return ResponseEntity.ok(novoContato);
    }

    @PutMapping("/{id}")
    public Contato atualizarContato(@PathVariable Long id, @RequestBody Contato contatoAtualizado) {
        Contato contato = repositorioContato.findById(id).orElseThrow(() -> new RuntimeException("Contato não encontrado."));
        contato.setTipoContato(contatoAtualizado.getTipoContato());
        contato.setValorContato(contatoAtualizado.getValorContato());
        contato.setObservacao(contatoAtualizado.getObservacao());
        return repositorioContato.save(contato);
    }

    @DeleteMapping("/{id}")
    public void deletarContato(@PathVariable Long id) {
        repositorioContato.deleteById(id);
    }
}
