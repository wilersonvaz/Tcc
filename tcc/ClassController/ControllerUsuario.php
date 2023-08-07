<?php

    include ("../ClassModel/ClassUsuario.php");
    include("../ClassModelDAO/ClassUsuarioDAO.php");
    
    $usuario = new Usuario();
    $animal = new Animal();
    $agenda = new Agenda();
    $usuarioDAO = new UsuarioDAO();        
    
    //echo (file_get_contents('php://input'));

    $jsonString = file_get_contents('php://input');
    $jsonObj = json_decode($jsonString, true);

    // echo "{$jsonObj["acao"]} - {$jsonObj["nome"]} - {$jsonObj["sobreNome"]} - {$jsonObj["celular"]} - {$jsonObj["email"]} - {$jsonObj["endereco"]}- {$jsonObj["dataNascimento"]} - {$jsonObj["senha"]}";

//    if(isset($jsonObj["acao"])){
        switch ($_POST["acao"])
        {
            case 'login':
                if(isset($_POST["acao"])){$usuario->setAcao($_POST["acao"]);}
                $usuario->setEmail($_POST["email"]);
                $usuario->setSenha($_POST["senha"]);
                $animal->setCaminhoImagem($_POST["caminhoImagem"]);
                $usuario->setAnimal($animal);
                if(isset($_POST["idUsuario"])){$usuario->setIdUsuario($_POST["idUsuario"]);}
                $usuarioDAO->login($usuario);
                // echo json_encode(array("response" => "Acao: {$_POST["acao"]} - {$_POST["caminhoImagem"]}" ), JSON_UNESCAPED_UNICODE);
                break;
            case 'cadastrarUsuario':
                $usuario->setNome($_POST["nome"]);
                $usuario->setSobreNome($_POST["sobreNome"]);
                $usuario->setCpf($_POST["cpf"]);
                $usuario->setCelular($_POST["celular"]);
                $usuario->setDataNascimento(date('Y-m-d', strtotime(str_replace("/", "-", $_POST["dataNascimento"]))));
                $usuario->setEmail($_POST["email"]);
                $usuario->setSenha($_POST["senha"]);

                $usuarioDAO->cadastrarUsuario($usuario);

                break;
            case 'loadMainInfos':
                //if(isset($_POST["email"])){$usuario->setEmail($_POST["email"]);}
                //if(isset($_POST["senha"])){$usuario->setSenha($_POST["senha"]);}
                $usuario->setAnimal($animal);
                if(isset($_POST["acao"])){$usuario->setAcao($_POST["acao"]);}
                if(isset($_POST["idUsuario"])){$usuario->setIdUsuario($_POST["idUsuario"]);}
                if(isset($_POST["idAnimal"])){$usuario->getAnimal()->setIdAnimal($_POST["idAnimal"]);}
                $usuarioDAO->loadMainInfos($usuario);                
                break;                                
//                echo json_encode(array("response" => "id Animal: ".$usuario->getAnimal()->getIdAnimal()), JSON_UNESCAPED_UNICODE);
            case 'listaAnimais':
                if(isset($_POST["idUsuario"])){$usuario->setIdUsuario($_POST["idUsuario"]);}
                $usuarioDAO->listaAnimais($usuario);
                break;
//                echo json_encode(array("response" => "id usuario: ".$_POST["idUsuario"]), JSON_UNESCAPED_UNICODE);
            case 'loadPetInfo':
                $usuario->setAnimal($animal);
                if(isset($_POST["acao"])){$usuario->setAcao($_POST["acao"]);}
                if(isset($_POST["idUsuario"])){$usuario->setIdUsuario($_POST["idUsuario"]);}
                if(isset($_POST["idAnimal"])){$usuario->getAnimal()->setIdAnimal($_POST["idAnimal"]);}
                $usuarioDAO->loadPetInfo($usuario);
                //echo json_encode(array("response" => "id usuario: ".$_POST["acao"]." ".$_POST["idUsuario"]." ".$_POST["idAnimal"]), JSON_UNESCAPED_UNICODE);
                break;
            case 'listaAgenda':
                $usuario->setAnimal($animal);
                $usuario->setAgenda($agenda);
                if(isset($_POST["acao"])){$usuario->setAcao($_POST["acao"]);}
                if(isset($_POST["idUsuario"])){$usuario->setIdUsuario($_POST["idUsuario"]);}
                if(isset($_POST["idAnimal"])){$usuario->getAnimal()->setIdAnimal($_POST["idAnimal"]);}
                if(isset($_POST["servico"])){$usuario->getAgenda()->setServico($_POST["servico"]);}
                $usuarioDAO->listaAgenda($usuario);
                // echo json_encode(array("response" => "id usuario: ".$_POST["acao"]." - ".$usuario->getIdUsuario()." - ".$usuario->getAnimal()->getIdAnimal()." - ".$usuario->getAgenda()->getServico()), JSON_UNESCAPED_UNICODE);
                break;
            case 'pesquisaAgenda':
                $usuario->setAnimal($animal);
                $usuario->setAgenda($agenda);
                if(isset($_POST["acao"])){$usuario->setAcao($_POST["acao"]);}
                if(isset($_POST["idUsuario"])){$usuario->setIdUsuario($_POST["idUsuario"]);}
                if(isset($_POST["idAnimal"])){$usuario->getAnimal()->setIdAnimal($_POST["idAnimal"]);}
                if(isset($_POST["servico"])){$usuario->getAgenda()->setServico($_POST["servico"]);}
                if(isset($_POST["pesquisa"])){$usuario->getAgenda()->setPesquisa($_POST["pesquisa"]);}
                $usuarioDAO->pesquisaAgenda($usuario);
                // echo json_encode(array("response" => "id usuario: ".$_POST["acao"]." - ".$usuario->getIdUsuario()." - ".$usuario->getAnimal()->getIdAnimal()." - ".$usuario->getAgenda()->getServico()." - ".$usuario->getAgenda()->getPesquisa()), JSON_UNESCAPED_UNICODE);
                break;    

            default:
                 echo json_encode(array("response" => "Acao: {$_POST["acao"]}" ), JSON_UNESCAPED_UNICODE);
        }
//    }else{
//        echo "Açao nao foi passada!";
//    }
?>
