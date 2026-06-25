package br.com.lvpcdev.gerenciador.biblioteca.service;

import br.com.lvpcdev.gerenciador.biblioteca.model.Emprestimo;
import br.com.lvpcdev.gerenciador.biblioteca.model.StatusEmprestimo;
import br.com.lvpcdev.gerenciador.biblioteca.repository.EmprestimoRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EmprestimoScheduler {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoScheduler(EmprestimoRepository emprestimoRepository) {
        this.emprestimoRepository = emprestimoRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void atualizarEmprestimosAtrasados() {
        List<Emprestimo> atrasados = emprestimoRepository.findByStatusEmprestimoAndDataPrevistaDevolucaoBefore(StatusEmprestimo.EMPRESTADO, LocalDate.now());

        for (Emprestimo emprestimo : atrasados) {
            emprestimo.setStatusEmprestimo(StatusEmprestimo.ATRASADO);
        }

        emprestimoRepository.saveAll(atrasados);
    }
}
