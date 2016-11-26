package com.made.uellisson.agrofrios24.database;


import com.made.uellisson.agrofrios24.modelo.Cliente;

/**
 * Created by Uellisson on 18/02/2016.
 */
public class Scripts_Clientes {
    /**
     * Cria ou abre a tabela clientes
     */
    public static String abreOuCriaTabela_clientes() {
        String comandoSQL = "CREATE TABLE  IF NOT EXISTS tb_clientes (" +
                "razao_social TEXT, fantasia TEXT, codigo_cliente TEXT, " +
                "fone TEXT, inscricao_est TEXT, cnpj_cpf TEXT, " +
                "rua TEXT, numero TEXT, bairro TEXT, cep TEXT, cidade TEXT, uf TEXT);";

        return comandoSQL;
    }

    	/**
	 * Script que salva um cliente no banco de dados
	 **/
	public static String salvarCliente(Cliente c){
//        Cliente c = new Cliente("razao_social", "fantasia", "codigo_cliente", "fone", "inscricao_est", "cnpj_cpf",
//                                        "rua", "numero", "bairro", "cep", "cidade", "uf");

		String comandoSQL = "INSERT INTO tb_clientes (razao_social, fantasia, codigo_cliente, fone, inscricao_est, cnpj_cpf, " +
                                                            "rua, numero, bairro, cep, cidade, uf) values ('"

				+c.getRazao_social()+"','"+c.getFantasia()+"','"+c.getCodigo_cliente()+"','"
                +c.getFone()+"','"+c.getInscricao_est()+"','"+c.getCnpj_cpf()+"','"
				+c.getRua()+"','"+c.getNumero()+"','"+c.getBairro()+"','"+c.getCep()+"','"+c.getCidade()+"','"+c.getUf()+"')";

        return comandoSQL;
	}

    /**
     * Script que excui todos os cadastros de clientes
     **/
    public static String excuir_todos_clientes(){

        String comandoSQL = "DELETE FROM tb_clientes;";

        return comandoSQL;
    }
}
