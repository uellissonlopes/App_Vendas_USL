package com.made.uellisson.agrofrios24.activityes;

import android.app.Activity;
import android.content.Intent;
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

import com.made.uellisson.agrofrios24.R;
import com.made.uellisson.agrofrios24.controle.Controle;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produto;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Uellisson on 28/02/2016.
 *
 * * Classe que gerencia a tela Editar clientes, carregada a partir do layout
 * editar_cliente_end.xml
 */
public class Editar_Pedido_Produto_up extends Activity implements View.OnClickListener{


    //Declaracao dos componentes da tela
    private Spinner spinner_produtos_app, spinner_referencia_app;
    private EditText et_quantidade_app, et_qtd_und_app, et_preco_und_app, et_preco_pc_cx_app;
    private EditText  editText_cx_completa_app, et_desconto_app, et_valor_desconto_app, et_valor_total_app;
    private TextView textView_unidade, textView_qtd, textView_produto, textView_preco;
    private Button bt_add_poduto_app, bt_voltar_app, bt_salvar_pedido_app, bt_calcular_desconto_app;
    private ImageButton imageButton_voltar_app;
    private ListView listView_qtd_produtos_app, listView_nome_produtos_app, listView_preco_produtos_app;

    //declaracao e istanciacao da variavel que controla a altura da lista que exibe os produtos
    int altura_lista = 40;


    //declaracao dos array adapters que gerenciam o spinner
    private ArrayAdapter<String> produtos;
    private ArrayAdapter<Produto> lista_produtos;
    private ArrayList<Produtos_pedido> lista_produtos_pedido;


    //declaracao dos ArrayAdapters e Strings que armazenam os valores dos componentes da tela anterior
    private ArrayAdapter<String> adp_codigo_produtos_adicionados;
    private ArrayAdapter<String> adp_qtd_produtos_adicionados;
    private ArrayAdapter<String> adp_nome_produtos_adicionados;
    private ArrayAdapter<String> adp_preco_produtos_adicionados;
    ArrayAdapter<String> referencia_adp = null;
    String cliente_fantasia="";
    String codigo_cliente="";
    String tipo_pagamento="";
    String data_pedido = "";
    String pecas_caixa = "";
    String unds_pecas = "";
    String preco_produto_cadastro = "";


    //delcaracao das String que armazena os edittexts dos produtos
    String nome, codigo_produto, quantidade, preco, preco_original, unidade_padrao, total_pedido_anterior;
    String unidades_por_pecas="0";

    //Declaraca e inicializacao das variaveis que controla o desconto
    Double total_sem_desconto = 0.0;
    String desconto="0.0";

    //variavel que armazena do id do pedido
    String id_pedido = "";

    // variavel que controla o numero de clique
    int cliques_no_calcular = 0;

    //vairavel que controla a alteracao
    boolean alterou = false;

    //Declaracao do objeto controle utilizado na classe.
    Controle controle;


