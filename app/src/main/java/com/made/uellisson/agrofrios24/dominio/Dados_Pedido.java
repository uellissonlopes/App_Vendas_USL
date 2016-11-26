package com.made.uellisson.agrofrios24.dominio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import com.made.uellisson.agrofrios24.controle.Controle;
import com.made.uellisson.agrofrios24.database.Script_Pedido;
import com.made.uellisson.agrofrios24.database.Script_Produtos_Pedido;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;

import java.util.ArrayList;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Dados_Pedido {

    private SQLiteDatabase conexao;

    public Dados_Pedido(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    Dados_Produtos_pedido dpp = new Dados_Produtos_pedido(conexao);


    /*
    Busca um pedido pelo nomo do cliente
     */
    public ArrayAdapter<Pedido> busca_pedidos(Context context){
        ArrayAdapter<Pedido> pedidos = null;
        Dados_Produtos_pedido dpp = new Dados_Produtos_pedido(conexao);
        ArrayAdapter<String> clientes_pedido = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1);

        ArrayList<Pedido> pedidos_mesmo_cliente = new ArrayList<Pedido>();

        Cursor cursor = null;
        Controle ct = new Controle();

        //armazena todos os dados
        cursor = conexao.query("tb_pedidos", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            pedidos = new ArrayAdapter<Pedido>(context, android.R.layout.simple_list_item_1);
            cursor.moveToFirst();

            do {

                String id_pedido = cursor.getString(0);
                String cliente_fantasia = cursor.getString(1);
                String codigo_cliente = cursor.getString(2);
                String tipo_pagamento = cursor.getString(3);
                String data_atual = cursor.getString(4);
                String produtos = cursor.getString(5);
                String valor_total = cursor.getString(6);
                String desconto = cursor.getString(7);

                ArrayAdapter<Produtos_pedido> adpp = dpp.busca_produtos_pedidos_id(id_pedido, context, conexao);

                Pedido pedido = new Pedido(id_pedido, cliente_fantasia, codigo_cliente, tipo_pagamento,
                        data_atual, adpp, valor_total, desconto);

                boolean tem_esse=false;
                int indice_do_que_tem = Integer.MAX_VALUE;

                if (pedidos_mesmo_cliente.size()>0){
                    for (int i=0; i<pedidos_mesmo_cliente.size(); i++){
                        if (cliente_fantasia.equals(pedidos_mesmo_cliente.get(i).getCliente_fantasia())){
                            tem_esse=true;
                            indice_do_que_tem = i;
                        }
                    }

                    if (tem_esse==true){
                        pedido.setCliente_fantasia(cliente_fantasia + " - Pedido: " + ct.converter_data_para_BR(data_atual));
                        pedidos_mesmo_cliente.add(pedido);
                    }
                    else{
                        pedidos_mesmo_cliente.add(pedido);
                    }
                }
                else{
                    pedidos_mesmo_cliente.add(pedido);
                }
            }

            while (cursor.moveToNext());
        }

        if (pedidos!=null){
            pedidos.addAll(pedidos_mesmo_cliente);
        }


        return pedidos;
    }



    public ArrayList<Pedido> busca_pedidos(String cliente){
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_pedidos", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String id_pedido = cursor.getString(0);
                String cliente_fantasia = cursor.getString(1);
                String codigo_cliente = cursor.getString(2);
                String tipo_pagamento = cursor.getString(3);
                String data_atual = cursor.getString(4);
                String produtos = cursor.getString(5);
                String valor_total = cursor.getString(6);
                String desconto = cursor.getString(7);



            }
            while (cursor.moveToNext());
        }

        return pedidos;
    }


    /*
   Altera o cadastro de um produto
   */
    public void alterar_pedido(Pedido pedido, ArrayList<Produtos_pedido> lista_produtos_pedido) {
        String alterar_preco = "update tb_pedidos set tipo_pagamento = '" + pedido.getTipo_pagamento() + "'" + "where id_pedido = '" + pedido.getId_pedido()+ "';";
        String alterar_data = "update tb_pedidos set data_atual = '" + pedido.getData_atual() + "'" + "where id_pedido = '" + pedido.getId_pedido()+ "';";
        String alterar_valor_total = "update tb_pedidos set valor_total = '" + pedido.getValor_total() + "'" + "where id_pedido = '" + pedido.getId_pedido()+ "';";
        String alterar_desconto = "update tb_pedidos set desconto = '" + pedido.getDesconto() + "'" + "where id_pedido = '" + pedido.getId_pedido()+ "';";

        //Sript para deletar todos os produts do pedido anterior
        String deletar_produtos_pedido = "DELETE FROM tb_produtos_pedido WHERE id_pedido = "+pedido.getId_pedido();

        //Execucao dos scipts de alteracao e exclusao
        conexao.execSQL(alterar_preco);
        conexao.execSQL(alterar_data);
        conexao.execSQL(alterar_valor_total);
        conexao.execSQL(alterar_desconto);
        conexao.execSQL(deletar_produtos_pedido);


        //For para salvar produtos do novo pedido
        for (int i=0; i<lista_produtos_pedido.size(); i++){
            String comando_salvar_pp = Script_Produtos_Pedido.salvar_Produto_pedido(lista_produtos_pedido.get(i));
            conexao.execSQL(comando_salvar_pp);
        }

    }



    public String busca_id_pedido(){
        //ArrayAdapter<Pedido> pedidos = new ArrayAdapter<Pedido>(context, android.R.layout.simple_list_item_1);

        String id_ultimo_pedido = null;
        Cursor cursor = null;

        conexao.execSQL(Script_Pedido.abreOuCriaTabela_pedidos());

        //armazena todos os dados
        cursor = conexao.query("tb_pedidos", null, null, null, null, null, null);

       cursor.moveToLast();

        Integer id_pedido = 0;
        if(cursor.getCount()>0) {
            id_ultimo_pedido = cursor.getString(0);


            if (id_ultimo_pedido == null) {
                return "1";
            } else {
                id_pedido = Integer.parseInt(id_ultimo_pedido) + 1;
            }
        }
        else{
            return "1";
        }

        return id_pedido.toString();
    }

    /*
       Exclui um pedido e seus produtos que tem um codigo igual ao passado como parametro
       */
    public void excluir_pedido(String id_pedido) {
        String deletar_pedido = "DELETE FROM tb_pedidos WHERE id_pedido = "+id_pedido;
        String deletar_produtos_pedido = "DELETE FROM tb_produtos_pedido WHERE id_pedido = "+id_pedido;

        conexao.execSQL(deletar_pedido);
        conexao.execSQL(deletar_produtos_pedido);
    }


    /*
      Exclui todos os pedidos e seus produtos      */
    public void excluir_todos_pedidos() {
        String deletar_pedido = "DELETE FROM tb_pedidos";
        String deletar_produtos_pedido = "DELETE FROM tb_produtos_pedido";

        conexao.execSQL(deletar_pedido);
        conexao.execSQL(deletar_produtos_pedido);
    }


    //####################----BACKUP---#################

    /*
    Busca os produtos e retorna eles em um arraylist
     */
    public ArrayList<Pedido> busca_lista_pedidos(){
        ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

        Cursor cursor = null;

        //armazena todos os dados
        cursor = conexao.query("tb_pedidos", null, null, null, null, null, null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do {

                String id_pedido = cursor.getString(0);
                String cliente_fantasia = cursor.getString(1);
                String codigo_cliente = cursor.getString(2);
                String tipo_pagamento = cursor.getString(3);
                String data_atual = cursor.getString(4);
                String produtos = cursor.getString(5);
                String valor_total = cursor.getString(6);
                String desconto = cursor.getString(7);

                Pedido pedido = new Pedido(id_pedido, cliente_fantasia, codigo_cliente, tipo_pagamento,
                                            data_atual, produtos, valor_total, desconto);

                pedidos.add(pedido);
            }
            while (cursor.moveToNext());
        }

        return pedidos;
    }

    /*
   Transforma todas os pedidos da tabela em uma String
   */
    public String todos_dados_pedido(Context context){
        ArrayList<Pedido> lista_todos_pedidos = new ArrayList<Pedido>();

        String string_todos_pedidos = "";
//        String codigo_pedido_ini="0";
//        String codigo_pedido_fim="0";


        lista_todos_pedidos = busca_lista_pedidos();
//        codigo_pedido_ini = lista_todos_pedidos.get(0).getId_pedido();
//        codigo_pedido_fim = lista_todos_pedidos.get(lista_todos_pedidos.size()-1).getId_pedido();



        for (int i = 0; i < lista_todos_pedidos.size(); i++) {
            string_todos_pedidos = string_todos_pedidos+"#"+lista_todos_pedidos.get(i).getId_pedido()+"#"+
                    lista_todos_pedidos.get(i).getCliente_fantasia()+"#"+
                    lista_todos_pedidos.get(i).getCodigo_cliente()+"#"+
                    lista_todos_pedidos.get(i).getTipo_pagamento()+"#"+
                    lista_todos_pedidos.get(i).getData_atual()+"#"+
                    lista_todos_pedidos.get(i).getValor_total()+"#"+
                    lista_todos_pedidos.get(i).getDesconto()+"%";
        }

        conexao.execSQL(Script_Produtos_Pedido.abreOuCriaTabela_produto_pedido());

        String todos_dados_produtos_pedido = dpp.todos_dados_produtos_pedido(context, conexao);

        return string_todos_pedidos+"@"+todos_dados_produtos_pedido+"%";

    }
}
