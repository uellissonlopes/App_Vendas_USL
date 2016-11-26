package com.made.uellisson.agrofrios24.controle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.made.uellisson.agrofrios24.database.BancoDeDados;
import com.made.uellisson.agrofrios24.dominio.Dados_Pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos;
import com.made.uellisson.agrofrios24.dominio.Dados_Produtos_pedido;
import com.made.uellisson.agrofrios24.dominio.Dados_Vendedor;
import com.made.uellisson.agrofrios24.dominio.Dados_cliente;
import com.made.uellisson.agrofrios24.dominio.Salvar_no_DB;
import com.made.uellisson.agrofrios24.modelo.Cliente;
import com.made.uellisson.agrofrios24.modelo.Pedido;
import com.made.uellisson.agrofrios24.modelo.Produto;
import com.made.uellisson.agrofrios24.modelo.Produtos_pedido;
import com.made.uellisson.agrofrios24.modelo.Vendedor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Uellisson on 10/06/2016.
 */
public class Controle {

    //Declaracao dos objetos que serao utilizados na classe.
    BancoDeDados bd;
    SQLiteDatabase conexao;
    Salvar_no_DB salvar_no_db;
    Dados_cliente dados_cliente;
    Dados_Pedido dados_pedidos;
    Dados_Produtos_pedido dados_produtos_pedido;
    Dados_Produtos dados_produtos;


    public void criar_tabelas_db(Context context){
        SQLiteDatabase conexao;
        BancoDeDados bd = new BancoDeDados(context);
        conexao = bd.getWritableDatabase();

        bd.criar_tb_clientes(conexao);
        bd.criar_tb_produtos(conexao);
        bd.criar_tb_pedidos(conexao);
        bd.criar_tb_produtos_pedidos(conexao);
        bd.criar_tb_vendedor(conexao);

    }

