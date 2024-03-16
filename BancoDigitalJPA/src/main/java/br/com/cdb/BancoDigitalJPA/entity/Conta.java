/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.cdb.BancoDigitalJPA.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 *
 * @author Andreia
 */
@Entity
public class Conta {

    @Id
    private long numero;
    private double saldo;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @Enumerated(EnumType.STRING)
    private TipoConta tipoConta;

    public Conta() {
    }

    public Conta(long numero, double saldo, Cliente cliente, TipoConta tipoConta ) {
        this.numero = numero;
        this.saldo = saldo;
        this.cliente = cliente;
        this.tipoConta = tipoConta;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }
    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public long getNumero() {
        return numero;
    }

    public void setNumero(long numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double mostrarSaldo() {
        return getSaldo();
    }

    public void transferirViaPix(Conta contaDestino, double valor) {
        double saldoAtual = getSaldo();

        if (saldoAtual >= valor) {
            setSaldo(saldoAtual - valor);
            contaDestino.setSaldo(contaDestino.getSaldo() + valor);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente para transferência.");
        }

    }

}
