package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.model.Contrato;
import br.com.lvpcdev.gerenciador.model.RegistroAula;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import br.com.lvpcdev.gerenciador.repository.ContratoRepository;
import br.com.lvpcdev.gerenciador.repository.RegistroAulaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.apache.poi.xwpf.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class RelatorioService {

    private final RegistroAulaRepository registroAulaRepository;
    private final AlunoRepository alunoRepository;
    private final ContratoRepository contratoRepository;

    public RelatorioService(RegistroAulaRepository registroAulaRepository, AlunoRepository alunoRepository, ContratoRepository contratoRepository) {
        this.registroAulaRepository = registroAulaRepository;
        this.alunoRepository = alunoRepository;
        this.contratoRepository = contratoRepository;
    }

    public byte[] gerarRelatorioPresencas(Long alunoId) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        List<RegistroAula> registros = registroAulaRepository.findByAlunoId(alunoId);

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Relatório de Presenças", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 12);
            document.add(new Paragraph("Aluno: " + aluno.getNome(), boldFont));
            document.add(new Paragraph("CPF: " + aluno.getCpf(), normalFont));
            document.add(Chunk.NEWLINE);

            long presentes = registros.stream()
                    .filter(r -> r.getPresencaStatus().name().equals("PRESENTE")).count();
            long ausentes = registros.size() - presentes;
            int taxa = registros.isEmpty() ? 0 :
                    (int) Math.round((presentes * 100.0) / registros.size());

            document.add(new Paragraph("Total de aulas: " + registros.size(), normalFont));
            document.add(new Paragraph("Presenças: " + presentes, normalFont));
            document.add(new Paragraph("Ausências: " + ausentes, normalFont));
            document.add(new Paragraph("Taxa de presença: " + taxa + "%", normalFont));
            document.add(Chunk.NEWLINE);

            PdfPTable tabela = new PdfPTable(5);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new float[]{2f, 3f, 2f, 2f, 2f});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
            String[] cabecalhos = {"Data", "Curso", "Início", "Término", "Presença"};
            for (String cab : cabecalhos) {
                PdfPCell cell = new PdfPCell(new Phrase(cab, headerFont));
                cell.setBackgroundColor(new BaseColor(230, 126, 34));
                cell.setPadding(6);
                tabela.addCell(cell);
            }

            for (RegistroAula r : registros) {
                tabela.addCell(r.getDataAula().toString());
                tabela.addCell(r.getCurso().getNome());
                tabela.addCell(r.getHoraInicio().toString());
                tabela.addCell(r.getHoraTermino().toString());
                tabela.addCell(r.getPresencaStatus().name());
            }

            document.add(tabela);
            document.close();

            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar relatório PDF.", e);
        }
    }

    public byte[] gerarPdfContrato(Long contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado."));

        try {
            FileInputStream fis = new FileInputStream("/home/contrato_template.docx");
            XWPFDocument document = new XWPFDocument(fis);
            fis.close();

            Aluno aluno = contrato.getAluno();
            String diasSemana = contrato.getDiasSemana().stream()
                    .map(d -> d.name())
                    .collect(java.util.stream.Collectors.joining(", "));

            java.util.Map<String, String> marcadores = new java.util.HashMap<>();
            marcadores.put("{{NUMERO_CONTRATO}}", String.valueOf(contrato.getId()));
            marcadores.put("{{NOME_ALUNO}}", aluno.getNome());
            marcadores.put("{{CPF}}", aluno.getCpf());
            marcadores.put("{{RG}}", aluno.getRg() != null ? aluno.getRg() : "---");
            marcadores.put("{{ENDERECO}}", aluno.getEndereco());
            marcadores.put("{{TELEFONE}}", aluno.getTelefone() != null ? aluno.getTelefone() : "---");
            marcadores.put("{{RESPONSAVEL_LEGAL}}", aluno.getResponsavelLegal() != null ? aluno.getResponsavelLegal() : "---");
            marcadores.put("{{DATA_NASCIMENTO}}", aluno.getDataNascimento()     != null ? aluno.getDataNascimento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "---");
            marcadores.put("{{DATA_INICIO}}", contrato.getDataInicio().format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new java.util.Locale("pt", "BR"))));
            marcadores.put("{{HORAS_AULAS_MES}}", String.valueOf(contrato.getHorasAulasMes()));
            marcadores.put("{{DIA_VENCIMENTO}}", String.valueOf(contrato.getDiaVencimento()));
            marcadores.put("{{HORA_INICIO}}", contrato.getHoraInicio().toString());
            marcadores.put("{{HORA_TERMINO}}", contrato.getHoraTermino().toString());
            marcadores.put("{{DIAS_SEMANA}}", diasSemana);
            marcadores.put("{{CURSO}}", contrato.getCurso().getNome());
            marcadores.put("{{DATA_CRIACAO}}", contrato.getDataCriacao().format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy", new java.util.Locale("pt", "BR"))));


            for (XWPFParagraph paragraph : document.getParagraphs()) {
                substituirMarcadores(paragraph, marcadores);
            }

            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            substituirMarcadores(paragraph, marcadores);
                        }
                    }
                }
            }

            java.io.File tempDocx = java.io.File.createTempFile("contrato_", ".docx");
            FileOutputStream fos = new FileOutputStream(tempDocx);
            document.write(fos);
            fos.close();
            document.close();

            java.io.File tempDir = tempDocx.getParentFile();
            ProcessBuilder pb = new ProcessBuilder(
                    "libreoffice", "--headless", "--convert-to", "pdf",
                    "--outdir", tempDir.getAbsolutePath(),
                    tempDocx.getAbsolutePath()
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();
            process.waitFor();

            String pdfPath = tempDocx.getAbsolutePath().replace(".docx", ".pdf");
            java.io.File pdfFile = new java.io.File(pdfPath);
            byte[] pdfBytes = java.nio.file.Files.readAllBytes(pdfFile.toPath());

            tempDocx.delete();
            pdfFile.delete();

            return pdfBytes;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do contrato.", e);
        }
    }

    private void substituirMarcadores(XWPFParagraph paragraph, java.util.Map<String, String> marcadores) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                for (java.util.Map.Entry<String, String> entry : marcadores.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                }
                run.setText(text, 0);
            }
        }
    }
}