package br.com.lvpcdev.gerenciador.biblioteca.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PessoaRequestDTO(
        @NotBlank(message = "O nome é obrigatório.")
        String nome,

        @Pattern(
                regexp = "^$|^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$",
                message = "O CPF informado é inválido."
        )
        String cpf,

        @Email(message = "O formato do e-mail é inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        String telefone,

        @NotBlank(message = "O endereço é obrigatório.")
        String endereco
) {

}
