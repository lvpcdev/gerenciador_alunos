package br.com.lvpcdev.gerenciador.biblioteca.repository;

import br.com.lvpcdev.gerenciador.biblioteca.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
