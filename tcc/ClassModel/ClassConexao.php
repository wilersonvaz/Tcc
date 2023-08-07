<?php
    /*
    @author Wilerson Alves Vaz
    @version 1.0
    @since 2016-08-09
    @description Sisteminha básico de cadastro
    */

    session_start();


    class conexao
    {
        //Variável de conexão
        private static $conn;

        //Função que faz a conexão com o banco de dados
        static function getConn()
        {

            //Verifica se a conexão ainda não foi estabelecida, caso ainda nã tenha sido, faz a conexão
            if(is_null(self::$conn))
            {
                //Atribui os valores do banco de dados à variável de conexão
                //Atribui os valores do banco de dados à variável de conexão
                if($_SERVER['HTTP_HOST'] == "localhost" || $_SERVER['HTTP_HOST'] == "127.0.0.1" || $_SERVER['HTTP_HOST'] == "192.168.56.1" || $_SERVER['HTTP_HOST'] == "192.168.57.1") 
                {
                    self::$conn = new PDO('mysql:host=localhost;dbname=tcc','root','') or die( "Unable to select database");;
                }else{
                    self::$conn = new PDO('mysql:host=localhost;dbname=id19457158_pwpp3','id19457158_root','!Padariapwpp3') or die( "Unable to select database");
                }                


                //Permite a inserção de caracteres especiais no bando
                self::$conn->exec('SET NAMES utf8');
                self::$conn->exec('SET character_set_connection=utf8');
                self::$conn->exec('SET character_set_client=utf8');
                self::$conn->exec('SET character_set_results=utf8');

                //Caso haja erros, envia mensagem de erro
                self::$conn->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
            }

            //Em caso de conexão bem sucedida, retorna variável de conexão com os valores do banco de dados
            return self::$conn;
        }


    //Fecha classe
    }

?>
