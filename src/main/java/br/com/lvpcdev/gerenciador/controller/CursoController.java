package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.CursoRequestDTO;
import br.com.lvpcdev.gerenciador.model.Curso;
import br.com.lvpcdev.gerenciador.service.CursoService;
import jakarta.validation.Valid;
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

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.listarTodos();
    }
}
