package com.example.tcc.Model;

import java.util.ArrayList;

public class Animal {
    private int idAnimal;
    private Usuario usuario;
    private String imagemPet;
    private String nome;
    private String especie;
    private String dataNascimento;
    private String dataAdocao;
    private String dataFalecimento;
    private String peso;
    private String cor;
    private String sexo;
    private String notas;
    private String raca;
    private String acao;
    private String resumo;
    private ArrayList<String> listaPets;

    public Animal(){

    }
    public Animal(String imagemPet, String nome, String especie, String dataNascimento, String dataAdocao, String peso, String cor, String sexo, String notas, String raca, String acao) {
        this.imagemPet = imagemPet;
        this.nome = nome;
        this.especie = especie;
        this.dataNascimento = dataNascimento;
        this.dataAdocao = dataAdocao;
        this.peso = peso;
        this.cor = cor;
        this.sexo = sexo;
        this.raca = raca;
        this.notas = notas;
        this.acao = acao;
    }

    public Animal(String nome, String resumo, String imagem, ArrayList<String> pets) {
        this.nome = nome;
        this.resumo = resumo;
        this.imagemPet = imagem;
        this.listaPets = pets;
    }

    public Animal(int idAnimal, String nomePet, String imagemPet, String resumo) {
        this.idAnimal = idAnimal;
        this.nome = nomePet;
        this.imagemPet = imagemPet;
        this.resumo = resumo;
    }

    public Animal(String nome) {
        this.nome = nome;
    }

    public Animal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Animal(int idAnimal, String nomePet, String imagemPet, String sexoPet, String dataNascimentoPet, String resumo) {
        this.idAnimal = idAnimal;
        this.nome = nomePet;
        this.imagemPet = imagemPet;
        this.sexo = sexoPet;
        this.dataNascimento = dataNascimentoPet;
        this.resumo = resumo;
    }

    public Animal(int idAnimal, String nomePet, String imagemPet, String dataNascimentoPet, String resumo) {
        this.idAnimal = idAnimal;
        this.nome = nomePet;
        this.imagemPet = imagemPet;
        this.dataNascimento = dataNascimentoPet;
        this.resumo = resumo;
    }

    public int getIdAnimal() {
        return idAnimal;
    }

    public void setIdAnimal(int idAnimal) {
        this.idAnimal = idAnimal;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getImagemPet() {
        return imagemPet;
    }

    public void setImagemPet(String imagemPet) {
        this.imagemPet = imagemPet;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataAdocao() {
        return dataAdocao;
    }

    public void setDataAdocao(String dataAdocao) {
        this.dataAdocao = dataAdocao;
    }

    public String getDataFalecimento() {
        return dataFalecimento;
    }

    public void setDataFalecimento(String dataFalecimento) {
        this.dataFalecimento = dataFalecimento;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public ArrayList<String> getListaPets() {
        return listaPets;
    }

    public void setListaPets(ArrayList<String> listaPets) {
        this.listaPets = listaPets;
    }
}
