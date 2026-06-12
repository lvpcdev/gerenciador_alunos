package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.enums.Perfil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotBlank(message = "O login é obrigatório")
        String login,

        String senha,

        @NotBlank(message = "O nome é obrigatório")
        String nome,

        @NotNull(message = "O perfil é obrigatório")
        Perfil perfil
) {
}
