package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.PresencaStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroAulaRequestDTO(

        @NotNull(message = "O ID do aluno é obrigatório.")
        Long alunoId,

        @NotNull(message = "O ID do curso é obrigatório.")
        Long cursoId,

        @NotNull(message = "A data da aula é obrigatória.")
        LocalDate dataAula,

        @NotNull(message = "A hora de início é obrigatória.")
        LocalTime horaInicio,

        @NotNull(message = "A hora de término é obrigatória.")
        LocalTime horaTermino,

        @NotBlank(message = "O exercício é obrigatório.")
        String exercicio,

        @NotBlank(message = "O tipo de aula é obrigatório.")
        String tipoAula,

        @NotNull(message = "O númeor da máquina é obrigatório.")
        @Positive(message = "O número da máquina deve ser maior que zero.")
        Integer numeroMaquina,

        @NotNull(message = "O status de presença é obrigatório.")
        PresencaStatus presencaStatus
) {
}
