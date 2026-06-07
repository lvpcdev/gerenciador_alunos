package br.com.lvpcdev.gerenciador.controller;


import br.com.lvpcdev.gerenciador.dto.AlunoRequestDTO;
import br.com.lvpcdev.gerenciador.model.Aluno;
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
    public Aluno cadastrarAluno(@Valid @RequestBody AlunoRequestDTO dto) {

        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setEmail(dto.email());
        aluno.setTelefone(dto.telefone());
        aluno.setDataNascimento(dto.dataNascimento());
        aluno.setRg(dto.rg());
        aluno.setResponsavelLegal(dto.responsavelLegal());
        aluno.setEndereco(dto.endereco());

        return alunoService.salvarAluno(aluno);
    }

    @GetMapping("/ativos")
    public List<Aluno> listarAtivos() {
        return alunoService.listarAtivos();
    }

    @GetMapping("/inativos")
    public List<Aluno> listarInativos() {
        return alunoService.listarInativos();
    }


    @PutMapping("/{id}")
    public Aluno editarAluno(@PathVariable Long id, @Valid @RequestBody AlunoRequestDTO dto) {

        Aluno novosDados = new Aluno();
        novosDados.setNome(dto.nome());
        novosDados.setCpf(dto.cpf());
        novosDados.setEmail(dto.email());
        novosDados.setTelefone(dto.telefone());
        novosDados.setDataNascimento(dto.dataNascimento());
        novosDados.setRg(dto.rg());
        novosDados.setResponsavelLegal(dto.responsavelLegal());
        novosDados.setEndereco(dto.endereco());

        return alunoService.atualizarAluno(id, novosDados);
    }

    @PutMapping("/{id}/ativar")
    public ResponseEntity<Aluno> ativarAluno(@PathVariable Long id) {
        Aluno alunoAtivado = alunoService.ativarAluno(id);

        return ResponseEntity.ok(alunoAtivado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inativarAluno(@PathVariable Long id) {
        alunoService.inativarAluno(id);

        return ResponseEntity.noContent().build();
    }
}
