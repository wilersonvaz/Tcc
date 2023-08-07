<?php
    include('../ClassModel/ClassConexao.php');
    
    class AgendaDAO{

        function cadastrarAgendaTeste(Agenda $agenda){
            try{
                $db = new conexao();
                $dataAgenda = $agenda->getDataAgenda()." ".$agenda->getHorarioAgenda();
                $servico = $agenda->getServico();
                $titulo = $agenda->getTitulo();
                $observacao = $agenda->getObservacao();
                $horario = $agenda->getHorarioAgenda();
                $idAnimal = $agenda->getAnimal()->getIdAnimal();

                if($agenda->getIntervalo() == 1){
                    echo "É só uma vez";
                }else{
                    //Se o intervalo é 2, quer dizer que o intervalo é em horas, multiplica o intervalo por 60
                    if($agenda->getIntervalo() == 2){
                        $qtdeDoses = ((24 / $agenda->getDiferenca()) * $agenda->getqtdeDias());
                        
                        $tempDataAgenda = date('Y-m-d H:i', strtotime($dataAgenda));
                        $dataAgenda = date('Y-m-d', strtotime($tempDataAgenda));
                        $horario = date('H:i', strtotime($tempDataAgenda));
                            
                        $data[] = array('servico' => $servico, 'titulo' => $titulo, 'observacao' => $observacao, 'dataAgenda' => $dataAgenda, 'horario' => $horario, 'idAnimal' => $idAnimal);
                        
                        for($i = 0; $i < $qtdeDoses; $i++){
                            $tempDataAgenda = date('Y-m-d H:i', strtotime("+{$agenda->getDiferenca()} hours", strtotime($tempDataAgenda)));
                            $dataAgenda = date('Y-m-d', strtotime($tempDataAgenda));
                            $horario = date('H:i', strtotime($tempDataAgenda));
                            $data[] = array('servico' => $servico, 'titulo' => $titulo, 'observacao' => $observacao, 'dataAgenda' => $dataAgenda, 'horario' => $horario, 'idAnimal' => $idAnimal);
                        }

                        $sql = "INSERT INTO agenda (
                                                servico,
                                                titulo, 
                                                observacao,
                                                dataAgenda,
                                                horario, 
                                                animal_idAnimal
                                            ) VALUES (
                                                :servico,
                                                :titulo, 
                                                :observacao, 
                                                :dataAgenda,
                                                :horario, 
                                                :idAnimal
                                            )";

                        $stmt = $db->getConn()->prepare($sql);
                        
                        $countInsert = 0;            
                        foreach ($data as $row)
                        {
                            
                            $stmt->execute(
                                array(
                                        ':servico' => $row["servico"],
                                        ':titulo' => $row["titulo"], 
                                        ':observacao' => $row["observacao"], 
                                        ':dataAgenda' => $row["dataAgenda"],
                                        ':horario' => $row["horario"],
                                        ':idAnimal' => $row["idAnimal"]
                                        )
                            );

                            if($stmt->rowCount() > 0){
                                $countInsert += $stmt->rowCount();
                                // echo $row["servico"]." -> ".$row["dataAgenda"]." -> ".$row["horario"]." inserido!<br/>";
                            }
                            
                        }

                        $rows = array('response' => "qtdeDoses: {$qtdeDoses} -> {$agenda->getDiferenca()} -> {$agenda->getqtdeDias()}");

                        $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                        echo $json;

                        
                    }
                    
                }
            }catch (Exception $ex) {
                echo json_encode(array('response' => 'Erro ao cadastrar o usuário '.$ex->getMessage()));
                 // "Erro ao cadastrar o usuário ".$ex->getMessage();
            }
        }


        
        function cadastrarAgenda(Agenda $agenda){            
            
            try{
                $db = new conexao();
                
                $sql = "INSERT INTO agenda (
                                                idAgenda, 
                                                servico,
                                                titulo, 
                                                observacao,
                                                dataAgenda,
                                                horario, 
                                                animal_idAnimal
                                            ) VALUES (
                                                NULL, 
                                                :servico,
                                                :titulo, 
                                                :observacao, 
                                                :dataAgenda,
                                                :horario, 
                                                :idAnimal
                                            )";
                
                $stmt = $db->getConn()->prepare($sql);
                
                $stmt->execute(
                                array(
                                        ':servico' => $agenda->getServico(),
                                        ':titulo' => $agenda->getTitulo(), 
                                        ':observacao' => $agenda->getObservacao(), 
                                        ':dataAgenda' => $agenda->getDataAgenda(),
                                        ':horario' => $agenda->getHorarioAgenda(),
                                        ':idAnimal' => $agenda->getAnimal()->getIdAnimal()
                                        )
                            );

                if($stmt->rowCount() > 0){
                    // $rows = [];

                    $rows = array('response' => $db->getConn()->lastInsertId());

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
                
                $imagePath = "http://192.168.56.1/tcc/Imagens/";
                
                $sql = "SELECT A.idUsuario, A.nome, A.tipoUsuario, CONCAT('".$imagePath."', B.imagem) AS imagem, CONCAT(B.especie ,', ', B.raca ,', ',YEAR(FROM_DAYS(TO_DAYS(NOW())-TO_DAYS(B.dataNascimento))), ' anos',', ', B.sexo) AS resumo, B.idAnimal, B.nome AS nomePet FROM usuario A LEFT JOIN animal B ON (A.idUsuario = B.usuario_idUsuario) where ((A.email = :email OR A.nome = :email) AND (A.senha = :senha))";
                
                
//                $sql = "SELECT * FROM usuario where (email = :email OR nome = :email) AND (senha = :senha)";
                
                $stmt = $db->getConn()->prepare($sql);

                $stmt->execute(array(':email' => $agenda->getEmail(), ':senha' => $agenda->getSenha(), ':usuario_idUsuario' => -1));
                

                if($stmt->rowCount() > 0){
                    
                    while($dados = $stmt->fetchObject()){
                        $rows[] = array(
                            'idUsuario' => $dados->idUsuario, 
                            'nomeUsuario' => $dados->nome, 
                            'tipoUsuario' => $dados->tipoUsuario,
                            'idAnimal' => $dados->idAnimal,
                            'nomePet' => $dados->nomePet, 
                            'imagem' => $dados->imagem,
                            'resumo' => $dados->resumo
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
        
    }
?>
