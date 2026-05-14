package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.model.RegistroAula;
import br.com.lvpcdev.gerenciador.service.RegistroAulaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroAulaController {

    private final RegistroAulaService registroAulaService;

    public RegistroAulaController(RegistroAulaService registroAulaService) {
        this.registroAulaService = registroAulaService;
    }

    @PostMapping
    public RegistroAula regitrarAula(@RequestBody RegistroAula registroAula) {
        return registroAulaService.salvarRegistro(registroAula);
    }

    @GetMapping
    public List<RegistroAula> listarRegistros() {
        return registroAulaService.listarTodos();
    }
}
