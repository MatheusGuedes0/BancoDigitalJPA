/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.entity.TipoConta;
import br.com.cdb.BancoDigitalJPA.exception.ChavePixNaoEncontradaException;
import br.com.cdb.BancoDigitalJPA.exception.ContaNotFoundException;
import br.com.cdb.BancoDigitalJPA.exception.SaldoInsuficienteException;
import br.com.cdb.BancoDigitalJPA.exception.SenhaIncorretaException;

import br.com.cdb.BancoDigitalJPA.repository.ContaRepository;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
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

    public Conta criarConta(Cliente cliente, TipoConta tipoConta, String chavePix, String senha) {
        Conta conta = new Conta();
        Random random = new Random();
        conta.setNumeroConta(random.nextLong((1 * Long.SIZE), Long.MAX_VALUE));
        conta.setSaldoConta(0);
        conta.setCliente(cliente);
        conta.setTipoConta(tipoConta);
        LocalDate today = LocalDate.now();
        conta.setDataCriacao(today);
        conta.setChavePix(chavePix);
        conta.setSenha(senha);
        return contaRepository.save(conta);
    }

    public List<Conta> getConta() {
        return contaRepository.findAll();
    }

    public Conta adicionarTaxas(Long idConta) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        LocalDate dataInicioAtividades = conta.getDataCriacao();
        LocalDate today = LocalDate.now();
        Period period = Period.between(dataInicioAtividades, today);
        int diasCorridos = period.getDays();

        if (diasCorridos % 30 == 0 && conta.getTipoConta().equals(TipoConta.CONTACORRENTE)) {
            conta.setSaldoConta(conta.getSaldoConta() - 22.90);
        } else if (diasCorridos % 30 == 0 && conta.getTipoConta().equals(TipoConta.CONTAPOUPANCA)) {
            conta.setSaldoConta(conta.getSaldoConta() + (conta.getSaldoConta() + 0.08));
        }

        // Salva a conta atualizada no banco de dados
        return contaRepository.save(conta);
    }

    public Conta transferirViaPix(Long numeroConta, String chavePix, double valor, String senha) {
        Conta contaOrigem = contaRepository.findByNumeroConta(numeroConta);
        if (contaOrigem.getSenha().equals(senha)) {

            if (contaOrigem.getSaldoConta() >= valor) {
                Conta contaDestino = contaRepository.findByChavePix(chavePix);
                if (contaDestino != null) {

                    contaDestino.setSaldoConta(contaDestino.getSaldoConta() + valor);
                    contaOrigem.setSaldoConta(contaOrigem.getSaldoConta() - valor);
                    contaRepository.save(contaOrigem);
                    return contaRepository.save(contaDestino);
                } else {
                    throw new ChavePixNaoEncontradaException("Chave pix não encontrada!");
                }
            } else {
                throw new SaldoInsuficienteException("Saldo insuficiente para realizar a transferência");
            }
        } else {
            throw new SenhaIncorretaException("Senha incorreta, impossivel realizar trasferência!");

        }
    }

    public Conta buscarContaPorNumero(Long numeroConta) {
        Conta conta = contaRepository.findByNumeroConta(numeroConta);
        
        if(conta != null){
            return conta;
        }else{
            throw new ContaNotFoundException("Conta não encontrada.");
        }
    }
    
    
    
}
