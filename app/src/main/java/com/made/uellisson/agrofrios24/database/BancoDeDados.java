package com.made.uellisson.agrofrios24.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class BancoDeDados extends SQLiteOpenHelper {


	public BancoDeDados(Context context) {
		super(context, "agrofrios", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Scripts_Clientes.abreOuCriaTabela_clientes());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	public void criar_tb_produtos(SQLiteDatabase db){
		db.execSQL(Script_Produtos.abreOuCriaTabela_produtos());
	}

	public void criar_tb_pedidos(SQLiteDatabase db){
		db.execSQL(Script_Pedido.abreOuCriaTabela_pedidos());
	}

	public void criar_tb_produtos_pedidos(SQLiteDatabase db){
		db.execSQL(Script_Produtos_Pedido.abreOuCriaTabela_produto_pedido());
	}

	public void criar_tb_clientes(SQLiteDatabase db){
		db.execSQL(Scripts_Clientes.abreOuCriaTabela_clientes());
	}

	public void criar_tb_vendedor(SQLiteDatabase db){
		db.execSQL(Script_Vendedor.abreOuCriaTabela_vendedor());
	}

	/**
	 * metodo que excui todos os dados de uma tabela
	 **/
	public void excuir_todos_clientes(SQLiteDatabase db)throws SQLException{

		String comandoSQL = "drop table tb_clientes;";
		db.execSQL(comandoSQL);

	}

	/**
	 * metodo que excui todos os dados de uma tabela
	 **/
	public void excuir_todos_Produtos(SQLiteDatabase db, String tabela)throws SQLException{

		String comandoSQL = "drop table "+tabela+";";
		db.execSQL(comandoSQL);

	}

//	Controle ct = new Controle();
//
//	//Variaveis do Banco de dados
//		SQLiteDatabase bancoDados =null;
//		Cursor cursor;
//
//		Cliente c = null;
//
//
//		String razao_social = "", fantasia = "", codigo_cliente = "";
//		String rua = "", numero = "", bairro = "", cep = "", cidade = "", uf = "";
//		String fone = "", inscricao_est = "", cnpj_cpf = "";
//
//
//
//		//Outras
//		int contador=0; //busca
//		ArrayList<Produto> produtos = new ArrayList<Produto>();
//		String cliente = "";
//
//
//	/**
//	 * Abre ou Cria o banco de dados
//	 */
//	public void abreOuCriaBanco(){
//		String nomeBanco = "agrofrios.db";
//
//		try{
//			bancoDados = openOrCreateDatabase(nomeBanco, Context.MODE_PRIVATE, null);
//
//			mensagemExibir("Banco", "Banco de dados criado ou aberto com sucesso =D");
//		}
//		catch(Exception erro){
//			mensagemExibir("Erro Banco", "Erro ao abrir ou criar o Banco: ");// + erro.getMessage());
//		}
//	}
//
//
//	/**
//	 * Fecha o banco
//	 */
//	public void fechaBanco(){
//		try {
//			bancoDados.close();
//		}
//
//		catch (Exception erro) {
//			mensagemExibir("Erro Banco", "Erro ao fechar o Banco: " + erro.getMessage());
//		}
//	}
//
//	/**
//	 * Cria ou abre a tabela clientes
//	 */
//	public void abreOuCriaTabela_clientes(){
//		String comandoSQL = "CREATE TABLE IF NOT EXISTS clientes "+
//				"(razao_social TEXT, " +
//				"fantasia TEXT, " +
//				"codigo_cliente TEXT, " +
//
//				"rua TEXT, " +
//				"numero TEXT, " +
//				"bairro TEXT, " +
//				"cep TEXT, " +
//				"cidade TEXT, " +
//				"uf TEXT, " +
//
//				"fone TEXT, " +
//				"inscricao_est TEXT, " +
//				"cnpj_cpf TEXT);";
//
//		try {
//			bancoDados.execSQL(comandoSQL);
//			//mensagemExibir("Tabela", "Tabela clientes criada com sucesso");
//
//		} catch (Exception e) {
//			//mensagemExibir("Erro tabela", "Problema ao criar talela clientes" + e.getMessage());
//		}
//
//	}
//
//	/**
//	 * Metodo que salva um cliente no banco de dados
//	 **/
//	public void salvarCliente(Cliente c){
//		abreOuCriaBanco();
//		abreOuCriaTabela_clientes();
//
//		String comandoSQL = "INSERT INTO clientes (razao_social, fantasia, codigo_cliente, rua, numero, bairro, cep, cidade, uf, " +
//				/*"rua, numero, bairro, cep, cidade, uf, " +*/
//												"fone, inscricao_est, cnpj_cpf) values ('"
//
//				+c.getRazao_social()+"','"+c.getFantasia()+"','"+c.getCodigo_cliente()+"','"
//				+c.getRua()+"','"+c.getNumero()+"','"+c.getBairro()+"','"+c.getCep()+"','"+c.getCidade()+"','"+c.getUf()+"','"
//				+fone+"','"+inscricao_est+"','"+cnpj_cpf+"')";
//
//		try {
//			bancoDados.execSQL(comandoSQL);
//			mensagemExibir("Cadastro", "Cadastro do cliente salvo com sucesso =D");
//		} catch (Exception e) {
//			mensagemExibir("Erro salvar", "Erro ao tenar salvar cadastro" + e.getMessage());
//		}
//	}
//
//
//	/**
//	 * Cria ou abre a tabela Produtos
//	 */
//	public void abreOuCriaTabela_Produtos(){
//		String comandoSQL = "CREATE TABLE IF NOT EXISTS produtos "+
//				"(nome TEXT, " +
//				"codigo_produto TEXT, " +
//				"preco TEXT);";
//
//		try {
//			bancoDados.execSQL(comandoSQL);
//			//mensagemExibir("Tabela", "Tabela produtos criada com sucesso");
//
//		} catch (Exception e) {
//			mensagemExibir("Erro tabela", "Problema ao criar talela produtos" + e.getMessage());
//		}
//	}
//
//	/**
//	 * Metodo que salva um produto no banco de dados
//	 **/
//	public void salvarProduto(Produto p){
//		abreOuCriaBanco();
//		abreOuCriaTabela_Produtos();
//
//		String comandoSQL = "INSERT INTO produtos (nome, codigo_produto, preco) values ('"
//
//												+p.getNome()+"','"
//												+p.getCodigo_produto()+"','"
//												+p.getPreco()+"')";
//
//		try {
//			bancoDados.execSQL(comandoSQL);
//			mensagemExibir("Cadastro", "Cadastro do produto salvo com sucesso");
//		} catch (Exception e) {
//			mensagemExibir("Erro salvar", "Erro ao tenar salvar Produto" + e.getMessage());
//		}
//	}
//
//	/**
//	 * Gerenciador de Mensagens
//	 */
//	public void mensagemExibir(String titulo, String texto) {
//		AlertDialog.Builder mensagem = new AlertDialog.Builder(this);
//		mensagem.setTitle(titulo);
//		mensagem.setMessage(texto);
//		mensagem.setNeutralButton("OK", null);
//		mensagem.show();
//	}




//	/**
//	 * Busca produtos no banco
//	 */
//	private boolean buscarProdutos(String nome){
//		abreOuCriaBanco();
//		//abreOuCriaTabela_Produtos();
//
//		
//		//elvNome_prod = (ExpandableListView) findViewById(R.id.elvNomeProd);
//		
//		Integer numReg = 0;
//		
//		
//		try {
//									//Tabela e coluna
//			cursor = bancoDados.query("produtos", new String [] {"nome","codigo_produto", "preco"}, 
//										null, //selection, 
//										null, //selectionArgs, 
//										null, //groupBy, 
//										null, //having, 
//										null //orderBy
//									);
//			
//			numReg = cursor.getCount();
//			
//			//mensagemExibir("num reg", numReg.toString());
//			
//		} 
//		
//		catch (Exception erro) {
//			tp.mensagemExibir("Erro Banco", "Erro ao buscar dados no Banco: "+erro.getMessage());
//		
//		}
//		
//		cursor.moveToFirst();
//				
//		
//		while (true) {
//			if (nome.equals(cursor.getString(0).toString())) {
//				etNome.setText(cursor.getString(0).toString());
//				etCodigo_produto.setText(cursor.getString(1).toString());
//				etPreco.setText(cursor.getString(2).toString());
//				
//				Produto p = new Produto(etNome.getText().toString(), 
//									etCodigo_produto.getText().toString(), 
//										etPreco.getText().toString());
//				
//				produtos.add(p);
//				
//				break;
//			}
//			if (numReg!=0) {
//				cursor.moveToNext();
//				numReg--;
//			}
//			if (numReg==0) {
//				etNome.setText("");
//				etCodigo_produto.setText("");
//				etPreco.setText("");
//				tp.mensagemExibir("Produto", "Produto n�o cadastrado");
//				
//				break;
//			}
//		}
//		fechaBanco();
//		return false;
//		
//	}
//	
//	/**
//	 * Busca produtos no banco
//	 */
//	private boolean buscarClientes(String razao_social){
//		abreOuCriaBanco();
//		abreOuCriaTabela_clientes();
//		
//		Integer numReg = 0;
//				
//		try {
//									//Tabela e coluna
//			cursor = bancoDados.query("clientes", new String [] {"razao_social","fantasia", "codigo_cliente","rua", "numero", "bairro", "cep", "cidade", "uf","fone", "inscricao_est", "cnpj_cpf"},
//										null, //selection, 
//										null, //selectionArgs, 
//										null, //groupBy, 
//										null, //having, 
//										null //orderBy
//									);
//			
//			numReg = cursor.getCount();
//			
//			//mensagemExibir("num reg", numReg.toString());
//			
//		} 
//		
//		catch (Exception erro) {
//			tp.mensagemExibir("Erro Banco", "Erro ao buscar dados no Banco: "+erro.getMessage());
//		
//		}
//		
//		cursor.moveToFirst();
//				
//		
//		while (true) {
//			if (razao_social.equals(cursor.getString(0).toString())) {
//				etRazao_social_bc.setText(cursor.getString(0).toString());
//				etFantasia_bc.setText(cursor.getString(1).toString());
//				etCodigo_cliente_bc.setText(cursor.getString(2).toString());
//				et_cnpj_bc.setText(cursor.getString(11).toString());
//				
//				cliente = cursor.getString(0).toString();
//				break;
//			}
//			if (numReg!=0) {
//				cursor.moveToNext();
//				numReg--;
//			}
//			if (numReg==0) {
//				etRazao_social_bc.setText("");
//				etFantasia_bc.setText("");
//				etCodigo_cliente_bc.setText("");
//				et_cnpj_bc.setText("");
//				tp.mensagemExibir("Cliente", "Cliente n�o cadastrado");
//				
//				break;
//			}
//		}
//		fechaBanco();
//		return false;
//		
//	}
//	
////	/**
////	 * Metodo que salva uma venda no banco de dados
////	 **/
////	public void salvarVenda(){
////		abreOuCriaBanco();
////		abreOuCriaTabela_Produtos();
////				
////		String comandoSQL = "INSERT INTO produtos (nome, codigo_produto, preco) values ('"
////				
////												+etNome.getText().toString()+"','"
////												+etCodigo_produto.getText().toString()+"','"
////												+etPreco.getText().toString()+"')";
////										
////		try {
////			bancoDados.execSQL(comandoSQL);
////			mensagemExibir("Cadastro", "Cadastro do produto salvo com sucesso");
////		} catch (Exception e) {
////			mensagemExibir("Erro salvar", "Erro ao tenar salvar Produto"+e.getMessage());
////		}
////	}
////	
//	
}
