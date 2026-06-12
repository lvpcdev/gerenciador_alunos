package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.UsuarioRequestDTO;
import br.com.lvpcdev.gerenciador.dto.UsuarioResponseDTO;
import br.com.lvpcdev.gerenciador.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrarUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO usuario = usuarioService.salvarUsuario(dto);
        return ResponseEntity.status(201).body(usuario);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarTodos();
    }

    @PutMapping("/{id}")
    public UsuarioResponseDTO editarAula(@PathVariable Long id, @Valid @RequestBody UsuarioRequestDTO dto) {
        return usuarioService.atualizarUsuario(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable Long id) {

        usuarioService.deletarUsuario(id);

        return ResponseEntity.noContent().build();
    }
}
