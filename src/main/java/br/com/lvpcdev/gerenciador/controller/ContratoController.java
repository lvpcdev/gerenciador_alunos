package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.ContratoRequestDTO;
import br.com.lvpcdev.gerenciador.dto.ContratoResponseDTO;
import br.com.lvpcdev.gerenciador.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @PostMapping
    public ContratoResponseDTO criarContrato(@Valid @RequestBody ContratoRequestDTO dto) {
        return contratoService.criarContrato(dto);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<ContratoResponseDTO> listarPorAluno(@PathVariable Long alunoId) {
        return contratoService.listarPorAluno(alunoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarContrato(@PathVariable Long id) {

        contratoService.deletarContrato(id);

        return ResponseEntity.noContent().build();
    }

}
