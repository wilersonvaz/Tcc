<?php
    include('../ClassModel/ClassConexao.php');
    
    class UsuarioDAO{
        
        function cadastrarUsuario(Usuario $usuario){            
            
            try{
                $db = new conexao();
                
                $sql = "INSERT INTO usuario (
                                                idUsuario, 
                                                nome, 
                                                sobreNome, 
                                                cpf,
                                                celular, 
                                                dataNascimento,
                                                email, 
                                                senha
                                            ) VALUES (
                                                NULL, 
                                                :nome, 
                                                :sobreNome, 
                                                :cpf,
                                                :celular, 
                                                :dataNascimento,
                                                :email, 
                                                :senha
                                            )";
                
                $stmt = $db->getConn()->prepare($sql);
                
                $stmt->execute(
                                array(
                                        ':nome' => $usuario->getNome(), 
                                        ':sobreNome' => $usuario->getSobreNome(), 
                                        ':cpf' => $usuario->getCpf(),
                                        ':celular' => $usuario->getCelular(),
                                        ':dataNascimento' => $usuario->getDataNascimento(), 
                                        ':email' => $usuario->getEmail(),                                         
                                        ':senha' => $usuario->getSenha()
                                        )
                            );

                if($stmt->rowCount() > 0){
                    $rows = [];

                    $nomeUsuario = $usuario->getNome();

                    $rows = array(
                                'idUsuario' => $db->getConn()->lastInsertId(), 
                                'nomeUsuario' => $nomeUsuario, 
                            );

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                }
                
            } catch (Exception $ex) {
                echo json_encode(array('response' => 'Erro ao cadastrar o usuário '.$ex->getMessage()));
                 // "Erro ao cadastrar o usuário ".$ex->getMessage();
            }
        }



        function login(Usuario $usuario){
            $db = new conexao();

            try{
                
                // $imagePath = "http://192.168.57.1/tcc/Imagens/";
                
                $sql = "SELECT A.idUsuario, A.nome, A.tipoUsuario, CONCAT('".$usuario->getAnimal()->getCaminhoImagem()."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet, B.sexo, CONCAT('Data de Nascimento: ', DATE_FORMAT(B.dataNascimento, '%d/%m/%Y')) AS dataNascimento FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) where ((A.email = :email OR A.nome = :email) AND (A.senha = :senha))";
                
                
//                $sql = "SELECT * FROM usuario where (email = :email OR nome = :email) AND (senha = :senha)";
                
                $stmt = $db->getConn()->prepare($sql);

                $stmt->execute(array(':email' => $usuario->getEmail(), ':senha' => $usuario->getSenha(), ':usuario_idUsuario' => -1));
                

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'nomeUsuario' => $dados->nome, 
                            'tipoUsuario' => $dados->tipoUsuario,
                            'idAnimal' => $dados->idAnimal,
                            'nomePet' => $dados->nomePet, 
                            'imagem' => $dados->imagem,
                            'sexo' => $dados->sexo,
                            'dataNascimento' => $dados->dataNascimento,
                            'resumo' => $dados->resumo
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;


                }

 //echo json_encode(array("response" => "Sql: ".$sql), JSON_UNESCAPED_UNICODE);
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }


        function loadPetInfo(Usuario $usuario){
            $db = new conexao();
            
            try{
                
                $imagePath = "http://192.168.57.1/tcc/Imagens/";

                $sql = "SELECT A.idUsuario, A.nome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) ORDER BY B.idAnimal";

                // $sql = "SELECT A.idUsuario, A.nome, A.sobrenome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet, B.dataNascimento, B.sexo, C.idAgenda, C.servico, C.titulo, C.observacao, C.dataAgenda, C.horario FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) LEFT JOIN agenda C ON (B.idAnimal = C.animal_idAnimal) where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) ORDER BY B.idAnimal";
                
                $stmt = $db->getConn()->prepare($sql);
                    
                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario(), ':usuario_idAnimal' => $usuario->getAnimal()->getIdAnimal()));
                

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'nomeUsuario' => $dados->nome, 
                            'tipoUsuario' => $dados->tipoUsuario,
                            'idAnimal' => $dados->idAnimal,
                            'nomePet' => $dados->nomePet, 
                            'imagem' => $dados->imagem,
                            // 'dataNascimento' => $dados->dataNascimento,
                            // 'sexo' => $dados->sexo,
                            'resumo' => $dados->resumo
                            ,                            'sql' => $sql
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;

                }else{
                    $rows = array(
                                'response' => "Senha ou usuário errado!"
                            );

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                }

