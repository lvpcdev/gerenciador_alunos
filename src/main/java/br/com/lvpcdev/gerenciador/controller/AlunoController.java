package br.com.lvpcdev.gerenciador.controller;


import br.com.lvpcdev.gerenciador.dto.AlunoRequestDTO;
import br.com.lvpcdev.gerenciador.dto.AlunoResponseDTO;
import br.com.lvpcdev.gerenciador.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public AlunoResponseDTO cadastrarAluno(@Valid @RequestBody AlunoRequestDTO dto) {
        return alunoService.salvarAluno(dto);
    }

    @GetMapping("/ativos")
    public List<AlunoResponseDTO> listarAtivos() {
        return alunoService.listarAtivos();
    }

    @GetMapping("/inativos")
    public List<AlunoResponseDTO> listarInativos() {
        return alunoService.listarInativos();
    }


    @PutMapping("/{id}")
    public AlunoResponseDTO editarAluno(@PathVariable Long id, @Valid @RequestBody AlunoRequestDTO dto) {
        return alunoService.atualizarAluno(id, dto);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<AlunoResponseDTO> ativarAluno(@PathVariable Long id) {
        AlunoResponseDTO alunoAtivado = alunoService.ativarAluno(id);

        return ResponseEntity.ok(alunoAtivado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarAluno(@PathVariable Long id) {
        alunoService.inativarAluno(id);

        return ResponseEntity.noContent().build();
    }
}
