/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.exception.SaldoInsuficienteException;
import br.com.cdb.BancoDigitalJPA.exception.SenhaIncorretaException;

import br.com.cdb.BancoDigitalJPA.service.ContaService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mathe
 */
@RestController
@RequestMapping("/conta")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/add")
    public ResponseEntity<String> addConta(@RequestBody Conta conta) {
        Conta contaAdicionada = contaService.salvarConta(conta.getCliente(), conta.getTipoConta(), conta.getChavePix(), conta.getSenha());
        if (contaAdicionada != null) {
            return new ResponseEntity<>("Conta adicionada com sucesso!",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Um dos campos da conta é inválido!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Conta>> getAllConta() {
        List<Conta> conta = contaService.getConta();
        return new ResponseEntity<List<Conta>>(conta, HttpStatus.OK);
    }

    @GetMapping("/verificarDiasCorridos")
    public ResponseEntity<List<Conta>> verificarDiasCorridosEAtualizarSaldo() {
        List<Conta> conta = contaService.getConta();
        for (Conta contas : conta) {
            Long idConta = contas.getId();
            contaService.adicionarTaxas(idConta);
        }
        return new ResponseEntity<List<Conta>>(conta, HttpStatus.OK);
    }

    @PostMapping("/trasferirPix")
    public ResponseEntity<?> transferirViaPix(@RequestBody Map<String, Object> request) {
        try {
            String chavePix = (String) request.get("chavePix");
            Long numeroConta = Long.valueOf(String.valueOf(request.get("numeroConta")));
            String senha = (String) request.get("senha");
            double valor = Double.parseDouble(String.valueOf(request.get("valor")));

            Conta contaDestino = contaService.transferirViaPix(numeroConta, chavePix, valor, senha);
            return ResponseEntity.ok(contaDestino);
        } catch (SaldoInsuficienteException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (SenhaIncorretaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/saldo")
    public ResponseEntity<?> getSaldo(@RequestBody Map<String, Object> request) {
        try {
            Long numeroConta = Long.valueOf(String.valueOf(request.get("numeroConta")));
            String senha = (String) request.get("senha");

            Conta conta = contaService.buscarContaPorNumero(numeroConta);
            if (conta.getSenha().equals(senha)) {
                return ResponseEntity.ok(conta.getSaldoConta());
            } else {
                return ResponseEntity.badRequest().body("Senha incorreta.");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Número da conta inválido.");
        }
    }
}
