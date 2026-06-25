package br.com.lvpcdev.gerenciador.biblioteca.service;


import br.com.lvpcdev.gerenciador.biblioteca.dto.LivroRequestDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.LivroResponseDTO;
import br.com.lvpcdev.gerenciador.biblioteca.model.Livro;
import br.com.lvpcdev.gerenciador.biblioteca.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public LivroResponseDTO salvarLivro(LivroRequestDTO dto) {
        Livro livro = new Livro();

        livro.setAutor(dto.autor());
        livro.setTitulo(dto.titulo());
        livro.setQuantidadeTotal(dto.quantidadeTotal());
        livro.setQuantidadeDisponivel(dto.quantidadeTotal());

        return toDTO(livroRepository.save(livro));
    }

    public List<LivroResponseDTO> listarAtivos() {
        return livroRepository.findAllByAtivoTrue().stream().map(this::toDTO).toList();
    }

    public List<LivroResponseDTO> listarInativos() {
        return livroRepository.findAllByAtivoFalse().stream().map(this::toDTO).toList();
    }

    public LivroResponseDTO atualizarLivro(Long id, LivroRequestDTO dto) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado para a edição."));

        livroExistente.setTitulo(dto.titulo());
        livroExistente.setAutor(dto.autor());
        livroExistente.setQuantidadeTotal(dto.quantidadeTotal());

        return toDTO(livroRepository.save(livroExistente));
    }

    public LivroResponseDTO inativarLivro(Long id) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        livroExistente.setAtivo(false);

        return toDTO(livroRepository.save(livroExistente));
    }

    public LivroResponseDTO ativarLivro(Long id) {
        Livro livroExistente = livroRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));

        livroExistente.setAtivo(true);

        return toDTO(livroRepository.save(livroExistente));
    }



    private LivroResponseDTO toDTO(Livro livro) {
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getQuantidadeTotal(),
                livro.getQuantidadeDisponivel(),
                livro.getAtivo()
        );
    }
}
