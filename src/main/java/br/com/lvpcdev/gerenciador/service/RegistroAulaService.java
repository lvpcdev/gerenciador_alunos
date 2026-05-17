package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.model.RegistroAula;
import br.com.lvpcdev.gerenciador.repository.RegistroAulaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistroAulaService {

    private final RegistroAulaRepository registroAulaRepository;

    public RegistroAulaService(RegistroAulaRepository registroAulaRepository) {
        this.registroAulaRepository = registroAulaRepository;
    }

    public RegistroAula salvarRegistro(RegistroAula registroAula) {
        return registroAulaRepository.save(registroAula);
    }

    public List<RegistroAula> listarTodos() {
        return registroAulaRepository.findAll();
    }

    public RegistroAula atualizarRegistro(Long id, RegistroAula novosDados) {

        RegistroAula registroExistente = registroAulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro não encontrado para a edição."));

        registroExistente.setAluno(novosDados.getAluno());
        registroExistente.setCurso(novosDados.getCurso());

        registroExistente.setDataAula(novosDados.getDataAula());
        registroExistente.setHoraInicio(novosDados.getHoraInicio());
        registroExistente.setHoraTermino(novosDados.getHoraTermino());
        registroExistente.setExercicio(novosDados.getExercicio());
        registroExistente.setTipoAula(novosDados.getTipoAula());
        registroExistente.setNumeroMaquina(novosDados.getNumeroMaquina());
        registroExistente.setPresencaStatus(novosDados.getPresencaStatus());

        return registroAulaRepository.save(registroExistente);
    }

    public void deletarRegistro(Long id) {

        RegistroAula registroExistente = registroAulaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Registro não encontrado para a edição."));

        registroAulaRepository.delete(registroExistente);
    }
}