    /**
     * Metodo que carrega o layout e inicializa seus componentes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_pedido_produto_up);


       spinner_produtos_app = (Spinner) findViewById(R.id.spinner_produtos_app);
        spinner_referencia_app = (Spinner) findViewById(R.id.spinner_referencia_app);

        et_quantidade_app = (EditText) findViewById(R.id.et_quantidade_app);
        et_qtd_und_app = (EditText) findViewById(R.id.et_qtd_und_app);
        et_preco_und_app = (EditText) findViewById(R.id.et_preco_und_app);
        et_preco_pc_cx_app = (EditText) findViewById(R.id.et_preco_pc_cx_app);
        et_valor_total_app = (EditText) findViewById(R.id.et_valor_total_app);
        et_desconto_app = (EditText) findViewById(R.id.et_desconto_app);
        et_valor_desconto_app = (EditText) findViewById(R.id.et_valor_desconto_app);
        editText_cx_completa_app = (EditText) findViewById(R.id.editText_cx_completa_app);

        textView_unidade = (TextView) findViewById(R.id.textView_unidade);
        textView_qtd = (TextView) findViewById(R.id.textView_qtd);
        textView_produto = (TextView) findViewById(R.id.textView_produto);
        textView_preco = (TextView) findViewById(R.id.textView_preco);

        listView_qtd_produtos_app = (ListView) findViewById(R.id.listView_qtd_produtos_app);
        listView_nome_produtos_app = (ListView) findViewById(R.id.listView_nome_produtos_app);
        listView_preco_produtos_app = (ListView) findViewById(R.id.listView_preco_produtos_app);

        bt_add_poduto_app = (Button) findViewById(R.id.bt_add_poduto_app);
        bt_calcular_desconto_app = (Button) findViewById(R.id.bt_calcular_desconto_app);
        bt_salvar_pedido_app = (Button) findViewById(R.id.bt_salvar_pedido_app);
        bt_voltar_app = (Button) findViewById(R.id.bt_voltar_app);
        imageButton_voltar_app = (ImageButton) findViewById(R.id.imageButton_voltar_app);

        bt_add_poduto_app.setOnClickListener(this);
        bt_salvar_pedido_app.setOnClickListener(this);
        bt_voltar_app.setOnClickListener(this);
        imageButton_voltar_app.setOnClickListener(this);
        bt_calcular_desconto_app.setOnClickListener(this);

        //istanciacao dos array adapters e listas
        produtos = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        lista_produtos = new ArrayAdapter<Produto>(this, android.R.layout.simple_list_item_1);
        lista_produtos_pedido = new ArrayList<Produtos_pedido>();

        //inicializacao do controle
        controle = new Controle();

        //Instancia a data atual
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        data_pedido = sdf.format(data);

        //adiciona a referencia da quantidade ao spinner
        referencia_adp = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        //busca pedidos no banco de dados e insere no array adapter
        if (controle.buscar_todos_pedidos(this)!=null){
            lista_produtos = controle.busca_produtos_disponiveis(this);
            preenche_spinner_produtos();
        }
        //se não tiver pedidos avisa ao cliente
        else{
            controle.mensagem_simples(this, "Não existem pprodutos disponiveis!");
        }

        //carrega os dados do pedido anterior
        carreg_pedido();

        //chamada dos metodos desta classe, declarados ao final
        gerencia_qtd_digitada();
        gerencia_desconto_digitado();

        //altera o preco da peca com base no preco da unidade
        altera_preco_peca();

        //apaga as quantidades quando muda a referencia de peca para unidade e vice-versa
        gerencia_qtd_pela_referencia();
        excluir_produtos();
    }


    /**
     * Metodo que gerencia o click nos botoes da tela.
     * @param v
     */
    @Override
    public void onClick(View v) {

        if (v==bt_add_poduto_app){

            alterou = true;
            if (v==bt_add_poduto_app){
                boolean tem_esse_produto = false;
                Double valor_parcial = 0.0;//valor do produto multiplicado pela quantidade

                //verifica se o produto está na lista
                for (int i=0; i<listView_nome_produtos_app.getCount(); i++){
                    if(nome.equals(listView_nome_produtos_app.getItemAtPosition(i).toString().substring(1,listView_nome_produtos_app.getItemAtPosition(i).toString().length()))){
                        tem_esse_produto = true;
                    }

                }//fim do for

                if (tem_esse_produto){
                    controle.mensagem_simples(this, "Este produto já está na lista!");
                }

                else{

                    //pega a quantidade digitada
                    quantidade = et_quantidade_app.getText().toString();

                    if (quantidade.equals("") || quantidade.equals("0")){
                        controle.mensagem_simples(this, "Erro!! Digite a Quantidade de Produtos");
                    }

                    else{
                        //pega a quantidade e a unidade padrao pra salvar no db
                        String quantidade_salva = et_quantidade_app.getText().toString()+" "+unidade_padrao;

                        if (et_qtd_und_app.getText().toString().equals("-")){
                            if (spinner_referencia_app.getSelectedItem().toString().equalsIgnoreCase("Cx")){
                                quantidade_salva = et_quantidade_app.getText().toString()+" "+spinner_referencia_app.getSelectedItem().toString();
                                //mensagemExibir(pecas_caixa);
                                Double calcula_quantidade = Double.parseDouble(et_quantidade_app.getText().toString().replace(",","."))*Double.parseDouble(pecas_caixa);
                                calcula_quantidade = controle.arreedondar(calcula_quantidade);
                                quantidade = calcula_quantidade.toString();
                            }

                            else {
                                quantidade_salva = et_quantidade_app.getText().toString() + " " + spinner_referencia_app.getSelectedItem().toString();
                                Double calcula_quantidade = Double.parseDouble(et_quantidade_app.getText().toString().replace(",", ".")) * Double.parseDouble(unds_pecas);
                                calcula_quantidade = controle.arreedondar(calcula_quantidade);
                                quantidade = calcula_quantidade.toString();
                            }
                        }

                        //esse else contepla o caso de uma linguica vendida por peca ou caixa
                        else{
                            if (spinner_referencia_app.getSelectedItem().toString().equalsIgnoreCase("Cx")){
                                quantidade_salva = et_quantidade_app.getText().toString()+" "+spinner_referencia_app.getSelectedItem().toString();
                                //mensagemExibir(pecas_caixa);
                                Double calcula_quantidade = Double.parseDouble(et_quantidade_app.getText().toString().replace(",","."))*Double.parseDouble(pecas_caixa);
                                calcula_quantidade = controle.arreedondar(calcula_quantidade);
                                quantidade = calcula_quantidade.toString();
                            }
                            else{
                                quantidade_salva = et_qtd_und_app.getText().toString()+" "+unidade_padrao;
                                quantidade = et_qtd_und_app.getText().toString().replace(",", ".");
                            }

                        }

                        preco = et_preco_und_app.getText().toString();
                        altura_lista = altura_lista+40;

                        //calcula o valor parcial do produto
                        valor_parcial = Double.parseDouble(preco) * Double.parseDouble(quantidade);
                        valor_parcial = controle.arreedondar(valor_parcial);

                        String qtd_produto_list_view = quantidade_salva;
                        String nome_produto_list_view = nome;
                        String preco_produto_list_view = "R$ "+valor_parcial.toString();

                        adp_qtd_produtos_adicionados.add(qtd_produto_list_view);
                        adp_nome_produtos_adicionados.add(nome_produto_list_view);
                        adp_preco_produtos_adicionados.add(preco_produto_list_view);

                        //alteracao do tamanho do listview
                        TableRow.LayoutParams layoutParams_qtd = new TableRow.LayoutParams(textView_qtd.getLayoutParams().width, altura_lista);
                        TableRow.LayoutParams layoutParams_nome = new TableRow.LayoutParams(textView_produto.getLayoutParams().width, altura_lista);
                        TableRow.LayoutParams layoutParams_preco = new TableRow.LayoutParams(textView_preco.getLayoutParams().width, altura_lista);

                        listView_qtd_produtos_app.setLayoutParams(layoutParams_qtd);
                        listView_nome_produtos_app.setLayoutParams(layoutParams_nome);
                        listView_preco_produtos_app.setLayoutParams(layoutParams_preco);


                        //Adiciona os arrayadapters aos listviews
                        listView_qtd_produtos_app.setAdapter(adp_qtd_produtos_adicionados);
                        listView_nome_produtos_app.setAdapter(adp_nome_produtos_adicionados);
                        listView_preco_produtos_app.setAdapter(adp_preco_produtos_adicionados);

                        String codigo_produto_lista = codigo_produto;
                        adp_codigo_produtos_adicionados.add(codigo_produto_lista);

                        String nome_produto_lista = nome.replace("\n", "");
                        String quantidade_lista = quantidade_salva.replace("\n", "");
                        String preco_total_produtos = valor_parcial.toString();//"R$ "+valor_parcial.toString();

                        //desconto.toString() agora é 0 e dado no fim do pedido
                        Produtos_pedido pp = new Produtos_pedido(id_pedido, codigo_produto_lista, nome_produto_lista,
                                quantidade_lista, "0", preco_total_produtos.replace("\n", ""));
                        lista_produtos_pedido.add(pp);

                        total_sem_desconto = total_sem_desconto+valor_parcial;
                        total_sem_desconto = controle.arreedondar(total_sem_desconto);

                        et_valor_total_app.setText("R$ " + total_sem_desconto.toString());

                        et_quantidade_app.setText("");
                        et_qtd_und_app.setText("");

                        et_desconto_app.setText("");
                        et_valor_desconto_app.setText("");
                        //et_desconto_app.setBackgroundColor(255);

                    }

                }
            }
        }

        if (v==bt_calcular_desconto_app) {
            alterou = true;
            bt_calcular_desconto_app.setEnabled(false);
            et_desconto_app.setEnabled(false);

            //bt_salvar_pedido.setEnabled(true);

            //verifica se foi informado o valor do desconto
            if (et_desconto_app.getText().toString().equals("")){
                controle.mensagem_simples(this, "Pedido sem desconto!");
            }
            else {
                //formata o operador para calcular o deconto
                Double desconto_decimal=1-(Double.parseDouble(et_desconto_app.getText().toString())/100);

                if (et_desconto_app.getText().toString().equals("0")){
                    desconto_decimal=1.0;
                }

                Double preco_desconto=0.0;

                //calcula o valor total do pedido com o desconto
                Double total_pedido_com_desconto = desconto_decimal*total_sem_desconto;
                total_pedido_com_desconto = controle.arreedondar(total_pedido_com_desconto);

                //calcula o valor do desconto
                Double total_desconto = total_sem_desconto-total_pedido_com_desconto;
                total_desconto = controle.arreedondar(total_desconto);

                //atualiza os edittexts com o valor do desconto
                et_valor_total_app.setText("R$ " + total_pedido_com_desconto.toString());
                et_valor_desconto_app.setText("R$ " + total_desconto.toString());


            }
        }


        if (v==bt_salvar_pedido_app){

            bt_salvar_pedido_app.setEnabled(false);

            if (alterou){

                desconto = et_desconto_app.getText().toString();

                Pedido pedido = new Pedido(id_pedido, cliente_fantasia, codigo_cliente, tipo_pagamento, data_pedido, "produtos_db",
                                                total_sem_desconto.toString(), desconto);


                    if (controle.editar_pedido(pedido, lista_produtos_pedido, this)){
                        controle.mensagem_simples(this, "Pedido editado com SUCESSO!");
                    }
                    else {
                        controle.mensagem_simples(this, "ERRO ao editar o pedido");
                    }

                    Intent it = new Intent(this, Editar_Pedido_Cliente.class);
                    startActivity(it);
                    finish();

                }

            //mensagem exibida se cliente nao alterar pedido
            else{
                controle.mensagem_simples(this, "Você deve alterar o pedido antes de Salvar!");
            }
        }

        if (v==bt_voltar_app || v==imageButton_voltar_app){
            Intent it = new Intent(this, Editar_Pedido_Cliente.class);
            startActivity(it);
            finish();
        }

    }


