package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.RegistroAulaRequestDTO;
import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.model.Curso;
import br.com.lvpcdev.gerenciador.model.RegistroAula;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import br.com.lvpcdev.gerenciador.repository.CursoRepository;
import br.com.lvpcdev.gerenciador.service.RegistroAulaService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroAulaController {

    private final RegistroAulaService registroAulaService;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public RegistroAulaController(RegistroAulaService registroAulaService, AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.registroAulaService = registroAulaService;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    @PostMapping
    public RegistroAula regitrarAula(@Valid @RequestBody RegistroAulaRequestDTO dto) {
        Aluno alunoEncontrado = alunoRepository.findById(dto.alunoId()).
                orElseThrow(() -> new RuntimeException("Aluno não encontrado no banco de dados"));
        Curso cursoEncontrado = cursoRepository.findById(dto.cursoId()).
                orElseThrow(() -> new RuntimeException("Curso não encontrado no banco de dados"));

        RegistroAula registroAula = new RegistroAula();

        registroAula.setAluno(alunoEncontrado);
        registroAula.setCurso(cursoEncontrado);

        registroAula.setDataAula(dto.dataAula());
        registroAula.setHoraInicio(dto.horaInicio());
        registroAula.setHoraTermino(dto.horaTermino());
        registroAula.setExercicio(dto.exercicio());
        registroAula.setTipoAula(dto.tipoAula());
        registroAula.setNumeroMaquina(dto.numeroMaquina());
        registroAula.setPresencaStatus(dto.presencaStatus());

        return registroAulaService.salvarRegistro(registroAula);
    }

    @GetMapping
    public List<RegistroAula> listarRegistros() {
        return registroAulaService.listarTodos();
    }
}
