package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.controle.Controle;
import com.made.uellisson.agrofrios24.modelo.Vendedor;


/**
 * Created by Uellisson on 18/02/2016.
 *
 * Classe que gerencia a tela de configuracoes, carregada a partir do layout
 * configuracoes.xml
 */
public class Configuracoes extends Activity implements View.OnClickListener{


    //Declaracao dos componentes da tela
    EditText et_nome_vendedor_conf, et_codigo_vendedor_conf;
    Button btvoltar_conf, btsalvar_conf, button_excluir_clientes_conf, button_excluir_pedidos_conf;
    ImageButton imagebutton_voltar_conf, imagebutton_excluir_clientes_conf, imagebutton_excluir_pedidos_conf;

    //Declaracao dos objetos que serao utilizados na classe.
    Vendedor vendedor;
    Controle controle;


    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracoes);

        et_nome_vendedor_conf = (EditText) findViewById(R.id.et_nome_vendedor_conf);
        et_codigo_vendedor_conf = (EditText) findViewById(R.id.et_codigo_vendedor_conf);


        btvoltar_conf = (Button) findViewById(R.id.btvoltar_conf);
        btsalvar_conf = (Button) findViewById(R.id.btsalvar_conf);
        button_excluir_clientes_conf = (Button) findViewById(R.id.button_excluir_clientes_conf);
        button_excluir_pedidos_conf = (Button) findViewById(R.id.button_excluir_pedidos_conf);
        imagebutton_voltar_conf = (ImageButton) findViewById(R.id.imagebutton_voltar_conf);
        imagebutton_excluir_clientes_conf = (ImageButton) findViewById(R.id.imagebutton_excluir_clientes_conf);
        imagebutton_excluir_pedidos_conf = (ImageButton) findViewById(R.id.imagebutton_excluir_pedidos_conf);

        button_excluir_pedidos_conf.setOnClickListener(this);
        imagebutton_excluir_pedidos_conf.setOnClickListener(this);
        btvoltar_conf.setOnClickListener(this);
        btsalvar_conf.setOnClickListener(this);
        button_excluir_clientes_conf.setOnClickListener(this);
        imagebutton_voltar_conf.setOnClickListener(this);
        imagebutton_excluir_clientes_conf.setOnClickListener(this);

        //inicializacao do controle
        controle = new Controle();

        carrega_dados_vendedor();
    }


    /**
     * Metodo que gerencia o click nos botoes da tela.
     * @param v
     */
    @Override
    public void onClick(View v) {

        /*Salva um vendedor no banco de dados.*/
        if (v==btsalvar_conf) {
            btsalvar_conf.setEnabled(false);

            String nome_vendedor = et_nome_vendedor_conf.getText().toString();
            String codigo_vendedor = et_codigo_vendedor_conf.getText().toString();

            Vendedor vendedor = new Vendedor(nome_vendedor, codigo_vendedor);

            if (controle.salvar_vendedor(vendedor, this)){
                controle.mensagem_simples(this, "Vendedor cadastrado com SUCESSO!");
            }
            else{
                controle.mensagem_simples(this, "ERRO ao salvar o cadastro!");
            }

            Intent it = new Intent(this, Configuracoes.class);
                startActivity(it);
                finish();

        }
        else{
            if (v==imagebutton_voltar_conf || v==btvoltar_conf){
                Intent it = new Intent(this, TelaPrincipal.class);
                startActivity(it);
                finish();
            }

            /*
            Botoes excluir clientes
             */
            if (v==imagebutton_excluir_clientes_conf || v==button_excluir_clientes_conf){
                button_excluir_clientes_conf.setEnabled(false);
                imagebutton_excluir_clientes_conf.setEnabled(false);

                if (controle.exclui_todos_clientes(this)){
                    controle.mensagem_simples(this, "Todos os clientes exluidos com SUCESSO!");
                }

                else{
                    controle.mensagem_simples(this, "ERRRO ao excluir os cadastros!");
                }

                Intent it = new Intent(this, Configuracoes.class);
                startActivity(it);
                finish();

            }

             /* Botoes excluir pedidos. */
            if (v==imagebutton_excluir_pedidos_conf || v==button_excluir_pedidos_conf){
                imagebutton_excluir_pedidos_conf.setEnabled(false);
                button_excluir_pedidos_conf.setEnabled(false);

                if (controle.exclui_todos_pedidos(this)){
                    controle.mensagem_simples(this, "Todos os pedidos foram exluidos com SUCESSO!");
                }

                else{
                    controle.mensagem_simples(this, "ERRO ao excluir os pedidos");
                }
            }
        }
    }

    /**
     *carrega dados do vendedor na tela
     */
    public void carrega_dados_vendedor(){
        vendedor = controle.buscar_vendedor(this);

        if (vendedor!=null){
            et_nome_vendedor_conf.setText(vendedor.getNome_vendedor());
            et_codigo_vendedor_conf.setText(vendedor.getCodigo_vendedor());
        }
        else {
            controle.mensagem_simples(this, "Cadastre um vendedor!");
        }

    }

}
