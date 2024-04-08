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
import java.util.Date;

/**
 *
 * @author mathe
 */
@Entity
@DiscriminatorValue("credito")
public class CartaoCredito extends Cartao {

    private Date dataFatura;
    private double valorFatura;
    private double limiteAtual;
    private double limiteTotalDoCartao;

    public CartaoCredito() {
        super();
    }

    public CartaoCredito(Long id, Long numero, boolean ativo, String senha, Conta conta, Date dataFatura,
            double valorFatura, double limiteAtual, double limiteTotalDoCartao) {
        super(id, numero, ativo, senha, conta);
        this.dataFatura = dataFatura;
        this.valorFatura = valorFatura;
        this.limiteAtual = limiteAtual;
        this.limiteTotalDoCartao = limiteTotalDoCartao;
    }

    public Date getDataFatura() {
        return dataFatura;
    }

    public void setDataFatura(Date dataFatura) {
        this.dataFatura = dataFatura;
    }

    public double getValorFatura() {
        return valorFatura;
    }

    public void setValorFatura(double valorFatura) {
        this.valorFatura = valorFatura;
    }

    public double getLimiteAtual() {
        return limiteAtual;
    }

    public void setLimiteAtual(double limiteAtual) {
        this.limiteAtual = limiteAtual;
    }

    public double getLimiteTotalDoCartao() {
        return limiteTotalDoCartao;
    }

    public void setLimiteTotalDoCartao(double limiteTotalDoCartao) {
        this.limiteTotalDoCartao = limiteTotalDoCartao;
    }

}
