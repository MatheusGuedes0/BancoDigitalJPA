/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Cartao;
import br.com.cdb.BancoDigitalJPA.entity.CartaoCredito;
import br.com.cdb.BancoDigitalJPA.entity.CartaoDebito;
import br.com.cdb.BancoDigitalJPA.exception.SaldoInsuficienteException;
import br.com.cdb.BancoDigitalJPA.exception.SenhaIncorretaException;
import br.com.cdb.BancoDigitalJPA.service.CartaoService;
import java.util.Map;
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

        Cartao cartaoAdicionado = cartaoService.salvarCartao(cartao.getClass(), cartao.getSenha(), cartao.getConta());
        if (cartaoAdicionado != null) {
            return new ResponseEntity<>("Conta adicionada com sucesso!",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Um dos campos da conta é inválido!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/addCartaoDebito")
    public ResponseEntity<String> addCartao(@RequestBody CartaoDebito cartao) {

        Cartao cartaoAdicionado = cartaoService.salvarCartao(cartao.getClass(), cartao.getSenha(), cartao.getConta());
        if (cartaoAdicionado != null) {
            return new ResponseEntity<>("Conta adicionada com sucesso!",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Um dos campos da conta é inválido!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/pagarNoCredito")
    public ResponseEntity<String> pagarNoCredito(@RequestBody Map<String, Object> request) {
        Long numeroCartao = (Long) request.get("numero");
        String senhaCartao = (String) request.get("senha");
        String numeroBoleto = (String) request.get("numeroBoleto");

        try {
            CartaoCredito cartaoCredito = (CartaoCredito) cartaoService.realizarPagamentos(numeroCartao, senhaCartao, numeroBoleto);
            return new ResponseEntity<>("pagamento realizado com sucesso!", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/pagarNoDebito")
    public ResponseEntity<String> pagarNoDebito(@RequestBody Map<String, Object> request) {
        Long numeroCartao = (Long) request.get("numero");
        String senhaCartao = (String) request.get("senha");
        String numeroBoleto = (String) request.get("numeroBoleto");

        try {
            CartaoDebito cartaoDebito = (CartaoDebito) cartaoService.realizarPagamentos(numeroCartao, senhaCartao, numeroBoleto);
            return new ResponseEntity<>("pagamento realizado com sucesso!", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/alterarSenha")
    public ResponseEntity<String> alterarSenha(@RequestBody Map<String, Object> request) {
        Long id = Long.valueOf((Integer) request.get("id"));
        String antigaSenha = (String) request.get("antigaSenha");
        String novaSenha = (String) request.get("novaSenha");
        String confirmacaoNovaSenha = (String) request.get("confirmacaoNovaSenha");
        try {
            Cartao cartao = cartaoService.mudarSenha(id, antigaSenha, novaSenha, confirmacaoNovaSenha);
            return new ResponseEntity<>("Senha alterada com sucesso!", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/ativarCartao")
    public ResponseEntity<String> ativarCartao(@RequestBody Map<String, Object> request) {
        Long id = Long.valueOf((Integer) request.get("id"));
        String senha = (String) request.get("senha");
        boolean ativar = (boolean) request.get("ativar");
        try {
            cartaoService.ativarCartao(id, senha, ativar);
            if (ativar) {
                return new ResponseEntity<>("Cartão ativado com sucesso!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Cartão desativado com sucesso!", HttpStatus.OK);
            }

        } catch (SenhaIncorretaException e) {
            return new ResponseEntity<>("Senha incorreta! Não foi possível ativar o cartão.", HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

//    @PostMapping("/alterarLimite")
//    public ResponseEntity<String> alterarLimite(@RequestBody Map<String, Object> request) {
//        Long id = Long.valueOf((Integer) request.get("id"));
//        String senha = (String) request.get("senha");
//    }

}
