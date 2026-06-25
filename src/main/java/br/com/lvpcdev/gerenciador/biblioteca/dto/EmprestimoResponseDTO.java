package br.com.lvpcdev.gerenciador.biblioteca.dto;

import br.com.lvpcdev.gerenciador.biblioteca.model.StatusEmprestimo;

import java.time.LocalDate;

public record EmprestimoResponseDTO(
        Long id,
        PessoaResumoDTO pessoa,
        LivroResumoDTO livro,
        LocalDate dataEmprestimo,
        LocalDate dataPrevistaDevolucao,
        LocalDate dataDevolucao,
        StatusEmprestimo statusEmprestimo
) {
}
