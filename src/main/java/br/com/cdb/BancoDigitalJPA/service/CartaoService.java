/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cartao;
import br.com.cdb.BancoDigitalJPA.entity.CartaoCredito;
import br.com.cdb.BancoDigitalJPA.entity.CartaoDebito;
import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.repository.CartaoRepository;
import java.util.Calendar;
import java.util.Date;
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
            cartaoDebito.setSenha(senha);
            cartaoDebito.setConta(conta);
            return cartaoRepository.save(cartaoDebito);
        } else {
            return null;
        }
    }
    
    
    
}
