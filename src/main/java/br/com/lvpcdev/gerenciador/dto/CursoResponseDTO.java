package br.com.lvpcdev.gerenciador.dto;

public record CursoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer cargaHoraria,
        Boolean ativo
) {
}
