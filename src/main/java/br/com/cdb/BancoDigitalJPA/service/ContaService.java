/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.Conta;
import br.com.cdb.BancoDigitalJPA.entity.TipoCliente;
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
import java.util.Optional;
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

    //Esse método funciona melhor em um sistema automatizado com classes ScheduledExecutorService
    //Como o foco não é sistema automaziado, esse método via ser testado com outro método chamado no controller
    //o método está por ultimo nessa classe
    public Conta adicionarTaxas(Long idConta) {
        Conta conta = contaRepository.findById(idConta).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        LocalDate dataInicioAtividades = conta.getDataCriacao();
        LocalDate today = LocalDate.now();
        
        long diasCorridos = ChronoUnit.DAYS.between(dataInicioAtividades, today);

        if (diasCorridos % 30 == 0) {
            if (conta.getTipoConta().equals(TipoConta.CONTACORRENTE)) {
                if (conta.getCliente().getTipoCliente() == TipoCliente.COMUM) {
                    conta.setSaldoConta(conta.getSaldoConta() - 12.00);
                } else if (conta.getCliente().getTipoCliente() == TipoCliente.SUPER) {
                    conta.setSaldoConta(conta.getSaldoConta() - 8.00);
                }

            } else if (conta.getTipoConta().equals(TipoConta.CONTAPOUPANCA)) {
                double taxaJurosMensal = 0.08;
                double rendimento = conta.getSaldoConta() * Math.pow(1 + taxaJurosMensal, 1) - conta.getSaldoConta();
                conta.setSaldoConta(conta.getSaldoConta() + rendimento);
            }
        }
        if (diasCorridos % 365 == 0) {
            if (conta.getTipoConta().equals(TipoConta.CONTAPOUPANCA)) {
                double taxaRendimentoAnual = 0.0;
                if (conta.getCliente().getTipoCliente() == TipoCliente.COMUM) {
                    taxaRendimentoAnual = 0.005;
                } else if (conta.getCliente().getTipoCliente() == TipoCliente.SUPER) {
                    taxaRendimentoAnual = 0.007;
                } else if (conta.getCliente().getTipoCliente() == TipoCliente.PREMIUM) {
                    taxaRendimentoAnual = 0.009;
                }

                double rendimentoAnual = conta.getSaldoConta() * Math.pow(1 + taxaRendimentoAnual, 1) - conta.getSaldoConta();
                conta.setSaldoConta(conta.getSaldoConta() + rendimentoAnual);
            }
        }

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

        if (conta != null) {
            return conta;
        } else {
            throw new ContaNotFoundException("Conta não encontrada.");
        }
    }

    // esse método tem a função apenas de alterar a data inicial para pordermos testar a aplicação de taxas e juros
    //do método adicionarTaxas(Long id) no método verificarDiasCorridosEAtualizarSaldo() do contaController
    public Conta alteraDataConta(Long id, int dias) {
        Optional<Conta> contaOptional = contaRepository.findById(id);
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            LocalDate novaData = conta.getDataCriacao().plusDays(dias);
            conta.setDataCriacao(novaData);
            return contaRepository.save(conta);
        } else {
            throw new IllegalArgumentException("Conta não encontrada");
        }
    }

}
