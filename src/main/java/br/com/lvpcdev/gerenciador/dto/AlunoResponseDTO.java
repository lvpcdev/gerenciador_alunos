package br.com.lvpcdev.gerenciador.dto;

import java.time.LocalDate;

public record AlunoResponseDTO(
        Long id,
        String nome,
        String cpf,
        String rg,
        String email,
        String telefone,
        LocalDate dataNascimento,
        String responsavelLegal,
        String endereco,
        Boolean ativo
) {
}
