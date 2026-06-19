package br.com.lvpcdev.gerenciador.biblioteca.repository;

import br.com.lvpcdev.gerenciador.biblioteca.model.Emprestimo;
import br.com.lvpcdev.gerenciador.biblioteca.model.StatusEmprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByStatusEmprestimoAndDataPrevistaDevolucaoBefore(StatusEmprestimo statusEmprestimo, LocalDate data);
}
