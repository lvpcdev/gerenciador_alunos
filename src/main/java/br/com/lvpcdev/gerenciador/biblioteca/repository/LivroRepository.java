package br.com.lvpcdev.gerenciador.biblioteca.repository;

import br.com.lvpcdev.gerenciador.biblioteca.model.Livro;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findAllByAtivoTrue();

    List<Livro> findAllByAtivoFalse();

    List<Livro> findAllByAtivoTrue(Sort sort);

    List<Livro> findAllByAtivoFalse(Sort sort);
}
