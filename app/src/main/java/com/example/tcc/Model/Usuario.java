package com.example.tcc.Model;

import android.text.Editable;

import java.util.ArrayList;

public class Usuario {
    private int idUsuario;
    private String nome;
    private String sobreNome;
    private String celular;
    private String email;
    private String cpf;
    private String dataNascimento;
    private String senha;
    private int tipoUsuario;
    private String acao;
    private Animal animal;
    private Agenda agenda;

    public Usuario(){

    }

    public Usuario(String nome, String sobreNome, String celular, String email, String cpf, String dataNascimento, String senha, String acao) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.celular = celular;
        this.email = email;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
        this.acao = acao;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(int idUsuario, Animal animal) {
        this.idUsuario = idUsuario;
        this.animal = animal;
    }

    public Usuario(int idUsuario, Animal animal, Agenda agenda) {
        this.idUsuario = idUsuario;
        this.animal = animal;
        this.agenda = agenda;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobreNome() {
        return sobreNome;
    }

    public void setSobreNome(String sobreNome) {
        this.sobreNome = sobreNome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setEndereco(String endereco) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(Agenda agenda) {
        this.agenda = agenda;
    }
}
