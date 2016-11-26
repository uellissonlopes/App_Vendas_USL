package com.made.uellisson.agrofrios24.modelo;

public class Cliente {
	//Atrinbutos do cliente
	String razao_social;
	String fantasia;
	String codigo_cliente;
	String fone;
	String inscricao_est;
	String cnpj_cpf;

	String rua, numero, bairro, cep, cidade, uf;


	/*
	 * Construtor da Classe Cliente VAZIO
	 */
	public Cliente(){
		super();
	}
	
	/*
	 * Construtor da Classe Cliente
	 */
	public Cliente(String razao_social, String fantasia, String codigo_cliente,
				   String fone, String inscricao_est, String cnpj_cpf,
				   String rua, String numero, String bairro, String cep,
				   String cidade, String uf) {
		super();
		this.razao_social = razao_social;
		this.fantasia = fantasia;
		this.codigo_cliente = codigo_cliente;
		this.fone = fone;
		this.inscricao_est = inscricao_est;
		this.cnpj_cpf = cnpj_cpf;
		this.rua = rua;
		this.numero = numero;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.uf = uf;

	}


	public String getRazao_social() {
		return razao_social;
	}


	public void setRazao_social(String razao_social) {
		this.razao_social = razao_social;
	}


	public String getFantasia() {
		return fantasia;
	}


	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}


	public String getCodigo_cliente() {
		return codigo_cliente;
	}


	public void setCodigo_cliente(String codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}


	public String getFone() {
		return fone;
	}


	public void setFone(String fone) {
		this.fone = fone;
	}


	public String getInscricao_est() {
		return inscricao_est;
	}


	public void setInscricao_est(String inscricao_est) {
		this.inscricao_est = inscricao_est;
	}


	public String getCnpj_cpf() {
		return cnpj_cpf;
	}


	public void setCnpj_cpf(String cnpj_cpf) {
		this.cnpj_cpf = cnpj_cpf;
	}


	public String getRua() {
		return rua;
	}


	public void setRua(String rua) {
		this.rua = rua;
	}


	public String getNumero() {
		return numero;
	}


	public void setNumero(String numero) {
		this.numero = numero;
	}


	public String getBairro() {
		return bairro;
	}


	public void setBairro(String bairro) {
		this.bairro = bairro;
	}


	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getCidade() {
		return cidade;
	}


	public void setCidade(String cidade) {
		this.cidade = cidade;
	}


	public String getUf() {
		return uf;
	}


	public void setUf(String uf) {
		this.uf = uf;
	}


}
