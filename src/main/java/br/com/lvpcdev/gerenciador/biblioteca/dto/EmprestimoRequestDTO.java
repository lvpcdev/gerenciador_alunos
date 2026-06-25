package br.com.lvpcdev.gerenciador.biblioteca.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmprestimoRequestDTO(
        @NotNull(message = "O ID da pessoa é obrigatório.")
        Long pessoaId,

        @NotNull(message = "O ID do livro é obrigatório.")
        Long livroId,

        @NotNull(message = "A data prevista de devolução é obrigatória.")
        LocalDate dataPrevistaDevolucao
) {
}
