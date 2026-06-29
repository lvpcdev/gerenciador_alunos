package br.com.lvpcdev.gerenciador.dto;

import br.com.lvpcdev.gerenciador.model.enums.CategoriaCurso;

public record CursoResponseDTO(
        Long id,
        String nome,
        String descricao,
        Integer cargaHoraria,
        Boolean ativo,
        CategoriaCurso categoria
) {
}
