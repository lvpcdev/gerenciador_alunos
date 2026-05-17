package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.CursoRequestDTO;
import br.com.lvpcdev.gerenciador.model.Curso;
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
    public Curso cadastrarAluno(@Valid @RequestBody CursoRequestDTO dto) {

        Curso curso = new Curso();

        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());

        return cursoService.salvarCurso(curso);
    }

    @GetMapping("/ativos")
    public List<Curso> listarAtivos() {
        return cursoService.listarAtivos();
    }

    @GetMapping("/inativos")
    public List<Curso> listarInativos() {
        return cursoService.listarInativos();
    }



    @PutMapping("/{id}")
    public Curso editarCurso(@PathVariable Long id, @Valid @RequestBody CursoRequestDTO dto) {

        Curso novosDados = new Curso();
        novosDados.setNome(dto.nome());
        novosDados.setDescricao(dto.descricao());
        novosDados.setCargaHoraria(dto.cargaHoraria());

        return cursoService.atualizarCurso(id, novosDados);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Curso> ativarCurso(@PathVariable Long id) {
        Curso cursoAtivado = cursoService.ativarCurso(id);

        return ResponseEntity.ok(cursoAtivado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarCurso(@PathVariable Long id) {
        cursoService.inativarCurso(id);
        return ResponseEntity.noContent().build();
    }
}
