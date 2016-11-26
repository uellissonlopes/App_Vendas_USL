package com.made.uellisson.agrofrios24.dominio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.made.uellisson.agrofrios24.database.Gerenciar_Arquivos;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;


/**
 * Created by Uellisson on 28/02/2016.
 */
public class Dados_Produtos_pedido {

    private SQLiteDatabase conexao;
    Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

    public Dados_Produtos_pedido(SQLiteDatabase conexao){
        this.conexao = conexao;

    }


    public ArrayAdapter<Produtos_pedido> busca_produtos_pedidos(Context context, SQLiteDatabase conexao_db){
        //ArrayList<Produtos_pedido> lista_produtos_pedidos = new ArrayList<Produtos_pedido>();
//
        ArrayAdapter<Produtos_pedido> adapter_produtos_pedidos = new ArrayAdapter<Produtos_pedido>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao_db.query("tb_produtos_pedido", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String id_pedido = cursor.getString(0);
                String codigo_produto = cursor.getString(1);
                String nome_produto = cursor.getString(2);
                String quantidade = cursor.getString(3);
                String desconto = cursor.getString(4);
                String preco_total = cursor.getString(5);

                Produtos_pedido produtos_pedido = new Produtos_pedido(id_pedido, codigo_produto, nome_produto, quantidade, desconto, preco_total);
                adapter_produtos_pedidos.add(produtos_pedido);

            }
            while (cursor.moveToNext());
        }

        //lista_produtos_pedidos = adapter_produtos_pedidos.;

        return adapter_produtos_pedidos;
    }



    /*
    Busca produtos de um pedido com o mesmo id
     */
    public ArrayAdapter<Produtos_pedido> busca_produtos_pedidos_id(String id_pedido, Context context, SQLiteDatabase conexao_db){
        //ArrayList<Produtos_pedido> lista_produtos_pedidos = new ArrayList<Produtos_pedido>();
//
        ArrayAdapter<Produtos_pedido> adapter_produtos_pedidos = new ArrayAdapter<Produtos_pedido>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao_db.query("tb_produtos_pedido", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String id_pedido_db = cursor.getString(0);
                String codigo_produto = cursor.getString(1);
                String nome_produto = cursor.getString(2);
                String quantidade = cursor.getString(3);
                String desconto = cursor.getString(4);
                String preco_total = cursor.getString(5);

                if (id_pedido.equalsIgnoreCase(id_pedido_db)){
                    Produtos_pedido produtos_pedido = new Produtos_pedido(id_pedido, codigo_produto, nome_produto, quantidade, desconto, preco_total);
                    adapter_produtos_pedidos.add(produtos_pedido);
                }

            }
            while (cursor.moveToNext());
        }

        return adapter_produtos_pedidos;
    }


    /*
    Transforma todas os produtos da tabela em uma String
    */
    public String todos_dados_produtos_pedido(Context context, SQLiteDatabase conexao_db){
        //ArrayList<Produtos_pedido> lista_todos_produtos_pedidos = new ArrayList<Produtos_pedido>();
        ArrayAdapter<Produtos_pedido> adapter_todos_produtos_pedidos = new ArrayAdapter<Produtos_pedido>(context, android.R.layout.simple_list_item_1);


        String string_todos_produtos = "";

//        adapter_todos_produtos_pedidos = busca_produtos_pedidos(context);
        adapter_todos_produtos_pedidos = busca_produtos_pedidos(context, conexao_db);

        for (int i = 0; i < adapter_todos_produtos_pedidos.getCount(); i++) {
            string_todos_produtos = string_todos_produtos+"#"+adapter_todos_produtos_pedidos.getItem(i).getId_pedido()+"#"+
                    adapter_todos_produtos_pedidos.getItem(i).getCodigo_produto()+"#"+
                    adapter_todos_produtos_pedidos.getItem(i).getNome_produto()+"#"+
                    adapter_todos_produtos_pedidos.getItem(i).getQuantidade()+"#"+
                    adapter_todos_produtos_pedidos.getItem(i).getDesconto()+"#"+
                    adapter_todos_produtos_pedidos.getItem(i).getPreco_total()+"%";
        }
        return string_todos_produtos+"%";

    }

//    /*
//     Cria a lista com dados do cliente restaurado de um backup
//    */
//    public ArrayList<Produto> dados_produtos_do_backup(String nome_arq){
//        ArrayList<Produto> lista_produtos = new ArrayList<Produto>();
//
//        String todos_dados_produtos = bk.ler_arquivo(nome_arq);
//
//        String atributo = "";
//
//        String nome = "";
//        String codigo_produto = "";
//        String preco = "";
//        String unidade = "";
//        String completo = "";
//        String pecas_caixa = "";
//        String unds_peca = "";
//        String disponivel = "";
//
//        int contador_hashtag = 0;
//
//
//        for (int i = 0; i < todos_dados_produtos.length()-1; i++) {
//
//            if (todos_dados_produtos.substring(i, i+1).equalsIgnoreCase("#")) {
//                atributo = "";
//                contador_hashtag+=1;
//            }
//
//            else{
//                if (todos_dados_produtos.substring(i, i+1).equalsIgnoreCase("%")) {
//                    Produto p = new Produto(nome, codigo_produto, preco, unidade, completo, pecas_caixa, unds_peca, disponivel);
//
//                    lista_produtos.add(p);
//
//                    contador_hashtag=0;
//                    //seta todos os atributos pra vazio
//                    atributo = ""; nome = ""; codigo_produto = ""; preco = ""; unidade = ""; completo = ""; pecas_caixa = ""; unds_peca = ""; disponivel = "";
//                }
//                else{
//                    if (contador_hashtag==1) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        nome=atributo;
//                    }
//                    if (contador_hashtag==2) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        codigo_produto=atributo;
//                    }
//                    if (contador_hashtag==3) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        preco=atributo;
//                    }
//                    if (contador_hashtag==4) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        unidade=atributo;
//                    }
//                    if (contador_hashtag==5) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        completo=atributo;
//                    }
//                    if (contador_hashtag==6) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        pecas_caixa=atributo;
//                    }
//                    if (contador_hashtag==7) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        unds_peca=atributo;
//                    }
//                    if (contador_hashtag==8) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        disponivel=atributo;
//                    }
//                }
//            }
//
//        }//fim do for
//
//        return lista_produtos;
//    }
}
