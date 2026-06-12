package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.dto.AlunoResumoDTO;
import br.com.lvpcdev.gerenciador.dto.CursoResumoDTO;
import br.com.lvpcdev.gerenciador.dto.RegistroAulaRequestDTO;
import br.com.lvpcdev.gerenciador.dto.RegistroAulaResponseDTO;
import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.model.Curso;
import br.com.lvpcdev.gerenciador.model.RegistroAula;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import br.com.lvpcdev.gerenciador.repository.CursoRepository;
import br.com.lvpcdev.gerenciador.repository.RegistroAulaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroAulaService {

    private final RegistroAulaRepository registroAulaRepository;
    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;

    public RegistroAulaService(RegistroAulaRepository registroAulaRepository, AlunoRepository alunoRepository, CursoRepository cursoRepository) {
        this.registroAulaRepository = registroAulaRepository;
        this.alunoRepository = alunoRepository;
        this.cursoRepository = cursoRepository;
    }

    public RegistroAulaResponseDTO salvarRegistro(RegistroAulaRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado"));
        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));

        RegistroAula registro = new RegistroAula();
        registro.setAluno(aluno);
        registro.setCurso(curso);
        registro.setDataAula(dto.dataAula());
        registro.setHoraInicio(dto.horaInicio());
        registro.setHoraTermino(dto.horaTermino());
        registro.setExercicio(dto.exercicio());
        registro.setTipoAula(dto.tipoAula());
        registro.setNumeroMaquina(dto.numeroMaquina());
        registro.setPresencaStatus(dto.presencaStatus());

        return toDTO(registroAulaRepository.save(registro));
    }

    public List<RegistroAulaResponseDTO> listarTodos() {
        return registroAulaRepository.findAll().stream().map(this::toDTO).toList();
    }

    public RegistroAulaResponseDTO atualizarRegistro(Long id, RegistroAulaRequestDTO dto) {

        RegistroAula registroExistente = registroAulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro não encontrado para a edição."));

        registroExistente.setAluno(alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado.")));
        registroExistente.setCurso(cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado.")));

        registroExistente.setDataAula(dto.dataAula());
        registroExistente.setHoraInicio(dto.horaInicio());
        registroExistente.setHoraTermino(dto.horaTermino());
        registroExistente.setExercicio(dto.exercicio());
        registroExistente.setTipoAula(dto.tipoAula());
        registroExistente.setNumeroMaquina(dto.numeroMaquina());
        registroExistente.setPresencaStatus(dto.presencaStatus());

        return toDTO(registroAulaRepository.save(registroExistente));
    }

    public void deletarRegistro(Long id) {

        RegistroAula registroExistente = registroAulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro não encontrado para a edição."));

        registroAulaRepository.delete(registroExistente);
    }

    private RegistroAulaResponseDTO toDTO(RegistroAula registro) {
        return new RegistroAulaResponseDTO(
                registro.getId(),
                new AlunoResumoDTO(registro.getAluno().getId(), registro.getAluno().getNome()),
                new CursoResumoDTO(registro.getCurso().getId(), registro.getCurso().getNome()),
                registro.getDataAula(),
                registro.getHoraInicio(),
                registro.getHoraTermino(),
                registro.getExercicio(),
                registro.getTipoAula(),
                registro.getNumeroMaquina(),
                registro.getPresencaStatus()
        );
    }

}
