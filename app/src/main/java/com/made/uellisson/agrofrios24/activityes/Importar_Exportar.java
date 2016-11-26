package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.Gerenciar_Arquivos;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.database.Script_Produtos;
import com.made.uellisson.agrofrios24.dominio.Dados_Pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos;
import com.made.uellisson.agrofrios24.dominio.Dados_cliente;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Cliente;
import com.made.uellisson.agrofrios24.modelo.Produto;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Uellisson on 27/03/2016.
 */
public class Importar_Exportar extends Activity implements View.OnClickListener{

    private TextView txt_diretorio;

    private Spinner spn_arq_cliente;
    //private Spinner spn_arq_produtos;

    private Button btn_imp_clientes;
    private Button btn_imp_produtos;
    private Button btn_exp_pedidos;
    private Button button_exp_clientes;

    ImageButton imageButton_voltar_ie;

    private ArrayList<String> Arquivos = new ArrayList<String>();
    Dados_cliente dc;
    Dados_Produtos dpr;
    Dados_Pedido dpe;

    String data_hoje="";

    //diretorio onde são salvos os arquivos
    String dir_arq_download = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ Environment.DIRECTORY_DOWNLOADS;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.importar_exportar);

            txt_diretorio = (TextView) findViewById(R.id.txt_diretorio);
            spn_arq_cliente  = (Spinner)  findViewById(R.id.spn_arq_cliente);
            //spn_arq_produtos  = (Spinner)  findViewById(R.id.spn_arq_produtos);
            btn_imp_clientes = (Button)  findViewById(R.id.btn_imp_clientes);
            btn_imp_produtos = (Button)  findViewById(R.id.btn_imp_produtos);
            btn_exp_pedidos = (Button)  findViewById(R.id.btn_exp_pedidos);
            button_exp_clientes = (Button)  findViewById(R.id.button_exp_clientes);
            imageButton_voltar_ie = (ImageButton)  findViewById(R.id.imageButton_voltar_ie);

            btn_imp_clientes.setOnClickListener(this);
            btn_imp_produtos.setOnClickListener(this);
            btn_exp_pedidos.setOnClickListener(this);
            button_exp_clientes.setOnClickListener(this);
            imageButton_voltar_ie.setOnClickListener(this);

            //txtRoot.append(ObterDiretorio());
            txt_diretorio.append(dir_arq_download);

            Listar();

        }
        catch (Exception e)
        {
            Mensagem("Erro : " + e.getMessage());
        }

        //Instancia a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        data_hoje = sdf.format(data);
    }


    /*
    Lista todos os arquivos no diretoio com um spinner
     */
    public void Listar()
    {
        //File diretorio = new File(ObterDiretorio());
        File diretorio = new File(dir_arq_download);
        File[] arquivos = diretorio.listFiles();
        if(arquivos != null)
        {
            int length = arquivos.length;
            for(int i = 0; i < length; ++i)
            {
                File f = arquivos[i];
                if (f.isFile())
                {
                    Arquivos.add(f.getName());
                }
            }

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>
                    (this,android.R.layout.simple_dropdown_item_1line, Arquivos);
            spn_arq_cliente.setAdapter(arrayAdapter);
        }
    }


//    //Método que importa os clintes do arquivo
//    public void click_importar_clientes(View view) {
//
//    }

