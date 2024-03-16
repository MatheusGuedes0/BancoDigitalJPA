/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.entity.TipoConta;

import br.com.cdb.BancoDigitalJPA.repository.ContaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class ContaService {
    
    
    @Autowired
    private ContaRepository contaRepository;
    
    public Conta salvarConta(Long numero, double saldo, Cliente cliente, TipoConta tipoConta ){
        Conta conta = new Conta();
        conta.setNumero(numero);
        conta.setSaldo(saldo);
        conta.setCliente(cliente);
        conta.setTipoConta(tipoConta);
        return contaRepository.save(conta);
    }

     public List<Conta>getConta(){
         return contaRepository.findAll();
     }
    
}
