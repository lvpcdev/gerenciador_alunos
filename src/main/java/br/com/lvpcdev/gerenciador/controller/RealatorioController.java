package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/relatorios")
public class RealatorioController {

    private final RelatorioService relatorioService;

    public RealatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/presencas/{alunoId}")
    public ResponseEntity<byte[]> gerarRelatorioPresencas(@PathVariable Long alunoId) {
        byte[] pdf = relatorioService.gerarRelatorioPresencas(alunoId);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=relatorio-presencas.pdf")
                .body(pdf);
    }

    @GetMapping("/contrato/{contratoId}")
    public ResponseEntity<byte[]> gerarPdfContrato(@PathVariable Long contratoId) {
        byte[] pdf = relatorioService.gerarPdfContrato(contratoId);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=contrato-matricula.pdf")
                .body(pdf);
    }
}
