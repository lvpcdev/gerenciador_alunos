package br.com.lvpcdev.gerenciador.repository;

import br.com.lvpcdev.gerenciador.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
}
