package com.example.calculadoradecombustvel.model;

import java.io.Serializable;

public class Lista implements Serializable {

    private Long id;
    private String nomeCombutivel;
    private double valorCombustivel;
    private String dataCombustivel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCombutivel() {
        return nomeCombutivel;
    }

    public void setNomeCombutivel(String nomeCombutivel) {
        this.nomeCombutivel = nomeCombutivel;
    }

    public double getValorCombustivel() {
        return valorCombustivel;
    }

    public void setValorCombustivel(double valorCombustivel) {
        this.valorCombustivel = valorCombustivel;
    }

    public String getDataCombustivel() {
        return dataCombustivel;
    }

    public void setDataCombustivel(String dataCombustivel) {
        this.dataCombustivel = dataCombustivel;
    }
}
