package br.com.lvpcdev.gerenciador.biblioteca.controller;


import br.com.lvpcdev.gerenciador.biblioteca.dto.LivroRequestDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.LivroResponseDTO;
import br.com.lvpcdev.gerenciador.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {
    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @PostMapping
    public LivroResponseDTO cadastrarLivro(@Valid @RequestBody LivroRequestDTO dto) {
        return livroService.salvarLivro(dto);
    }

    @GetMapping("/ativos")
    public List<LivroResponseDTO> listarAtivos(@RequestParam(defaultValue = "id") String ordenacao) {
        return livroService.listarAtivos(ordenacao);
    }

    @GetMapping("/inativos")
    public List<LivroResponseDTO> listarInativos(@RequestParam(defaultValue = "id") String ordenacao) {
        return livroService.listarInativos(ordenacao);
    }



    @PutMapping("/{id}")
    public LivroResponseDTO editarLivro(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO dto) {
        return livroService.atualizarLivro(id, dto);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<LivroResponseDTO> ativarLivro(@PathVariable Long id) {
        LivroResponseDTO livroAtivado = livroService.ativarLivro(id);

        return ResponseEntity.ok(livroAtivado);
    }

    @PutMapping("/{id}/inativar")
    public ResponseEntity<LivroResponseDTO> inativarLivro(@PathVariable Long id) {
        LivroResponseDTO livroInativado = livroService.inativarLivro(id);
        return ResponseEntity.ok(livroInativado);
    }
}