    /**
     * Carrega os dados do pedido anterior
     */
    public void carreg_pedido(){

        Produtos_pedido pp;

        //Dados que vem da outra tela
        Bundle bdl = getIntent().getExtras();
        ArrayList<String> lista_codigo_produto_list_view = bdl.getStringArrayList("lista_codigo_produto_list_view");
        ArrayList<String> lista_qtd_produto_list_view = bdl.getStringArrayList("lista_qtd_produto_list_view");
        ArrayList<String> lista_nome_produto_list_view = bdl.getStringArrayList("lista_nome_produto_list_view");
        ArrayList<String> lista_preco_produto_list_view = bdl.getStringArrayList("lista_preco_produto_list_view");

        id_pedido = bdl.getString("id_pedido");
        cliente_fantasia= bdl.getString("cliente_fantasia");
        codigo_cliente= bdl.getString("codigo_cliente");
        //data_pedido = bdl.getString("data_pedido");
        tipo_pagamento = bdl.getString("tipo_pagamento");
        desconto = bdl.getString("desconto");
        total_pedido_anterior = bdl.getString("total_pedido");
        if(desconto.equals("")){
            desconto="0";
        }

        et_desconto_app.setText(desconto);
        Double desconto_decimal = Double.parseDouble(desconto)/100;
        Double valor_desconto = desconto_decimal*Double.parseDouble(total_pedido_anterior);
        valor_desconto = controle.arreedondar(valor_desconto);
        et_valor_desconto_app.setText(valor_desconto.toString());

        total_sem_desconto = Double.parseDouble(total_pedido_anterior);


        Double total_com_desconto = total_sem_desconto-valor_desconto;
        total_com_desconto = controle.arreedondar(total_com_desconto);
        et_valor_total_app.setText("R$ "+total_com_desconto);


        //istanciação dos array adapteres que vão receber os dados dos produtos do pedido que será alterado
        adp_codigo_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        adp_qtd_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        adp_nome_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);
        adp_preco_produtos_adicionados = new ArrayAdapter<String>(this, android.R.layout.test_list_item);


