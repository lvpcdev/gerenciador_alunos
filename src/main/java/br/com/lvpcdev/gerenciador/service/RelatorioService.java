package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.model.Aluno;
import br.com.lvpcdev.gerenciador.model.Contrato;
import br.com.lvpcdev.gerenciador.model.Curso;
import br.com.lvpcdev.gerenciador.model.enums.CategoriaCurso;
import br.com.lvpcdev.gerenciador.model.enums.PresencaStatus;
import br.com.lvpcdev.gerenciador.model.RegistroAula;
import br.com.lvpcdev.gerenciador.repository.AlunoRepository;
import br.com.lvpcdev.gerenciador.repository.ContratoRepository;
import br.com.lvpcdev.gerenciador.repository.CursoRepository;
import br.com.lvpcdev.gerenciador.repository.RegistroAulaRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ByteArrayOutputStream;
import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
public class RelatorioService {

    private final RegistroAulaRepository registroAulaRepository;
    private final AlunoRepository alunoRepository;
    private final ContratoRepository contratoRepository;
    private final CursoRepository cursoRepository;

    public RelatorioService(RegistroAulaRepository registroAulaRepository, AlunoRepository alunoRepository, ContratoRepository contratoRepository, CursoRepository cursoRepository) {
        this.registroAulaRepository = registroAulaRepository;
        this.alunoRepository = alunoRepository;
        this.contratoRepository = contratoRepository;
        this.cursoRepository = cursoRepository;
    }

