package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.controle.Controle;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;

import java.util.ArrayList;


/**
 * Created by Uellisson on 28/02/2016.
 *
 * Classe que gerencia a tela Editar clientes, carregada a partir do layout
 * editar_cliente.xml
 */
public class Editar_Pedido_Cliente extends Activity implements View.OnClickListener{

    //Declaracao dos componentes da tela
    private Spinner spinner_cliente_ap;
    private EditText et_data_pedido_ap, editText_tipo_pagamento_ap, et_desconto_ap, et_cod_cliente_ap;
    private Button bt_proximo_desc_ap, bt_voltar_ap, bt_proximo_ap;
    private ImageButton imageButton_voltar_ap, imageButton_peoximo_ap;


    //Declaracao do array adapter que armazena o nome dos clientes que tem alguma pedido
    private ArrayAdapter<String> clientes_pedido;
    ArrayList<Produtos_pedido> produtos_pedido_cliente;

    //Declaracao e inicializacao dos ArrayList que armazenarao dodos sobe os produtos
    ArrayList<String> lista_codigo_produto_list_view = null;
    ArrayList<String> lista_qtd_produto_list_view = null;
    ArrayList<String> lista_nome_produto_list_view = null;
    ArrayList<String> lista_preco_produto_list_view = null;

    //Declaracao do array adapter que armazena os dados do pedido de um cliente selecionado
    private ArrayAdapter<Pedido> pedidos;

    //Declaracao e inicializacao de variaveis utilizadas na classe
    String total_pedido="0.0";
    String id_pedido = "0";
    String desconto = "0";

