package br.com.lvpcdev.gerenciador.dto;

public record UsuarioResponseDTO(
        Long id,
        String login,
        String nome
) {
}
