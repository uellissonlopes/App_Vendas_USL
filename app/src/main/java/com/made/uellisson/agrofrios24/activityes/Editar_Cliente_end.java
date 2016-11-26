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
import com.made.uellisson.agrofrios24.modelo.Cliente;

/**
 * Created by Uellisson on 26/03/2016.
 *
 * Classe que gerencia a tela Editar clientes, carregada a partir do layout
 * editar_cliente_end.xml
 *
 */
public class Editar_Cliente_end extends Activity implements View.OnClickListener{

    //Declaracao dos componentes da tela
    private EditText etRua, etNumero, etBairro, etCep, etCidade, etEstado;
    private Button bt_salvar;
    private Button bt_voltar_cce;
    private ImageButton imageButton_voltar_cc_end;

    //declaracao e inicializacas das variavies usadas
    String razao_social = "", fantasia = "", codigo_cliente = "";
    String telefone = "", inscricao_est = "", cnpj_cpf = "";
    String rua = "", numero = "", bairro = "", cep = "", cidade = "", uf = "";

    //Declaracao do objeto controle utilizado na classe.
    Controle controle;


    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_cliente_end);

        etRua = (EditText) findViewById(R.id.et_Rua_ec);
        etNumero = (EditText) findViewById(R.id.et_Numero_ec);
        etBairro = (EditText) findViewById(R.id.et_Bairro_ec);
        etCep = (EditText) findViewById(R.id.et_Cep_ec);
        etCidade = (EditText) findViewById(R.id.et_Cidade_ec);
        etEstado = (EditText) findViewById(R.id.et_estado_ec);

        bt_salvar = (Button) findViewById(R.id.bt_salvar_ec_end);
        bt_voltar_cce = (Button) findViewById(R.id.bt_voltar_ec_end);
        imageButton_voltar_cc_end = (ImageButton) findViewById(R.id.imageButton_voltar_ec_end);

        bt_salvar.setOnClickListener(this);
        bt_voltar_cce.setOnClickListener(this);
        imageButton_voltar_cc_end.setOnClickListener(this);

        //inicializacao do controle
        controle = new Controle();

        //carregamento dos dados vindos da outra editar cliente
        Bundle bdl = getIntent().getExtras();
        razao_social = bdl.getString("razao_social");
        fantasia = bdl.getString("fantasia");
        codigo_cliente = bdl.getString("codigo_cliente");
        telefone = bdl.getString("telefone");
        inscricao_est = bdl.getString("ins_estadual");
        cnpj_cpf = bdl.getString("cnpj_cpf");

        etRua.setText(bdl.getString("rua"));
        etNumero.setText(bdl.getString("numero"));
        etBairro.setText(bdl.getString("bairro"));
        etCidade.setText(bdl.getString("cidade"));
        etCep.setText(bdl.getString("cep"));
        etEstado.setText(bdl.getString("estado"));

    }

    /**
     * Metodo que gerencia o click nos botoes da tela.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v==bt_salvar){
            bt_salvar.setEnabled(false);

            rua = etRua.getText().toString();
            numero = etNumero.getText().toString();
            bairro = etBairro.getText().toString();
            cep = etCep.getText().toString();
            cidade = etCidade.getText().toString();
            uf = etEstado.getText().toString();

            Cliente c = new Cliente(razao_social, fantasia, codigo_cliente,
                    telefone, inscricao_est, cnpj_cpf,
                    rua, numero, bairro, cep, cidade, uf);

            //chama o editar e exibe mensagem de sucesso
            if (controle.editar_cliente(c, this)){
                 controle.mensagem_simples(this, "Cliente editado com Sucesso!");
            }
            else{
                controle.mensagem_simples(this, "ERRO ao editar cliente!");
            }

            Intent it = new Intent(this, Editar_Cliente.class);
            startActivity(it);
            finish();

        }
        if (v==bt_voltar_cce || v==imageButton_voltar_cc_end){
            Intent it = new Intent(this, Editar_Cliente.class);

            startActivity(it);
            finish();
        }

    }

}

