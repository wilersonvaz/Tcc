<?php
    include('../ClassModel/ClassConexao.php');
    
    class AnimalDAO{
        
        function cadastrarAnimal(Animal $animal){            
            
            try{
                $db = new conexao();

                $rows = [];
                
                $sql = "INSERT INTO animal (
                                                idAnimal, 
                                                usuario_idUsuario,
                                                especie,
                                                nome, 
                                                dataNascimento, 
                                                dataAdocao,
                                                peso,
                                                cor, 
                                                sexo,
                                                notas, 
                                                imagem,
                                                raca
                                            ) VALUES (
                                                NULL, 
                                                :usuario_idUsuario,
                                                :especie,
                                                :nome, 
                                                :dataNascimento, 
                                                :dataAdocao, 
                                                :peso,
                                                :cor, 
                                                :sexo,
                                                :notas, 
                                                :imagem,
                                                :raca
                                            )";
                
                $stmt = $db->getConn()->prepare($sql);

                $novoNomeImagem = self::uploadToFolder($_POST["imagem"]);
                
                $stmt->execute(
                                array(
                                        ':nome' => $animal->getNome(),
                                        ':usuario_idUsuario' => $animal->getUsuario()->getIdUsuario(),
                                        ':especie' => $animal->getEspecie(),
                                        ':dataNascimento' => $animal->getDataNascimento(), 
                                        ':dataAdocao' => $animal->getDataAdocao(),
                                        ':peso' => $animal->getPeso(),
                                        ':cor' => $animal->getCor(), 
                                        ':sexo' => $animal->getSexo(),
                                        ':notas' => $animal->getNotas(), 
                                        ':imagem' => $novoNomeImagem,
                                        ':raca' => $animal->getRaca()
                                        )
                            );

                if($stmt->rowCount() > 0){
                    
                    $rows = array('response' => $db->getConn()->lastInsertId());

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                }
                
            } catch (Exception $ex) {
                unlink("../Imagens/".$novoNomeImagem);

                $rows = array('response' => "Erro ao cadastrar o animal ".$ex->getMessage());

                $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                echo $json;              
            }
        }

        function uploadToFolder($file){

            if(!empty($file))
            {
                $imagem = base64_decode($file);
                try{
                    

                    $extensao = @end(explode('.', $imagem));

                    // $novoNomeImagem = md5(uniqid(rand(), true)).'.'.$extensao;

                    $novoNomeImagem = md5(uniqid(rand(), true)).".jpg";

                    $upload_path = "../Imagens/".$novoNomeImagem;

                    file_put_contents($upload_path, $imagem);

                }catch(Exception $ex){
                    echo json_encode(array('response'=>'Ocorreu um erro ao inserir a imagem {$ex}'));
                }

            }

            return $novoNomeImagem;

        }



        function login(Usuario $usuario){
            $db = new conexao();

            try{
                
                $sql = "SELECT * FROM usuario where (email = :email OR nome = :email) AND (senha = :senha)";
                
                $stmt = $db->getConn()->prepare($sql);

                $stmt->execute(array(':email' => $usuario->getEmail(), ':senha' => $usuario->getSenha()));

                if($stmt->rowCount() > 0){
                    $dados = $stmt->fetchObject();

                    $rows = [];
                    
                    $rows = array(
                                'idUsuario' => $dados->idUsuario, 
                                'nomeUsuario' => $dados->nome, 
                                'tipoUsuario' => $dados->tipoUsuario
                            );

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;

                }else{
                    echo "Usuário não encontrado!";
                }


            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }




        function carregaUpdate(Animal $animal){
            $db = new conexao();

            try{
                
                $sql = "SELECT * FROM animal where idAnimal = :idAnimal";
                
                $stmt = $db->getConn()->prepare($sql);

                $stmt->execute(array(':idAnimal' => $animal->getIdAnimal()));

                if($stmt->rowCount() > 0){
                    while($dados = $stmt->fetchObject()){
                    
                        $rows[] = array(
                            'idAnimal' => $dados->idAnimal, 
                            'nome' => $dados->nome, 
                            'especie' => $dados->especie,
                            'dataNascimento' => $dados->dataNascimento,
                            'dataAdocao' => $dados->dataAdocao,
                            'peso' => $dados->peso, 
                            'sexo' => $dados->sexo, 
                            'notas' => $dados->notas, 
                            'imagem' => $dados->imagem,
                            'raca' => $dados->raca
                        );
                    }

                    $json = json_encode($rows, JSON_UNESCAPED_UNICODE);

                    echo $json;
                    
                }


            } catch (Exception $ex) {
                echo $ex->getMessage();
            }
        }


    }
?>
