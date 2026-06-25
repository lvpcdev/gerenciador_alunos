package br.com.lvpcdev.gerenciador.biblioteca.service;

import br.com.lvpcdev.gerenciador.biblioteca.dto.PessoaRequestDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.PessoaResponseDTO;
import br.com.lvpcdev.gerenciador.biblioteca.model.Pessoa;
import br.com.lvpcdev.gerenciador.biblioteca.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public PessoaResponseDTO salvarPessoa(PessoaRequestDTO dto) {
        Pessoa pessoa = new Pessoa();
        pessoa.setCpf(dto.cpf());
        pessoa.setNome(dto.nome());
        pessoa.setEndereco(dto.endereco());
        pessoa.setTelefone(dto.telefone());
        pessoa.setEmail(dto.email());

        return toDTO(pessoaRepository.save(pessoa));
    }

    public List<PessoaResponseDTO> listarAtivos() {
        return pessoaRepository.findAllByAtivoTrue().stream().map(this::toDTO).toList();
    }

    public List<PessoaResponseDTO> listarInativos() {
        return pessoaRepository.findAllByAtivoFalse().stream().map(this::toDTO).toList();
    }

    public PessoaResponseDTO atualizarPessoa(Long id, PessoaRequestDTO dto) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada para a edição."));

        pessoaExistente.setEmail(dto.email());
        pessoaExistente.setTelefone(dto.telefone());
        pessoaExistente.setNome(dto.nome());
        pessoaExistente.setEndereco(dto.endereco());
        pessoaExistente.setCpf(dto.cpf());

        return toDTO(pessoaRepository.save(pessoaExistente));
    }

    public PessoaResponseDTO inativarPessoa(Long id) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        pessoaExistente.setAtivo(false);

        return toDTO(pessoaRepository.save(pessoaExistente));
    }

    public PessoaResponseDTO ativarPessoa(Long id) {
        Pessoa pessoaExistente = pessoaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada."));

        pessoaExistente.setAtivo(true);

        return toDTO(pessoaRepository.save(pessoaExistente));
    }



    private PessoaResponseDTO toDTO(Pessoa pessoa) {
        return new PessoaResponseDTO(
                pessoa.getId(),
                pessoa.getNome(),
                pessoa.getCpf(),
                pessoa.getEmail(),
                pessoa.getTelefone(),
                pessoa.getEndereco(),
                pessoa.getAtivo()
        );
    }
}
