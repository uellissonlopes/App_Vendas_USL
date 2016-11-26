package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
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
import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Dados_Pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos_pedido;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produto;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Pedido_Produto_act extends Activity implements View.OnClickListener {

    //spinners que exibem a lista de produtos e a referencial de quantidade, que pode ser peca ou unidade
    Spinner spinner_produtos, spinner_referencia_quantidade;

    //array adapters que armazena o nome dos produtos exibidos no spinner produtos
    private ArrayAdapter<String> produtos;

    //array adapter que armazena os dados dos produtos vindos do banco de dados
    private ArrayAdapter<Produto> lista_produtos;

    //array adapter que armazena a lista de produtos do pedido
    private ArrayList<Produtos_pedido> lista_produtos_pedido = new ArrayList<Produtos_pedido>();

    //listviews que exibem os dados dos produtos do pedido
    private ListView listView_qtd_produtos, listView_preco_produtos, listView_nome_produtos;

    //array adapters que armazenam os dados dos listviews desta tela
    private ArrayAdapter<String> adp_codigo_produtos_adicionados;//nao e um listview, mas serve para armazenar o codigo que vai pro db
    private ArrayAdapter<String> qtd_produtos_adicionados;
    private ArrayAdapter<String> nome_produtos_adicionados;
    private ArrayAdapter<String> preco_produtos_adicionados;

    ArrayAdapter<String> referencia_adp = null;

    // Edittext desta tela
    //EditText et_busca;
    EditText  et_quantidade, et_valor_total_ped_produto, editText_cx_completa, et_qtd_und;//et_desconto,etCodigo_produto,
    EditText et_preco_und_prod_pedido, et_preco_pca_caixa_prod_pedido;
    EditText et_desconto_pp, et_valor_desconto_pp;

    //text view que exibe a unidade do produto, tal como cadastrado no banco de dados
    TextView textView_unidade, textView_qtd, textView_produto, textView_preco;

    //Botões da tela
    Button bt_adicionar_produto, bt_salvar_pedido, bt_voltar_produto_pedido, bt_calcular_desconto_pp;
    ImageButton imageButton_voltar_pp;

    //armazena os edittexts dos produtos
    String nome, codigo_produto, quantidade, preco, preco_original, unidade_padrao;
    String unidades_por_pecas_ou_caixa = "0";
    String unidade_quantidade = "";
    int altura_tabela = 40;



    //Dados do desconto
    Double total_sem_desconto = 0.0;
    //String desconto;

    //String que armazenam os valores dos componentes da tela anterior
    String cliente_fantasia = "";
    String codigo_cliente = "";
    String tipo_pagamento = "";


    String pecas_caixa = "";
    String unds_pecas = "";
    String preco_produto_cadastro = "";


    // variavel que controla o numero de clique
    int cliques_no_calcular = 0;

    //armazena a data do dia atual, para salvar no pedido
    String data_atual = "";

    //armazena o id do pedido
    String id_pedido = "";

    //Variáveis do banco
    BancoDeDados bd;
    SQLiteDatabase conexao;

    //Objetos
    Salvar_no_DB salvar_no_db;
    Dados_Produtos dados_produtos;
    Dados_Pedido dados_pedido;
    Dados_Produtos_pedido dados_produtos_pedido;


    //    ArrayList<String> qtd_produts_lv = new ArrayList<String>();
//    ArrayList<String> nome_produts_lv = new ArrayList<String>();
//    ArrayList<String> preco_produts_lv = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pedido_produto);


        // inicializacao dos Spinners
        spinner_produtos = (Spinner) findViewById(R.id.spinner_produtos_pp);
        spinner_referencia_quantidade = (Spinner) findViewById(R.id.spinner_referencia);


        listView_qtd_produtos = (ListView) findViewById(R.id.listView_qtd_produtos);
        listView_nome_produtos = (ListView) findViewById(R.id.listView_nome_produtos);
        listView_preco_produtos = (ListView) findViewById(R.id.listView_preco_produtos);

        //inicializa os adapters que armazena os dados exibidos nos listviews
        adp_codigo_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);//não é paa o listview
        qtd_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        nome_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        preco_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);

        //inicializa o arrayadapter de produtos
        produtos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //et_busca = (EditText) findViewById(R.id.et_busca);
        et_desconto_pp = (EditText) findViewById(R.id.et_desconto_pp);
        et_preco_pca_caixa_prod_pedido = (EditText) findViewById(R.id.et_preco_pca_caixa_prod_pedido);
        et_valor_desconto_pp = (EditText) findViewById(R.id.et_valor_desconto_pp);
        et_preco_und_prod_pedido = (EditText) findViewById(R.id.et_preco_und_prod_pedido);
        et_qtd_und = (EditText) findViewById(R.id.et_qtd_und);
        editText_cx_completa = (EditText) findViewById(R.id.editText_cx_completa);
        et_quantidade = (EditText) findViewById(R.id.et_quantidade_pro);
        et_valor_total_ped_produto = (EditText) findViewById(R.id.et_valor_total_ped_produto);

        textView_unidade = (TextView) findViewById(R.id.textView_unidade);
        textView_qtd = (TextView) findViewById(R.id.textView_qtd);
        textView_produto = (TextView) findViewById(R.id.textView_produto);
        textView_preco = (TextView) findViewById(R.id.textView_preco);

        bt_adicionar_produto = (Button) findViewById(R.id.bt_add_poduto);
        bt_salvar_pedido = (Button) findViewById(R.id.bt_salvar_pedido);
        bt_voltar_produto_pedido = (Button) findViewById(R.id.bt_voltar_produto_pedido);
        bt_calcular_desconto_pp = (Button) findViewById(R.id.bt_calcular_desconto_pp);
        imageButton_voltar_pp = (ImageButton) findViewById(R.id.imageButton_voltar_pp);

        bt_adicionar_produto.setOnClickListener(this);
        bt_salvar_pedido.setOnClickListener(this);
        bt_voltar_produto_pedido.setOnClickListener(this);
        bt_calcular_desconto_pp.setOnClickListener(this);
        imageButton_voltar_pp.setOnClickListener(this);


        //carrega os Dados que vem da outra tela
        Bundle bdl = getIntent().getExtras();
        cliente_fantasia = bdl.getString("cliente_fantasia");
        codigo_cliente = bdl.getString("codigo_cliente");
        tipo_pagamento = bdl.getString("tipo_pagamento");

        //Instancia a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        data_atual = sdf.format(data);

        //inicializa o adapter referencia
        referencia_adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);


        //cria conexão com Banco de dados
        bd = new BancoDeDados(this);
        conexao = bd.getReadableDatabase();
        preenche_spinner_produtos(conexao);

        //chamada dos metodos desta classe, declarados ao final
        gerencia_qtd_digitada();
        gerencia_desconto_digitado();
        //altera o preco da peca com base no preco da unidade
        altera_preco_da_peca();
        //apaga as quantidades quando muda a referencia de peca para unidade e vice-versa
        gerencia_qtd_pela_referencia();
        excluir_produtos();

    }//FIM DO ONCREATE



    @Override
    public void onClick(View v) {

            if (v==bt_adicionar_produto){
                boolean tem_esse_produto = false;
                Double valor_parcial = 0.0;//valor do produto multiplicado pela quantidade

                //verifica se o produto está na lista
                for (int i=0; i<listView_nome_produtos.getCount(); i++){
                    if(nome.equals(listView_nome_produtos.getItemAtPosition(i).toString())){
                        tem_esse_produto = true;
                    }
                    //mensagemExibir(listView_nome_produtos.getItemAtPosition(i).toString());

                }//fim do for

            if (tem_esse_produto){
                mensagemExibir("Este produto já está na lista!");
            }

            else{

                if (et_quantidade.getText().toString().equals("") || et_quantidade.getText().toString().equals("0")){
                    mensagemExibir("Erro!! Digite a Quantidade de Produtos");
                }

                else{
                    //pega a quatintade e aunidade padrao pra salvar no db
                    String quantidade_salva = "";


                    if (et_qtd_und.getText().toString().equals("-")){

                        if (spinner_referencia_quantidade.getSelectedItem().toString().equalsIgnoreCase("Cx")){
                            quantidade_salva = et_quantidade.getText().toString()+" "+spinner_referencia_quantidade.getSelectedItem().toString();
                            //mensagemExibir(pecas_caixa);
                            Double calcula_quantidade = Double.parseDouble(et_quantidade.getText().toString().replace(",","."))*Double.parseDouble(pecas_caixa);
                            calcula_quantidade = arreedondar(calcula_quantidade);
                            quantidade = calcula_quantidade.toString();
                        }

                        else {
                            quantidade_salva = et_quantidade.getText().toString() + " " + spinner_referencia_quantidade.getSelectedItem().toString();
                            Double calcula_quantidade = Double.parseDouble(et_quantidade.getText().toString().replace(",", ".")) * Double.parseDouble(unds_pecas);
                            calcula_quantidade = arreedondar(calcula_quantidade);
                            quantidade = calcula_quantidade.toString();
                        }
                    }

                    //esse else contepla o caso de uma linguica vendida por peca ou caixa
                    else{
                        if (spinner_referencia_quantidade.getSelectedItem().toString().equalsIgnoreCase("Cx")){
                            quantidade_salva = et_quantidade.getText().toString()+" "+spinner_referencia_quantidade.getSelectedItem().toString();
                            //mensagemExibir(pecas_caixa);
                            Double calcula_quantidade = Double.parseDouble(et_quantidade.getText().toString().replace(",","."))*Double.parseDouble(pecas_caixa);
                            calcula_quantidade = arreedondar(calcula_quantidade);
                            quantidade = calcula_quantidade.toString();
                        }
                        else{
                            quantidade_salva = et_qtd_und.getText().toString()+" "+unidade_padrao;
                            quantidade = et_qtd_und.getText().toString().replace(",", ".");
                        }

                    }

                    preco = et_preco_und_prod_pedido.getText().toString();
                    altura_tabela = altura_tabela+40;

                    //Pega o ID do PEDIDO
                    dados_pedido = new Dados_Pedido(conexao);
                    id_pedido = dados_pedido.busca_id_pedido();

                    //calcula o valor parcial do produto
                    valor_parcial = Double.parseDouble(preco) * Double.parseDouble(quantidade);// * Double.parseDouble(unidades_por_pecas_ou_caixa);
                    valor_parcial =arreedondar(valor_parcial);

                    String qtd_produto_list_view = quantidade_salva;
                    String nome_produto_list_view = nome;
                    String preco_produto_list_view = "R$ "+valor_parcial.toString();

                    qtd_produtos_adicionados.add(qtd_produto_list_view);
                    nome_produtos_adicionados.add(nome_produto_list_view);
                    preco_produtos_adicionados.add(preco_produto_list_view);


                    //Adiciona os arrayadapters aos listviews
                    listView_qtd_produtos.setAdapter(qtd_produtos_adicionados);
                    listView_nome_produtos.setAdapter(nome_produtos_adicionados);
                    listView_preco_produtos.setAdapter(preco_produtos_adicionados);

                    //alteracao do tamanho do listview
                    TableRow.LayoutParams layoutParams_qtd = new TableRow.LayoutParams(textView_qtd.getLayoutParams().width, altura_tabela);
                    TableRow.LayoutParams layoutParams_nome = new TableRow.LayoutParams(textView_produto.getLayoutParams().width, altura_tabela);
                    TableRow.LayoutParams layoutParams_preco = new TableRow.LayoutParams(textView_preco.getLayoutParams().width, altura_tabela);

                    listView_qtd_produtos.setLayoutParams(layoutParams_qtd);
                    listView_nome_produtos.setLayoutParams(layoutParams_nome);
                    listView_preco_produtos.setLayoutParams(layoutParams_preco);


                    String codigo_produto_lista = codigo_produto;
                    String nome_produto_lista = nome;
                    String quantidade_lista = quantidade_salva;
                    String preco_total_produtos = "R$ "+valor_parcial.toString();


                    //desconto.toString() agora é 0 e dado no fim do pedido
                    Produtos_pedido pp = new Produtos_pedido(id_pedido, codigo_produto_lista, nome_produto_lista,
                            quantidade_lista, "0", preco_total_produtos);
                    lista_produtos_pedido.add(pp);

                    total_sem_desconto = total_sem_desconto+valor_parcial;
                    total_sem_desconto =arreedondar(total_sem_desconto);

                    et_valor_total_ped_produto.setText("R$ " + total_sem_desconto.toString());

                    et_quantidade.setText("");
                    et_qtd_und.setText("-");

                }

            }
        }

        if (v==bt_calcular_desconto_pp) {

            bt_calcular_desconto_pp.setEnabled(false);

            //bt_salvar_pedido.setEnabled(true);

            //verifica se foi informado o valor do desconto
            if (et_desconto_pp.getText().toString().equals("") || et_desconto_pp.getText().toString().equals("0")){
                mensagemExibir("Pedido sem desconto!");
            }
            else {
                //formata o operador para calcular o deconto
                Double desconto_decimal=1-(Double.parseDouble(et_desconto_pp.getText().toString())/100);

                Double preco_desconto=0.0;

                //calcula o valor total do pedido com o desconto
                Double total_pedido_desconto = desconto_decimal*Double.parseDouble(et_valor_total_ped_produto.getText().toString().substring(3, et_valor_total_ped_produto.getText().toString().length()));
                total_pedido_desconto = arreedondar(total_pedido_desconto);

                //calcula o valor do desconto
                Double  total_desconto = total_sem_desconto-total_pedido_desconto;
                total_desconto = arreedondar(total_desconto);

                //atualiza os edittexts com o valor do desconto
                et_valor_total_ped_produto.setText("R$ "+total_pedido_desconto.toString());
                et_valor_desconto_pp.setText("R$ "+total_desconto.toString());

            }
        }



        if (v==bt_salvar_pedido){

            bt_salvar_pedido.setEnabled(false);

            //tira o "R$ " do valor que será salvo no DB
            for (int i = 0; i<lista_produtos_pedido.size(); i++){
                lista_produtos_pedido.get(i).setPreco_total(lista_produtos_pedido.get(i).getPreco_total().substring(3, lista_produtos_pedido.get(i).getPreco_total().toString().length()).replace("/n",""));
            }


            String desconto_do_pedido = "0";//desconto percentual

            //se o pedido tem desconto eu pedo o valor do desconto
            if (!et_desconto_pp.getText().toString().equals("")){
                desconto_do_pedido = et_desconto_pp.getText().toString();
            }

            //salva o total sem desconto
            Pedido pedido = new Pedido(id_pedido, cliente_fantasia, codigo_cliente, tipo_pagamento, data_atual, "produtos db",
                                        total_sem_desconto.toString(), desconto_do_pedido);


            try {
                //cria conexão com db
                bd = new BancoDeDados(this);
                conexao = bd.getWritableDatabase();
                bd.criar_tb_pedidos(conexao);
                bd.criar_tb_produtos_pedidos(conexao);

                //salva dados do pedido
                salvar_no_db = new Salvar_no_DB(conexao);

                salvar_no_db.salvar_pedido(pedido, lista_produtos_pedido);

                mensagemExibir("Pedido salvo com SUCESSO!");

                Intent it = new Intent(this, Pedido_Cliente_act.class);
                startActivity(it);
                finish();


            }
            //gerenciamento de excessões
            catch (SQLException erro){
                mensagemExibir("Erro ao salvar : " + erro.getMessage());

            }

        }


        if (v==bt_voltar_produto_pedido || v==imageButton_voltar_pp){
            Intent it = new Intent(this, Pedido_Cliente_act.class);
            startActivity(it);
            finish();
        }



    }


    /*
    Insere os pdodutos do banco de dados no spinner produtos
     */
    public void preenche_spinner_produtos(SQLiteDatabase conexao_db){

        dados_produtos = new Dados_Produtos(conexao_db);

        //busca produtos armazenados no db e insere no array adapter
        lista_produtos = dados_produtos.busca_produtos_disponiveis(this);

        if(lista_produtos==null){
            mensagemExibir("Não Existem Produtos Cadastrados!");
        }

        else{
            for (int i = 0; i < lista_produtos.getCount(); i++) {
                produtos.add(lista_produtos.getItem(i).getCodigo_produto()+" - "+lista_produtos.getItem(i).getNome().toString()+" - R$ "+lista_produtos.getItem(i).getPreco().toString());
            }

            //adiciona produtos ao spinner
            spinner_produtos.setAdapter(produtos);

            //Evento que muda os edittext de acordo como os produtos selecionados
            spinner_produtos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    //etCodigo_produto.setText(lista_produtos.getItem(position).getCodigo_produto().toString());
                    codigo_produto = lista_produtos.getItem(position).getCodigo_produto().toString();
                    //preco unidade
                    et_preco_und_prod_pedido.setText(lista_produtos.getItem(position).getPreco().toString().replace(",", "."));

                    // et_desconto.setText("0.0");
                    preco_original = et_preco_und_prod_pedido.getText().toString();

                    nome = lista_produtos.getItem(position).getNome().toString();
                    // mensagemExibir(nome);

                    unidades_por_pecas_ou_caixa = lista_produtos.getItem(position).getUnds_peca().replace(",", ".");
                    editText_cx_completa.setText(lista_produtos.getItem(position).getCompleto());
                    unidade_padrao = lista_produtos.getItem(position).getUnidade();
                    textView_unidade.setText("-" + unidade_padrao);
                    et_quantidade.setText("");
                    et_qtd_und.setText("-");

                    pecas_caixa = lista_produtos.getItem(position).getPecas_caixa().replace(",", ".");
                    unds_pecas = lista_produtos.getItem(position).getUnds_peca().replace(",", ".");
                    preco_produto_cadastro = lista_produtos.getItem(position).getPreco().replace(",", ".");

                   if (pecas_caixa.equals("1") && unds_pecas.equals("1")) {
                        et_preco_und_prod_pedido.setText(preco_produto_cadastro);
                        et_preco_pca_caixa_prod_pedido.setText(preco_produto_cadastro);
                        unidade_quantidade = unidade_padrao;
                        referencia_adp.clear();
                        referencia_adp.add(unidade_padrao);
                        spinner_referencia_quantidade.setAdapter(referencia_adp);
                    }

                    else{
                        if (unds_pecas.equals("1") && nome!=null && nome.length()>5){
                            if (nome.substring(0,6).equalsIgnoreCase("LINGUI")||nome.substring(0,6).equalsIgnoreCase("LINGÜI")){
                                unidade_quantidade = unidade_padrao;
                            }

                            et_preco_und_prod_pedido.setText(preco_produto_cadastro);
                            Double preco_pca_caixa = Double.parseDouble(preco_produto_cadastro)*Double.parseDouble(pecas_caixa);
                            preco_pca_caixa = arreedondar(preco_pca_caixa);
                            et_preco_pca_caixa_prod_pedido.setText(preco_pca_caixa.toString());

                            referencia_adp.clear();
                            referencia_adp.add("Cx");
                            referencia_adp.add(unidade_padrao);
                            spinner_referencia_quantidade.setAdapter(referencia_adp);

                        }//if unds_peca = 1

                        else{
                            if(nome!=null && nome.length()>5){

                                if (nome.substring(0,6).equalsIgnoreCase("LINGUI")||nome.substring(0,6).equalsIgnoreCase("LINGÜI")){
                                    unidade_quantidade = unidade_padrao;
                                }//if linguica no else pc_cx e unds_pca diferete de 1

                                et_preco_und_prod_pedido.setText(preco_produto_cadastro);
                                Double preco_pca_caixa = Double.parseDouble(preco_produto_cadastro)*Double.parseDouble(unds_pecas);
                                preco_pca_caixa = arreedondar(preco_pca_caixa);
                                et_preco_pca_caixa_prod_pedido.setText(preco_pca_caixa.toString());

                                referencia_adp.clear();
                                referencia_adp.add("Pç");
                                //referencia_adp.add(unidade_padrao);
                                spinner_referencia_quantidade.setAdapter(referencia_adp);
                            }

                            else{
                                et_preco_und_prod_pedido.setText(preco_produto_cadastro);
                                Double preco_pca_caixa = Double.parseDouble(preco_produto_cadastro)*Double.parseDouble(unds_pecas);
                                preco_pca_caixa = arreedondar(preco_pca_caixa);
                                et_preco_pca_caixa_prod_pedido.setText(preco_pca_caixa.toString());

                                referencia_adp.clear();
                                referencia_adp.add("Pç");
                                //referencia_adp.add(unidade_padrao);
                                spinner_referencia_quantidade.setAdapter(referencia_adp);
                            }
                        }
                    }//else unds_peca = 1



//                    //preenche o preço da caixa
//                    Double preco_caixa = Double.parseDouble(lista_produtos.getItem(position).getPreco().toString().replace(",", ".")) * Double.parseDouble(lista_produtos.getItem(position).getUnds_peca().replace(",", "."));
//                    preco_caixa = arreedondar(preco_caixa);
//                    et_preco_pca_caixa_prod_pedido.setText(preco_caixa.toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }



    //gerencia quantidade digitada no edittext
    public void gerencia_qtd_digitada(){
        et_quantidade.setOnKeyListener(new View.OnKeyListener() {
            String qtd_digitada = "";
            Double calculo_quant=0.0;
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et_quantidade.setText("");
                    et_qtd_und.setText("-");
                }

                qtd_digitada = et_quantidade.getText().toString();

                if (qtd_digitada.equals("")) {
                    et_qtd_und.setText("-");
                }
                else {

                    if(nome!=null && nome.length()>5) {

                        if (nome.substring(0, 6).equalsIgnoreCase("LINGUI") || nome.substring(0, 6).equalsIgnoreCase("LINGÜI")) {
                            if (spinner_referencia_quantidade.getSelectedItem().equals(unidade_padrao)) {
                                //et_quantidade.setText("");
                                et_qtd_und.setText("-");
                            } else {
                                if (spinner_referencia_quantidade.getSelectedItem().toString().equalsIgnoreCase("CX")) {
                                    calculo_quant = Double.parseDouble(qtd_digitada) * Double.parseDouble(pecas_caixa);
                                    calculo_quant = arreedondar(calculo_quant);
                                    et_qtd_und.setText(calculo_quant.toString());
                                } else {
                                    calculo_quant = Double.parseDouble(qtd_digitada) * Double.parseDouble(unds_pecas);
                                    calculo_quant = arreedondar(calculo_quant);
                                    et_qtd_und.setText(calculo_quant.toString());
                                }


                                Double calcula_quantidade = Double.parseDouble(et_quantidade.getText().toString()) * Double.parseDouble(unds_pecas);
                                calcula_quantidade = arreedondar(calcula_quantidade);
                                et_qtd_und.setText(calcula_quantidade.toString());
                            }//else que ver a possibiidade se for linguica e for vendido por peca ou caixa

                        } else {
                            //et_quantidade.setText("");
                            et_qtd_und.setText("-");
                        }
                    }//if lenth >5
//                    if (spinner_referencia_quantidade.getSelectedItem().toString().equals("Caixa")) {
//                        Double qdo_pecas_digitadas = Double.parseDouble(qtd_digitada) * Double.parseDouble(unidades_por_pecas);
//                        et_qtd_und.setText(qdo_pecas_digitadas.toString());
//                    }

                }

                return false;
            }
        });

    }

    //permite controlar o calculo do desonto
    public void gerencia_desconto_digitado(){
        et_desconto_pp.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et_desconto_pp.setText("");
                    bt_calcular_desconto_pp.setEnabled(false);
                }

                else{
                    if (cliques_no_calcular==0) {
                        cliques_no_calcular = 1;
                        bt_calcular_desconto_pp.setEnabled(true);
                    }
                }

                return false;
            }
        });
    }

    //Altera o preço da caixa de acordo com o preço digitado
    public void altera_preco_da_peca(){
        et_preco_und_prod_pedido.setOnKeyListener(new View.OnKeyListener() {
            String preco_digitado = "";

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                preco_digitado = et_preco_und_prod_pedido.getText().toString();

                //mensagemExibir(String.valueOf(keyCode)+"del "+String.valueOf(KeyEvent.KEYCODE_DEL));

                if (preco_digitado.length()<2){
                    mensagemExibir("Digite o preço! Ex.: 21.65");
                    et_preco_pca_caixa_prod_pedido.setText("");
                }

                else{

                    if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        preco_digitado = preco_digitado.substring(0, preco_digitado.length() - 1);
                        Double preco_pecas_digitadas = Double.parseDouble(preco_digitado) * Double.parseDouble(unidades_por_pecas_ou_caixa);

                        preco_pecas_digitadas = arreedondar(preco_pecas_digitadas);
                        et_preco_pca_caixa_prod_pedido.setText(preco_pecas_digitadas.toString());
                    }

                    else {
                        Double preco_pecas_digitadas = Double.parseDouble(preco_digitado) * Double.parseDouble(unidades_por_pecas_ou_caixa);
                        preco_pecas_digitadas = arreedondar(preco_pecas_digitadas);
                        et_preco_pca_caixa_prod_pedido.setText(preco_pecas_digitadas.toString());

                    }
                }

                return false;
            }
        });

    }


    //apaga as quantidades quando muda a referencia de peca para unidade e vice-versa
    public void gerencia_qtd_pela_referencia(){
        //Evento que agora a quantidade quando muda a referencia
        spinner_referencia_quantidade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_quantidade.setText("");
                et_qtd_und.setText("-");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //Permite excluir produtos da lista
    public void excluir_produtos(){
        listView_nome_produtos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Double valor_produto_excluido = Double.parseDouble(listView_preco_produtos.getItemAtPosition(position).toString().substring(3, listView_preco_produtos.getItemAtPosition(position).toString().length()));

            //mensagemExibir(valor_produto_excluido.toString());
            total_sem_desconto = total_sem_desconto - valor_produto_excluido;
            total_sem_desconto = arreedondar(total_sem_desconto);
            et_valor_total_ped_produto.setText("R$ " + total_sem_desconto.toString());

            qtd_produtos_adicionados.remove(qtd_produtos_adicionados.getItem(position).toString());
            nome_produtos_adicionados.remove(nome_produtos_adicionados.getItem(position).toString());
            preco_produtos_adicionados.remove(preco_produtos_adicionados.getItem(position).toString());

            listView_qtd_produtos.setAdapter(qtd_produtos_adicionados);
            listView_nome_produtos.setAdapter(nome_produtos_adicionados);
            listView_preco_produtos.setAdapter(preco_produtos_adicionados);

            lista_produtos_pedido.remove(position);

            }
        });
    }

    /**
     * Gerenciador de Mensagens
     */
    private void mensagemExibir(String msg){
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //Arredonda um valor para 2 casas decimais
    public Double arreedondar(Double valor) {
        BigDecimal bigDecimal = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        valor =bigDecimal.doubleValue();

        return valor;
    }

}
