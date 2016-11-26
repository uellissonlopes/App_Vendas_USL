package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Dados_Pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_cliente;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Cliente;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Uellisson on 21/03/2016.
 */
public class Wr_arquivos extends Activity {
    private TextView txtRoot;
    private TextView txtNomeArq;
    private TextView txtSalvar;
    private TextView txtLer;
    private Spinner SpnListarArquivos;
    private ArrayList<String> Arquivos = new ArrayList<String>();

    Dados_cliente dc;
    Dados_Pedido dpe;

    //diretorio onde são salvos os arquivos
    String dir_arq_download = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ Environment.DIRECTORY_DOWNLOADS;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        try
        {
            setContentView(R.layout.wr_arquivos);

            txtRoot = (TextView) findViewById(R.id.txtRoot2);
            txtNomeArq = (TextView) findViewById(R.id.edtNomeArq);
            txtSalvar = (TextView) findViewById(R.id.edtSalvar);
            txtLer = (TextView) findViewById(R.id.edtLer);
            SpnListarArquivos = (Spinner)  findViewById(R.id.spListarArquivos);
            //txtRoot.append(ObterDiretorio());
            txtRoot.append(dir_arq_download);

            Listar();

        }
        catch (Exception e)
        {
            Mensagem("Erro : " + e.getMessage());
        }
    }

    private void Mensagem(String msg)
    {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }



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
            SpnListarArquivos.setAdapter(arrayAdapter);
        }
    }

    public void click_Salvar(View v)
    {
        String lstrNomeArq;
        File arq;
        byte[] dados;
        try
        {
            lstrNomeArq = txtNomeArq.getText().toString();

//            arq = new File(Environment.getExternalStorageDirectory()+"/arquivos", lstrNomeArq);
            arq = new File(dir_arq_download, lstrNomeArq);
            FileOutputStream fos;

            dados = txtSalvar.getText().toString().getBytes();

            fos = new FileOutputStream(arq);
            fos.write(dados);
            fos.flush();
            fos.close();
            Mensagem("Texto Salvo com sucesso!");
            Listar();
        }
        catch (Exception e)
        {
            Mensagem("Erro : " + e.getMessage());
        }
    }

    public void click_Carregar(View v)
    {
        //cria conexão com db
        BancoDeDados bd = new BancoDeDados(this);
        SQLiteDatabase conexao = bd.getReadableDatabase();
        Salvar_no_DB salvar_no_db = new Salvar_no_DB(conexao);
        bd.onCreate(conexao);
        dc= new Dados_cliente(conexao);

        String nome_arquivo = SpnListarArquivos.getSelectedItem().toString();
//        String dados_arquivo = dc.ler_arquivo(nome_arquivo);
//        txtLer.append(dados_arquivo);


        ArrayList<Cliente> lista_clientes = null;
        try {
            lista_clientes = dc.dados_clientes_do_backup(nome_arquivo);
        } catch (IOException e) {

            e.printStackTrace();
        }


        for (int i=0; i<lista_clientes.size(); i++){

            //salva dados do cliente
            salvar_no_db.salvar_cliente(lista_clientes.get(i));
        }


//        String lstrNomeArq;
//        File arq;
//        String lstrlinha;
//        try
//        {
//            lstrNomeArq = SpnListarArquivos.getSelectedItem().toString();
//
//            txtLer.setText("");
//
////            arq = new File(Environment.getExternalStorageDirectory()+"/arquivos", lstrNomeArq);
//
//            arq = new File(dir_arq_download, lstrNomeArq);
//            BufferedReader br = new BufferedReader(new FileReader(arq));
//
//            while ((lstrlinha = br.readLine()) != null)
//            {
//                if (!txtLer.getText().toString().equals(""))
//                {
//                    txtLer.append("\n");
//                }
//                txtLer.append(lstrlinha);
//
//            }
//
//            Mensagem("Texto Carregado com sucesso!");
//
//        }
//        catch (Exception e)
//        {
//            Mensagem("Erro : " + e.getMessage());
//        }
    }

}
