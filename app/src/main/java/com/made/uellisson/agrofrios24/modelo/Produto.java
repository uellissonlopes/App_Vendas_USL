package com.made.uellisson.agrofrios24.modelo;

public class Produto {
	private String nome;
	private String codigo_produto;
	private String preco;
	private String unidade;
	private String completo;
	private String pecas_caixa;
	private String unds_peca;
	private String disponivel;


	/**
	 * Contrutor
 	*/
	public Produto(String nome, String codigo_produto, String preco, String unidade, String completo,
				   String pecas_caixa, String unds_peca, String disponivel) {
		this.nome = nome;
		this.codigo_produto = codigo_produto;
		this.preco = preco;
		this.unidade = unidade;
		this.completo = completo;
		this.pecas_caixa = pecas_caixa;
		this.unds_peca = unds_peca;
		this.disponivel = disponivel;
	}

	/**
	 * Get's e set's
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(String codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getCompleto() {
		return completo;
	}

	public void setCompleto(String completo) {
		this.completo = completo;
	}

	public String getPecas_caixa() {
		return pecas_caixa;
	}

	public void setPecas_caixa(String pecas_caixa) {
		this.pecas_caixa = pecas_caixa;
	}

	public String getUnds_peca() {
		return unds_peca;
	}

	public void setUnds_peca(String unds_peca) {
		this.unds_peca = unds_peca;
	}

	public String getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(String disponivel) {
		this.disponivel = disponivel;
	}
}
