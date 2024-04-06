/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

/**
 *
 * @author mathe
 */
public enum TipoCliente {
    COMUM ("Cliente Comum"),
    SUPER("Cliente Super"),
    PREMIUM("Cliente Premium");
    
    private String tipoCliente;

    private TipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    
    
}
