/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author mathe
 */
@Entity
@DiscriminatorValue("debito")
public class CartaoDebito extends Cartao {

    private final double limiteDiario = 1000;

    public CartaoDebito() {
    }

    public CartaoDebito(Long id, Long numero, boolean ativo, String senha, Conta conta) {
        super(id, numero, ativo, senha, conta);
    }

    

}
