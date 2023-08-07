<?php

    include ("../ClassModel/ClassAgenda.php");
    include("../ClassModelDAO/ClassAgendaDAO.php");

    include('../ClassModel/ClassAnimal.php');

    $agenda = new Agenda();    
    $animal = new Animal();
    $agendaDAO = new AgendaDAO();
    
    //echo (file_get_contents('php://input'));

    $jsonString = file_get_contents('php://input');
    $jsonObj = json_decode($jsonString, true);

    // echo "{$jsonObj["acao"]} - {$jsonObj["nome"]} - {$jsonObj["sobreNome"]} - {$jsonObj["celular"]} - {$jsonObj["email"]} - {$jsonObj["endereco"]}- {$jsonObj["dataNascimento"]} - {$jsonObj["senha"]}";

//    if(isset($jsonObj["acao"])){
    if(isset($_GET["acao"])){
        switch ($_GET["acao"])
        {
            //localhost/tcc/ClassController/ControllerAgenda.php?acao=cadastrarAgenda&servico=Banho/Tosa&titulo=Banho na Margarida&observacao=Banho na Bella&intervalo=2&diferenca=8&qtdeDias=7&dataAgenda=2023-03-30&horario=09:00&idAnimal=5
            case 'cadastrarAgenda':
                // echo "{$_GET["servico"]} - {$_GET["titulo"]} - {$_GET["observacao"]} - {$_GET["intervalo"]} - {$_GET["diferenca"]} - {$_GET["numeroDias"]} - {$_GET["dataInicio"]} - {$_GET["horario"]}";
                $agenda->setAnimal($animal);
                if(isset($_GET["acao"])){$agenda->setAcao($_GET["acao"]);}
                if(isset($_GET["servico"])){$agenda->setServico($_GET["servico"]);}
                if(isset($_GET["titulo"])){$agenda->setTitulo($_GET["titulo"]);}
                if(isset($_GET["observacao"])){$agenda->setObservacao($_GET["observacao"]);}
                if(isset($_GET["intervalo"])){$agenda->setIntervalo($_GET["intervalo"]);}
                if(isset($_GET["diferenca"])){$agenda->setDiferenca($_GET["diferenca"]);}
                if(isset($_GET["qtdeDias"])){$agenda->setqtdeDias($_GET["qtdeDias"]);}
                if(isset($_GET["dataAgenda"])){$agenda->setDataAgenda(date('Y-m-d', strtotime(str_replace("/", "-", $_GET["dataAgenda"]))));}
                if(isset($_GET["horario"])){$agenda->setHorarioAgenda($_GET["horario"]);}
                if(isset($_GET["idAnimal"])){
                    $agenda->getAnimal()->setIdAnimal($_GET["idAnimal"]);
                }
                //echo json_encode(array("response" => "Acao: {$_POST["acao"]} -> {$_POST["servico"]} -> {$_POST["titulo"]} -> {$_POST["idAnimal"]}" ), JSON_UNESCAPED_UNICODE);
                $agendaDAO->cadastrarAgendaTeste($agenda);

                break;
            default:
                echo "Entrou no default";
            
        }
    }else{
        switch ($_POST["acao"])
        {
            case 'cadastrarAgenda':
                $agenda->setAnimal($animal);
                if(isset($_POST["acao"])){$agenda->setAcao($_POST["acao"]);}
                if(isset($_POST["servico"])){$agenda->setServico($_POST["servico"]);}
                if(isset($_POST["titulo"])){$agenda->setTitulo($_POST["titulo"]);}
                if(isset($_POST["observacao"])){$agenda->setObservacao($_POST["observacao"]);}
                if(isset($_POST["intervalo"])){$agenda->setIntervalo($_POST["intervalo"]);}
                if(isset($_POST["diferenca"])){$agenda->setDiferenca($_POST["diferenca"]);}
                if(isset($_POST["qtdeDias"])){$agenda->setqtdeDias($_POST["qtdeDias"]);}
                if(isset($_POST["dataAgenda"])){$agenda->setDataAgenda(date('Y-m-d', strtotime(str_replace("/", "-", $_POST["dataAgenda"]))));}
                if(isset($_POST["horario"])){$agenda->setHorarioAgenda($_POST["horario"]);}
                if(isset($_POST["idAnimal"])){
                    $agenda->getAnimal()->setIdAnimal($_POST["idAnimal"]);
                }
                //echo json_encode(array("response" => "Acao: {$_POST["acao"]} -> servico: {$_POST["servico"]} -> Titulo: {$_POST["titulo"]} -> Observação: {$_POST["observacao"]} -> Intervalo: {$_POST["intervalo"]} ->  Diferenca: {$_POST["diferenca"]} -  QtdeDias: {$_POST["qtdeDias"]} -> Data Agenda:  {$_POST["dataAgenda"]} -> Horário: {$_POST["horario"]} ->  Id Animal: {$_POST["idAnimal"]}" ), JSON_UNESCAPED_UNICODE);
                $agendaDAO->cadastrarAgendaTeste($agenda);


                break;
            case 'concluirCompromisso':
                if(isset($_POST["idAgenda"])){$agenda->setIdAgenda($_POST["idAgenda"]);}
                // echo json_encode(array("response" => "Acao: {$_POST["acao"]} -> Id da agenda: {$_POST["idAgenda"]}" ), JSON_UNESCAPED_UNICODE);
                $agendaDAO->concluirCompromisso($agenda);
                break;
            case 'excluirCompromisso':
                if(isset($_POST["idAgenda"])){$agenda->setIdAgenda($_POST["idAgenda"]);}
                // echo json_encode(array("response" => "Acao: {$_POST["acao"]} -> Id da agenda: {$_POST["idAgenda"]}" ), JSON_UNESCAPED_UNICODE);
                $agendaDAO->excluirCompromisso($agenda);
                break;    
            default:
                echo json_encode(array("response" => "Acao: {$_POST["acao"]}" ), JSON_UNESCAPED_UNICODE);
        }
    }
        
//    }else{
//        echo "Açao nao foi passada!";
//    }
?>
