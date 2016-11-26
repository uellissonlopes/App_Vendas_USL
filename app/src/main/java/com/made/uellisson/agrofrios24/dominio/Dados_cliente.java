package com.made.uellisson.agrofrios24.dominio;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.made.uellisson.agrofrios24.database.Gerenciar_Arquivos;
import com.made.uellisson.agrofrios24.database.Script_Produtos_Pedido;
import com.made.uellisson.agrofrios24.database.Scripts_Clientes;
import com.made.uellisson.agrofrios24.modelo.Cliente;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Uellisson on 21/02/2016.
 */
public class Dados_cliente {

    private SQLiteDatabase conexao;
    Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

    public Dados_cliente(SQLiteDatabase conexao){
        this.conexao = conexao;

    }

    /*
        Edita o cadastro de um cliente
        */
    public void editar_cliente(Cliente c) {

        String alterar_razao_social = "update tb_clientes set razao_social = '" + c.getRazao_social() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_fantasia = "update tb_clientes set fantasia = '" + c.getFantasia() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_fone = "update tb_clientes set fone = '" + c.getFone() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_inscricao_est = "update tb_clientes set inscricao_est = '" + c.getInscricao_est() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_cnpj_cpf = "update tb_clientes set cnpj_cpf = '" + c.getCnpj_cpf() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_rua = "update tb_clientes set rua = '" + c.getRua() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_numero = "update tb_clientes set numero = '" + c.getNumero() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_bairro = "update tb_clientes set bairro = '" + c.getBairro() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_cep = "update tb_clientes set cep = '" + c.getCep() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_cidade = "update tb_clientes set cidade = '" + c.getCidade() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";
        String alterar_uf = "update tb_clientes set uf = '" + c.getUf() + "'" + "where codigo_cliente = '" + c.getCodigo_cliente() + "';";


        conexao.execSQL(alterar_razao_social);
        conexao.execSQL(alterar_fantasia);
        conexao.execSQL(alterar_fone);
        conexao.execSQL(alterar_inscricao_est);
        conexao.execSQL(alterar_cnpj_cpf);
        conexao.execSQL(alterar_rua);
        conexao.execSQL(alterar_numero);
        conexao.execSQL(alterar_bairro);
        conexao.execSQL(alterar_cep);
        conexao.execSQL(alterar_cidade);
        conexao.execSQL(alterar_uf);

    }

    /*
        Exclui o cadastro de um cliente
        */
    public void excluir_cliente(String cod_cliente) {
        String deletar_cliente = "DELETE FROM tb_clientes WHERE codigo_cliente = "+cod_cliente;

        conexao.execSQL(deletar_cliente);


    }

    /*
        Exclui o todos os clientes
        */
    public void excluir_cliente() {
        String deletar_cliente = Scripts_Clientes.excuir_todos_clientes();

        conexao.execSQL(deletar_cliente);

    }


    public ArrayAdapter<Cliente> busca_todos_clientes(Context context){
        ArrayAdapter<Cliente> clientes = null;

        Cursor cursor = null;

       //armazena todos os dados
       cursor = conexao.query("tb_clientes", null, null, null, null, null, "fantasia");

        if(cursor.getCount()>0){
            clientes = new ArrayAdapter<Cliente>(context, android.R.layout.simple_list_item_1);

            cursor.moveToFirst();
            do {

                Cliente c = new Cliente();

                c.setRazao_social(cursor.getString(0));
                c.setFantasia(cursor.getString(1));
                c.setCodigo_cliente(cursor.getString(2));
                c.setFone(cursor.getString(3));
                c.setInscricao_est(cursor.getString(4));
                c.setCnpj_cpf(cursor.getString(5));

                c.setRua(cursor.getString(6));
                c.setNumero(cursor.getString(7));
                c.setBairro(cursor.getString(8));
                c.setCep(cursor.getString(9));
                c.setCidade(cursor.getString(10));
                c.setUf(cursor.getString(11));

                clientes.add(c);
            }
            while (cursor.moveToNext());
       }

        return clientes;
    }

