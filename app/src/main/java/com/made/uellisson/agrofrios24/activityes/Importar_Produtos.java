package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.database.Script_Produtos;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Produto;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Uellisson on 27/03/2016.
 */
public class Importar_Produtos extends Activity implements View.OnClickListener{

    private ImageButton imagebutton_voltar_ip, imagebutton_importar_produtos_ip;

    private Spinner spn_arq_produto_ip;

    private Button btn_imp_produtos_ip, button_voltar_ip;

    ProgressBar progressbar_ip;


    private ArrayAdapter<String> lista_arquivos;

    Dados_Produtos dpr;

    String data_hoje="";

    //diretorio onde são salvos os arquivos
    String dir_arq_download = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ Environment.DIRECTORY_DOWNLOADS;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.importar_produtos);

        imagebutton_voltar_ip = (ImageButton)  findViewById(R.id.imagebutton_voltar_ip);
        imagebutton_importar_produtos_ip = (ImageButton)  findViewById(R.id.imagebutton_importar_produtos_ip);

        spn_arq_produto_ip  = (Spinner)  findViewById(R.id.spn_arq_produto_ip);
        progressbar_ip = (ProgressBar)  findViewById(R.id.progressbar_ip);
        progressbar_ip.setVisibility(ProgressBar.INVISIBLE);


        button_voltar_ip = (Button)  findViewById(R.id.button_voltar_ip);
        btn_imp_produtos_ip = (Button)  findViewById(R.id.btn_imp_produtos_ip);

        imagebutton_voltar_ip.setOnClickListener(this);
        imagebutton_importar_produtos_ip.setOnClickListener(this);
        button_voltar_ip.setOnClickListener(this);
        btn_imp_produtos_ip.setOnClickListener(this);

        lista_arquivos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        Listar();


        //Instancia a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        data_hoje = sdf.format(data);
    }


    /*
    Lista todos os arquivos no diretoio com um spinner
     */
    public void Listar(){
        //File diretorio = new File(ObterDiretorio());
        File diretorio = new File(dir_arq_download);
        File[] arquivos = diretorio.listFiles();
        if(arquivos != null){
            int length = arquivos.length;
            for(int i = 0; i < length; ++i)
            {
                File f = arquivos[i];
                if (f.isFile())
                {
                    lista_arquivos.add(f.getName());
                }
            }

            spn_arq_produto_ip.setAdapter(lista_arquivos);
        }
    }



    @Override
    public void onClick(View v) {
        if (v == btn_imp_produtos_ip || v==imagebutton_importar_produtos_ip) {
            //progressbar_ip.setProgress(0);


           // td.start();

            int imp_todos=0;

            //cria conexão com db
            BancoDeDados bd = new BancoDeDados(this);
            SQLiteDatabase conexao = bd.getReadableDatabase();
            Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
            bd.onCreate(conexao);
            dpr = new Dados_Produtos(conexao);
            conexao.execSQL(Script_Produtos.abreOuCriaTabela_produtos());


            String nome_arquivo = spn_arq_produto_ip.getSelectedItem().toString();

            ArrayList<Produto> lista_produtos = dpr.dados_produtos_do_backup(nome_arquivo);



            try {
                bd.excuir_todos_Produtos(conexao, "tb_produtos");
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }


            conexao.execSQL(Script_Produtos.abreOuCriaTabela_produtos());

            btn_imp_produtos_ip.setEnabled(false);

            for (int i = 0; i < lista_produtos.size(); i++) {
                //salva dados do cliente
                salvar_no_db.salvar_produto(lista_produtos.get(i));
                imp_todos++;
            }
//            progressbar_ip.setProgress(100);
//            progressbar_ip.setVisibility(ProgressBar.INVISIBLE);

            if(imp_todos==lista_produtos.size()){
                Mensagem("Produtos importados com sucesso!");
            }
            else{
                Mensagem("Erro ao Importar Produtos!");
            }
        }
        if (v==imagebutton_voltar_ip || v==button_voltar_ip ){
            Intent it = new Intent(this, Tela_Gerenciar_Cad.class);
            startActivity(it);
            finish();
        }
    }


    private void Mensagem(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }


}

