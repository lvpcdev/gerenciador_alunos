package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.dto.RegistroAulaRequestDTO;
import br.com.lvpcdev.gerenciador.dto.RegistroAulaResponseDTO;


import br.com.lvpcdev.gerenciador.service.RegistroAulaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroAulaController {

    private final RegistroAulaService registroAulaService;

    public RegistroAulaController(RegistroAulaService registroAulaService) {
        this.registroAulaService = registroAulaService;
    }

    @PostMapping
    public RegistroAulaResponseDTO regitrarAula(@Valid @RequestBody RegistroAulaRequestDTO dto) {
        return registroAulaService.salvarRegistro(dto);
    }

    @GetMapping
    public List<RegistroAulaResponseDTO> listarRegistros() {
        return registroAulaService.listarTodos();
    }

    @GetMapping
    public List<RegistroAulaResponseDTO> listarRegistrosPorData(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate data) {
        LocalDate dataConsulta = data != null ? data : LocalDate.now();
        return registroAulaService.listarPorData(dataConsulta);
    }

    @PutMapping("/{id}")
    public RegistroAulaResponseDTO editarAula(@PathVariable Long id, @Valid @RequestBody RegistroAulaRequestDTO dto) {
        return registroAulaService.atualizarRegistro(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarRegistro(@PathVariable Long id) {

        registroAulaService.deletarRegistro(id);

        return ResponseEntity.noContent().build();
    }
}
