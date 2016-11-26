package com.made.uellisson.agrofrios24.database;


import com.made.uellisson.agrofrios24.modelo.Vendedor;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Script_Vendedor {
    /**
     * Cria ou abre a tabela produtos
     */
    public static String abreOuCriaTabela_vendedor() {
        String comandoSQL = "CREATE TABLE  IF NOT EXISTS tb_vendedor (" +
                "nome_vendedor  varchar(50), codigo_vendedor  varchar(10));";

        return comandoSQL;
    }

    /**
     * Script que salva um vendedor no banco de dados
     **/
    public static String salvar_vendedor(Vendedor vendedor){

        String comandoSQL = "INSERT INTO tb_vendedor (nome_vendedor, codigo_vendedor) values ('"

                +vendedor.getNome_vendedor()+"','"+vendedor.getCodigo_vendedor()+"')";

        return comandoSQL;
    }

    /**
     * Script que excui todos os dados de uma tabela
     **/
    public static String excuir_todos_vendedores(){

        String comandoSQL = "DELETE FROM tb_vendedor;";

        return comandoSQL;
    }
}
