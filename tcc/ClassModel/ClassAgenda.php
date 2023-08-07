<?php
    // include('ClassAnimal.php');
    
    class Agenda{
        private $idAgenda;
        private $servico;
        private $titulo;
        private $observacao;
        private $intervalo;
        private $qtdeDias;
        private $diferenca;
        private $dataAgenda;
        private $horarioAgenda;
        private $status;
        private $pesquisa;
        private Animal $animal;
        private $acao;
        
        public function getIdAgenda() {
            return $this->idAgenda;
        }

        public function getServico() {
            return $this->servico;
        }

        public function getTitulo() {
            return $this->titulo;
        }

        public function getObservacao() {
            return $this->observacao;
        }

        public function getIntervalo() {
            return $this->intervalo;
        }

        public function getqtdeDias() {
            return $this->qtdeDias;
        }

        public function getDiferenca() {
            return $this->diferenca;
        }

        public function getDataAgenda() {
            return $this->dataAgenda;
        }

        public function getHorarioAgenda() {
            return $this->horarioAgenda;
        }

        public function getStatus(){
            return $this->status;
        }

        public function getPesquisa(){
            return $this->pesquisa;
        }

        public function setAcao($acao){
            $this->acao = $acao;
        }

        public function setIdAgenda($idAgenda) {
            $this->idAgenda = $idAgenda;
        }

        public function setServico($servico) {
            $this->servico = $servico;
        }

        public function setTitulo($titulo) {
            $this->titulo = $titulo;
        }

        public function setObservacao($observacao) {
            $this->observacao = $observacao;
        }

        public function setIntervalo($intervalo) {
            $this->intervalo = $intervalo;
        }

        public function setqtdeDias($qtdeDias) {
            $this->qtdeDias = $qtdeDias;
        }

        public function setDiferenca($diferenca) {
            $this->diferenca = $diferenca;
        }

        public function setDataAgenda($dataAgenda) {
            $this->dataAgenda = $dataAgenda;
        }

        public function setHorarioAgenda($horarioAgenda) {
            $this->horarioAgenda = $horarioAgenda;
        }

        public function setStatus($status){
            $this->status = $status;
        }

        public function getAcao() {
            return $this->acao;
        }
        
        public function getAnimal() {
            return $this->animal;
        }

        public function setAnimal($animal) {
            $this->animal = $animal;
        }

        public function setPesquisa($pesquisa): void{
            $this->pesquisa = $pesquisa;
        }

        
    }       
    
 ?>
