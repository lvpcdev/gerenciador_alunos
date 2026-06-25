package br.com.lvpcdev.gerenciador.biblioteca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroRequestDTO(
        @NotBlank(message = "O título do livro é obrigatório.")
        String titulo,

        @NotBlank(message = "O autor do livro é obrigatório.")
        String autor,

        @NotNull(message = "A quantidade de estoque é obrigatória.")
        Integer quantidadeTotal
) {
}
