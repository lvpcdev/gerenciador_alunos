package br.com.lvpcdev.gerenciador.biblioteca.dto;

public record LivroResponseDTO(
        Long id,
        String titulo,
        String autor,
        Integer quantidadeTotal,
        Integer quantidadeDisponivel,
        Boolean ativo
) {
}
