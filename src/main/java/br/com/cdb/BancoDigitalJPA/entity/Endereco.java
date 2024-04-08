/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Embeddable;

/**
 *
 * @author mathe
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Endereco {

    private Long id;
    private String rua;
    private int numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco() {
    }

    public Endereco(String rua, int numero, String complemento, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCeo(String cep) {
        this.cep = cep;
    }

    @Override
    public String toString() {
        return "rua:" + rua + "," + numero + ", complemento: " + complemento + ", " + cidade + "-" + estado + "," + cep;
    }

}