    /*
    Retorna um array list com todos os dados dos clientes
     */
    public ArrayList<Cliente> lista_todos_clientes(){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_clientes", null, null, null, "fantasia", null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                Cliente c = new Cliente();

                c.setRazao_social(cursor.getString(0));
                c.setFantasia(cursor.getString(1));
                c.setCodigo_cliente(cursor.getString(2));
                c.setFone(cursor.getString(3));
                c.setInscricao_est(cursor.getString(4));
                c.setCnpj_cpf(cursor.getString(5));

                c.setRua(cursor.getString(6));
                c.setNumero(cursor.getString(7));
                c.setBairro(cursor.getString(8));
                c.setCep(cursor.getString(9));
                c.setCidade(cursor.getString(10));
                c.setUf(cursor.getString(11));

                clientes.add(c);
            }
            while (cursor.moveToNext());
        }

        return clientes;
    }


    /*
    Busca o nome de todas as cidades que tem cleintes cadastrados no app
     */
    public ArrayAdapter<String> busca_todas_cidades(Context context){
        ArrayAdapter<String> todas_cidades = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_clientes", null, null, null, "cidade", null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

               todas_cidades.add(cursor.getString(10));
            }
            while (cursor.moveToNext());
        }

        return todas_cidades;
    }

    public ArrayList<Cliente> busca_cliente_cidade(String cidade){
        ArrayList<Cliente> clientes = new ArrayList<Cliente>();
        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_clientes", null, null, null, null, null, "fantasia");


        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                Cliente c = new Cliente();

                c.setRazao_social(cursor.getString(0));
                c.setFantasia(cursor.getString(1));
                c.setCodigo_cliente(cursor.getString(2));
                c.setFone(cursor.getString(3));
                c.setInscricao_est(cursor.getString(4));
                c.setCnpj_cpf(cursor.getString(5));

                c.setRua(cursor.getString(6));
                c.setNumero(cursor.getString(7));
                c.setBairro(cursor.getString(8));
                c.setCep(cursor.getString(9));
                c.setCidade(cursor.getString(10));
                c.setUf(cursor.getString(11));

                if (cidade.equalsIgnoreCase(c.getCidade())){
                    clientes.add(c);
                }
            }
            while (cursor.moveToNext());
        }

        return clientes;
    }

    /*
    IMPORTACAO E EXPORTAÇAO
     */
 /*
   Transforma todas os pedidos da tabela em uma String
   */
    public String todos_dados_cliente(Context context){
        ArrayList<Cliente> lista_todos_clientes = new ArrayList<Cliente>();

        String string_todos_clientes = "";


        lista_todos_clientes = lista_todos_clientes();


        for (int i = 0; i < lista_todos_clientes.size(); i++) {
            string_todos_clientes = string_todos_clientes+"#"+
                    lista_todos_clientes.get(i).getRazao_social()+"#"+
                    lista_todos_clientes.get(i).getFantasia()+"#"+
                    lista_todos_clientes.get(i).getCodigo_cliente()+"#"+
                    lista_todos_clientes.get(i).getFone()+"#"+
                    lista_todos_clientes.get(i).getInscricao_est()+"#"+
                    lista_todos_clientes.get(i).getCnpj_cpf()+"#"+

                    lista_todos_clientes.get(i).getRua()+"#"+
                    lista_todos_clientes.get(i).getNumero()+"#"+
                    lista_todos_clientes.get(i).getBairro()+"#"+
                    lista_todos_clientes.get(i).getCep()+"#"+
                    lista_todos_clientes.get(i).getCidade()+"#"+
                    lista_todos_clientes.get(i).getUf()+"%";


            }

        conexao.execSQL(Script_Produtos_Pedido.abreOuCriaTabela_produto_pedido());


        return string_todos_clientes+"%";

    }


    /*
     Cria a lista com dados do cliente restaurado de um backup
     */
    public ArrayList<Cliente> dados_clientes_do_backup(String nome_arq) throws IOException {
        ArrayList<Cliente> lista_clientes = new ArrayList<Cliente>();

        String todos_dados_clientes = bk.ler_arquivo(nome_arq);

        String atributo = "";

        String razao_social = "";
        String fantasia = "";
        String codigo_cliente = "";
        String fone = "";
        String inscricao_est = "";
        String cnpj_cpf = "";

        //endereco
        String rua = ""; String numero = "";
        String bairro = ""; String cep = "";
        String cidade = ""; String estado = "";



        int contador_hashtag = 0;

        for (int i = 0; i < todos_dados_clientes.length()-1; i++) {

            if (todos_dados_clientes.substring(i, i+1).equalsIgnoreCase("#")) {
                System.out.println("encontrei um #");
                System.out.println("Valor atribuo: "+atributo);
                atributo = "";
                contador_hashtag+=1;
            }

            else{
                if (todos_dados_clientes.substring(i, i+1).equalsIgnoreCase("%")) {
                    Cliente c = new Cliente(razao_social, fantasia, codigo_cliente,
                            fone, inscricao_est, cnpj_cpf,
                            rua, numero, bairro, cep, cidade, estado);
                    lista_clientes.add(c);

                    System.out.println("Vai para outro objeto################");
                    contador_hashtag=0;
                    //seta todos os atributos pra vazio
                    atributo = ""; razao_social = ""; fantasia = ""; codigo_cliente = "";
                    fone = ""; inscricao_est = ""; cnpj_cpf = "";
                    rua = ""; numero = ""; bairro = ""; cep = ""; cidade = ""; estado = "";
                }
                else{
                    if (contador_hashtag==1) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        razao_social=atributo;
                    }
                    if (contador_hashtag==2) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        fantasia=atributo;
                    }
                    if (contador_hashtag==3) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        codigo_cliente=atributo;
                    }
                    if (contador_hashtag==4) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        fone=atributo;
                    }
                    if (contador_hashtag==5) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        inscricao_est=atributo;
                    }
                    if (contador_hashtag==6) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        cnpj_cpf=atributo;
                    }
                    if (contador_hashtag==7) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        rua=atributo;
                    }
                    if (contador_hashtag==8) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        numero=atributo;
                    }
                    if (contador_hashtag==9) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        bairro=atributo;
                    }
                    if (contador_hashtag==10) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        cep=atributo;

                    }

                    if (contador_hashtag==11) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        cidade=atributo;

                    }

                    if (contador_hashtag==12) {
                        atributo=atributo+todos_dados_clientes.substring(i, i+1);
                        estado=atributo;

                    }
                }
            }

        }//fim do for

        return lista_clientes;

    }//fim do metodo que restaurado a lista de objetos do bakup

//    public void importar_dados_clientes(String nome_arq){
//        ArrayList<Cliente> lista_clientes = dados_clientes_do_backup(nome_arq);
//
//        //cria conexão com db
//        BancoDeDados bd = new BancoDeDados(null);
//        SQLiteDatabase conexao = bd.getReadableDatabase();
//        Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
//        bd.onCreate(conexao);
//
//        for (int i=0; i<lista_clientes.size(); i++){
//
//            //salva dados do cliente
//            salvar_no_db.salvar_cliente(lista_clientes.get(i));
//        }
//
//    }

}
