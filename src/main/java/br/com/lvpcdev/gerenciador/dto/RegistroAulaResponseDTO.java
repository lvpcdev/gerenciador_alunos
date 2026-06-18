package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.enums.PresencaStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record RegistroAulaResponseDTO(
        Long id,
        AlunoResumoDTO aluno,
        CursoResumoDTO curso,
        LocalDate dataAula,
        LocalTime horaInicio,
        LocalTime horaTermino,
        String exercicio,
        String tipoAula,
        Integer numeroMaquina,
        PresencaStatus presencaStatus
) {
}