//    //Método que importa os produtos atraves do arquivo
//    public void click_importar_produtos() {
//
//    }

    @Override
    public void onClick(View v) {
        if (v==btn_imp_clientes){
            //cria conexão com db
            BancoDeDados bd = new BancoDeDados(this);
            SQLiteDatabase conexao = bd.getReadableDatabase();
            Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
            bd.onCreate(conexao);
            dc = new Dados_cliente(conexao);

            String nome_arquivo = spn_arq_cliente.getSelectedItem().toString();

            ArrayList<Cliente> lista_clientes = null;

            try {
                lista_clientes = dc.dados_clientes_do_backup(nome_arquivo);
            } catch (IOException e) {
                Mensagem(e.getMessage());
                 e.printStackTrace();
            }


            for (int i = 0; i < lista_clientes.size(); i++) {
                //salva dados do cliente
               // Mensagem(lista_clientes.get(i).getFantasia());
                salvar_no_db.salvar_cliente(lista_clientes.get(i));
            }
            //Mensagem(lista_clientes.get(0).getFantasia());

             Mensagem("Clientes importados com sucesso!");
        }
        else {
            if (v == btn_imp_produtos) {
                int imp_todos=0;

                //cria conexão com db
                BancoDeDados bd = new BancoDeDados(this);
                SQLiteDatabase conexao = bd.getReadableDatabase();
                Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
                bd.onCreate(conexao);
                dpr = new Dados_Produtos(conexao);
                conexao.execSQL(Script_Produtos.abreOuCriaTabela_produtos());

                String nome_arquivo = spn_arq_cliente.getSelectedItem().toString();

                ArrayList<Produto> lista_produtos = dpr.dados_produtos_do_backup(nome_arquivo);

                try {
                    bd.excuir_todos_Produtos(conexao, "tb_produtos");
                } catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

                conexao.execSQL(Script_Produtos.abreOuCriaTabela_produtos());

                for (int i = 0; i < lista_produtos.size(); i++) {
                    //salva dados do cliente
                    salvar_no_db.salvar_produto(lista_produtos.get(i));
                    imp_todos++;
                }

                if(imp_todos==lista_produtos.size()){
                    Mensagem("Produtos importados com sucesso!");
                }
                else{
                    Mensagem("Erro ao Importar Produtos!");
                }


            } else {
                if (v == btn_exp_pedidos) {

                    //cria conexão com db
                    BancoDeDados bd = new BancoDeDados(this);
                    SQLiteDatabase conexao = bd.getReadableDatabase();
                    bd.onCreate(conexao);

                    dpe = new Dados_Pedido(conexao);


                    String lstrNomeArq;
                    File arq;
                    byte[] dados;

//                    Mensagem("pedidos"+dpe.todos_dados_pedido(this));
                    try
                    {
                        lstrNomeArq = "pedidos_agrofrios-"+data_hoje+".txt";

                        //  arq = new File(Environment.getExternalStorageDirectory()+"/arquivos", lstrNomeArq);
                        arq = new File(dir_arq_download, lstrNomeArq);
                        FileOutputStream fos;


                        dados = dpe.todos_dados_pedido(this).getBytes();

                        fos = new FileOutputStream(arq);
                        fos.write(dados);
                        fos.flush();
                        fos.close();
                        Mensagem("Pedidos Exportados com Sucesso!");
                        Listar();
                    }
                    catch (Exception e)
                    {
                        Mensagem("Erro : " + e.getLocalizedMessage());
                    }

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "pedidos_agrofrios-"+data_hoje+".txt")));
                    intent.setType("*/*");
                    startActivity(intent);

                }

                if (v==button_exp_clientes){

                    //cria conexão com db
                    BancoDeDados bd = new BancoDeDados(this);
                    SQLiteDatabase conexao = bd.getReadableDatabase();
                    bd.onCreate(conexao);

                    Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

                    dc = new Dados_cliente(conexao);


                    String nome_arquivo;
                    String dados_tabela;
                    String codificacao="";

                    try
                    {
                        nome_arquivo = "clientes_agrofrios-"+data_hoje+".txt";
                        dados_tabela = dc.todos_dados_cliente(this);
                        codificacao=bk.salvar_arquivo(nome_arquivo, dados_tabela);
                        Listar();
                    }
                    catch (Exception e)
                    {
                        Mensagem("Erro : " + e.getLocalizedMessage());
                    }

                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "pedidos_agrofrios-" + data_hoje + ".txt")));
                    intent.setType("*/*");
                    startActivity(intent);

                    //Mensagem("Codificação: "+codificacao);

                    Mensagem("Clientes Exportados com Sucesso!");

                }
                else{
                    if (v==imageButton_voltar_ie){
                        Intent it = new Intent(this, TelaPrincipal.class);
                        startActivity(it);
                        finish();

                    }
                }
            }
        }
    }


    private void Mensagem(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}

