package com.made.uellisson.agrofrios24.dominio;

import android.database.sqlite.SQLiteDatabase;

import com.made.uellisson.agrofrios24.database.Script_Pedido;
import com.made.uellisson.agrofrios24.database.Script_Produtos;
import com.made.uellisson.agrofrios24.database.Script_Produtos_Pedido;
import com.made.uellisson.agrofrios24.database.Script_Vendedor;
import com.made.uellisson.agrofrios24.database.Scripts_Clientes;
import com.made.uellisson.agrofrios24.modelo.Cliente;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produto;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;
import com.made.uellisson.agrofrios24.modelo.Vendedor;

import java.util.ArrayList;


/**
 * Created by Uellisson on 21/02/2016.
 */
public class Salvar_no_DB {

    private SQLiteDatabase conexao;

    public Salvar_no_DB(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    //salvar cliente
    public String salvar_cliente(Cliente c){

        Scripts_Clientes sc = new Scripts_Clientes();

        String comando_salvar = sc.salvarCliente(c);

        conexao.execSQL(comando_salvar);

        return comando_salvar;
    }



    //salvar cliente
    public void salvar_produto(Produto produto){

        String comando_salvar = Script_Produtos.salvar_Produto(produto);

        conexao.execSQL(comando_salvar);

    }

    //salvar pedido
    public void salvar_pedido(Pedido pedido, ArrayList<Produtos_pedido> lista_produtos_pedido){

        String comando_salvar_pedido = Script_Pedido.salvar_Pedido(pedido);


        //For para salvar produtos do pedido
        for (int i=0; i<lista_produtos_pedido.size(); i++){
            String comando_salvar_pp = Script_Produtos_Pedido.salvar_Produto_pedido(lista_produtos_pedido.get(i));
            conexao.execSQL(comando_salvar_pp);
        }

        conexao.execSQL(comando_salvar_pedido);

    }

    //salvar pedido
    public void salvar_vendedor(Vendedor vendedor){

        String comando_excluir_vendedor = Script_Vendedor.excuir_todos_vendedores();
        //String comando_criar_tabela_venderor = Script_Vendedor.abreOuCriaTabela_produtos();
        String comando_salvar_vendedor = Script_Vendedor.salvar_vendedor(vendedor);

        conexao.execSQL(comando_excluir_vendedor);
        conexao.execSQL(comando_salvar_vendedor);

    }

//    //salvar produtos pedido
//    public void salvar_produto_pedido(Produtos_pedido produtos_pedido){
//
//        String comando_salvar = Script_Produtos_Pedido.salvar_Produto_pedido(produtos_pedido);
//
//        conexao.execSQL(comando_salvar);
//
//    }
}
