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
import android.widget.Spinner;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.database.Scripts_Clientes;
import com.made.uellisson.agrofrios24.dominio.Dados_cliente;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Cliente;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Uellisson on 27/03/2016.
 */
public class Importar_Clientes extends Activity implements View.OnClickListener{

    private ImageButton imagebutton_voltar_ie, imagebutton_importar_clientes_ic;

    private Spinner spn_arq_cliente_ic;

    private Button button_voltar_ic, btn_imp_clientes_ic;


    private ArrayAdapter<String> lista_arquivos;

    Dados_cliente dc;

    String data_hoje="";

    //diretorio onde são salvos os arquivos
    String dir_arq_download = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ Environment.DIRECTORY_DOWNLOADS;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.importar_clientes);

        imagebutton_voltar_ie = (ImageButton)  findViewById(R.id.imagebutton_voltar_ic);
        imagebutton_importar_clientes_ic = (ImageButton)  findViewById(R.id.imagebutton_importar_clientes_ic);

        spn_arq_cliente_ic  = (Spinner)  findViewById(R.id.spn_arq_cliente_ic);

        button_voltar_ic = (Button)  findViewById(R.id.button_voltar_ic);
        btn_imp_clientes_ic = (Button)  findViewById(R.id.btn_imp_clientes_ic);

        imagebutton_voltar_ie.setOnClickListener(this);
        imagebutton_importar_clientes_ic.setOnClickListener(this);
        button_voltar_ic.setOnClickListener(this);
        btn_imp_clientes_ic.setOnClickListener(this);

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

            spn_arq_cliente_ic.setAdapter(lista_arquivos);
        }
    }



    @Override
    public void onClick(View v) {
        if (v == btn_imp_clientes_ic) {
            //cria conexão com db
            BancoDeDados bd = new BancoDeDados(this);
            SQLiteDatabase conexao = bd.getReadableDatabase();
            Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
            bd.onCreate(conexao);
            dc = new Dados_cliente(conexao);

            String nome_arquivo = spn_arq_cliente_ic.getSelectedItem().toString();

            ArrayList<Cliente> lista_clientes = null;

            try {
                lista_clientes = dc.dados_clientes_do_backup(nome_arquivo);
            } catch (IOException e) {
                Mensagem(e.getMessage());
                e.printStackTrace();
            }

            try {
                bd.excuir_todos_clientes(conexao);
            } catch (SQLException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

            conexao.execSQL(Scripts_Clientes.abreOuCriaTabela_clientes());

            btn_imp_clientes_ic.setEnabled(false);

            for (int i = 0; i < lista_clientes.size(); i++) {
                salvar_no_db.salvar_cliente(lista_clientes.get(i));
            }
            Mensagem("Clientes importados com sucesso!");
        }

        if (v==imagebutton_voltar_ie || v==button_voltar_ic){
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

