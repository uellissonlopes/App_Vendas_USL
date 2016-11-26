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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.controle.Controle;
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Dados_Pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos_pedido;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;


/**
 * Created by Uellisson on 28/02/2016.
 */
public class Ver_pedido extends Activity implements View.OnClickListener{

    //array adapters que gerenciam o spinner

    private ArrayAdapter<Pedido> pedidos;
    private ArrayAdapter<String> clientes_pedido;
//    private ArrayAdapter<String> detalhes_pedido;


    Spinner spinner_clientes_pedido;
    TextView textView_qtd, textView_produto, textView_preco;
    private ListView listView_qtd_produtos_vp, listView_nome_produtos_vp, listView_preco_produtos_vp;


    private EditText et_total_ver_pedidos, et_data_pedido, et_desconto_perc, et_total_com_desconto;

    Double valor_total=0.0;

    Button bt_voltar_pedido; // tela cadastro de Produto
    ImageButton imageButton_voltar_vp;

    //Variáveis do banco
    BancoDeDados bd;
    SQLiteDatabase conexao;
    Salvar_no_DB salvar_no_db;
    Dados_Pedido dados_pedidos;
    Dados_Produtos_pedido dados_produtos_pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_pedidos);

        spinner_clientes_pedido = (Spinner) findViewById(R.id.spinner_clientes_ver_pedidos);
        et_total_ver_pedidos = (EditText) findViewById(R.id.et_total_ver_pedidos);
        et_data_pedido = (EditText) findViewById(R.id.et_data_ver_pedido);
        et_desconto_perc = (EditText) findViewById(R.id.et_desconto_perc);
        et_total_com_desconto = (EditText) findViewById(R.id.et_total_com_desconto);

        bt_voltar_pedido = (Button) findViewById(R.id.bt_voltar_ver_pedidos);
        imageButton_voltar_vp = (ImageButton) findViewById(R.id.imageButton_voltar_vp);

        bt_voltar_pedido.setOnClickListener(this);
        imageButton_voltar_vp.setOnClickListener(this);

        textView_qtd  = (TextView) findViewById(R.id.textView_qtd);
        textView_produto  = (TextView) findViewById(R.id.textView_produto);
        textView_preco  = (TextView) findViewById(R.id.textView_preco);

        listView_qtd_produtos_vp = (ListView) findViewById(R.id.listView_qtd_produtos_vp);
        listView_nome_produtos_vp = (ListView) findViewById(R.id.listView_nome_produtos_vp);
        listView_preco_produtos_vp = (ListView) findViewById(R.id.listView_preco_produtos_vp);


        pedidos = new ArrayAdapter<Pedido>(this, android.R.layout.simple_list_item_1);
        clientes_pedido = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //detalhes_pedido = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        final ArrayAdapter<String> adp_qtd_produto_list_view = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        final ArrayAdapter<String> adp_nome_produto_list_view = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        final ArrayAdapter<String> adp_preco_produto_list_view = new ArrayAdapter<String>(this, android.R.layout.test_list_item);


        try {
            //cria conexão com Banco de dados
            bd = new BancoDeDados(this);
            conexao = bd.getReadableDatabase();
            dados_pedidos = new Dados_Pedido(conexao);
            bd.criar_tb_pedidos(conexao);

            //busca produtos armazenados no db e insere no array adapter
            pedidos = dados_pedidos.busca_pedidos(this);


            for (int i=0; i<pedidos.getCount(); i++){
                clientes_pedido.add(pedidos.getItem(i).getCliente_fantasia());
            }

            spinner_clientes_pedido.setAdapter(clientes_pedido);

            //Evento que muda os edittext de acordo como os produtos selecionados
            spinner_clientes_pedido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ArrayAdapter<Produtos_pedido> produtos_pedido_cliente = null;

                    adp_qtd_produto_list_view.clear();
                    adp_nome_produto_list_view.clear();
                    adp_preco_produto_list_view.clear();
                    listView_qtd_produtos_vp.setAdapter(adp_qtd_produto_list_view);
                    listView_nome_produtos_vp.setAdapter(adp_nome_produto_list_view);
                    listView_preco_produtos_vp.setAdapter(adp_preco_produto_list_view);


                    for (int i=0; i<pedidos.getCount(); i++){

                        if (pedidos.getItem(i).getCliente_fantasia().equalsIgnoreCase(spinner_clientes_pedido.getSelectedItem().toString())){
                           produtos_pedido_cliente = pedidos.getItem(i).getAdpp();

                        }
                    }

                    int altura_tabela = 40;

                    if (produtos_pedido_cliente!=null){
                        for (int i=0; i<produtos_pedido_cliente.getCount(); i++){
                            altura_tabela = altura_tabela+40;

                            //esse veio pra cima
                            adp_qtd_produto_list_view.add(produtos_pedido_cliente.getItem(i).getQuantidade().toString());
                            adp_nome_produto_list_view.add(produtos_pedido_cliente.getItem(i).getNome_produto().toString());
                            adp_preco_produto_list_view.add("R$ "+produtos_pedido_cliente.getItem(i).getPreco_total().toString());

                        }//fim do for

                    }//fim di if cliente != null

                    listView_qtd_produtos_vp.setAdapter(adp_qtd_produto_list_view);
                    listView_nome_produtos_vp.setAdapter(adp_nome_produto_list_view);
                    listView_preco_produtos_vp.setAdapter(adp_preco_produto_list_view);

                    //alteracao do tamanho do listview
                    TableRow.LayoutParams layoutParams_qtd = new TableRow.LayoutParams(textView_qtd.getLayoutParams().width, altura_tabela);
                    TableRow.LayoutParams layoutParams_nome = new TableRow.LayoutParams(textView_produto.getLayoutParams().width, altura_tabela);
                    TableRow.LayoutParams layoutParams_preco = new TableRow.LayoutParams(textView_preco.getLayoutParams().width, altura_tabela);

                    listView_qtd_produtos_vp.setLayoutParams(layoutParams_qtd);
                    listView_nome_produtos_vp.setLayoutParams(layoutParams_nome);
                    listView_preco_produtos_vp.setLayoutParams(layoutParams_preco);


                    String total_sem_desconto = pedidos.getItem(position).getValor_total();
                    Double desconto_percentual = 0.0;

                    //verifica se tem deseconto
                    if(pedidos.getItem(position).getDesconto().equals("") || pedidos.getItem(position).getDesconto().equals("0")){
                        et_desconto_perc.setText("-");
                        et_total_ver_pedidos.setText("R$ " + total_sem_desconto);
                        et_total_com_desconto.setText("R$ "+ total_sem_desconto);
                    }
                    else{
                        //et_total_ver_pedidos.setText("R$ " + total_com_desconto);
                        desconto_percentual = 1-(Double.parseDouble(pedidos.getItem(position).getDesconto())/100);

                        //calcula valor total com desconto
                        Double total_com_desconto = Double.parseDouble(total_sem_desconto)*desconto_percentual;
                        total_com_desconto = arreedondar(total_com_desconto);

                        //calcula valor total do desconto
                        Double total_do_desconto = Double.parseDouble(total_sem_desconto)-total_com_desconto;
                        total_do_desconto = arreedondar(total_do_desconto);

                        et_total_com_desconto.setText("R$ " + total_do_desconto);
                        et_desconto_perc.setText(pedidos.getItem(position).getDesconto()+"%");

                    }



                    Controle ct = new Controle();
                    String data_pedido = ct.converter_data_para_BR(pedidos.getItem(position).getData_atual());
                    et_data_pedido.setText(data_pedido);


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }//fim do try

        //Gerencia as exceções
        catch (SQLException erro){
            AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
            mensagem.setMessage("Erro: "+erro.getMessage());
            mensagem.setPositiveButton("Ok", null);
            mensagem.show();

        }


    }


    @Override
    public void onClick(View v) {

        if (v==bt_voltar_pedido || v==imageButton_voltar_vp){
            Intent it = new Intent(this, Tela_Gerenciar_Pedidos.class);
            startActivity(it);
            finish();
        }

    }

    //Arredonda um valor para 2 casas decimais
    public Double arreedondar(Double valor) {
        BigDecimal bigDecimal = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        valor =bigDecimal.doubleValue();

        return valor;
    }

    private void mensagem(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * Gerenciador de Mensagens
     */
    public void mensagemExibir(String titulo, String texto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(
                this);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("OK", null);
        mensagem.show();
    }

}
