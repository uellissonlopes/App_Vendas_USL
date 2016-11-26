package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.controle.Controle;
import com.made.uellisson.agrofrios24.modelo.Cliente;

/**
 * Created by Uellisson on 26/03/2016.
 *
 * Classe que gerencia a tela de cadastro do endereco do cliente, carregada a partir do layout
 * cadastro_cliente_end.xml
 */
public class CadastroCliente_end extends Activity implements View.OnClickListener{

    //Declaracao dos componentes da tela
    EditText etRua, etNumero, etBairro, etCep, etCidade, etEstado;
    private Button bt_salvar;
    private Button bt_voltar_cce;
    ImageButton imageButton_voltar_cc_end;


    //Declaracao e inicializacao das variaveis utilizadas
    String razao_social = "", fantasia = "", codigo_cliente = "";
    String telefone = "", inscricao_est = "", cnpj_cpf = "";
    String rua = "", numero = "", bairro = "", cep = "", cidade = "", uf = "";


    //Declaracao dos objetos que serao utilizados na classe.
    Controle controle;
    Cliente cliente;

    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_cliente_end);

        etRua = (EditText) findViewById(R.id.et_Rua_cc);
        etNumero = (EditText) findViewById(R.id.et_Numero_cc);
        etBairro = (EditText) findViewById(R.id.et_Bairro_cc);
        etCep = (EditText) findViewById(R.id.et_Cep_cc);
        etCidade = (EditText) findViewById(R.id.et_Cidade_cc);
        etEstado = (EditText) findViewById(R.id.et_estado_cc);

        bt_salvar = (Button) findViewById(R.id.bt_salvar_ec_end);
        bt_voltar_cce = (Button) findViewById(R.id.bt_voltar_tend);
        imageButton_voltar_cc_end = (ImageButton) findViewById(R.id.imageButton_voltar_cc_end);

        bt_salvar.setOnClickListener(this);
        bt_voltar_cce.setOnClickListener(this);
        imageButton_voltar_cc_end.setOnClickListener(this);

        /*Recuperacao dos dados da tela anterior (CadastroCliente)*/
        Bundle bdl = getIntent().getExtras();
        razao_social = bdl.getString("razao_social");
        fantasia = bdl.getString("fantasia");
        codigo_cliente = bdl.getString("codigo_cliente");
        telefone = bdl.getString("telefone");
        inscricao_est = bdl.getString("ins_estadual");
        cnpj_cpf = bdl.getString("cnpj_cpf");

        //inicailizacao do controle
        controle = new Controle();

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

            /*Inicializacao do objeto cliente, com todos seus atributos*/
            cliente = new Cliente(razao_social, fantasia, codigo_cliente,
                    telefone, inscricao_est, cnpj_cpf,
                    rua, numero, bairro, cep, cidade, uf);


            //salvamos o cliente, chamsndo o metodo do cliente.
            if(controle.salvar_cliente(cliente, this)){
                //mensagem de sucesso
                exibe_mensagem("Cliente Salvo com Sucesso");
            }
            //mensagem de erro
            else{
                exibe_mensagem("ERRO ao tentar salvar o cadastro!");
            }

            //Inicia a Activity anterior, para que o usuario possa cadastrar um novo usuario.
            Intent it = new Intent(this, CadastroCliente.class);
            startActivity(it);
            finish();
        }

        //Gerencia botao voltar
        if (v==bt_voltar_cce || v==imageButton_voltar_cc_end){
            Intent it = new Intent(this, CadastroCliente.class);

            startActivity(it);
            finish();
        }

    }

    /**
     * Gerenciador a exibica de mensagem na tela.
     */
    private void exibe_mensagem(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}

