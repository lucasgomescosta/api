package br.o.tarefas.controller;

import br.o.tarefas.dto.TarefaDTO;
import br.o.tarefas.dto.UsuarioDTO;
import br.o.tarefas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('USUARIO')")
    public ResponseEntity<List<UsuarioDTO>> recuperarUsuarios() {
        return ResponseEntity.ok(usuarioService.recuperarUsuarios());
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping
    public ResponseEntity<UsuarioDTO> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PreAuthorize("hasRole('USUARIO')")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioDTO));
    }

    @PreAuthorize("hasRole('USUARIO')")
    @GetMapping("/{usuarioId}/convites")
    public ResponseEntity<List<TarefaDTO>> getTarefasConvidado(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(usuarioService.buscaTarefasConvidado(usuarioId));
    }
}
