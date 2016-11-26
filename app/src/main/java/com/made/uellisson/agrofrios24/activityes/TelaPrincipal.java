package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.controle.Controle;

/**
 * Created by Uellisson on 26/03/2016.
 */
public class TelaPrincipal extends Activity implements View.OnClickListener{

    EditText et_fantasia_tp;
    String arquivo = "images.png";//"pedidos_app_ok-04-06.bp";
    /**
     * Declaração de variáveis
     */
    Button bt_fazer_vendas, bt_pedido, bt_gerenciar_cad, bt_config, bt_tela_principal_sair;
    ImageButton ib_fazer_vendas, ib_pedidos, ib_gerenciar_cad, ib_config;
    TextView data_atual_tp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telaprincipal_moderna);

        //inicialização dos Imagebuttons
        ib_fazer_vendas = (ImageButton) findViewById(R.id.ib_fazer_vendas);
        ib_pedidos = (ImageButton) findViewById(R.id.ib_pedidos);
        ib_gerenciar_cad = (ImageButton) findViewById(R.id.ib_gerenciar_cad);
        ib_config = (ImageButton) findViewById(R.id.ib_config);

        //inicialização dos buttons
        bt_fazer_vendas = (Button) findViewById(R.id.bt_fazer_vendas);
        bt_pedido = (Button) findViewById(R.id.bt_pedido);
        bt_gerenciar_cad = (Button) findViewById(R.id.bt_gerenciar_cad);
        bt_config = (Button) findViewById(R.id.bt_config);
        bt_tela_principal_sair = (Button) findViewById(R.id.bt_tela_principal_sair);

        bt_fazer_vendas.setOnClickListener(this);
        bt_pedido.setOnClickListener(this);
        bt_gerenciar_cad.setOnClickListener(this);
        bt_config.setOnClickListener(this);
        bt_tela_principal_sair.setOnClickListener(this);
        ib_fazer_vendas.setOnClickListener(this);
        ib_pedidos.setOnClickListener(this);
        ib_gerenciar_cad.setOnClickListener(this);
        ib_config.setOnClickListener(this);


        //Configuração da data atual que aparece na tela principal
        data_atual_tp = (TextView) findViewById(R.id.data_atual_tp);

        Controle ct = new Controle();
        String data_string = ct.data_atual();
        data_string = ct.converter_data_para_BR(data_string);

        data_atual_tp.setText("Boas vendas! " + data_string);

        //cria todas as tabelas do banco de dados
        ct.criar_tabelas_db(this);

    }

    @Override
    public void onClick(View v) {

        if (v==bt_fazer_vendas || v==ib_fazer_vendas){
            Intent it = new Intent(this, Pedido_Cliente_act.class);
            startActivity(it);
            //finish();
        }
        if (v==bt_pedido || v==ib_pedidos){
            Intent it = new Intent(this, Tela_Gerenciar_Pedidos.class);
            startActivity(it);
            //finish();
        }
        if (v==ib_gerenciar_cad || v==bt_gerenciar_cad){
            Intent it = new Intent(this, Tela_Gerenciar_Cad.class);
            startActivity(it);
           // finish();
        }
        if (v==ib_config || v==bt_config){
            Intent it = new Intent(this, Configuracoes.class);
            startActivity(it);
           // finish();
        }
        if (v==bt_tela_principal_sair || v==bt_tela_principal_sair){
            finish();
        }


    }



}