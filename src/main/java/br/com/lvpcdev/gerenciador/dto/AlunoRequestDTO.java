package br.com.lvpcdev.gerenciador.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record AlunoRequestDTO(

        @NotBlank(message = "O nome do aluno é obrigatório.")
        String nome,

        @NotBlank(message = "O CPF é obrigatório.")
        @CPF(message = "O CPF informado é inválido.")
        String cpf,

        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        String telefone,

        @NotNull(message = "A data de nascimento é obrigatória.")
        @Past(message = "A data de nascimento deve ser uma data no passado.")
        LocalDate dataNascimento,

        String rg,

        String responsavelLegal,

        @NotBlank(message = "O endereço é obrigatório.")
        String endereco


) {
}
