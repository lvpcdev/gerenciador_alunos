package br.com.lvpcdev.gerenciador.model;

import br.com.lvpcdev.gerenciador.model.enums.DiaSemana;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    private LocalDate dataInicio;
    private Integer horasAulasMes;
    private Integer diaVencimento;
    private LocalTime horaInicio;
    private LocalTime horaTermino;

    @ElementCollection
    @CollectionTable(name = "contrato_dias_semana", joinColumns = @JoinColumn(name = "contrato_id"))
    @Enumerated(EnumType.STRING)
    private List<DiaSemana> diasSemana = new ArrayList<>();
    private LocalDate dataCriacao;

    public Contrato(Aluno aluno, Curso curso, LocalDate dataInicio, Integer horasAulasMes, Integer diaVencimento, LocalTime horaInicio, LocalTime horaTermino, List<DiaSemana> diasSemana, LocalDate dataCriacao) {
        this.aluno = aluno;
        this.curso = curso;
        this.dataInicio = dataInicio;
        this.horasAulasMes = horasAulasMes;
        this.diaVencimento = diaVencimento;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.diasSemana = diasSemana;
        this.dataCriacao = dataCriacao;
    }

    public Contrato() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Integer getHorasAulasMes() {
        return horasAulasMes;
    }

    public void setHorasAulasMes(Integer horasAulasMes) {
        this.horasAulasMes = horasAulasMes;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraTermino() {
        return horaTermino;
    }

    public void setHoraTermino(LocalTime horaTermino) {
        this.horaTermino = horaTermino;
    }

    public List<DiaSemana> getDiasSemana() {
        return diasSemana;
    }

    public void setDiasSemana(List<DiaSemana> diasSemana) {
        this.diasSemana = diasSemana;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
