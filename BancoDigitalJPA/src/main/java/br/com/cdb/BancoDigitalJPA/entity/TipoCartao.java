/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

/**
 *
 * @author mathe
 */
public enum TipoCartao {
    
    DEBITO("Cartão de Débito"),
    CREDITO("Cartão de Crédito");
    
    private String tipoCartao;

    private TipoCartao(String tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public String getTipoCartao() {
        return tipoCartao;
    }

    @Override
    public String toString() {
        return tipoCartao ;
    }


    
    
}
