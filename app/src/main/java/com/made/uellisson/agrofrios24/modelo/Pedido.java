package com.made.uellisson.agrofrios24.modelo;

import android.widget.ArrayAdapter;

/**
 * Created by Uellisson on 28/02/2016.
 */
public class Pedido {

    private String id_pedido;
    private String cliente_fantasia;
    private String codigo_cliente;
    private String tipo_pagamento;
    private String data_atual;
    private String produtos;
    ArrayAdapter<Produtos_pedido> adpp;
    private String valor_total;
    private String desconto;

    /*
    Contrutor
     */
    public Pedido(String id_pedido, String cliente_fantasia, String codigo_cliente, String tipo_pagamento, String data_atual, String produtos, String valor_total, String desconto) {
        this.id_pedido = id_pedido;
        this.cliente_fantasia = cliente_fantasia;
        this.codigo_cliente = codigo_cliente;
        this.tipo_pagamento = tipo_pagamento;
        this.data_atual = data_atual;
        this.produtos = produtos;
        this.valor_total = valor_total;
        this.desconto = desconto;
    }

    /*
    Construtor com lista de produtos do pedido
     */
    public Pedido(String id_pedido, String cliente_fantasia, String codigo_cliente, String tipo_pagamento, String data_atual, ArrayAdapter<Produtos_pedido> adpp, String valor_total, String desconto) {
        this.id_pedido = id_pedido;
        this.cliente_fantasia = cliente_fantasia;
        this.codigo_cliente = codigo_cliente;
        this.tipo_pagamento = tipo_pagamento;
        this.data_atual = data_atual;
        this.adpp = adpp;
        this.valor_total = valor_total;
        this.desconto = desconto;
    }

    //Get's
    public String getCliente_fantasia() {
        return cliente_fantasia;
    }

    public String getCodigo_cliente() {
        return codigo_cliente;
    }

    public String getTipo_pagamento() {

        return tipo_pagamento;
    }

    public String getProdutos() {
        return produtos;
    }

    public String getValor_total() {
        return valor_total;
    }

    public String getData_atual() {
        return data_atual;
    }

    public String getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(String id_pedido) {

        this.id_pedido = id_pedido;
    }

    public void setCliente_fantasia(String cliente_fantasia) {
        this.cliente_fantasia = cliente_fantasia;
    }

    public void setCodigo_cliente(String codigo_cliente) {

        this.codigo_cliente = codigo_cliente;
    }

    public void setTipo_pagamento(String tipo_pagamento) {

        this.tipo_pagamento = tipo_pagamento;
    }

    public void setData_atual(String data_atual) {

        this.data_atual = data_atual;
    }

    public void setProdutos(String produtos) {

        this.produtos = produtos;
    }

    public void setValor_total(String valor_total) {

        this.valor_total = valor_total;
    }

    public String getDesconto() {
        return desconto;
    }

    public void setDesconto(String desconto) {
        this.desconto = desconto;
    }


    public ArrayAdapter<Produtos_pedido> getAdpp() {
        return adpp;
    }

    public void setAdpp(ArrayAdapter<Produtos_pedido> adpp) {
        this.adpp = adpp;
    }
}