//                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario()));
//
//                if($stmt->rowCount() > 0){
//                    $rows = array();
//                    
//                    while($dados = $stmt->fetchObject()){
//                        $rows[] = array(
//                                'idAnimal' => $dados->idAnimal,
//                                'nome' => $dados->nome, 
//                                'imagem' => $dados->imagem, 
//                                'resumo' => $dados->resumo
//                            );
//                    }                    
//
//                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);
//
//                    echo $json;
//
//                }else{
//                    echo json_encode(array('response' => 'Nao foi encontrado '.$stmt->rowCount()), JSON_UNESCAPED_UNICODE);
//                }
           
                
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }


        
        function loadMainInfos(Usuario $usuario){
            $db = new conexao();
            
            try{
                
                $imagePath = "http://192.168.57.1/tcc/Imagens/";

                $sql = "SELECT A.idUsuario, A.nome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) ORDER BY B.idAnimal";

                // $sql = "SELECT A.idUsuario, A.nome, A.sobrenome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet, B.dataNascimento, B.sexo, C.idAgenda, C.servico, C.titulo, C.observacao, C.dataAgenda, C.horario FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) LEFT JOIN agenda C ON (B.idAnimal = C.animal_idAnimal) where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) ORDER BY B.idAnimal";
                
                $stmt = $db->getConn()->prepare($sql);
                    
                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario(), ':usuario_idAnimal' => $usuario->getAnimal()->getIdAnimal()));
                

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'nomeUsuario' => $dados->nome, 
                            'tipoUsuario' => $dados->tipoUsuario,
                            'idAnimal' => $dados->idAnimal,
                            'nomePet' => $dados->nomePet, 
                            'imagem' => "http://192.168.57.1/tcc/Imagens/".$dados->imagem,
                            // 'dataNascimento' => $dados->dataNascimento,
                            // 'sexo' => $dados->sexo,
                            'resumo' => $dados->resumo
                            ,                            'sql' => $sql
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;

                }else{
                    $rows = array(
                                'response' => "Senha ou usuário errado!"
                            );

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                }           
                
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }


        function listaAgenda(Usuario $usuario){
            $db = new conexao();
            
            try{
                

                $sql = "SELECT A.idUsuario, B.idAnimal, B.nome AS nomePet , C.idAgenda, C.servico, C.titulo, C.observacao, C.dataAgenda, C.horario, C.status FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) LEFT JOIN agenda C ON (B.idAnimal = C.animal_idAnimal) where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) AND (C.servico = :servico) AND CONCAT(C.dataAgenda,' ',C.horario) >= DATE_FORMAT(SYSDATE(), '%Y-%m-%d %H:%m') ORDER BY C.status, C.dataAgenda, C.horario";
                
                $stmt = $db->getConn()->prepare($sql);
                    
                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario(), ':usuario_idAnimal' => $usuario->getAnimal()->getIdAnimal() , ':servico' => $usuario->getAgenda()->getServico()));
                

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'idAnimal' => $dados->idAnimal, 
                            'idAgenda' => $dados->idAgenda,
                            'nomePet' => $dados->nomePet, 
                            'servico' => $dados->servico,
                            'titulo' => $dados->titulo,
                            'observacao' => $dados->observacao,
                            'dataAgenda' => $dados->dataAgenda,
                            'horario' => $dados->horario, 
                            'status' => $dados->status
                            //,'sql' => $sql
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                    

                }        

                #echo json_encode(array("response" => "Sql: ".$sql.":usuario_idUsuario: ".$usuario->getIdUsuario()." usuario_idAnimal: ".$usuario->getAnimal()->getIdAnimal()." servico ".$usuario->getAgenda()->getServico()), JSON_UNESCAPED_UNICODE);
                
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }


        function pesquisaAgenda(Usuario $usuario){
            $db = new conexao();
            
            try{
                
                $pesquisa=$usuario->getAgenda()->getPesquisa();
                $sql = "SELECT A.idUsuario, B.idAnimal, B.nome AS nomePet , C.idAgenda, C.servico, C.titulo, C.observacao, C.dataAgenda, C.horario, C.status FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) LEFT JOIN agenda C ON (B.idAnimal = C.animal_idAnimal) where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) AND (C.servico = :servico) AND (lower(C.titulo LIKE '%$pesquisa%') OR lower(C.observacao like '%$pesquisa%') OR C.dataAgenda like '%$pesquisa%' ) ORDER BY C.status, C.dataAgenda, C.horario";
                
                $stmt = $db->getConn()->prepare($sql);
                    
                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario(), ':usuario_idAnimal' => $usuario->getAnimal()->getIdAnimal() , ':servico' => $usuario->getAgenda()->getServico()));
                

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'idAnimal' => $dados->idAnimal, 
                            'idAgenda' => $dados->idAgenda,
                            'nomePet' => $dados->nomePet, 
                            'servico' => $dados->servico,
                            'titulo' => $dados->titulo,
                            'observacao' => $dados->observacao,
                            'dataAgenda' => $dados->dataAgenda,
                            'horario' => $dados->horario, 
                            'status' => $dados->status
                            //,'sql' => $sql
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                    

                }        

                #echo json_encode(array("response" => "Sql: ".$sql.":usuario_idUsuario: ".$usuario->getIdUsuario()." usuario_idAnimal: ".$usuario->getAnimal()->getIdAnimal()." servico ".$usuario->getAgenda()->getServico()), JSON_UNESCAPED_UNICODE);
                
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }


        function loadMainInfos_(Usuario $usuario){
            $db = new conexao();
            
            try{
                
                $imagePath = "http://192.168.57.1/tcc/Imagens/";

                $sql = "SELECT A.idUsuario, A.nome, A.sobrenome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet, B.dataNascimento, B.sexo, C.idAgenda, C.servico, C.titulo, C.observacao, C.dataAgenda, C.horario FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) LEFT JOIN agenda C ON (B.idAnimal = C.animal_idAnimal) ";
                
                if($usuario->getAcao() == "login"){
                    $sql.="where (A.email = :email OR A.nome = :email) AND (A.senha = :senha) ORDER BY B.idAnimal";

                    $stmt = $db->getConn()->prepare($sql);
                
                    $stmt->execute(array(':email' => $usuario->getEmail(), ':senha' => $usuario->getSenha())); 
                }else if($usuario->getAcao() == "loadPetInfo"){
                    $sql.="where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) ORDER BY B.idAnimal";
                
                    $stmt = $db->getConn()->prepare($sql);
                    
                    $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario(), ':usuario_idAnimal' => $usuario->getAnimal()->getIdAnimal()));
                }else if($usuario->getAcao() == "listaAgenda"){
                    $sql.="where (A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :usuario_idAnimal) AND (C.servico = :servico) ORDER BY B.idAnimal";
                
                    $stmt = $db->getConn()->prepare($sql);
                    
                    $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario(), ':usuario_idAnimal' => $usuario->getAnimal()->getIdAnimal() , ':servico' => $usuario->getAgenda()->getServico()));
                }
