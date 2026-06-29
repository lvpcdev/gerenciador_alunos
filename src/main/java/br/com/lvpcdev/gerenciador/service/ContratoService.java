package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.dto.*;
import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.model.Contrato;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import br.com.lvpcdev.gerenciador.repository.ContratoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private final AlunoRepository alunoRepository;

    public ContratoService(ContratoRepository contratoRepository, AlunoRepository alunoRepository) {
        this.contratoRepository = contratoRepository;
        this.alunoRepository = alunoRepository;
    }

    public ContratoResponseDTO criarContrato(ContratoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));


        Contrato contrato = new Contrato();
        contrato.setAluno(aluno);
        contrato.setModalidade(dto.modalidade());
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
                contrato.getModalidade(),
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