    /**
     * Metodo que recebe um cliente como parametro e salva seus dados no banco
     * e retorna true se der certo.
     *
     * @param cliente
     * @return
     */
    public boolean salvar_cliente (Cliente cliente, Activity activity){

        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //salva dados do cliente
            salvar_no_db = new Salvar_no_DB(conexao);
            salvar_no_db.salvar_cliente(cliente);

            return true;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro salvar", erro.getMessage());
            return false;
        }

    }

    /**
     * Metodo que busca todos os clientes salvos no banco de dados e retorna eles
     * em uma lista.
     *
     * @param activity
     * @return
     */
    public ArrayAdapter<Cliente> buscar_todos_clientes (Activity activity){
        ArrayAdapter<Cliente> lista_clientes = null;

        //ArrayAdapter<Cliente> lista_clientes = null;

        try {
            //cria conexao com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //busca clientes no banco de dados.
            dados_cliente = new Dados_cliente(conexao);
            lista_clientes = dados_cliente.busca_todos_clientes(activity);

            return lista_clientes;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro ao buscar", erro.getMessage());

            return lista_clientes;
        }
    }

    /**
     * Metodo que edita um cliente, recebento um objeto desse tipo como paremetro e
     * retorna true se der certo.
     *
     * @param cliente
     * @param activity
     * @return
     */
    public boolean editar_cliente(Cliente cliente, Activity activity){
        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //Edita um cliente.
            dados_cliente = new Dados_cliente(conexao);
            dados_cliente.editar_cliente(cliente);


            return true;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro ao excluir: ", erro.getMessage());

            return false;
        }

    }


    /**
     * Metodo que exclui um cliente pelo seu codigo e
     * retorna true se der certo.
     *
     * @param codigo_cliente
     * @param activity
     * @return
     */
    public boolean excluir_cliente(String codigo_cliente, Activity activity){
        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //exclui um cliente pelo codigo.
            dados_cliente = new Dados_cliente(conexao);
            dados_cliente.excluir_cliente(codigo_cliente);

            return true;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro ao excluir: ", erro.getMessage());

            return false;
        }

    }

    /**
     * Metodo que exclui todos os clientes do banco de dados
     * e retorna true se der certo.
     *
     * @param activity
     * @return
     */
    public boolean exclui_todos_clientes (Activity activity){

        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.criar_tb_clientes(conexao);

            //Exclui dados do cliente
            Dados_cliente dados_cliente = new Dados_cliente(conexao);
            dados_cliente.excluir_cliente();

            return true;

        }
        //gerenciamento de excessões
        catch (SQLException erro){
            Log.d("Erro excluir: ", "Erro ao excluir os cadastros: " + erro.getMessage());
            return false;
        }

    }

    /**
     * Metodos que busca todos os pedidos no banco de dados e
     * retorna uma lista com seus dados.
     *
     * @param activity
     * @return
     */
    public ArrayAdapter<Produto> busca_produtos_disponiveis(Activity activity){
        ArrayAdapter<Produto> lista_produtos = null;

        //ArrayAdapter<Cliente> lista_clientes = null;

        try {
            //cria conexao com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //busca pedidos no banco de dados.
            dados_produtos = new Dados_Produtos(conexao);
            lista_produtos = dados_produtos.busca_produtos_disponiveis(activity);

            return lista_produtos;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro ao buscar", erro.getMessage());

            return lista_produtos;
        }
    }

    /**
     * Metodos que busca todos os pedidos no banco de dados e
     * retorna uma lista com seus dados.
     *
     * @param activity
     * @return
     */
    public ArrayAdapter<Pedido> buscar_todos_pedidos(Activity activity){
        ArrayAdapter<Pedido> lista_pedidos = null;

        //ArrayAdapter<Cliente> lista_clientes = null;

        try {
            //cria conexao com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //busca pedidos no banco de dados.
            dados_pedidos = new Dados_Pedido(conexao);
            lista_pedidos = dados_pedidos.busca_pedidos(activity);

            return lista_pedidos;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro ao buscar", erro.getMessage());

            return lista_pedidos;
        }
    }

     /**
     * Metodo que edita um pedido, recebendo um objeto desse tipo como paremetro, outro com seus
     * respecitivos produtos e retorna true se der certo.
     *
     * @param pedido
     * @param lista_produtos_pedido
     * @param activity
     * @return
     */
    public boolean editar_pedido(Pedido pedido, ArrayList<Produtos_pedido> lista_produtos_pedido, Activity activity){
        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.onCreate(conexao);

            //Edita um pedido.

            dados_pedidos = new Dados_Pedido(conexao);
            dados_pedidos.alterar_pedido(pedido, lista_produtos_pedido);

            return true;
        }

        //gerenciamento de erros
        catch (SQLException erro){
            Log.d("Erro ao excluir: ", erro.getMessage());

            return false;
        }

    }


    /**
     * * Metodo que exclui todos os pedidos do banco de dados
     * e retorna true se der certo.
     *
     * @param activity
     * @return
     */
    public boolean exclui_todos_pedidos (Activity activity){

        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.criar_tb_clientes(conexao);

            //exclui todos os pedidos
            Dados_Pedido dados_pedido = new Dados_Pedido(conexao);
            dados_pedido.excluir_todos_pedidos();

            return true;
        }
        //gerenciamento de excessões
        catch (SQLException erro){
            Log.d("Erro excluir: ", "Erro ao excluir os pedidos: " + erro.getMessage());

            return false;
        }
    }




    /**
     *  Metodo que recebe um vendedor como parametro e salva seus dados no banco.
     *
     * @param vendedor
     * @param activity
     * @return
     */
     public boolean salvar_vendedor (Vendedor vendedor, Activity activity){

        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.criar_tb_vendedor(conexao);

            //salva dados do vendedor
            salvar_no_db = new Salvar_no_DB(conexao);
            salvar_no_db.salvar_vendedor(vendedor);

            return true;
        }

        //gerenciamento de excessoes
        catch (SQLException erro){
            Log.d("Erro Salvar: ", "Erro ao salvar o cadastro do vendedor: " + erro.getMessage());
            return false;
        }

    }

    /**
     * Metodo que busca o ultimo vendedor cadastrado no banco de dados.

     * @param activity
     * @return
     */
    public Vendedor buscar_vendedor ( Activity activity){

        Vendedor vendedor = null;

        try {
            //cria conexão com db
            bd = new BancoDeDados(activity);
            conexao = bd.getReadableDatabase();
            bd.criar_tb_vendedor(conexao);

            Dados_Vendedor dados_vendedor = new Dados_Vendedor(conexao);
            vendedor = dados_vendedor.busca_vendedor();

            return vendedor;

        }
        //gerenciamento de excessões
        catch (SQLException erro){
            mensagem_pop_up(activity, "Erro Buscar!", "Erro ao carregar dados do vendedor: " + erro.getMessage());
        }

        return vendedor;
    }

    /**
     *  Metodo que Pega a data atual do dispositivo e retorna ela formatada com
     *  "ano-mes-dia"
     */
    public String data_atual(){
        long data = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String data_atual = sdf.format(data);

        return data_atual;
    }

    //Converte datas para o formato americano entendido pelo MySql aaaa-mm-dd
    public String converter_data_para_US(String data) {
        //data = data.replace("/", "-");

        String dia = data.substring(0, 2);
        String mes = data.substring(3, 5);
        String ano = data.substring(6, 10);

        String data_formatada = ano+"-"+mes+"-"+dia;

        return data_formatada;

    }

    //Converte datas para o formato brasileiro dd-mm-aaaa
    public String converter_data_para_BR(String data) {
        //data = data.replace("/", "-");

        String ano = data.substring(0, 4);
        String mes = data.substring(5, 7);
        String dia = data.substring(8, 10);

        //mudar para - e -
        String data_formatada = dia+"-"+mes+"-"+ano;

        return data_formatada;

    }


    //Metodo que arredonda um valor Double para 2 casas decimais

    /**
     * Metodo que arredonda um valor Double para 2 casas decimais,
     * de 34.6333333 para 34.60
     *
     * @param valor
     * @return
     */
    public Double arreedondar(Double valor) {
        BigDecimal bigDecimal = new BigDecimal(valor).setScale(2, RoundingMode.HALF_EVEN);
        valor =bigDecimal.doubleValue();

        return valor;
    }

    /**
     *Mostra uma mensagem em forma de popup
     */
    public void mensagem_pop_up(Activity activity, String titulo, String texto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(activity);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("OK", null);
        mensagem.show();
    }

    /**
     *Mostra uma mensagem em simples, na tela do usuario de popup
     */
    public void mensagem_simples(Activity activity, String msg){
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }


}
