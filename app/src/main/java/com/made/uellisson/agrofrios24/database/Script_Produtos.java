package com.made.uellisson.agrofrios24.database;


import com.made.uellisson.agrofrios24.modelo.Produto;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Script_Produtos {
    /**
     * Cria ou abre a tabela produtos
     */
    public static String abreOuCriaTabela_produtos() {
        String comandoSQL = "CREATE TABLE  IF NOT EXISTS tb_produtos (" +
                "nome  varchar(50), codigo_produto  varchar(10), preco  varchar(10), unidade  varchar(10)," +
                    "completo  varchar(15), pecas_caixa  varchar(15), unds_peca  varchar(15), disponivel varchar(5) );";

        return comandoSQL;
    }

    /**
     * Script que salva um produto no banco de dados
     **/
    public static String salvar_Produto(Produto produto){

        String comandoSQL = "INSERT INTO tb_produtos (nome, codigo_produto, preco, unidade, completo, pecas_caixa, unds_peca, disponivel) values ('"

                +produto.getNome()+"','"+produto.getCodigo_produto()+"','"+produto.getPreco()+"','"
                +produto.getUnidade()+"','"+produto.getCompleto()+"','"+produto.getPecas_caixa()+"','"+produto.getUnds_peca()+"','"+produto.getDisponivel()+"')";

        return comandoSQL;
    }

    /**
     * Script que excui todos os dados de uma tabela
     **/
    public static String excuir_todos_Produtos(String tabela){

        String comandoSQL = "drop table "+tabela+";";

        return comandoSQL;
    }
}
