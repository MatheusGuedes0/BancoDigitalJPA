/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Cartao;
import br.com.cdb.BancoDigitalJPA.entity.CartaoCredito;
import br.com.cdb.BancoDigitalJPA.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mathe
 */

@RestController
@RequestMapping("/cartao")
public class CartaoController {
    
    @Autowired
    private CartaoService cartaoService;
    
   @PostMapping("/addCartaoCredito")
    public ResponseEntity<String> addCartao(@RequestBody CartaoCredito cartao) {
        int operacao = 1;
        Cartao cartaoAdicionado = cartaoService.salvarCartao(cartao.getClass(), cartao.getSenha(), cartao.getConta());
        if (cartaoAdicionado != null) {
            return new ResponseEntity<>("Conta adicionada com sucesso!",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Um dos campos da conta é inválido!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }
    
    
}
