package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.enums.DiaSemana;
import br.com.lvpcdev.gerenciador.model.enums.ModalidadeContrato;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ContratoRequestDTO(
        @NotNull(message = "O ID do aluno é obrigatório.")
        Long alunoId,

        @NotNull(message = "A modalidade do contrato é obrigatória.")
        ModalidadeContrato modalidade,

        @NotNull(message = "A data de inicio é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "As horas/aulas por mês são obrigatórias")
        Integer horasAulasMes,

        @NotNull(message = "O dias do vencimento é obrigatório")
        Integer diaVencimento,

        @NotNull(message = "A hora de inicio é obrigatória")
        LocalTime horaInicio,

        @NotNull(message = "A hora de termino é obrigatória")
        LocalTime horaTermino,

        @NotNull(message = "Selecione pelo menos 1 dia da semana")
        List<DiaSemana> diasSemana

) {

}
