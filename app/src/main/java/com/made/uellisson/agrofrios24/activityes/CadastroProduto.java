package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Produto;


/**
 * Created by Uellisson on 18/02/2016.
 */
public class CadastroProduto extends Activity implements View.OnClickListener{


    // tela cadastro de Produto
    EditText etNome, etCodigo_produto, etPreco, editText_completo_cp, editText_pecas_caixa_cp, editText_unds_peca_cp;

    Spinner spinner_unidade_ref_cp;
    Spinner spinner_disponivel_cp;

    ArrayAdapter<String> adp_unidades_ref;
    ArrayAdapter<String> adp_disponivel;


    //String que armazenarao os valores do edittext
    String nome, codigo_produto, preco;

    Button btSalvar_Produto, btVoltar_Produto; // tela cadastro de Produto
    ImageButton imageButton_voltar_cp;

    //Variáveis do banco
    BancoDeDados bd;
    SQLiteDatabase conexao;
    Salvar_no_DB salvar_no_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_produto);

        etNome = (EditText) findViewById(R.id.et_nome_produto);
        etCodigo_produto = (EditText) findViewById(R.id.txtCodigo_produto);
        etPreco = (EditText) findViewById(R.id.txtPreco);
        editText_completo_cp = (EditText) findViewById(R.id.editText_completo_cp);
        editText_pecas_caixa_cp = (EditText) findViewById(R.id.editText_pecas_caixa_cp);
        editText_unds_peca_cp = (EditText) findViewById(R.id.editText_unds_peca_cp);
        spinner_unidade_ref_cp = (Spinner) findViewById(R.id.spinner_unidade_ref_cp);
        spinner_disponivel_cp = (Spinner) findViewById(R.id.spinner_disponivel_cp);

        adp_disponivel = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adp_disponivel.add("Sim");
        adp_disponivel.add("Não");

        spinner_disponivel_cp.setAdapter(adp_disponivel);



        adp_unidades_ref = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adp_unidades_ref.add("KG"); adp_unidades_ref.add("PCT"); adp_unidades_ref.add("UND");
        adp_unidades_ref.add("CX"); adp_unidades_ref.add("BDJ"); adp_unidades_ref.add("FD");
        adp_unidades_ref.add("FT"); adp_unidades_ref.add("SC"); adp_unidades_ref.add("RF");
        adp_unidades_ref.add("DP"); adp_unidades_ref.add("CT"); adp_unidades_ref.add("LT"); adp_unidades_ref.add("BD");

        spinner_unidade_ref_cp.setAdapter(adp_unidades_ref);

        btSalvar_Produto = (Button) findViewById(R.id.btSalvar_Produto);
        btVoltar_Produto = (Button) findViewById(R.id.btVoltar_Produto);
        imageButton_voltar_cp = (ImageButton) findViewById(R.id.imageButton_voltar_cp);

        btSalvar_Produto.setOnClickListener(this);
        btVoltar_Produto.setOnClickListener(this);
        imageButton_voltar_cp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v==btSalvar_Produto) {
            btSalvar_Produto.setEnabled(false);

            nome = etNome.getText().toString();
            codigo_produto = etCodigo_produto.getText().toString();
            preco = etPreco.getText().toString();
            String unidade_referencia = spinner_unidade_ref_cp.getSelectedItem().toString();
            String completo = editText_completo_cp.getText().toString();
            String pecas_caixa_cp = editText_pecas_caixa_cp.getText().toString();
            String unds_peca_cp = editText_unds_peca_cp.getText().toString();
            String disponivel = spinner_disponivel_cp.getSelectedItem().toString();

            Produto produto = new Produto(nome, codigo_produto, preco, unidade_referencia, completo, pecas_caixa_cp, unds_peca_cp, disponivel);

            try {
                //cria conexão com db
                bd = new BancoDeDados(this);
                conexao = bd.getReadableDatabase();
                bd.criar_tb_produtos(conexao);

                //salva dados do produto
                salvar_no_db = new Salvar_no_DB(conexao);
                salvar_no_db.salvar_produto(produto);


                mensagemExibir("Cadastro do produto salvo com SUCESSO!");

                Intent it = new Intent(this, CadastroProduto.class);
                startActivity(it);
                finish();
            }
            //gerenciamento de excessões
            catch (SQLException erro){
                mensagemExibir(
                        "Erro ao salvar : " + erro.getMessage());

            }
        }
        else{
            if (v==btVoltar_Produto || v==imageButton_voltar_cp){
                Intent it = new Intent(this, Tela_Gerenciar_Cad.class);
                startActivity(it);
                finish();
            }
        }
    }

    /**
     * Gerenciador de Mensagens
     */
    private void mensagemExibir(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
//    public void mensagemExibir(String titulo, String texto) {
//        AlertDialog.Builder mensagem = new AlertDialog.Builder(
//                this);
//        mensagem.setTitle(titulo);
//        mensagem.setMessage(texto);
//        mensagem.setNeutralButton("OK", null);
//        mensagem.show();
//    }
}
