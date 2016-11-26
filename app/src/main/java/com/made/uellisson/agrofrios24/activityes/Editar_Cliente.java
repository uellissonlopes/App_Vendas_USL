package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
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
import com.made.uellisson.agrofrios24.modelo.Cliente;

/**
 * Created by Uellisson on 26/03/2016.
 *
 * Classe que gerencia a tela Editar clientes, carregada a partir do layout
 * editar_cliente.xml
 */
public class Editar_Cliente extends Activity implements View.OnClickListener{

    //Declaracao dos componentes da tela
    private Spinner spinner_cliente_ep;
    private EditText et_raz_social;
    private EditText et_cod_cliente;
    private EditText et_telefone;
    private EditText et_ins_estadual;
    private EditText et_cnpj_cpf;
    private Button bt_proximo;
    private Button bt_voltar, button_excuir_cliente;
    ImageButton imageButton_voltar, imageButton_proximo;

    //Declaracao das variaveis e objetos que serao utilizados na classe.
    private ArrayAdapter<String> adp_clientes;
    private ArrayAdapter<Cliente> lista_todos_clientes;
    Controle controle;

    //Dados do endereco que vao pra outra tela
    String rua, numero, bairro, cep, cidade, estado;


    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_cliente);

        spinner_cliente_ep = (Spinner) findViewById(R.id.spinner_cliente_ec);
        et_raz_social = (EditText) findViewById(R.id.et_raz_social_ep);
        et_cod_cliente = (EditText) findViewById(R.id.et_cod_cliente_ep);
        et_telefone = (EditText) findViewById(R.id.et_telefone_ep);
        et_ins_estadual = (EditText) findViewById(R.id.et_ins_estadual_ep);
        et_cnpj_cpf = (EditText) findViewById(R.id.et_cnpj_cpf_ep);

        bt_proximo = (Button) findViewById(R.id.bt_proximo_ec);
        bt_voltar = (Button) findViewById(R.id.bt_voltar_ec);
        imageButton_voltar = (ImageButton) findViewById(R.id.imageButton_voltar_ec);
        imageButton_proximo = (ImageButton) findViewById(R.id.imageButton_proximo_ec);

        button_excuir_cliente = (Button) findViewById(R.id.button_excuir_cliente);
        button_excuir_cliente.setOnClickListener(this);


        bt_proximo.setOnClickListener(this);
        bt_voltar.setOnClickListener(this);
        imageButton_voltar.setOnClickListener(this);
        imageButton_proximo.setOnClickListener(this);

        adp_clientes = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //Inicializacao do controle
        controle = new Controle();

        /*Busca todos os clientes no banco e inicializa lista
        e chama os metodos que gerenciam a edicao do cliente.  */
        if (controle.buscar_todos_clientes(this)!=null){
            lista_todos_clientes = controle.buscar_todos_clientes(this);
            carregar_clientes();
            mudar_cliente_selecionado();
        }

        //caso nao existam clientes salvos, exibe mensagem para o usuario.
        else{
            controle.mensagem_simples(this, "Nenhum cliente cadastrado!");
        }

    }

    /**
     * Metodo que gerencia o click nos botoes da tela.
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v==bt_proximo || v==imageButton_proximo){
            Intent it = new Intent(this, Editar_Cliente_end.class);

            /* Salva o conteudo desta tela antes, para que possam
            ser recuperalos na tela seguinte (CadastroCliente_end). */
            it.putExtra("razao_social", et_raz_social.getText().toString());
            it.putExtra("fantasia", spinner_cliente_ep.getSelectedItem().toString());
            it.putExtra("codigo_cliente", et_cod_cliente.getText().toString());
            it.putExtra("telefone", et_telefone.getText().toString());
            it.putExtra("ins_estadual", et_ins_estadual.getText().toString());
            it.putExtra("cnpj_cpf", et_cnpj_cpf.getText().toString());

            it.putExtra("rua", rua);
            it.putExtra("numero", numero);
            it.putExtra("bairro", bairro);
            it.putExtra("cep", cep);
            it.putExtra("cidade", cidade);
            it.putExtra("estado", estado);

            startActivity(it);
            finish();
        }
        if (v==bt_voltar || v== imageButton_voltar){
            Intent it = new Intent(this, Tela_Gerenciar_Cad.class);
            startActivity(it);
            finish();
        }

        if (v==button_excuir_cliente){

            String codigo_clientes = et_cod_cliente.getText().toString();

            if (controle.excluir_cliente(codigo_clientes, this)){
                controle.mensagem_simples(this, "Cliente excluido com Sucesso!");
            }
            else{
                controle.mensagem_simples(this, "ERRO ao excluir o cliente!");
            }

            Intent it = new Intent(this, Editar_Cliente.class);
            startActivity(it);
            finish();
        }

    }

    /**
     *  Metodo que Carrega o nome fantasia dos clientes no spinner_clientes
     */
    private void carregar_clientes(){
        adp_clientes.clear();

        for (int i=0; i<lista_todos_clientes.getCount(); i++){
            if (lista_todos_clientes.getItem(i).getFantasia()!=null){
                adp_clientes.add(lista_todos_clientes.getItem(i).getFantasia());
            }
        }
        spinner_cliente_ep.setAdapter(adp_clientes);

    }

    /**
     * Metodo que carrega os dados do cliente selecionado no spinner
     */
    private void mudar_cliente_selecionado(){
        spinner_cliente_ep.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                rua=""; numero=""; bairro=""; cep=""; cidade=""; estado="";


                if (lista_todos_clientes.getCount() > position) {
                    // i = spinner_bc_fantasia.getSelectedItemPosition();
                    et_cod_cliente.setText(lista_todos_clientes.getItem(position).getCodigo_cliente().toString());
                    et_raz_social.setText(lista_todos_clientes.getItem(position).getRazao_social().toString());

                    if (lista_todos_clientes.getItem(position).getCnpj_cpf()!=null){
                        et_cnpj_cpf.setText(lista_todos_clientes.getItem(position).getCnpj_cpf().toString());
                    }
                    if(lista_todos_clientes.getItem(position).getFone()!=null){
                        et_telefone.setText(lista_todos_clientes.getItem(position).getFone().toString());
                    }

                    if(lista_todos_clientes.getItem(position).getInscricao_est()!=null){
                        et_ins_estadual.setText(lista_todos_clientes.getItem(position).getInscricao_est().toString());

                    }

                    if(lista_todos_clientes.getItem(position).getRua()!=null){
                        rua = lista_todos_clientes.getItem(position).getRua().toString();
                    }

                    if(lista_todos_clientes.getItem(position).getNumero()!=null){
                        numero = lista_todos_clientes.getItem(position).getNumero().toString();
                    }

                    if(lista_todos_clientes.getItem(position).getBairro()!=null){
                        bairro = lista_todos_clientes.getItem(position).getBairro().toString();
                    }
                    if(lista_todos_clientes.getItem(position).getCep()!=null){
                        cep = lista_todos_clientes.getItem(position).getCep().toString();
                    }

                    if(lista_todos_clientes.getItem(position).getCidade()!=null){
                        cidade = lista_todos_clientes.getItem(position).getCidade().toString();
                    }

                    if(lista_todos_clientes.getItem(position).getUf()!=null){
                        estado = lista_todos_clientes.getItem(position).getUf().toString();
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
