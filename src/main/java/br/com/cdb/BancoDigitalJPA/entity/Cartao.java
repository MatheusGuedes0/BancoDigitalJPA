/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

/**
 *
 * @author mathe
 */
public class Cartao {

    @Id
    private Long numero;
    String senha;
    private Conta conta;
    @Enumerated(EnumType.STRING)
    private TipoCartao tipoCartao;
    private boolean ativo;

    public Cartao() {
    }

    public Cartao(Long numero, String senha, Conta conta, TipoCartao tipoCartao, boolean ativo) {
        this.numero = numero;
        this.senha = senha;
        this.conta = conta;
        this.tipoCartao = tipoCartao;
        this.ativo = ativo;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public TipoCartao getTipoCartao() {
        return tipoCartao;
    }

    public void setTipoCartao(TipoCartao tipoCartao) {
        this.tipoCartao = tipoCartao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    
   

}