        if (lista_qtd_produto_list_view!=null){
            for (int i=0; i<lista_qtd_produto_list_view.size(); i++){
                adp_codigo_produtos_adicionados.add(lista_codigo_produto_list_view.get(i));
                adp_qtd_produtos_adicionados.add(lista_qtd_produto_list_view.get(i));
                adp_nome_produtos_adicionados.add(lista_nome_produto_list_view.get(i));
                adp_preco_produtos_adicionados.add("R$ "+lista_preco_produto_list_view.get(i));

                pp = new Produtos_pedido(id_pedido, lista_codigo_produto_list_view.get(i), lista_nome_produto_list_view.get(i),
                                        lista_qtd_produto_list_view.get(i), "0", lista_preco_produto_list_view.get(i));

                lista_produtos_pedido.add(pp);

                altura_lista = altura_lista+40;
            }

        }
        //alteracao do tamanho do listview
        TableRow.LayoutParams layoutParams_qtd = new TableRow.LayoutParams(textView_qtd.getLayoutParams().width, altura_lista);
        TableRow.LayoutParams layoutParams_nome = new TableRow.LayoutParams(textView_produto.getLayoutParams().width, altura_lista);
        TableRow.LayoutParams layoutParams_preco = new TableRow.LayoutParams(textView_preco.getLayoutParams().width, altura_lista);

