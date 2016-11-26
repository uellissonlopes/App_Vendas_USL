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
import com.made.uellisson.agrofrios24.dominio.Dados_Pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_Vendedor;
import com.made.uellisson.agrofrios24.modelo.Vendedor;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by Uellisson on 26/03/2016.
 */
public class Tela_Gerenciar_Pedidos extends Activity implements View.OnClickListener{

   /**
     * Declaração de variáveis
     */
    Button bt_exportar_pedidos, bt_visualizar_pedido, bt_editar_pedido, bt_voltar;
    ImageButton ib_exportar_pedidos, ib_visualizar_pedido, ib_editar_pedido, ib_voltar_gp;

    Dados_Pedido dpe;

    String data_hoje="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_gerenciar_pedidos);

        //inicialização dos Imagebuttons
        ib_exportar_pedidos = (ImageButton) findViewById(R.id.ib_exportar_pedidos);
        ib_visualizar_pedido = (ImageButton) findViewById(R.id.ib_visualizar_pedido);
        ib_editar_pedido = (ImageButton) findViewById(R.id.ib_editar_pedido);
        ib_voltar_gp = (ImageButton) findViewById(R.id.ib_voltar_gp);

        //inicialização dos buttons
        bt_exportar_pedidos = (Button) findViewById(R.id.bt_exportar_pedidos);
        bt_visualizar_pedido = (Button) findViewById(R.id.bt_visualizar_pedido);
        bt_editar_pedido = (Button) findViewById(R.id.bt_editar_pedido);
        bt_voltar = (Button) findViewById(R.id.bt_voltar_gp);

        ib_exportar_pedidos.setOnClickListener(this);
        ib_visualizar_pedido.setOnClickListener(this);
        ib_editar_pedido.setOnClickListener(this);
        ib_voltar_gp.setOnClickListener(this);

        bt_exportar_pedidos.setOnClickListener(this);
        bt_visualizar_pedido.setOnClickListener(this);
        bt_editar_pedido.setOnClickListener(this);
        bt_voltar.setOnClickListener(this);

        //Instancia a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        data_hoje = sdf.format(data);

    }

    @Override
    public void onClick(View v) {

        if (v==bt_exportar_pedidos || v==ib_exportar_pedidos){
            //cria conexão com db
            BancoDeDados bd = new BancoDeDados(this);
            SQLiteDatabase conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            Gerenciar_Arquivos bk = new Gerenciar_Arquivos();

            dpe = new Dados_Pedido(conexao);
            Dados_Vendedor dados_vendedor = new Dados_Vendedor(conexao);


            String nome_arquivo = "";
            String dados_tabela;


            try
            {
                Vendedor vendedor = dados_vendedor.busca_vendedor();
                if (vendedor != null){
                    nome_arquivo = "Pedidos_"+vendedor.getNome_vendedor()+data_hoje+".txt";
                    dados_tabela = dpe.todos_dados_pedido(this);
                    if (!dados_tabela.equalsIgnoreCase("@%%")){
                        bk.salvar_arquivo(nome_arquivo, dados_tabela);

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/" + nome_arquivo)));
                        intent.setType("*/*");
                        startActivity(intent);

                        Mensagem("Pedidos Exportados com Sucesso!");
                    }
                    else{
                        Mensagem("Não existem pedidos para serem exportados!");
                    }
                }

                else {
                    Mensagem("Cadastre o Vendedor, em CONFIGURAÇÕES, antes de exportar o pedido!");
                }

            }
            catch (Exception e)
            {
                Mensagem("Erro : " + e.getLocalizedMessage());
            }



        }
        if (v==bt_visualizar_pedido || v==ib_visualizar_pedido){
            Intent it = new Intent(this, Ver_pedido.class);
            startActivity(it);
            finish();
        }
        if (v==ib_editar_pedido || v==bt_editar_pedido){
            Intent it = new Intent(this, Editar_Pedido_Cliente.class);
            startActivity(it);
            finish();
        }
        if (v==ib_voltar_gp || v==bt_voltar){
            Intent it = new Intent(this, TelaPrincipal.class);
            startActivity(it);
            finish();
        }
    }


    private void Mensagem(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

}