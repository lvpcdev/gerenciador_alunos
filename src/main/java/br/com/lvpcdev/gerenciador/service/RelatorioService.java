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
            Document document = new Document();
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11);


            Paragraph header = new Paragraph("ALL CAN\nCursos de Informática", titleFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);
            document.add(Chunk.NEWLINE);


            Paragraph titulo = new Paragraph("CONTRATO DE MATRÍCULA", titleFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(Chunk.NEWLINE);

            Aluno aluno = contrato.getAluno();
            document.add(new Paragraph("Nome do aluno: " + aluno.getNome(), boldFont));
            document.add(new Paragraph("CPF: " + aluno.getCpf() + "    RG: " + (aluno.getRg() != null ? aluno.getRg() : "---"), normalFont));
            document.add(new Paragraph("Data de Nascimento: " + (aluno.getDataNascimento() != null ? aluno.getDataNascimento() : "---"), normalFont));
            document.add(new Paragraph("Responsável legal: " + (aluno.getResponsavelLegal() != null ? aluno.getResponsavelLegal() : "---"), normalFont));
            document.add(new Paragraph("Endereço: " + aluno.getEndereco(), normalFont));
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Início do Curso: " + contrato.getDataInicio(), boldFont));
            document.add(new Paragraph("Horas/Aulas Mês: " + contrato.getHorasAulasMes() + " horas", normalFont));
            document.add(new Paragraph("Dia do vencimento: " + contrato.getDiaVencimento() + " de cada mês", normalFont));
            document.add(new Paragraph("Horário das aulas: " + contrato.getHoraInicio() + " às " + contrato.getHoraTermino(), normalFont));
            document.add(new Paragraph("Dias da semana: " + contrato.getDiasSemana(), normalFont));
            document.add(new Paragraph("Curso: " + contrato.getCurso().getNome(), normalFont));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            document.add(new Paragraph("Goiânia, " + contrato.getDataCriacao(), normalFont));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("_______________________________________", normalFont));
            document.add(new Paragraph(aluno.getNome(), normalFont));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("_______________________________________", normalFont));
            document.add(new Paragraph("All Can Informática", normalFont));

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF do contrato.", e);
        }
    }
}