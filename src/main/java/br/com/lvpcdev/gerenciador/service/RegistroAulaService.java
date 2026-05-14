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
}
