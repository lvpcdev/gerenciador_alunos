package br.com.lvpcdev.gerenciador.repository;

import br.com.lvpcdev.gerenciador.model.RegistroAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RegistroAulaRepository extends JpaRepository<RegistroAula, Long> {
    List<RegistroAula> findByAlunoId(Long alunoId);

    List<RegistroAula> findByAlunoIdAndDataAulaBetween(Long alunoId, LocalDate inicio, LocalDate fim);

    List<RegistroAula> findByDataAulaOrderByHoraInicioAsc(LocalDate dataAula);
}
