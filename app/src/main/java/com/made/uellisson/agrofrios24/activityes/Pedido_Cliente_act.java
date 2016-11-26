package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Dados_cliente;
import com.made.uellisson.agrofrios24.modelo.Cliente;

import java.util.ArrayList;

/**
 * Created by Uellisson on 26/03/2016.
 */
public class Pedido_Cliente_act extends Activity implements View.OnClickListener{

    private Spinner spinner_clientes;
    private Spinner spinner_pagamento_pedido;
    private Spinner spinner_cidade;

    private ArrayAdapter<String> clientes_da_cidade;
    private ArrayAdapter<String> tipos_pagamento;
    private ArrayAdapter<String> todas_cidades;

    private ArrayAdapter<Cliente> lista_todos_clientes;
    private ArrayList<Cliente> lista_clientes_cidade;

    //private EditText et_desconto_pedido_cli;
    private EditText et_raz_social_cc;
    private EditText et_cod_cliente_cc;
    private EditText et_cnpj_cpf;

    private Button bt_proximo;
   // private Button bt_proximo_desc;
    private Button bt_voltar;

    ImageButton imageButton_voltar_pc, imageButton_proximo_pc;

    BancoDeDados bd;
    SQLiteDatabase conexao;
    Dados_cliente dados_cliente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_cliente);

        spinner_clientes = (Spinner) findViewById(R.id.spinner_clientes);
        spinner_pagamento_pedido = (Spinner) findViewById(R.id.spinner_pagamento_pedido);
        spinner_cidade = (Spinner) findViewById(R.id.spinner_cidade);

       // et_desconto_pedido_cli = (EditText) findViewById(R.id.et_desconto_pedido_cli);
        et_raz_social_cc = (EditText) findViewById(R.id.et_razao_social_cp);
        et_cod_cliente_cc = (EditText) findViewById(R.id.et_cod_cliente_cp);
        et_cnpj_cpf = (EditText) findViewById(R.id.et_cnpj_cpf_cp);

        bt_proximo = (Button) findViewById(R.id.bt_proximo_bc);
        //bt_proximo_desc = (Button) findViewById(R.id.bt_proximo_desc);
        bt_voltar = (Button) findViewById(R.id.bt_voltar_bc);
        imageButton_voltar_pc = (ImageButton) findViewById(R.id.imageButton_voltar_pc);
        imageButton_proximo_pc = (ImageButton) findViewById(R.id.imageButton_proximo_pc);

        bt_proximo.setOnClickListener(this);
        bt_voltar.setOnClickListener(this);
        imageButton_voltar_pc.setOnClickListener(this);
        imageButton_proximo_pc.setOnClickListener(this);

        clientes_da_cidade = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        tipos_pagamento = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        todas_cidades = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        tipos_pagamento.add("A vista");
        tipos_pagamento.add("Boleto 7 dias");
        tipos_pagamento.add("Boleto 14 dias");
        tipos_pagamento.add("Boleto 21 dias");
        tipos_pagamento.add("Boleto 28 dias");
        tipos_pagamento.add("Cheque");



        spinner_pagamento_pedido.setAdapter(tipos_pagamento);

        try {
            //cria conexao com Banco de dados
            bd = new BancoDeDados(this);
            conexao = bd.getReadableDatabase();
            dados_cliente = new Dados_cliente(conexao);


            /*
            Preenche o spinner com todas as cidades
             */
            todas_cidades = dados_cliente.busca_todas_cidades(this);
            todas_cidades.add("Todas");
            spinner_cidade.setAdapter(todas_cidades);

            /*
            Busca todos os clientes no banco
             */
            lista_todos_clientes = dados_cliente.busca_todos_clientes(this);

            //Evento que muda os edittext de acordo como os clientes selecionados
            spinner_cidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (spinner_cidade.getSelectedItem()!=null){
                        if (spinner_cidade.getSelectedItem().toString().equalsIgnoreCase("Todas")){
                            clientes_da_cidade.clear();
                            for (int i=0; i<lista_todos_clientes.getCount(); i++){
                                if (lista_todos_clientes.getItem(i).getFantasia()!=null){
                                    clientes_da_cidade.add(lista_todos_clientes.getItem(i).getFantasia());
                                }
                            }
                            spinner_clientes.setAdapter(clientes_da_cidade);

                        }

                        else{
                            clientes_da_cidade.clear();

                            lista_clientes_cidade = dados_cliente.busca_cliente_cidade(spinner_cidade.getSelectedItem().toString());

                            for (int i=0; i<lista_clientes_cidade.size(); i++){
                                clientes_da_cidade.add(lista_clientes_cidade.get(i).getFantasia());
                            }
                            spinner_clientes.setAdapter(clientes_da_cidade);

                        }

                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Evento que muda os edittext de acordo como os clientes selecionados
            spinner_clientes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        if (lista_clientes_cidade.size()>position){
                            // i = spinner_bc_fantasia.getSelectedItemPosition();
                            et_cod_cliente_cc.setText(lista_clientes_cidade.get(position).getCodigo_cliente().toString());
                            et_raz_social_cc.setText(lista_clientes_cidade.get(position).getRazao_social().toString());
                            et_cnpj_cpf.setText(lista_clientes_cidade.get(position).getCnpj_cpf().toString());
                        }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        //Gerencia as excecoes
        catch (SQLException erro){
            AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
            mensagem.setMessage("Erro: "+erro.getMessage());
            mensagem.setPositiveButton("Ok", null);
            mensagem.show();

        }
    }

    @Override
    public void onClick(View v) {

        if (v==bt_proximo || v == imageButton_proximo_pc){
            //cria o itent para chamar proxima tela
            Intent it = new Intent(this, Pedido_Produto_act.class);

            String cliente_ft="";
            if (spinner_clientes.getSelectedItem()!=null){
                cliente_ft = spinner_clientes.getSelectedItem().toString();
            }

            it.putExtra("cliente_fantasia", cliente_ft);
            it.putExtra("tipo_pagamento",  spinner_pagamento_pedido.getSelectedItem().toString());
            it.putExtra("codigo_cliente", et_cod_cliente_cc.getText().toString());

            startActivity(it);//inicia proxima tela
            finish();
        }//if proximo

        if (v==bt_voltar || v==imageButton_voltar_pc){
            Intent it = new Intent(this, TelaPrincipal.class);
            startActivity(it);
            finish();
        }


    }

    /**
     * Gerenciador de Mensagens
     */
    public void mensagemExibir(String titulo, String texto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("OK", null);
        mensagem.show();
    }

}
