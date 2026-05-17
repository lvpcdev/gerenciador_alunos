package br.com.lvpcdev.gerenciador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record AlunoRequestDTO(

        @NotBlank(message = "O nome do aluno é obrigatório.")
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O CPF informado é inválido.")
        String cpf,

        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        String telefone

) {
}