        listView_qtd_produtos_app.setLayoutParams(layoutParams_qtd);
        listView_nome_produtos_app.setLayoutParams(layoutParams_nome);
        listView_preco_produtos_app.setLayoutParams(layoutParams_preco);


        listView_qtd_produtos_app.setAdapter(adp_qtd_produtos_adicionados);
        listView_nome_produtos_app.setAdapter(adp_nome_produtos_adicionados);
        listView_preco_produtos_app.setAdapter(adp_preco_produtos_adicionados);

    }

    /**
     *Insere os produtos disponiveis no spinner produtos
    */
    public void preenche_spinner_produtos(){

        if (lista_produtos!=null){
            for (int i = 0; i < lista_produtos.getCount(); i++) {
                produtos.add(lista_produtos.getItem(i).getCodigo_produto()+" - "+lista_produtos.getItem(i).getNome());
            }
        }

        //adiciona produtos ao spinner
        spinner_produtos_app.setAdapter(produtos);

        //Evento que muda os edittext de acordo como os produtos selecionados
        spinner_produtos_app.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //etCodigo_produto.setText(lista_produtos.getItem(position).getCodigo_produto().toString());
                codigo_produto = lista_produtos.getItem(position).getCodigo_produto().toString();
                et_preco_und_app.setText(lista_produtos.getItem(position).getPreco().toString().replace(",","."));

                // et_desconto.setText("0.0");
                preco_original = et_preco_und_app.getText().toString();

                nome = lista_produtos.getItem(position).getNome().toString();

                unidades_por_pecas = lista_produtos.getItem(position).getUnds_peca().replace(",", ".");
                editText_cx_completa_app.setText(lista_produtos.getItem(position).getCompleto());
                unidade_padrao = lista_produtos.getItem(position).getUnidade();
                textView_unidade.setText("-" + unidade_padrao);
                et_quantidade_app.setText("");
                et_qtd_und_app.setText("");

                pecas_caixa = lista_produtos.getItem(position).getPecas_caixa().replace(",", ".");
                unds_pecas = lista_produtos.getItem(position).getUnds_peca().replace(",", ".");
                preco_produto_cadastro = lista_produtos.getItem(position).getPreco().replace(",", ".");

                String unidade_quantidade = "";

                if (pecas_caixa.equals("1") && unds_pecas.equals("1")) {
                    et_preco_und_app.setText(preco_produto_cadastro);
                    et_preco_pc_cx_app.setText(preco_produto_cadastro);
                    unidade_quantidade = unidade_padrao;
                    referencia_adp.clear();
                    referencia_adp.add(unidade_padrao);
                    spinner_referencia_app.setAdapter(referencia_adp);
                }

                else{
                    if (unds_pecas.equals("1") && nome!=null && nome.length()>5){
                        if (nome.substring(0,6).equalsIgnoreCase("LINGUI")||nome.substring(0,6).equalsIgnoreCase("LINGÜI")){
                            unidade_quantidade = unidade_padrao;
                        }

                        et_preco_und_app.setText(preco_produto_cadastro);
                        Double preco_pca_caixa = Double.parseDouble(preco_produto_cadastro)*Double.parseDouble(pecas_caixa);
                        preco_pca_caixa = controle.arreedondar(preco_pca_caixa);
                        et_preco_pc_cx_app.setText(preco_pca_caixa.toString());

                        referencia_adp.clear();
                        referencia_adp.add("Cx");
                        referencia_adp.add(unidade_padrao);
                        spinner_referencia_app.setAdapter(referencia_adp);

                    }//if unds_peca = 1

                    else{
                        if(nome!=null && nome.length()>5){

                            if (nome.substring(0,6).equalsIgnoreCase("LINGUI")||nome.substring(0,6).equalsIgnoreCase("LINGÜI")){
                                unidade_quantidade = unidade_padrao;
                            }//if linguica no else pc_cx e unds_pca diferete de 1

                            et_preco_pc_cx_app.setText(preco_produto_cadastro);
                            Double preco_pca_caixa = Double.parseDouble(preco_produto_cadastro)*Double.parseDouble(unds_pecas);
                            preco_pca_caixa = controle.arreedondar(preco_pca_caixa);
                            et_preco_pc_cx_app.setText(preco_pca_caixa.toString());

                            referencia_adp.clear();
                            referencia_adp.add("Pç");
                            //referencia_adp.add(unidade_padrao);
                            spinner_referencia_app.setAdapter(referencia_adp);
                        }

                        else{
                            et_preco_und_app.setText(preco_produto_cadastro);
                            Double preco_pca_caixa = Double.parseDouble(preco_produto_cadastro)*Double.parseDouble(unds_pecas);
                            preco_pca_caixa = controle.arreedondar(preco_pca_caixa);
                            et_preco_pc_cx_app.setText(preco_pca_caixa.toString());

                            referencia_adp.clear();
                            referencia_adp.add("Pç");
                            //referencia_adp.add(unidade_padrao);
                            spinner_referencia_app.setAdapter(referencia_adp);
                        }
                    }
                }//else unds_peca = 1



                pecas_caixa = lista_produtos.getItem(position).getPecas_caixa().replace(",", ".");
                unds_pecas = lista_produtos.getItem(position).getUnds_peca().replace(",", ".");
                //preco_produto_cadastro = lista_produtos.getItem(position).getPreco().replace(",", ".");


                //preenche o preço da caixa
                Double preco_caixa = Double.parseDouble(lista_produtos.getItem(position).getPreco().toString().replace(",", ".")) * Double.parseDouble(lista_produtos.getItem(position).getUnds_peca().replace(",", "."));
                preco_caixa = controle.arreedondar(preco_caixa);
                et_preco_pc_cx_app.setText(preco_caixa.toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    //gerencia quantidade digitada no edittext

    /**
     * gerencia quantidade digitada no edittext, de acordo com a quantidade muda o valor.
     */
    public void gerencia_qtd_digitada(){
        et_quantidade_app.setOnKeyListener(new View.OnKeyListener() {
            String qtd_digitada = "";
            Double calculo_quant=0.0;
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et_quantidade_app.setText("");
                    et_qtd_und_app.setText("-");
                }

                qtd_digitada = et_quantidade_app.getText().toString();

                if (qtd_digitada.equals("")) {
                    et_qtd_und_app.setText("-");
                }
                else {

                    if(nome!=null && nome.length()>5) {

                        if (nome.substring(0, 6).equalsIgnoreCase("LINGUI") || nome.substring(0, 6).equalsIgnoreCase("LINGÜI")) {
                            if (spinner_referencia_app.getSelectedItem().equals(unidade_padrao)) {
                                //et_quantidade.setText("");
                                et_qtd_und_app.setText("-");
                            } else {
                                if (spinner_referencia_app.getSelectedItem().toString().equalsIgnoreCase("CX")) {
                                    calculo_quant = Double.parseDouble(qtd_digitada) * Double.parseDouble(pecas_caixa);
                                    calculo_quant = controle.arreedondar(calculo_quant);
                                    et_qtd_und_app.setText(calculo_quant.toString());
                                } else {
                                    calculo_quant = Double.parseDouble(qtd_digitada) * Double.parseDouble(unds_pecas);
                                    calculo_quant = controle.arreedondar(calculo_quant);
                                    et_qtd_und_app.setText(calculo_quant.toString());
                                }


                                Double calcula_quantidade = Double.parseDouble(et_quantidade_app.getText().toString()) * Double.parseDouble(unds_pecas);
                                calcula_quantidade = controle.arreedondar(calcula_quantidade);
                                et_qtd_und_app.setText(calcula_quantidade.toString());
                            }//else que ver a possibiidade se for linguica e for vendido por peca ou caixa

                        } else {
                            //et_quantidade.setText("");
                            et_qtd_und_app.setText("-");
                        }
                    }//if lenth >5

                }

                return false;
            }
        });

    }

    /**
     * permite controlar o calculo do desonto automaticamente
     */
    public void gerencia_desconto_digitado(){
        et_desconto_app.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                    et_desconto_app.setText("");
                    bt_calcular_desconto_app.setEnabled(false);
                }

                else{
                    if (cliques_no_calcular==0) {
                        cliques_no_calcular = 1;
                        bt_calcular_desconto_app.setEnabled(true);
                    }
                }

                return false;
            }
        });
    }

    /**
     * Altera o preco total em pecas.
     */
    public void altera_preco_peca(){
        et_preco_und_app.setOnKeyListener(new View.OnKeyListener() {
            String preco_digitado = "";

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                preco_digitado = et_preco_und_app.getText().toString();

                //mensagemExibir(String.valueOf(keyCode)+"del "+String.valueOf(KeyEvent.KEYCODE_DEL));

                if (preco_digitado.length()<2){
                    controle.mensagem_simples((Activity) getApplicationContext(), "Digite o preço! Ex.: 21.65");
                    et_preco_pc_cx_app.setText("");
                }

                else{

                    if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                        preco_digitado = preco_digitado.substring(0, preco_digitado.length() - 1);
                        Double preco_pecas_digitadas = Double.parseDouble(preco_digitado) * Double.parseDouble(unidades_por_pecas);

                        preco_pecas_digitadas = controle.arreedondar(preco_pecas_digitadas);
                        et_preco_pc_cx_app.setText(preco_pecas_digitadas.toString());
                    }

                    else {
                        Double preco_pecas_digitadas = Double.parseDouble(preco_digitado) * Double.parseDouble(unidades_por_pecas);
                        preco_pecas_digitadas = controle.arreedondar(preco_pecas_digitadas);
                        et_preco_pc_cx_app.setText(preco_pecas_digitadas.toString());

                    }
                }

                return false;
            }
        });

    }


    /**
     * Metodos que apaga as quantidades quando muda a referencia de peca para unidade e vice-versa
     */
    public void gerencia_qtd_pela_referencia(){
        //Evento que agora a quantidade quando muda a referencia
        spinner_referencia_app.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                et_quantidade_app.setText("");
                et_qtd_und_app.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Metodo que Permite excluir produtos da lista
     */
    public void excluir_produtos(){
        listView_nome_produtos_app.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Double valor_produto_excluido = Double.parseDouble(listView_preco_produtos_app.getItemAtPosition(position).toString()
                                                .substring(3, listView_preco_produtos_app.getItemAtPosition(position).toString().length()));
                //mensagemExibir(valor_produto_excluido.toString());
                total_sem_desconto = total_sem_desconto - valor_produto_excluido;
                total_sem_desconto = controle.arreedondar(total_sem_desconto);
                et_valor_total_app.setText("R$ " + total_sem_desconto.toString());

                adp_qtd_produtos_adicionados.remove(adp_qtd_produtos_adicionados.getItem(position).toString());
                adp_nome_produtos_adicionados.remove(adp_nome_produtos_adicionados.getItem(position).toString());
                adp_preco_produtos_adicionados.remove(adp_preco_produtos_adicionados.getItem(position).toString());

                listView_preco_produtos_app.setAdapter(adp_qtd_produtos_adicionados);
                listView_nome_produtos_app.setAdapter(adp_nome_produtos_adicionados);
                listView_preco_produtos_app.setAdapter(adp_preco_produtos_adicionados);

                lista_produtos_pedido.remove(position);

                alterou = true;

                et_desconto_app.setText("");
                et_valor_desconto_app.setText("");

            }
        });
    }


}
