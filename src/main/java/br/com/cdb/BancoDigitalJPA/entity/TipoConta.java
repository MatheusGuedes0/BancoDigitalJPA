/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

/**
 *
 * @author mathe
 */
public enum TipoConta {
    CONTACORRENTE("Conta Corrente"),
    CONTAPOUPANCA("Conta Poupança");
    
    private String tipoConta;

    private TipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    @Override
    public String toString() {
        return  tipoConta ;
    }
    
    
    
    
    
}