//                $sql = "SELECT A.idAnimal, A.nome, CONCAT('".$imagePath."', A.imagem) AS imagem, CONCAT(A.especie ,', ', A.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(A.dataNascimento))), ' anos',', ', A.sexo) AS resumo FROM animal A WHERE A.usuario_idUsuario = :usuario_idUsuario ORDER BY A.dataNascimento ASC";
                 #$sql = "SELECT A.idUsuario, A.nome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) where ((A.idUsuario = :usuario_idUsuario) AND (B.idAnimal = :animal_idAnimal))";
                 
               
                
//                if(!empty($usuario->getIdusuario())){
//                    $stmt->execute(array(':email' => null, ':senha' => null, ':usuario_idUsuario' => $usuario->getIdUsuario()));
//                }else{
//                    $stmt->execute(array(':email' => $usuario->getEmail(), ':senha' => $usuario->getSenha(), ':usuario_idUsuario' => -1));
//                }

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'nomeUsuario' => $dados->nome, 
                            'tipoUsuario' => $dados->tipoUsuario,
                            'idAnimal' => $dados->idAnimal,
                            'nomePet' => $dados->nomePet, 
                            'imagem' => $dados->imagem,
                            'dataNascimento' => $dados->dataNascimento,
                            'sexo' => $dados->sexo,
                            'resumo' => $dados->resumo,
                            'idAgenda' => $dados->idAgenda,
                            'servico' => $dados->servico,
                            'titulo' => $dados->titulo,
                            'observacao' => $dados->observacao,
                            'dataAgenda' => $dados->dataAgenda,
                            'horario' => $dados->horario
                            ,                            'sql' => $sql
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;

                }else{
                    $rows = array(
                                'response' => "Senha ou usuário errado!"
                            );

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                }

//                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario()));
//
//                if($stmt->rowCount() > 0){
//                    $rows = array();
//                    
//                    while($dados = $stmt->fetchObject()){
//                        $rows[] = array(
//                                'idAnimal' => $dados->idAnimal,
//                                'nome' => $dados->nome, 
//                                'imagem' => $dados->imagem, 
//                                'resumo' => $dados->resumo
//                            );
//                    }                    
//
//                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);
//
//                    echo $json;
//
//                }else{
//                    echo json_encode(array('response' => 'Nao foi encontrado '.$stmt->rowCount()), JSON_UNESCAPED_UNICODE);
//                }
           
                
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }
        
        
        
        function listaAnimais(Usuario $usuario){
            $db = new conexao();
            
            try{
                
                $imagePath = "http://192.168.57.1/tcc/Imagens/";
                
                $sql = "SELECT A.idUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet, B.dataNascimento, B.sexo FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) where (A.idUsuario = :usuario_idUsuario) ORDER BY B.nome, B.dataNascimento";
                
                $stmt = $db->getConn()->prepare($sql);
                
                $stmt->execute(array(':usuario_idUsuario' => $usuario->getIdUsuario()));
                
                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'idAnimal' => $dados->idAnimal,
                            'nomePet' => $dados->nomePet, 
                            'imagem' => $dados->imagem,
                            'dataNascimento' => $dados->dataNascimento,
                            'sexo' => $dados->sexo
                        );

                    }
                    
                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;

                }else{
                    $rows = array(
                                'response' => "Nenhum animal encontrado na base!"
                            );

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                }
                
            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }
        
        
    }
?>
