/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 *
 * @author mathe
 */
@Entity
@DiscriminatorValue("debito")
public class CartaoDebito extends Cartao {

    private double limiteDiario;

    public CartaoDebito() {
    }

    public CartaoDebito(Long id, Long numero, boolean ativo, String senha, Conta conta) {
        super(id, numero, ativo, senha, conta);
    }

    public double getLimiteDiario() {
        return limiteDiario;
    }

    public void setLimiteDiario(double limiteDiario) {
        this.limiteDiario = limiteDiario;
    }
    
    
}
