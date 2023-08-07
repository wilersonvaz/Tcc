<?php
    class Animal{
        private $idAnimal;
        private Usuario $usuario;
        private $especie;
        private $nome;
        private $dataNascimento;        
        private $dataAdocao;
        private $dataFalecimento;
        private $peso;
        private $cor;
        private $sexo;
        private $raca;
        private $notas;
        private $imagemPet;
        private $caminhoImagem;
        
        public function getIdAnimal() {
            return $this->idAnimal;
        }

        public function getUsuario(): Usuario {
            return $this->usuario;
        }

        public function getEspecie() {
            return $this->especie;
        }

        public function getNome() {
            return $this->nome;
        }

        public function getDataNascimento() {
            return $this->dataNascimento;
        }

        public function getDataAdocao() {
            return $this->dataAdocao;
        }

        public function getDataFalecimento() {
            return $this->dataFalecimento;
        }

        public function getPeso() {
            return $this->peso;
        }

        public function getCor() {
            return $this->cor;
        }

        public function getSexo() {
            return $this->sexo;
        }

        public function getRaca() {
            return $this->raca;
        }

        public function getNotas() {
            return $this->notas;
        }

        public function getImagemPet() {
            return $this->imagemPet;
        }

        public function getCaminhoImagem(){
            return $this->caminhoImagem;
        }

        public function setIdAnimal($idAnimal): void {
            $this->idAnimal = $idAnimal;
        }

        public function setUsuario(Usuario $usuario): void {
            $this->usuario = $usuario;
        }

        public function setEspecie($especie): void {
            $this->especie = $especie;
        }

        public function setNome($nome): void {
            $this->nome = $nome;
        }

        public function setDataNascimento($dataNascimento): void {
            $this->dataNascimento = $dataNascimento;
        }

        public function setDataAdocao($dataAdocao): void {
            $this->dataAdocao = $dataAdocao;
        }

        public function setDataFalecimento($dataFalecimento): void {
            $this->dataFalecimento = $dataFalecimento;
        }

        public function setPeso($peso): void {
            $this->peso = $peso;
        }

        public function setCor($cor): void {
            $this->cor = $cor;
        }

        public function setSexo($sexo): void {
            $this->sexo = $sexo;
        }

        public function setRaca($raca): void {
            $this->raca = $raca;
        }

        public function setNotas($notas): void {
            $this->notas = $notas;
        }

        public function setImagemPet($imagemPet): void {
            $this->imagemPet = $imagemPet;
        }

        public function setCaminhoImagem($caminhoImagem): void{
            $this->caminhoImagem = $caminhoImagem."/tcc/Imagens/";
        }

        
    }       
    
 ?>
