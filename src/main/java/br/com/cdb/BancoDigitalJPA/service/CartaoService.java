/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cartao;
import br.com.cdb.BancoDigitalJPA.entity.CartaoCredito;
import br.com.cdb.BancoDigitalJPA.entity.CartaoDebito;
import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.exception.SaldoInsuficienteException;
import br.com.cdb.BancoDigitalJPA.exception.SenhaIncorretaException;
import br.com.cdb.BancoDigitalJPA.repository.CartaoRepository;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class CartaoService {
    
    @Autowired
    private CartaoRepository cartaoRepository;
    
    public Cartao salvarCartao(Class<? extends Cartao> tipoCartao, String senha, Conta conta) {
        Random random = new Random();
        if (CartaoCredito.class.equals(tipoCartao)) {
            CartaoCredito cartaoCredito = new CartaoCredito();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, 1);
            Date data = calendar.getTime();
            cartaoCredito.setDataFatura(data);
            cartaoCredito.setNumero(random.nextLong((1 * Long.SIZE), Long.MAX_VALUE));
            cartaoCredito.setLimiteDoCartao(2000);
            cartaoCredito.setAtivo(true);
            cartaoCredito.setSenha(senha);
            cartaoCredito.setConta(conta);
            return cartaoRepository.save(cartaoCredito);
        } else if (CartaoDebito.class.equals(tipoCartao)) {
            CartaoDebito cartaoDebito = new CartaoDebito();
            cartaoDebito.setNumero(random.nextLong((1 * Long.SIZE), Long.MAX_VALUE));
            cartaoDebito.setAtivo(true);
            cartaoDebito.setLimiteDiario(1000.00);
            cartaoDebito.setSenha(senha);
            cartaoDebito.setConta(conta);
            return cartaoRepository.save(cartaoDebito);
        } else {
            return null;
        }
    }
    
    public Cartao realizarPagamentos(Long numeroCartao, String senha, String numeroBoleto) {
        Cartao cartao = cartaoRepository.findByNumero(numeroCartao);
        // Nessa classe, implementariamos uma função de pagar algo a partir de uma requisição externa
        // com o número do boleto. Como não aprendemos a fazer isso, o metodo realizarPagamentos
        // apenas simula um pagamento.
        // Boleto boleto = boleto.buscarBoleto(numeroBoleto); // constroiria o objeto boleto a partir do numero
        // double valor = boleto.getValor(); // pegaria o valor do metodo construido
        double valor = 1200; // simulando o valor de um boleto

        // Estou ciente de que aqui precisa de uma implementação mais elaborada com Token usando Spring Security
        // Como não aprendemos no curso sobre token, achei melhor deixar em implementação simples
        // para evitar classes JWT's que não foram requeridas no projeto final
        if (cartao instanceof CartaoCredito) {
            CartaoCredito cartaoCredito = (CartaoCredito) cartaoRepository.findByNumero(numeroCartao);
            if (cartaoCredito.getSenha().equals(senha)) {
                if (valor <= cartaoCredito.getLimiteDoCartao() && cartaoCredito.isAtivo()) {
                    cartaoCredito.setLimiteDoCartao(cartaoCredito.getLimiteDoCartao() - valor);
                    if (cartaoCredito.getLimiteDoCartao() == 0) {
                        cartaoCredito.setAtivo(false);
                    }
                    return cartaoRepository.save(cartaoCredito);
                } else {
                    throw new RuntimeException("Limite do Cartão atingido!");
                }
            } else {
                throw new RuntimeException("Senha incorreta, impossível realizar pagamento!");
            }
        } else {
            CartaoDebito cartaoDebito = (CartaoDebito) cartaoRepository.findByNumero(numeroCartao);
            if (cartaoDebito.getSenha().equals(senha)) {
                if (valor <= cartaoDebito.getConta().getSaldoConta() && cartaoDebito.getLimiteDiario() <= valor) {
                    cartaoDebito.getConta().setSaldoConta(cartaoDebito.getConta().getSaldoConta() - valor);
                    return cartaoRepository.save(cartaoDebito);
                } else {
                    throw new RuntimeException("Saldo insuficiente ou limite diário excedido!");
                }
            } else {
                throw new RuntimeException("Senha incorreta, impossível realizar pagamento!");
            }
        }
    }
    
    public Cartao mudarSenha(Long id, String antigaSenha, String novaSenha, String novaSenhaConfirm) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(antigaSenha)) {
                if (novaSenha.equals(novaSenhaConfirm)) {
                    cartao.setSenha(novaSenha);
                    return cartaoRepository.save(cartao);
                } else {
                    throw new SenhaIncorretaException("A senhas não são iguais!");
                }
            } else {
                throw new RuntimeException("Senha incorreta!!");
            }
        } else {
            throw new RuntimeException("Cartao não encontrado!!");
        }
    }
    
    public Cartao ativarCartao(Long id, String senha, boolean ativar) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            
            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(senha)) {
                cartao.setAtivo(ativar);
                return cartaoRepository.save(cartao);
            } else {
                throw new RuntimeException("Senha incorreta!!");
            }
        } else {
            throw new RuntimeException("Cartao não encontrado!!");
        }
    }
    
    public Cartao ajustarLimite(Long id, String senha, double limite) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        
        if (cartaoOptional.isPresent()) {
            Cartao cartao = cartaoOptional.get();
            if (cartao.getSenha().equals(senha)) {
                if (cartao instanceof CartaoCredito) {
                    CartaoCredito cartaoCredito = (CartaoCredito) cartao;
                    cartaoCredito.setLimiteDoCartao(limite);
                } else if (cartao instanceof CartaoDebito) {
                    CartaoDebito cartaoDebito = (CartaoDebito) cartao;
                    cartaoDebito.setLimiteDiario(limite);
                } else {
                    throw new RuntimeException("Tipo de cartao não suportado!!");
                }
                return cartaoRepository.save(cartao);
            } else {
                throw new SenhaIncorretaException("Senha incorreta!!");
            }
        } else {
            throw new RuntimeException("Cartao não encontrado!!");
        }
    }
    
}
