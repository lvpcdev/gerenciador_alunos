package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.CursoRequestDTO;
import br.com.lvpcdev.gerenciador.dto.CursoResponseDTO;
import br.com.lvpcdev.gerenciador.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @PostMapping
    public CursoResponseDTO cadastrarCurso(@Valid @RequestBody CursoRequestDTO dto) {
        return cursoService.salvarCurso(dto);
    }

    @GetMapping("/ativos")
    public List<CursoResponseDTO> listarAtivos() {
        return cursoService.listarAtivos();
    }

    @GetMapping("/inativos")
    public List<CursoResponseDTO> listarInativos() {
        return cursoService.listarInativos();
    }



    @PutMapping("/{id}")
    public CursoResponseDTO editarCurso(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO dto) {
        return cursoService.atualizarCurso(id, dto);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<CursoResponseDTO> ativarCurso(@PathVariable Long id) {
        CursoResponseDTO cursoAtivado = cursoService.ativarCurso(id);

        return ResponseEntity.ok(cursoAtivado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarCurso(@PathVariable Long id) {
        cursoService.inativarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
