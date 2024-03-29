/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Conta;

import br.com.cdb.BancoDigitalJPA.service.ContaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
        Conta contaAdicionada = contaService.salvarConta(conta.getCliente(), conta.getTipoConta());
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
        for(Conta contas : conta){
            Long idConta = contas.getId();         
            contaService.adicionarTaxas(idConta);
        }
        return new ResponseEntity<List<Conta>>(conta, HttpStatus.OK);
    }

}
