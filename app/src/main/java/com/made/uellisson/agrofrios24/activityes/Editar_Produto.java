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
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Produto;


/**
 * Created by Uellisson on 18/02/2016.
 */
public class Editar_Produto extends Activity implements View.OnClickListener{


    // tela cadastro de Produto
    EditText et_Codigo_produto_ap, et_preco_produto_ap, et_unidade_ref_ap, editText_completo_ap, et_pecas_caixa_ep, et_unds_peca_ep;

    Spinner spn_nome_produto, spinner_disponivel_ep;
    private ArrayAdapter<Produto> lista_produtos;

    ArrayAdapter<String> adp_nome_produtos;
    ArrayAdapter<String> adp_disponivel;

    //String que armazenarao os valores do edittext
    String nome, codigo_produto, preco, unidade_ref, completo, pecas_caixa, unds_peca;

    Button btVoltar_alterar_prod, btSalvar_alterar_Produto, bt_salvar_meio_ap;
    ImageButton imageButton_voltar_ap;

    //Variáveis do banco
    BancoDeDados bd;
    SQLiteDatabase conexao;
    Salvar_no_DB salvar_no_db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_produto);

        et_Codigo_produto_ap = (EditText) findViewById(R.id.et_Codigo_produto_ap);
        et_preco_produto_ap = (EditText) findViewById(R.id.et_preco_produto_ap);

        et_unidade_ref_ap = (EditText) findViewById(R.id.et_unidade_ref_ap);
        editText_completo_ap = (EditText) findViewById(R.id.editText_completo_ap);
        et_pecas_caixa_ep = (EditText) findViewById(R.id.et_pecas_caixa_ep);
        et_unds_peca_ep = (EditText) findViewById(R.id.et_unds_peca_ep);

        spn_nome_produto = (Spinner) findViewById(R.id.spn_nome_produto);
        spinner_disponivel_ep = (Spinner) findViewById(R.id.spinner_disponivel_ep);

        adp_nome_produtos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        adp_disponivel = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        adp_disponivel.add("Sim");
        adp_disponivel.add("Não");

        spinner_disponivel_ep.setAdapter(adp_disponivel);


        btSalvar_alterar_Produto = (Button) findViewById(R.id.btSalvar_alterar_Produto);
        bt_salvar_meio_ap = (Button) findViewById(R.id.bt_salvar_meio_ap);
        btVoltar_alterar_prod = (Button) findViewById(R.id.btVoltar_alterar_prod);
        imageButton_voltar_ap = (ImageButton) findViewById(R.id.imageButton_voltar_ap);

        btSalvar_alterar_Produto.setOnClickListener(this);
        bt_salvar_meio_ap.setOnClickListener(this);
        btVoltar_alterar_prod.setOnClickListener(this);
        imageButton_voltar_ap.setOnClickListener(this);



        try {
            //cria conexão com Banco de dados
            bd = new BancoDeDados(this);
            conexao = bd.getReadableDatabase();
            Dados_Produtos dados_produtos = new Dados_Produtos(conexao);

            //busca produtos armazenados no db e insere no array adapter
            lista_produtos = dados_produtos.busca_produto(this);

            for (int i = 0; i < lista_produtos.getCount(); i++) {
                adp_nome_produtos.add(lista_produtos.getItem(i).getNome());
            }


            //adiciona produtos ao spinner
            spn_nome_produto.setAdapter(adp_nome_produtos);


            //Evento que muda os edittext de acordo como os produtos selecionados
            spn_nome_produto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    bt_salvar_meio_ap.setEnabled(true);
                    btSalvar_alterar_Produto.setEnabled(true);

                    et_Codigo_produto_ap.setText(lista_produtos.getItem(position).getCodigo_produto().toString().replace(",", "."));
                    et_preco_produto_ap.setText(lista_produtos.getItem(position).getPreco().toString().replace(",", "."));
                    et_unidade_ref_ap.setText(lista_produtos.getItem(position).getUnidade().toString());
                    et_pecas_caixa_ep.setText(lista_produtos.getItem(position).getPecas_caixa().toString().replace(",", "."));
                    et_unds_peca_ep.setText(lista_produtos.getItem(position).getUnds_peca().toString().replace(",", "."));
                    editText_completo_ap.setText(lista_produtos.getItem(position).getCompleto().toString().replace(",", "."));

                    if (lista_produtos.getItem(position).getDisponivel()!=null){
                        String disponivel = lista_produtos.getItem(position).getDisponivel();
                        if(disponivel.equalsIgnoreCase("Sim")){
                            spinner_disponivel_ep.setSelection(0);
                        }
                        else{
                            spinner_disponivel_ep.setSelection(1);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        //Gerencia as exceções
        catch (SQLException erro) {
            AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
            mensagem.setMessage("Erro: " + erro.getMessage());
            mensagem.setPositiveButton("Ok", null);
            mensagem.show();

        }
    }

    @Override
    public void onClick(View v) {

        if (v==btSalvar_alterar_Produto||v==bt_salvar_meio_ap) {
            bt_salvar_meio_ap.setEnabled(false);
            btSalvar_alterar_Produto.setEnabled(false);

            nome = spn_nome_produto.getSelectedItem().toString();
            codigo_produto = et_Codigo_produto_ap.getText().toString();
            preco = et_preco_produto_ap.getText().toString();
            unidade_ref = et_unidade_ref_ap.getText().toString();
            completo = editText_completo_ap.getText().toString();
            pecas_caixa = et_pecas_caixa_ep.getText().toString();
            unds_peca = et_unds_peca_ep.getText().toString();
            String disponivel = spinner_disponivel_ep.getSelectedItem().toString();

            Produto produto = new Produto(nome, codigo_produto, preco, unidade_ref, completo, pecas_caixa, unds_peca, disponivel);

            try {
                //cria conexão com db
                bd = new BancoDeDados(this);
                conexao = bd.getReadableDatabase();
                bd.criar_tb_produtos(conexao);

                //salva dados do produto
                Dados_Produtos dados_produtos= new Dados_Produtos(conexao);

                dados_produtos.alterar_produto(produto);


                mensagemExibir("Preço editado com SUCESSO!");

                Intent it = new Intent(this, Editar_Produto.class);
                startActivity(it);
                finish();
            }
            //gerenciamento de excessões
            catch (SQLException erro){
                mensagemExibir(
                        "Erro ao salvar a alteração do produto: " + erro.getMessage());

            }
        }
        else{
            if (v==btVoltar_alterar_prod || v==imageButton_voltar_ap){
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
