<?php
    include('ClassAnimal.php');
    include('ClassAgenda.php');

    
    class Usuario{
        private $idUsuario;
        private $nome;
        private $sobreNome;        
        private $celular;
        private $email;
        private $cpf;
        private $dataNascimento;
        private $senha;
        private $tipoUsuario;
        private Animal $animal;
        private Agenda $agenda;
        private $acao;

        function setIdUsuario($idUsuario){
            $this->idUsuario = $idUsuario;
        }

        function setNome($nome){
            $this->nome = $nome;
        }

        function setSobreNome($sobreNome){
            $this->sobreNome = $sobreNome;
        }

        function setCelular($celular){
            $this->celular = $celular;
        }

        function setEmail($email){
            $this->email = $email;
        }

        function setCpf($cpf){
            $this->cpf = $cpf;
        }
       
        function setDataNascimento($dataNascimento){
            $this->dataNascimento = $dataNascimento;
        }

        
        function setSenha($senha){
            $this->senha = $senha;
        }

        function setTipoUsuario($tipoUsuario){
            $this->tipoUsuario = $tipoUsuario;
        }
        

        public function setAcao($acao){
            $this->acao = $acao;
        }

        
        function getIdUsuario(){
            return $this->idUsuario;
        }

        function getNome(){
            return $this->nome;
        }

        function getSobrenome(){
            return $this->sobreNome;
        }

        function getCelular(){
            return $this->celular;
        }

        function getCpf(){
            return $this->cpf;
        }

        function getDataNascimento(){
            return $this->dataNascimento;
        }
        

        function getEmail(){
            return $this->email;
        }

        function getSenha(){
            return $this->senha;
        }

        public function getAcao() {
            return $this->acao;
        }

        public function getAnimal() {
            return $this->animal;
        }

        public function setAnimal($animal){
            $this->animal = $animal;
        }

        public function getAgenda() {
            return $this->agenda;
        }

        public function setAgenda($agenda){
            $this->agenda = $agenda;
        }
        
    }       
    
 ?>
