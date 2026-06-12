package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.enums.Perfil;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String nome,
        Perfil perfil
) {
}
