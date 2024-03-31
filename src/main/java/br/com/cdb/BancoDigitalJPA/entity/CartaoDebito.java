/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 *
 * @author mathe
 */
@Entity
public class CartaoDebito extends Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private final double limiteDiario = 1000;

    public CartaoDebito() {
    }

    public CartaoDebito(Long numero, String senha, Conta conta, boolean ativo) {
        super(numero, senha, conta, ativo);
    }

}
