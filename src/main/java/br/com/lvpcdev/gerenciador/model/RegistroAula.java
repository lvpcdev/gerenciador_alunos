package br.com.lvpcdev.gerenciador.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class RegistroAula {
    private Long id;
    private Aluno aluno;
    private Curso curso;
    private LocalDate dataAula;
    private LocalTime horaInicio;
    private LocalTime horaTermino;
    private String exercicio;
    private String tipoAula;
    private Integer numeroMaquina;
    private boolean compareceu;

    public RegistroAula(Aluno aluno, Curso curso, LocalDate dataAula, LocalTime horaInicio, LocalTime horaTermino, String exercicio, String tipoAula, Integer numeroMaquina, boolean compareceu) {
        this.aluno = aluno;
        this.curso = curso;
        this.dataAula = dataAula;
        this.horaInicio = horaInicio;
        this.horaTermino = horaTermino;
        this.exercicio = exercicio;
        this.tipoAula = tipoAula;
        this.numeroMaquina = numeroMaquina;
        this.compareceu = compareceu;
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

    public LocalDate getDataAula() {
        return dataAula;
    }

    public void setDataAula(LocalDate dataAula) {
        this.dataAula = dataAula;
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

    public String getExercicio() {
        return exercicio;
    }

    public void setExercicio(String exercicio) {
        this.exercicio = exercicio;
    }

    public String getTipoAula() {
        return tipoAula;
    }

    public void setTipoAula(String tipoAula) {
        this.tipoAula = tipoAula;
    }

    public Integer getNumeroMaquina() {
        return numeroMaquina;
    }

    public void setNumeroMaquina(Integer numeroMaquina) {
        this.numeroMaquina = numeroMaquina;
    }

    public boolean isCompareceu() {
        return compareceu;
    }

    public void setCompareceu(boolean compareceu) {
        this.compareceu = compareceu;
    }
}