    //Declaracao do objeto controle utilizado na classe.
    Controle controle;



    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_pedido_cliente);

        spinner_cliente_ap = (Spinner) findViewById(R.id.spinner_cliente_ap);

        et_data_pedido_ap = (EditText) findViewById(R.id.et_data_pedido_ap);
        editText_tipo_pagamento_ap = (EditText) findViewById(R.id.editText_tipo_pagamento_ap);
        et_desconto_ap = (EditText) findViewById(R.id.et_desconto_ap);
        et_cod_cliente_ap = (EditText) findViewById(R.id.et_cod_cliente_ap);

       // bt_proximo_desc_ap = (Button) findViewById(R.id.bt_proximo_desc_ap);
        bt_voltar_ap = (Button) findViewById(R.id.bt_voltar_ap);
        bt_proximo_ap = (Button) findViewById(R.id.bt_proximo_ap);
        imageButton_voltar_ap = (ImageButton) findViewById(R.id.imageButton_voltar_ap);
        imageButton_peoximo_ap = (ImageButton) findViewById(R.id.imageButton_peoximo_ap);

       // bt_proximo_desc_ap.setOnClickListener(this);
        bt_voltar_ap.setOnClickListener(this);
        bt_proximo_ap.setOnClickListener(this);
        imageButton_voltar_ap.setOnClickListener(this);
        imageButton_peoximo_ap.setOnClickListener(this);

        //inicializacao do controle
        controle = new Controle();


        produtos_pedido_cliente = new ArrayList<Produtos_pedido>();
        clientes_pedido = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        pedidos = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1);


        //busca pedidos no banco de dados e e insere no array adapter
        if (controle.buscar_todos_pedidos(this)!=null){
            pedidos = controle.buscar_todos_pedidos(this);
            preenche_spinner_produtos();
        }
        //se não tiver pedidos avisa ao cliente
        else{
            controle.mensagem_simples(this, "Não existem pedidos salvos!");
        }

    }


    /**
     * Metodo que gerencia o click nos botoes da tela.
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v==imageButton_peoximo_ap || v==bt_proximo_ap){

            //salva todos os dados que serao usandos na tela seguinta
            Intent it = new Intent(this, Editar_Pedido_Produto_up.class);

            it.putStringArrayListExtra("lista_codigo_produto_list_view", lista_codigo_produto_list_view);
            it.putStringArrayListExtra("lista_qtd_produto_list_view", lista_qtd_produto_list_view);
            it.putStringArrayListExtra("lista_nome_produto_list_view", lista_nome_produto_list_view);
            it.putStringArrayListExtra("lista_preco_produto_list_view", lista_preco_produto_list_view);

            it.putExtra("id_pedido", id_pedido);
            it.putExtra("desconto", desconto);
            it.putExtra("cliente_fantasia", spinner_cliente_ap.getSelectedItem().toString());
            it.putExtra("tipo_pagamento", editText_tipo_pagamento_ap.getText().toString());
            it.putExtra("codigo_cliente", et_cod_cliente_ap.getText().toString());
            it.putExtra("total_pedido", total_pedido);
            it.putExtra("data_pedido", controle.data_atual());

            startActivity(it);
            finish();
        }

        if (v==bt_voltar_ap || v==imageButton_voltar_ap){
            Intent it = new Intent(this, Tela_Gerenciar_Pedidos.class);
            startActivity(it);
            finish();
        }

    }

    /**
     * Insere os pdodutos do banco de dados no spinner produtos
     *
     */
    private void preenche_spinner_produtos(){

        try {

            for (int i=0; i<pedidos.getCount(); i++){
                clientes_pedido.add(pedidos.getItem(i).getCliente_fantasia());
            }

            spinner_cliente_ap.setAdapter(clientes_pedido);

            //Evento que muda os edittext de acordo como os produtos selecionados
            spinner_cliente_ap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ArrayAdapter<Produtos_pedido> adp_produtos_pedido_cliente = null;

                    lista_codigo_produto_list_view = new ArrayList<String>();
                    lista_qtd_produto_list_view = new ArrayList<String>();
                    lista_nome_produto_list_view = new ArrayList<String>();
                    lista_preco_produto_list_view = new ArrayList<String>();

                    lista_codigo_produto_list_view.clear();
                    lista_qtd_produto_list_view.clear();
                    lista_nome_produto_list_view.clear();
                    lista_preco_produto_list_view.clear();

                    produtos_pedido_cliente.clear();

                    for (int i=0; i<pedidos.getCount(); i++){

                        if (pedidos.getItem(i).getCliente_fantasia().equalsIgnoreCase(spinner_cliente_ap.getSelectedItem().toString())){
                            adp_produtos_pedido_cliente = pedidos.getItem(i).getAdpp();

                        }
                    }

                    for (int i=0; i<adp_produtos_pedido_cliente.getCount(); i++){
                        produtos_pedido_cliente.add(adp_produtos_pedido_cliente.getItem(i));
                    }



                    if (produtos_pedido_cliente!=null){

                        for (int i=0; i<produtos_pedido_cliente.size(); i++){
                            lista_codigo_produto_list_view.add(produtos_pedido_cliente.get(i).getCodigo_produto().toString());
                            lista_qtd_produto_list_view.add(produtos_pedido_cliente.get(i).getQuantidade().toString());
                            lista_nome_produto_list_view.add(produtos_pedido_cliente.get(i).getNome_produto().toString());
                            lista_preco_produto_list_view.add(produtos_pedido_cliente.get(i).getPreco_total().toString());
                       }

                    }//fim di if

                    id_pedido = pedidos.getItem(position).getId_pedido();
                    desconto = pedidos.getItem(position).getDesconto();

                    String data_convert= pedidos.getItem(position).getData_atual();
                    data_convert = controle.converter_data_para_BR(data_convert);

                    et_data_pedido_ap.setText(data_convert);
                    editText_tipo_pagamento_ap.setText(pedidos.getItem(position).getTipo_pagamento());
                    et_desconto_ap.setText(desconto);
                    et_cod_cliente_ap.setText(pedidos.getItem(position).getCodigo_cliente());
                    total_pedido = pedidos.getItem(position).getValor_total();

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }//fim do try

        //Gerencia as exceções
        catch (SQLException erro){
            controle.mensagem_simples(this, "Erro ao preencher lista de pedidos!");

        }
    }

}
