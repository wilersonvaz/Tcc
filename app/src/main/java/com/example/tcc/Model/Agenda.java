package com.example.tcc.Model;

public class Agenda {
    private int idAgenda;
    private String servico;
    private String titulo;
    private String observacao;
    private Integer intervalo;
    private int qtdeAgenda;
    private int diferencaAgenda; //Diferença ente horas, por exemplo 8 em 8 horas
    private String dataAgenda;
    private String horarioAgenda;
    private int status;
    private String pesquisa;
    private Animal animal;

    public Agenda(String servico, String titulo, String observacao, Integer intervalo, int diferencaAgenda, int qtdeAgenda, String dataAgenda, String horario, Animal animal) {
        this.servico = servico;
        this.titulo = titulo;
        this.observacao = observacao;
        this.intervalo = intervalo;
        this.diferencaAgenda = diferencaAgenda;
        this.qtdeAgenda = qtdeAgenda;
        this.dataAgenda = dataAgenda;
        this.horarioAgenda = horario;
        this.animal = animal;
    }



    public Agenda() {

    }

    public Agenda(String servico, String titulo, String observacao, Integer intervalo, String dataAgenda, String horario, Animal animal) {
        this.servico = servico;
        this.titulo = titulo;
        this.observacao = observacao;
        this.intervalo = intervalo;
        this.dataAgenda = dataAgenda;
        this.horarioAgenda = horario;
        this.animal = animal;
    }

    public Agenda(int idAgenda, String servico, String titulo, String observacao, String dataAgenda, String horario, Integer status, Animal animal) {
        this.idAgenda = idAgenda;
        this.servico = servico;
        this.titulo = titulo;
        this.observacao = observacao;
        this.dataAgenda = dataAgenda;
        this.horarioAgenda = horario;
        this.status = status;
        this.animal = animal;
    }

    public Agenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public Agenda(int idAgenda, String servico) {
        this.idAgenda = idAgenda;
        this.servico = servico;
    }

    public Agenda(int idAgenda, String servico, String pesquisa) {
        this.idAgenda = idAgenda;
        this.servico = servico;
        this.pesquisa = pesquisa;
    }


    public int getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(int idAgenda) {
        this.idAgenda = idAgenda;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQtdeAgenda() {
        return qtdeAgenda;
    }

    public void setQtdeAgenda(int qtdeAgenda) {
        this.qtdeAgenda = qtdeAgenda;
    }

    public Integer getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Integer intervalo) {
        this.intervalo = intervalo;
    }

    public String getDataAgenda() {
        return dataAgenda;
    }

    public void setDataAgenda(String dataAgenda) {
        this.dataAgenda = dataAgenda;
    }

    public String getHorarioAgenda() {
        return horarioAgenda;
    }

    public void setHorarioAgenda(String horarioAgenda) {
        this.horarioAgenda = horarioAgenda;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public int getDiferencaAgenda() {
        return diferencaAgenda;
    }

    public void setDiferencaAgenda(int diferencaAgenda) {
        this.diferencaAgenda = diferencaAgenda;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPesquisa() {
        return pesquisa;
    }

    public void setPesquisa(String pesquisa) {
        this.pesquisa = pesquisa;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }
}
