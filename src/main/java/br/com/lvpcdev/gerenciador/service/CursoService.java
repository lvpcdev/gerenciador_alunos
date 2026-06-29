package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.dto.CursoRequestDTO;
import br.com.lvpcdev.gerenciador.dto.CursoResponseDTO;
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

    public CursoResponseDTO salvarCurso(CursoRequestDTO dto) {

        Curso curso = new Curso();

        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setCargaHoraria(dto.cargaHoraria());
        curso.setCategoria(dto.categoria());

        return toDTO(cursoRepository.save(curso));
    }

    public List<CursoResponseDTO> listarAtivos() {
        return cursoRepository.findAllByAtivoTrue().stream().map(this::toDTO).toList();
    }

    public List<CursoResponseDTO> listarInativos() {
        return cursoRepository.findAllByAtivoFalse().stream().map(this::toDTO).toList();
    }

    public CursoResponseDTO atualizarCurso(Long id, CursoRequestDTO dto) {

        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado para a edição."));

        cursoExistente.setNome(dto.nome());
        cursoExistente.setDescricao(dto.descricao());
        cursoExistente.setCargaHoraria(dto.cargaHoraria());
        cursoExistente.setCategoria(dto.categoria());

        return toDTO(cursoRepository.save(cursoExistente));
    }

    public CursoResponseDTO inativarCurso(Long id) {

        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado."));

        cursoExistente.setAtivo(false);
        return toDTO(cursoRepository.save(cursoExistente));
    }

    public CursoResponseDTO ativarCurso(Long id) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado para reativação."));

        cursoExistente.setAtivo(true);
        return toDTO(cursoRepository.save(cursoExistente));
    }

    private CursoResponseDTO toDTO(Curso curso) {
        return new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getCargaHoraria(),
                curso.getAtivo(),
                curso.getCategoria()
        );

    }
}
