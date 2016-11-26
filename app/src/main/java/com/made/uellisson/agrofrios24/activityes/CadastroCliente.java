package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.made.uellisson.agrofrios24.R;

/**
 * Created by Uellisson on 26/03/2016.
 *
 * Classe que gerencia a tela de cadastro do cliente, carregada a partir do layout
 * cadastro_cliente.xml
 */
public class CadastroCliente extends Activity implements View.OnClickListener{

    //Declaracao dos componentes da tela
    private EditText et_raz_social_cc;
    private EditText et_fantasia_cc;
    private EditText et_cod_cliente_cc;
    private EditText et_telefone;
    private EditText et_ins_estadual;
    private EditText et_cnpj_cpf;

    private Button bt_proximo;
    private Button bt_voltar;
    ImageButton imageButton_voltar_cc, imageButton_proximo_cc;


    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_cliente);

        et_raz_social_cc = (EditText) findViewById(R.id.et_raz_social_cc);
        et_fantasia_cc = (EditText) findViewById(R.id.et_fantasia_cc);
        et_cod_cliente_cc = (EditText) findViewById(R.id.et_cod_cliente_cc);
        et_telefone = (EditText) findViewById(R.id.et_telefone);
        et_ins_estadual = (EditText) findViewById(R.id.et_ins_estadual);
        et_cnpj_cpf = (EditText) findViewById(R.id.et_cnpj_cpf);

        bt_proximo = (Button) findViewById(R.id.bt_proximo_cc);
        bt_voltar = (Button) findViewById(R.id.bt_voltar_cc);
        imageButton_voltar_cc = (ImageButton) findViewById(R.id.imageButton_voltar_cc);
        imageButton_proximo_cc = (ImageButton) findViewById(R.id.imageButton_proximo_cc);

        bt_proximo.setOnClickListener(this);
        bt_voltar.setOnClickListener(this);
        imageButton_voltar_cc.setOnClickListener(this);
        imageButton_proximo_cc.setOnClickListener(this);
    }

    /**
     * Metodo que gerencia o click nos botoes da tela.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v==bt_proximo || v==imageButton_proximo_cc){
            Intent it = new Intent(this, CadastroCliente_end.class);

            /* Salva o conteudo desta tela antes, para que possam
            ser recuperalos na tela seguinte (CadastroCliente_end). */
            it.putExtra("razao_social", et_raz_social_cc.getText().toString());
            it.putExtra("fantasia", et_fantasia_cc.getText().toString());
            it.putExtra("codigo_cliente", et_cod_cliente_cc.getText().toString());
            it.putExtra("telefone", et_telefone.getText().toString());
            it.putExtra("ins_estadual", et_ins_estadual.getText().toString());
            it.putExtra("cnpj_cpf", et_cnpj_cpf.getText().toString());

            //inicia a proxima tela.
            startActivity(it);
            finish();
        }
        if (v==bt_voltar || v== imageButton_voltar_cc){
            Intent it = new Intent(this, Tela_Gerenciar_Cad.class);
            startActivity(it);
        }

    }

}
