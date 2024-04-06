/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

/**
 *
 * @author mathe
 */
public enum TipoSeguros {
    FURTO("Seguro Furto"),
    BOLSAPROTEGIDA("Seguro Bolsa Protegida"),
    COACAO("Seguro de Saque Efetuado sob coação"),;

    private String tipoSeguros;

    private TipoSeguros(String tipoSeguros) {
        this.tipoSeguros = tipoSeguros;
    }

    public String getTipoSeguros() {
        return tipoSeguros;
    }

}
