package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.database.Gerenciar_Arquivos;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos;
import com.made.uellisson.agrofrios24.dominio.Dados_cliente;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by Uellisson on 26/03/2016.
 */
public class Tela_Gerenciar_Cad extends Activity implements View.OnClickListener{

    //Declaração das variáveis
    Button bt_editar_clientes, bt_importar_clientes, bt_cadastrar_clientes, bt_exportar_clientes, bt_editar_produtos,
            bt_importar_produtos, bt_cadastrar_produto, bt_exportar_produtos, bt_voltar;


    ImageButton ib_editar_clientes, ib_importar_clientes, ib_cadastrar_clientes, ib_exportar_clientes, ib_editar_produtos,
                ib_importar_produtos, ib_cadastrar_produto, ib_exportar_produtos, ib_voltar;


    Dados_cliente dc;
    Dados_Produtos dpr;
    String data_hoje="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_gerenciar_cadastros);

        //inicialização dos buttons
        bt_editar_clientes = (Button) findViewById(R.id.bt_editar_clientes);
        bt_importar_clientes = (Button) findViewById(R.id.bt_importar_clientes);
        bt_cadastrar_clientes = (Button) findViewById(R.id.bt_cadastrar_clientes);
        bt_exportar_clientes = (Button) findViewById(R.id.bt_exportar_clientes);
        bt_editar_produtos = (Button) findViewById(R.id.bt_editar_produtos);
        bt_importar_produtos = (Button) findViewById(R.id.bt_importar_produtos);
        bt_cadastrar_produto = (Button) findViewById(R.id.bt_cadastrar_produto);
        bt_exportar_produtos = (Button) findViewById(R.id.bt_exportar_produtos);
        bt_voltar = (Button) findViewById(R.id.bt_voltar_gc);


        ib_editar_clientes = (ImageButton) findViewById(R.id.ib_editar_clientes);
        ib_importar_clientes = (ImageButton) findViewById(R.id.ib_importar_clientes);
        ib_cadastrar_clientes = (ImageButton) findViewById(R.id.ib_cadastrar_clientes);
        ib_exportar_clientes = (ImageButton) findViewById(R.id.ib_exportar_clientes);
        ib_editar_produtos = (ImageButton) findViewById(R.id.ib_editar_produtos);
        ib_importar_produtos = (ImageButton) findViewById(R.id.ib_importar_produtos);
        ib_cadastrar_produto = (ImageButton) findViewById(R.id.ib_cadastrar_produto);
        ib_exportar_produtos = (ImageButton) findViewById(R.id.ib_exportar_produtos);
        ib_voltar = (ImageButton) findViewById(R.id.ib_voltar_gc);

        bt_editar_clientes.setOnClickListener(this);
        bt_importar_clientes.setOnClickListener(this);
        bt_cadastrar_clientes.setOnClickListener(this);
        bt_exportar_clientes.setOnClickListener(this);
        bt_editar_produtos.setOnClickListener(this);
        bt_importar_produtos.setOnClickListener(this);
        bt_cadastrar_produto.setOnClickListener(this);
        bt_exportar_produtos.setOnClickListener(this);
        bt_voltar.setOnClickListener(this);

        ib_editar_clientes.setOnClickListener(this);
        ib_importar_clientes.setOnClickListener(this);
        ib_cadastrar_clientes.setOnClickListener(this);
        ib_exportar_clientes.setOnClickListener(this);
        ib_editar_produtos.setOnClickListener(this);
        ib_importar_produtos.setOnClickListener(this);
        ib_cadastrar_produto.setOnClickListener(this);
        ib_exportar_produtos.setOnClickListener(this);
        ib_voltar.setOnClickListener(this);

        bt_voltar.setOnClickListener(this);

        //Instancia a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        data_hoje = sdf.format(data);

    }

    @Override
    public void onClick(View v) {

        if (v==bt_editar_clientes || v==ib_editar_clientes){
            Intent it = new Intent(this, Editar_Cliente.class);
            startActivity(it);
        }
        if (v==bt_importar_clientes || v==ib_importar_clientes){
            Intent it = new Intent(this, Importar_Clientes.class);
            startActivity(it);
        }
        if (v==bt_cadastrar_clientes || v==ib_cadastrar_clientes){
            Intent it = new Intent(this, CadastroCliente.class);
            startActivity(it);
        }
        if (v==bt_exportar_clientes || v==bt_exportar_clientes){
            //cria conexão com db
            BancoDeDados bd = new BancoDeDados(this);
            SQLiteDatabase conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

            dc = new Dados_cliente(conexao);


            String nome_arquivo;
            String dados_tabela;

            try
            {
                nome_arquivo = "clientes_"+data_hoje+".txt";
                dados_tabela = dc.todos_dados_cliente(this).replace("null", "");
                bk.salvar_arquivo(nome_arquivo, dados_tabela);

            }
            catch (Exception e)
            {
            //    mensagem("Erro : " + e.getLocalizedMessage());
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "clientes_" + data_hoje + ".txt")));
            intent.setType("*/*");
            startActivity(intent);

            //Mensagem("Codificação: "+codificacao);

            mensagem("Clientes Exportados com Sucesso!");

        }
        if (v==bt_editar_produtos || v==ib_editar_produtos){
            Intent it = new Intent(this, Editar_Produto.class);
            startActivity(it);
        }
        if (v==bt_importar_produtos || v==ib_importar_produtos){
            Intent it = new Intent(this, Importar_Produtos.class);
            startActivity(it);
        }
        if (v==bt_cadastrar_produto || v==bt_cadastrar_produto){
            Intent it = new Intent(this, CadastroProduto.class);
            startActivity(it);
        }

        if (v==bt_exportar_produtos || v==ib_exportar_produtos){
            //cria conexão com db
            BancoDeDados bd = new BancoDeDados(this);
            SQLiteDatabase conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

            dpr = new Dados_Produtos(conexao);


            String nome_arquivo;
            String dados_tabela;

            try
            {
                nome_arquivo = "produtos_"+data_hoje+".txt";
                dados_tabela = dpr.todos_dados_produtos();
                bk.salvar_arquivo(nome_arquivo, dados_tabela);

            }
            catch (Exception e)
            {
               // mensagem("Erro : " + e.getLocalizedMessage());
            }

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + "produtos_" + data_hoje + ".txt")));
            intent.setType("*/*");
            startActivity(intent);

            //Mensagem("Codificação: "+codificacao);

            mensagem("Produtos Exportados com Sucesso!");

        }
        if (v==ib_voltar || v==bt_voltar){
            Intent it = new Intent(this, TelaPrincipal.class);
            startActivity(it);
            finish();
        }

    }

    private void mensagem(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

//    /*
//       Lista todos os arquivos no diretoio com um spinner
//        */
//    public void Listar(){
//        //File diretorio = new File(ObterDiretorio());
//        File diretorio = new File(dir_arq_download);
//        File[] arquivos = diretorio.listFiles();
//        if(arquivos != null){
//            int length = arquivos.length;
//            for(int i = 0; i < length; ++i)
//            {
//                File f = arquivos[i];
//                if (f.isFile())
//                {
//                    //lista_arquivos.add(f.getName());
//                }
//            }
//
//           // spn_arq_cliente_ic.setAdapter(lista_arquivos);
//        }
//    }


}