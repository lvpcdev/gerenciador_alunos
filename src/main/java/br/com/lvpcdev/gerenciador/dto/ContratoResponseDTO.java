package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.enums.DiaSemana;
import br.com.lvpcdev.gerenciador.model.enums.ModalidadeContrato;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ContratoResponseDTO(
        Long id,
        AlunoResumoDTO aluno,
        ModalidadeContrato modalidade,
        LocalDate dataInicio,
        Integer horasAulasMes,
        Integer diaVencimento,
        LocalTime horaInicio,
        LocalTime horaTermino,
        List<DiaSemana> diasSemana,
        LocalDate dataCriacao
) {
}
