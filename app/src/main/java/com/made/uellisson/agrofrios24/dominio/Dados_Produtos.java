package com.made.uellisson.agrofrios24.dominio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.made.uellisson.agrofrios24.database.Gerenciar_Arquivos;
import com.made.uellisson.agrofrios24.database.Script_Produtos_Pedido;
import com.made.uellisson.agrofrios24.modelo.Produto;

import java.util.ArrayList;


/**
 * Created by Uellisson on 28/02/2016.
 */
public class Dados_Produtos {

    private SQLiteDatabase conexao;
    Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

    public Dados_Produtos(SQLiteDatabase conexao){
        this.conexao = conexao;

    }


    /*
    Busca todos os produtos no banco de dados
     */
    public ArrayAdapter<Produto> busca_produto(Context context){
        ArrayAdapter<Produto> produtos = new ArrayAdapter<Produto>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = null;

        //armazena todos os dados
        // parâmetros o nome da tabela, as colunas a selecionar, a condição/where da seleção, os argumentos do where, seguido de possíveis groupBy, having e orderBy
        cursor = conexao.query("tb_produtos", null, null, null, "nome", null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {
                String nome = cursor.getString(0);
                String codigo_produto = cursor.getString(1);
                String preco = cursor.getString(2);
                String unidade = cursor.getString(3);
                String completo = cursor.getString(4);
                String pecas_caixa = cursor.getString(5);
                String unds_peca = cursor.getString(6);
                String disponivel = cursor.getString(7);

                Produto produto = new Produto(nome, codigo_produto, preco, unidade, completo, pecas_caixa, unds_peca, disponivel);
                produtos.add(produto);

            }
            while (cursor.moveToNext());
        }

        return produtos;
    }

    /*
   Busca todos os produtos no banco de dados
    */
    public ArrayAdapter<Produto> busca_produtos_disponiveis(Context context){
        ArrayAdapter<Produto> produtos = new ArrayAdapter<Produto>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_produtos", null, null, null, "nome", null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String nome = cursor.getString(0);
                String codigo_produto = cursor.getString(1);
                String preco = cursor.getString(2);
                String unidade = cursor.getString(3);
                String completo = cursor.getString(4);
                String pecas_caixa = cursor.getString(5);
                String unds_peca = cursor.getString(6);
                String disponivel = cursor.getString(7);



                if(disponivel.equalsIgnoreCase("Sim")){
                    Produto produto = new Produto(nome, codigo_produto, preco, unidade, completo, pecas_caixa, unds_peca, disponivel);
                    produtos.add(produto);
                }

            }
            while (cursor.moveToNext());
        }

        return produtos;
    }


    /*
    Busca todos os produtos no banco de dados
     */
    public ArrayList<Produto> busca_lista_produto(){
        ArrayList<Produto> produtos = new ArrayList<Produto>();

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_produtos", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String nome = cursor.getString(0);
                String codigo_produto = cursor.getString(1);
                String preco = cursor.getString(2);
                String unidade = cursor.getString(3);
                String completo = cursor.getString(4);
                String pecas_caixa = cursor.getString(5);
                String unds_peca = cursor.getString(6);
                String disponivel = cursor.getString(7);

                Produto produto = new Produto(nome, codigo_produto, preco, unidade, completo, pecas_caixa, unds_peca, disponivel);
                produtos.add(produto);
            }
            while (cursor.moveToNext());
        }

        return produtos;
    }


    /*
    Altera o cadastro de um produto
    */
    public void alterar_produto(Produto p) {
        String alterar_preco = "update tb_produtos set preco = '" + p.getPreco() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
        String alterar_disponivel = "update tb_produtos set disponivel = '" + p.getDisponivel() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
        String alterar_completo = "update tb_produtos set completo = '" + p.getCompleto() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
        String alterar_pecas_caixa = "update tb_produtos set pecas_caixa = '" + p.getPecas_caixa() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
        String alterar_unds_peca = "update tb_produtos set unds_peca = '" + p.getUnds_peca() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
        String alterar_undidade_padrao = "update tb_produtos set unidade = '" + p.getUnidade() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";

        conexao.execSQL(alterar_preco);
        conexao.execSQL(alterar_disponivel);
        conexao.execSQL(alterar_completo);
        conexao.execSQL(alterar_pecas_caixa);
        conexao.execSQL(alterar_unds_peca);
        conexao.execSQL(alterar_undidade_padrao);

    }


    /*
   Transforma todas os produtos da tabela em uma String
   */
    public String todos_dados_produtos(){
        ArrayList<Produto> lista_todos_produtos = new ArrayList<Produto>();

        String string_todos_produtos = "";


        lista_todos_produtos = busca_lista_produto();

        for (int i = 0; i < lista_todos_produtos.size(); i++) {
            string_todos_produtos = string_todos_produtos+"#"+
                    lista_todos_produtos.get(i).getNome()+"#"+
                    lista_todos_produtos.get(i).getCodigo_produto()+"#"+
                    lista_todos_produtos.get(i).getPreco()+"#"+
                    lista_todos_produtos.get(i).getUnidade()+"#"+
                    lista_todos_produtos.get(i).getCompleto()+ "#"+
                    lista_todos_produtos.get(i).getPecas_caixa() +"#"+
                    lista_todos_produtos.get(i).getUnds_peca() + "#"+
                    lista_todos_produtos.get(i).getDisponivel()+"%";


        }

        conexao.execSQL(Script_Produtos_Pedido.abreOuCriaTabela_produto_pedido());


        return string_todos_produtos+"%";

    }



    /*
     Cria a lista com dados do cliente restaurado de um backup
    */
    public ArrayList<Produto> dados_produtos_do_backup(String nome_arq){
        ArrayList<Produto> lista_produtos = new ArrayList<Produto>();

        String todos_dados_produtos = bk.ler_arquivo(nome_arq);

        String atributo = "";

        String nome = "";
        String codigo_produto = "";
        String preco = "";
        String unidade = "";
        String completo = "";
        String pecas_caixa = "";
        String unds_peca = "";
        String disponivel = "";

        int contador_hashtag = 0;


        for (int i = 0; i < todos_dados_produtos.length()-1; i++) {

            if (todos_dados_produtos.substring(i, i+1).equalsIgnoreCase("#")) {
                atributo = "";
                contador_hashtag+=1;
            }

            else{
                if (todos_dados_produtos.substring(i, i+1).equalsIgnoreCase("%")) {
                    Produto p = new Produto(nome, codigo_produto, preco, unidade, completo, pecas_caixa, unds_peca, disponivel);

                    lista_produtos.add(p);

                    contador_hashtag=0;
                    //seta todos os atributos pra vazio
                    atributo = ""; nome = ""; codigo_produto = ""; preco = ""; unidade = ""; completo = ""; pecas_caixa = ""; unds_peca = ""; disponivel="";
                }
                else{
                    if (contador_hashtag==1) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        nome=atributo;
                    }
                    if (contador_hashtag==2) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        codigo_produto=atributo;
                    }
                    if (contador_hashtag==3) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        preco=atributo;
                    }
                    if (contador_hashtag==4) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        unidade=atributo;
                    }
                    if (contador_hashtag==5) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        completo=atributo;
                    }
                    if (contador_hashtag==6) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        pecas_caixa=atributo;
                    }
                    if (contador_hashtag==7) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        unds_peca=atributo;
                    }
                    if (contador_hashtag==8) {
                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
                        disponivel=atributo;
                    }

                }
            }

        }//fim do for

        return lista_produtos;
    }
}
