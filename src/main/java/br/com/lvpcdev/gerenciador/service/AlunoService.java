package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.dto.AlunoRequestDTO;
import br.com.lvpcdev.gerenciador.dto.AlunoResponseDTO;
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

    public AlunoResponseDTO salvarAluno(AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setEmail(dto.email());
        aluno.setTelefone(dto.telefone());
        aluno.setDataNascimento(dto.dataNascimento());
        aluno.setRg(dto.rg());
        aluno.setResponsavelLegal(dto.responsavelLegal());
        aluno.setEndereco(dto.endereco());

        return toDTO(alunoRepository.save(aluno));
    }

    public List<AlunoResponseDTO> listarAtivos() {

        return alunoRepository.findAllByAtivoTrue().stream().map(this::toDTO).toList();
    }

    public List<AlunoResponseDTO> listarInativos() {
        return alunoRepository.findAllByAtivoFalse().stream().map(this::toDTO).toList();
    }

    public AlunoResponseDTO atualizarAluno(Long id, AlunoRequestDTO dto) {

        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado para a edição."));

        alunoExistente.setNome(dto.nome());
        alunoExistente.setCpf(dto.cpf());
        alunoExistente.setEmail(dto.email());
        alunoExistente.setTelefone(dto.telefone());
        alunoExistente.setDataNascimento(dto.dataNascimento());
        alunoExistente.setRg(dto.rg());
        alunoExistente.setResponsavelLegal(dto.responsavelLegal());
        alunoExistente.setEndereco(dto.endereco());

        return toDTO(alunoRepository.save(alunoExistente));
    }

    public AlunoResponseDTO inativarAluno(Long id) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        alunoExistente.setAtivo(false);

        return toDTO(alunoRepository.save(alunoExistente));
    }

    public AlunoResponseDTO ativarAluno(Long id) {
        Aluno alunoExistente = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        alunoExistente.setAtivo(true);

        return toDTO(alunoRepository.save(alunoExistente));
    }

    private AlunoResponseDTO toDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getRg(),
                aluno.getEmail(),
                aluno.getTelefone(),
                aluno.getDataNascimento(),
                aluno.getResponsavelLegal(),
                aluno.getEndereco(),
                aluno.getAtivo()
        );
    }
}
