package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.dto.*;
import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.model.Contrato;
import br.com.lvpcdev.gerenciador.model.Curso;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import br.com.lvpcdev.gerenciador.repository.ContratoRepository;
import br.com.lvpcdev.gerenciador.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public ContratoService(ContratoRepository contratoRepository, AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.contratoRepository = contratoRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    public ContratoResponseDTO criarContrato(ContratoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        Contrato contrato = new Contrato();
        contrato.setAluno(aluno);
        contrato.setCurso(curso);
        contrato.setDataInicio(dto.dataInicio());
        contrato.setHorasAulasMes(dto.horasAulasMes());
        contrato.setDiaVencimento(dto.diaVencimento());
        contrato.setHoraInicio(dto.horaInicio());
        contrato.setHoraTermino(dto.horaTermino());
        contrato.setDiasSemana(dto.diasSemana());
        contrato.setDataCriacao(LocalDate.now());

        return toDTO(contratoRepository.save(contrato));
    }

    public List<ContratoResponseDTO> listarPorAluno(Long alunoId) {
        return contratoRepository.findByAlunoId(alunoId).stream().map(this::toDTO).toList();
    }

    public void deletarContrato(Long id) {
        Contrato contratoExistente = contratoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado para a exclusão."));

        contratoRepository.delete(contratoExistente);
    }

    private ContratoResponseDTO toDTO(Contrato contrato) {
        return new ContratoResponseDTO(
                contrato.getId(),
                new AlunoResumoDTO(contrato.getAluno().getId(), contrato.getAluno().getNome()),
                new CursoResumoDTO(contrato.getCurso().getId(), contrato.getCurso().getNome()),
                contrato.getDataInicio(),
                contrato.getHorasAulasMes(),
                contrato.getDiaVencimento(),
                contrato.getHoraInicio(),
                contrato.getHoraTermino(),
                contrato.getDiasSemana(),
                contrato.getDataCriacao()
        );
    }

}
