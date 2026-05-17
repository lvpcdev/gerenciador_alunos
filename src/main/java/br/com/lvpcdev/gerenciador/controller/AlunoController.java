package br.com.lvpcdev.gerenciador.controller;


import br.com.lvpcdev.gerenciador.dto.AlunoRequestDTO;
import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.service.AlunoService;
import jakarta.validation.Valid;
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
    public Aluno cadastrarAluno(@Valid @RequestBody AlunoRequestDTO dto) {

        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setEmail(dto.email());
        aluno.setTelefone(dto.telefone());

        return alunoService.salvarAluno(aluno);
    }

    @GetMapping
    public List<Aluno> listarAlunos() {
        return alunoService.listarTodos();
    }
}
