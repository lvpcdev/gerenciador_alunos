package br.com.lvpcdev.gerenciador.repository;

import br.com.lvpcdev.gerenciador.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findAllByAtivoTrue();

    List<Curso> findAllByAtivoFalse();
}
