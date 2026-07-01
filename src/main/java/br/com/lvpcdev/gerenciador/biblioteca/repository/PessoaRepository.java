package br.com.lvpcdev.gerenciador.biblioteca.repository;

import br.com.lvpcdev.gerenciador.biblioteca.model.Pessoa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findAllByAtivoTrue();

    List<Pessoa> findAllByAtivoFalse();

    List<Pessoa> findAllByAtivoTrue(Sort sort);

    List<Pessoa> findAllByAtivoFalse(Sort sort);
}
