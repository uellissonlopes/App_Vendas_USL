package com.made.uellisson.agrofrios24.database;


import com.made.uellisson.agrofrios24.modelo.Pedido;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Script_Pedido {
    /**
     * Cria ou abre a tabela pedidos
     */
    public static String abreOuCriaTabela_pedidos() {
        String comandoSQL = "CREATE TABLE  IF NOT EXISTS tb_pedidos (" + "id_pedido TEXT, "+
                "cliente_fantasia TEXT, codigo_cliente TEXT, tipo_pagamento TEXT, data_atual TEXT, " +
                                                "produtos TEXT, valor_total TEXT, desconto TEXT);";

        return comandoSQL;
    }

    /**
     * Script que salva um pedido no banco de dados
     **/
    public static String salvar_Pedido(Pedido pedido){

        String comandoSQL = "INSERT INTO tb_pedidos (id_pedido, cliente_fantasia, codigo_cliente, tipo_pagamento," +
                " data_atual, produtos, valor_total, desconto) values ('"

                +pedido.getId_pedido()+"','"+pedido.getCliente_fantasia()+"','"+pedido.getCodigo_cliente()
            +"','"+pedido.getTipo_pagamento()+"','"+pedido.getData_atual()
                +"','"+pedido.getProdutos()+"','"+pedido.getValor_total()+"','"+pedido.getDesconto()+"')";

        return comandoSQL;
    }
}
