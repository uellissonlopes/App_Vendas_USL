package com.made.uellisson.agrofrios24.database;


import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Script_Produtos_Pedido {
    /**
     * Cria ou abre a tabela produtos
     */
    public static String abreOuCriaTabela_produto_pedido() {
        String comandoSQL = "CREATE TABLE  IF NOT EXISTS tb_produtos_pedido (" +
                "id_pedido TEXT, codigo_produto TEXT,  nome_produto TEXT, quantidade TEXT, " +
                    "desconto TEXT, preco_total TEXT);";

        return comandoSQL;
    }

    /**
     * Script que salva um produto do pedido no banco de dados
     **/
    public static String salvar_Produto_pedido(Produtos_pedido produto){

        String comandoSQL = "INSERT INTO tb_produtos_pedido (id_pedido, codigo_produto, nome_produto, quantidade, desconto, preco_total) values ('"

                +produto.getId_pedido()+"','"+produto.getCodigo_produto()+"','"+produto.getNome_produto()+
                    "','"+produto.getQuantidade()+"','"
                +produto.getDesconto()+"','"+produto.getPreco_total()+"')";

        return comandoSQL;
    }
}
