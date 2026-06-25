package br.com.lvpcdev.gerenciador.biblioteca.dto;

public record PessoaResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        String endereco,
        Boolean ativo
) {
}
