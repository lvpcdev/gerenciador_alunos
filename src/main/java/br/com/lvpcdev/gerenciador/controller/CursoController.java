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
    public List<CursoResponseDTO> listarAtivos(@RequestParam(defaultValue = "id") String ordenacao) {
        return cursoService.listarAtivos(ordenacao);
    }

    @GetMapping("/inativos")
    public List<CursoResponseDTO> listarInativos(@RequestParam(defaultValue = "id") String ordenacao) {
        return cursoService.listarInativos(ordenacao);
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

    @PutMapping("/{id}/inativar")
    public ResponseEntity<CursoResponseDTO> inativarCurso(@PathVariable Long id) {
        CursoResponseDTO cursoInativado = cursoService.inativarCurso(id);
        return ResponseEntity.ok(cursoInativado);
    }
}
