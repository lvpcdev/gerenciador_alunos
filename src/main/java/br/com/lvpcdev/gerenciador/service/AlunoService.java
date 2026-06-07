package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService (AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno salvarAluno(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    public List<Aluno> listarAtivos() {
        return alunoRepository.findAllByAtivoTrue();
    }

    public List<Aluno> listarInativos() {
        return alunoRepository.findAllByAtivoFalse();
    }

    public Aluno atualizarAluno(Long id, Aluno novosDados) {

        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado para a edição."));

        alunoExistente.setNome(novosDados.getNome());
        alunoExistente.setCpf(novosDados.getCpf());
        alunoExistente.setEmail(novosDados.getEmail());
        alunoExistente.setTelefone(novosDados.getTelefone());
        alunoExistente.setDataNascimento(novosDados.getDataNascimento());
        alunoExistente.setRg(novosDados.getRg());
        alunoExistente.setResponsavelLegal(novosDados.getResponsavelLegal());
        alunoExistente.setEndereco(novosDados.getEndereco());

        return alunoRepository.save(alunoExistente);
    }

    public void inativarAluno(Long id) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        alunoExistente.setAtivo(false);

        alunoRepository.save(alunoExistente);
    }

    public Aluno ativarAluno(Long id) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        alunoExistente.setAtivo(true);

        return alunoRepository.save(alunoExistente);
    }
}
