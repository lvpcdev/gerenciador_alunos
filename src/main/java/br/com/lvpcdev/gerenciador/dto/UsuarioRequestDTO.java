package br.com.lvpcdev.gerenciador.dto;

import jakarta.validation.constraints.NotBlank;

public record UsuarioRequestDTO(
        @NotBlank(message = "O login é obrigatório")
        String login,

        @NotBlank(message = "A senha é obrigatória")
        String senha,

        @NotBlank(message = "O nome é obrigatório")
        String nome
) {
}
