package br.com.lvpcdev.gerenciador.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CursoRequestDTO(

        @NotBlank(message = "O nome do curso é obrigatório.")
        String nome,

        @NotBlank(message = "A descrição do curso é obrigatória")
        String descricao,

        @NotNull(message = "A carga horária é obrigatória.")
        @Positive(message = "A carga horária deve ser maior que zero.")
        Integer cargaHoraria
) { }
