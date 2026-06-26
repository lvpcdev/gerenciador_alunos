package br.com.lvpcdev.gerenciador.biblioteca.service;

import br.com.lvpcdev.gerenciador.biblioteca.dto.EmprestimoRequestDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.EmprestimoResponseDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.LivroResumoDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.PessoaResumoDTO;
import br.com.lvpcdev.gerenciador.biblioteca.model.Emprestimo;
import br.com.lvpcdev.gerenciador.biblioteca.model.Livro;
import br.com.lvpcdev.gerenciador.biblioteca.model.Pessoa;
import br.com.lvpcdev.gerenciador.biblioteca.model.StatusEmprestimo;
import br.com.lvpcdev.gerenciador.biblioteca.repository.EmprestimoRepository;
import br.com.lvpcdev.gerenciador.biblioteca.repository.LivroRepository;
import br.com.lvpcdev.gerenciador.biblioteca.repository.PessoaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepository;
    private final PessoaRepository pessoaRepository;
    private final LivroRepository livroRepository;

    public EmprestimoService(EmprestimoRepository emprestimoRepository, PessoaRepository pessoaRepository, LivroRepository livroRepository) {
        this.emprestimoRepository = emprestimoRepository;
        this.pessoaRepository = pessoaRepository;
        this.livroRepository = livroRepository;
    }

    @Transactional
    public EmprestimoResponseDTO registrarEmprestimo(EmprestimoRequestDTO dto) {
        Pessoa pessoa = pessoaRepository.findById(dto.pessoaId())
                .orElseThrow(() -> new IllegalArgumentException("Pessoa não encontrada"));
        Livro livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));

        if (dto.dataPrevistaDevolucao().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data prevista de devolução deve ser posterior à data de hoje.");
        }

        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataPrevistaDevolucao(dto.dataPrevistaDevolucao());
        emprestimo.setStatusEmprestimo(StatusEmprestimo.EMPRESTADO);
        emprestimo.setLivro(livro);
        emprestimo.setPessoa(pessoa);

        livro.decrementarEstoque();
        livroRepository.save(livro);

        emprestimoRepository.save(emprestimo);

        return toDTO(emprestimo);
    }

    @Transactional
    public EmprestimoResponseDTO devolverEmprestimo(Long id) {
        Emprestimo emprestimo = emprestimoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Emprestimo não encontrado"));

        emprestimo.setDataDevolucao(LocalDate.now());
        emprestimo.setStatusEmprestimo(StatusEmprestimo.DEVOLVIDO);

        Livro livro = emprestimo.getLivro();
        livro.incrementarEstoque();

        livroRepository.save(livro);
        emprestimoRepository.save(emprestimo);

        return toDTO(emprestimo);
    }

    public List<EmprestimoResponseDTO> listarTodos() {
        return emprestimoRepository.findAll().stream().map(this::toDTO).toList();
    }

    public List<EmprestimoResponseDTO> listarAtrasados() {
        return emprestimoRepository.findByStatusEmprestimo(StatusEmprestimo.ATRASADO).stream().map(this::toDTO).toList();
    }


    private EmprestimoResponseDTO toDTO(Emprestimo emprestimo) {
        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                new PessoaResumoDTO(emprestimo.getPessoa().getId(),emprestimo.getPessoa().getNome()),
                new LivroResumoDTO(emprestimo.getLivro().getId(), emprestimo.getLivro().getTitulo()),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataPrevistaDevolucao(),
                emprestimo.getDataDevolucao(),
                emprestimo.getStatusEmprestimo()
        );
    }
}
