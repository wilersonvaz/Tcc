<?php

//    include ("../ClassModel/ClassAnimal.php");
    include("../ClassModelDAO/ClassAnimalDAO.php");
    include ("../ClassModel/ClassUsuario.php");
    
    $animal = new Animal();    
    $usuario = new Usuario();
    $animalDAO = new AnimalDAO();    
    
    
    //echo (file_get_contents('php://input'));

    $jsonString = file_get_contents('php://input');
    $jsonObj = json_decode($jsonString, true);

    // echo $_POST["imagem"];

    // $animalDAO->uploadToFolder($_POST["imagem"]);

    switch ($_POST["acao"])
    {        
        case 'cadastrarAnimal':
            $animal->setNome($_POST["nome"]);
            $animal->setDataNascimento(date('Y-m-d', strtotime(str_replace("/", "-", $_POST["dataNascimento"]))));       
            $animal->setDataAdocao(date('Y-m-d', strtotime(str_replace("/", "-", $_POST["dataAdocao"]))));
            $animal->setPeso($_POST["peso"]);
            $animal->setCor($_POST["cor"]);
            $animal->setSexo($_POST["sexo"]);
            $animal->setNotas($_POST["notas"]);  
            $animal->setImagemPet($_POST["imagem"]);       
            $animal->setRaca($_POST["raca"]);
            $usuario->setIdUsuario($_POST["idUsuario"]);
            $animal->setUsuario($usuario);
            $animal->setEspecie($_POST["especie"]);
            
            $animalDAO->cadastrarAnimal($animal);
            
            break;

         case 'carregaUpdate':
            $animal->setIdAnimal($_POST["idAnimal"]);
            
            $animalDAO->carregaUpdate($animal);
            
            break;   
    }
?>