    public byte[] gerarRelatorioPresencas(Long alunoId, YearMonth mesAno) {
        Aluno aluno = alunoRepository.findById(alunoId)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado."));

        LocalDate inicio = mesAno.atDay(1);
        LocalDate fim = mesAno.atEndOfMonth();


        List<RegistroAula> registros = registroAulaRepository.findByAlunoIdAndDataAulaBetween(alunoId, inicio, fim);

        long totalPresencaMin = 0;
        long totalReporMin = 0;

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            DateTimeFormatter fmtMes = DateTimeFormatter.ofPattern("MMMM/yyyy", new Locale("pt", "BR"));
            String mesFormatado = mesAno.format(fmtMes);
            mesFormatado = mesFormatado.substring(0, 1).toUpperCase() + mesFormatado.substring(1);

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("Relatório de Presenças - " + mesFormatado, titleFont);
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


            PdfPTable tabela = new PdfPTable(6);
            tabela.setWidthPercentage(100);
            tabela.setWidths(new float[]{2f, 3f, 2f, 2f, 1.5f,2f});

            Font headerFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
            String[] cabecalhos = {"Data", "Curso", "Início", "Término", "Horas","Presença"};
            for (String cab : cabecalhos) {
                PdfPCell cell = new PdfPCell(new Phrase(cab, headerFont));
                cell.setBackgroundColor(new BaseColor(230, 126, 34));
                cell.setPadding(6);
                tabela.addCell(cell);
            }

            for (RegistroAula r : registros) {

                Duration duracao = Duration.between(r.getHoraInicio(), r.getHoraTermino());
                long minutos = duracao.toMinutes();

                String horasTexto = "-";
                if (r.getPresencaStatus().equals(PresencaStatus.PRESENTE)) {
                    totalPresencaMin += minutos;
                    horasTexto = formatarMinutos(minutos);
                } else {
                    totalReporMin += minutos;
                }
                tabela.addCell(r.getDataAula().toString());
                tabela.addCell(r.getCurso().getNome());
                tabela.addCell(r.getHoraInicio().toString());
                tabela.addCell(r.getHoraTermino().toString());
                tabela.addCell(horasTexto);
                tabela.addCell(r.getPresencaStatus().name());
            }

            document.add(tabela);

            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Total de horas no mês: " + formatarMinutos(totalPresencaMin), normalFont));
            document.add(new Paragraph("Horas a repor: " + formatarMinutos(totalReporMin), normalFont));

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
            marcadores.put("{{CURSO}}", contrato.getModalidade().toString());
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

    public byte[] gerarFichaDeAnotacoesDeAlunos(Long contratoId) {
        Contrato contrato = contratoRepository.findById(contratoId)
                .orElseThrow(() -> new IllegalArgumentException("Contrato não encontrado."));

        try {
            FileInputStream fis = new FileInputStream("/home/ficha_template.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            fis.close();

            XSSFSheet sheet = workbook.getSheetAt(0);

            sheet.getRow(0).getCell(4).setCellValue(contrato.getAluno().getNome());
            sheet.getRow(24).getCell(4).setCellValue(contrato.getDataInicio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            sheet.getRow(25).getCell(4).setCellValue(contrato.getHorasAulasMes());
            sheet.getRow(26).getCell(4).setCellValue(
                    contrato.getDiasSemana().stream()
                            .map(d -> d.name())
                            .collect(java.util.stream.Collectors.joining(", "))
            );
            sheet.getRow(27).getCell(4).setCellValue(
                    contrato.getHoraInicio() + " às " + contrato.getHoraTermino()
            );
            sheet.getRow(28).getCell(4).setCellValue(contrato.getDiaVencimento());

            Map<String, Integer> cursosEsquerda = new HashMap<>();
            cursosEsquerda.put("Digitação", 2);
            cursosEsquerda.put("Word", 3);
            cursosEsquerda.put("PowerPoint", 4);
            cursosEsquerda.put("Excel", 5);
            cursosEsquerda.put("Windows", 6);
            cursosEsquerda.put("Internet", 7);
            cursosEsquerda.put("CorelDRAW", 8);
            cursosEsquerda.put("Corel Photo", 9);

            Map<String, Integer> cursosDireita = new HashMap<>();
            cursosDireita.put("PrintArtistic", 2);
            cursosDireita.put("Fireworks", 3);
            cursosDireita.put("Dreanwever", 4);
            cursosDireita.put("Flash", 5);
            cursosDireita.put("Photoshop", 6);
            cursosDireita.put("Excel Avançado", 7);

            List<Curso> cursosMarcados;

            switch (contrato.getModalidade()) {
                case CURSOS_BASICOS, CURSO_BASICO_30_DIAS ->
                        cursosMarcados = cursoRepository.findAllByCategoria(CategoriaCurso.BASICO);
                case CURSOS_INTERMEDIARIOS ->
                        cursosMarcados = cursoRepository.findAllByCategoria(CategoriaCurso.AVANCADO);
                case CURSOS_BASICOS_E_INTERMEDIARIOS -> {
                    cursosMarcados = new ArrayList<>();
                    cursosMarcados.addAll(cursoRepository.findAllByCategoria(CategoriaCurso.BASICO));
                    cursosMarcados.addAll(cursoRepository.findAllByCategoria(CategoriaCurso.AVANCADO));
                }
                case DIGITACAO_30_DIAS, DIGITACAO_15_DIAS -> {
                    cursosMarcados = new ArrayList<>();
                    cursoRepository.findAllByCategoria(CategoriaCurso.BASICO)
                            .stream()
                            .filter(c -> c.getNome().equalsIgnoreCase("Digitação"))
                            .findFirst()
                            .ifPresent(cursosMarcados::add);
                }
                default -> cursosMarcados = new ArrayList<>();
            }

            for (Curso curso : cursosMarcados) {
                String nome = curso.getNome().trim();
                if (cursosEsquerda.containsKey(nome)) {
                    sheet.getRow(cursosEsquerda.get(nome)).getCell(0).setCellValue("X");
                } else if (cursosDireita.containsKey(nome)) {
                    sheet.getRow(cursosDireita.get(nome)).getCell(2).setCellValue("X");
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            workbook.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar ficha.", e);
        }
    }

    private void substituirMarcadores(XWPFParagraph paragraph, Map<String, String> marcadores) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : marcadores.entrySet()) {
                    text = text.replace(entry.getKey(), entry.getValue());
                }
                run.setText(text, 0);
            }
        }
    }

    private String formatarMinutos(long minutos) {
        long horas = minutos / 60;
        long restante = minutos % 60;
        return horas + "h" + (restante > 0 ? String.format("%02d", restante) + "m" : "");
    }
}