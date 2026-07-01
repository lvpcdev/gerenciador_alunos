package br.com.lvpcdev.gerenciador.biblioteca.controller;



import br.com.lvpcdev.gerenciador.biblioteca.dto.PessoaRequestDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.PessoaResponseDTO;
import br.com.lvpcdev.gerenciador.biblioteca.service.PessoaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public PessoaResponseDTO cadastrarPessoa(@Valid @RequestBody PessoaRequestDTO dto) {
        return pessoaService.salvarPessoa(dto);
    }

    @GetMapping("/ativos")
    public List<PessoaResponseDTO> listarAtivos(@RequestParam(defaultValue = "id") String ordenacao) {
        return pessoaService.listarAtivos(ordenacao);
    }

    @GetMapping("/inativos")
    public List<PessoaResponseDTO> listarInativos(@RequestParam(defaultValue = "id") String ordenacao) {
        return pessoaService.listarInativos(ordenacao);
    }


    @PutMapping("/{id}")
    public PessoaResponseDTO editarPessoa(@PathVariable Long id, @Valid @RequestBody PessoaRequestDTO dto) {
        return pessoaService.atualizarPessoa(id, dto);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<PessoaResponseDTO> ativarPessoa(@PathVariable Long id) {
        PessoaResponseDTO pessoaAtivada = pessoaService.ativarPessoa(id);

        return ResponseEntity.ok(pessoaAtivada);
    }


    @PutMapping("/{id}/inativar")
    public ResponseEntity<PessoaResponseDTO> inativarPessoa(@PathVariable Long id) {
        PessoaResponseDTO pessoaInativada = pessoaService.inativarPessoa(id);

        return ResponseEntity.ok(pessoaInativada);
    }
}
