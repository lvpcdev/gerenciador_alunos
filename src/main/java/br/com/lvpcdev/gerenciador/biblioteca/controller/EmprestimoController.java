package br.com.lvpcdev.gerenciador.biblioteca.controller;


import br.com.lvpcdev.gerenciador.biblioteca.dto.EmprestimoRequestDTO;
import br.com.lvpcdev.gerenciador.biblioteca.dto.EmprestimoResponseDTO;
import br.com.lvpcdev.gerenciador.biblioteca.service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emprestimos")
public class EmprestimoController {
    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public EmprestimoResponseDTO registrarEmprestimo(@Valid @RequestBody EmprestimoRequestDTO dto) {
        return emprestimoService.registrarEmprestimo(dto);
    }

    @PutMapping("/{id}/devolver")
    public ResponseEntity<EmprestimoResponseDTO> devolverEmprestimo(@PathVariable Long id) {
        EmprestimoResponseDTO emprestimo = emprestimoService.devolverEmprestimo(id);
        return ResponseEntity.ok(emprestimo);
    }

    @GetMapping
    public List<EmprestimoResponseDTO> listarTodos() {
        return emprestimoService.listarTodos();
    }

    @GetMapping("/atrasados")
    public List<EmprestimoResponseDTO> listarAtrasados() {
        return emprestimoService.listarAtrasados();
    }

}
