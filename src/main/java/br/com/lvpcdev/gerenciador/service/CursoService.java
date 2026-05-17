package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.model.Curso;
import br.com.lvpcdev.gerenciador.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    public Curso salvarCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    public List<Curso> listarAtivos() {
        return cursoRepository.findAllByAtivoTrue();
    }

    public List<Curso> listarInativos() {
        return cursoRepository.findAllByAtivoFalse();
    }

    public Curso atualizarCurso(Long id, Curso novosDados) {

        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado para a edição."));

        cursoExistente.setNome(novosDados.getNome());
        cursoExistente.setDescricao(novosDados.getDescricao());
        cursoExistente.setCargaHoraria(novosDados.getCargaHoraria());

        return cursoRepository.save(cursoExistente);
    }

    public void inativarCurso(Long id) {

        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        cursoExistente.setAtivo(false);
        cursoRepository.save(cursoExistente);
    }

    public Curso ativarCurso(Long id) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado para reativação."));

        cursoExistente.setAtivo(true);
        return cursoRepository.save(cursoExistente);
    }
}
