package com.made.uellisson.agrofrios24.modelo;

public class Produtos_pedido {
	private String id_pedido;
	private String codigo_produto;
	private String nome_produto;
	private String quantidade;
	private String desconto;
	private String preco_total;


	/**
	 * Contrutor
	 */
	public Produtos_pedido(String id_pedido, String codigo_produto, String nome_produto, String quantidade, String desconto, String preco_total) {
		this.id_pedido = id_pedido;
		this.codigo_produto = codigo_produto;
		this.nome_produto = nome_produto;
		this.quantidade = quantidade;
		this.desconto = desconto;
		this.preco_total = preco_total;
	}


	/**
	 * Get e Set
	 */
	public String getId_pedido() {
		return id_pedido;
	}

	public void setId_pedido(String id_pedido) {
		this.id_pedido = id_pedido;
	}

	public String getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(String codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	public String getNome_produto() {
		return nome_produto;
	}

	public void setNome_produto(String nome_produto) {
		this.nome_produto = nome_produto;
	}

	public String getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(String quantidade) {
		this.quantidade = quantidade;
	}

	public String getDesconto() {
		return desconto;
	}

	public void setDesconto(String desconto) {
		this.desconto = desconto;
	}

	public String getPreco_total() {
		return preco_total;
	}

	public void setPreco_total(String preco_total) {
		this.preco_total = preco_total;
	}


}
