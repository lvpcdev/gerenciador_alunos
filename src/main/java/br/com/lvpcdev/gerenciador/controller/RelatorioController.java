package br.com.lvpcdev.gerenciador.controller;

import br.com.lvpcdev.gerenciador.service.RelatorioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;

@RestController
@RequestMapping("/relatorios")
public class RelatorioController {

    private final RelatorioService relatorioService;

    public RelatorioController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/presencas/{alunoId}")
    public ResponseEntity<byte[]> gerarRelatorioPresencas(@PathVariable Long alunoId, @RequestParam String mesAno) {
        YearMonth ym = YearMonth.parse(mesAno);
        byte[] pdf = relatorioService.gerarRelatorioPresencas(alunoId, ym);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=relatorio-" + mesAno + ".pdf")
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
