package br.com.lvpcdev.gerenciador.repository;

import br.com.lvpcdev.gerenciador.model.Aluno;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    List<Aluno> findAllByAtivoTrue(Sort sort);

    List<Aluno> findAllByAtivoFalse(Sort sort);
}
