package com.made.uellisson.agrofrios24.dominio;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.made.uellisson.agrofrios24.database.Gerenciar_Arquivos;
import com.made.uellisson.agrofrios24.modelo.Vendedor;


/**
 * Created by Uellisson on 28/02/2016.
 */
public class Dados_Vendedor {

    private SQLiteDatabase conexao;
    Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

    public Dados_Vendedor(SQLiteDatabase conexao){
        this.conexao = conexao;

    }



    /*
    Busca todos os vendedore cadastrados no banco de dados
     */
    public Vendedor busca_vendedor(){

        Vendedor vendedor = null;
        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_vendedor", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String nome_vendedor = cursor.getString(0);
                String codigo_vendedor = cursor.getString(1);

                vendedor = new Vendedor(nome_vendedor, codigo_vendedor);

            }
            while (cursor.moveToNext());
        }

        return vendedor;
    }
//
//
//    /*
//    Busca todos os produtos no banco de dados
//     */
//    public ArrayList<Produto> busca_lista_produto(){
//        ArrayList<Produto> produtos = new ArrayList<Produto>();
//
//        Cursor cursor = null;
//
//        //armazena todos os dados
//        cursor = conexao.query("tb_produtos", null, null, null, null, null, null);
//
//        if(cursor.getCount()>0){
//            cursor.moveToFirst();
//            do {
//
//                String nome = cursor.getString(0);
//                String codigo_produto = cursor.getString(1);
//                String preco = cursor.getString(2);
//                String unidade = cursor.getString(3);
//                String completo = cursor.getString(4);
//                String und_caixa = cursor.getString(5);
//                String disponivel = cursor.getString(6);
//
//                Produto produto = new Produto(nome, codigo_produto, preco, unidade, completo, und_caixa, disponivel);
//
//                produtos.add(produto);
//            }
//            while (cursor.moveToNext());
//        }
//
//        return produtos;
//    }
//
//
//    /*
//    Altera o cadastro de um produto
//    */
//    public void alterar_produto(Produto p) {
//        String alterar_preco = "update tb_produtos set preco = '" + p.getPreco() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
//        String alterar_disponivel = "update tb_produtos set disponivel = '" + p.getDisponivel() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
////        String alterar_completo = "update tb_produtos set completo = '" + p.getCompleto() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
////        String alterar_und_caixa = "update tb_produtos set und_caixa = '" + p.getUnd_caixa() + "'" + "where codigo_produto = '" + p.getCodigo_produto() + "';";
//
//        conexao.execSQL(alterar_preco);
//        conexao.execSQL(alterar_disponivel);
////        conexao.execSQL(alterar_completo);
////        conexao.execSQL(alterar_und_caixa);
//
//    }
//
//
//    /*
//   Transforma todas os produtos da tabela em uma String
//   */
//    public String todos_dados_produtos(){
//        ArrayList<Produto> lista_todos_produtos = new ArrayList<Produto>();
//
//        String string_todos_clientes = "";
//
//
//        lista_todos_produtos = busca_lista_produto();
//
//        for (int i = 0; i < lista_todos_produtos.size(); i++) {
//            string_todos_clientes = string_todos_clientes+"#"+
//                    lista_todos_produtos.get(i).getNome()+"#"+
//                    lista_todos_produtos.get(i).getCodigo_produto()+"#"+
//                    lista_todos_produtos.get(i).getPreco()+"#"+
//                    lista_todos_produtos.get(i).getUnidade()+"#"+
//                    lista_todos_produtos.get(i).getUnd_caixa()+"#"+
//                    lista_todos_produtos.get(i).getCompleto()+"#"+
//                    lista_todos_produtos.get(i).getDisponivel()+"%";
//
//
//        }
//
//        conexao.execSQL(Script_Produtos_Pedido.abreOuCriaTabela_produto_pedido());
//
//
//        return string_todos_clientes+"%";
//
//    }
//
//
//
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
//        String und_caixa = "";
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
//                    Produto p = new Produto(nome, codigo_produto, preco, unidade, completo, und_caixa, disponivel);
//
//                    lista_produtos.add(p);
//
//                    contador_hashtag=0;
//                    //seta todos os atributos pra vazio
//                    atributo = ""; nome = ""; codigo_produto = ""; preco = ""; unidade = ""; completo = ""; und_caixa = ""; disponivel="";
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
//                        und_caixa=atributo;
//                    }
//                    if (contador_hashtag==6) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        completo=atributo;
//                    }
//
//                    if (contador_hashtag==7) {
//                        atributo=atributo+todos_dados_produtos.substring(i, i+1);
//                        disponivel=atributo;
//                    }
//
//                }
//            }
//
//        }//fim do for
//
//        return lista_produtos;
//    }
}
