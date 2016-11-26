package com.made.uellisson.agrofrios24.modelo;

/**
 * Created by Uellisson on 08/06/2016.
 */
public class Vendedor {
    private String nome_vendedor;
    private String codigo_vendedor;

    public Vendedor(String nome_vendedor, String codigo_vendedor) {
        this.nome_vendedor = nome_vendedor;
        this.codigo_vendedor = codigo_vendedor;
    }

    public String getNome_vendedor() {
        return nome_vendedor;
    }

    public void setNome_vendedor(String nome_vendedor) {
        this.nome_vendedor = nome_vendedor;
    }

    public String getCodigo_vendedor() {
        return codigo_vendedor;
    }

    public void setCodigo_vendedor(String codigo_vendedor) {
        this.codigo_vendedor = codigo_vendedor;
    }
}
