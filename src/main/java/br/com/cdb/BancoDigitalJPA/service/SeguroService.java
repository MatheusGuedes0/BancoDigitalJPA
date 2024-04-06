/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cartao;
import br.com.cdb.BancoDigitalJPA.entity.CartaoCredito;
import br.com.cdb.BancoDigitalJPA.entity.Seguro;
import br.com.cdb.BancoDigitalJPA.entity.TipoCliente;
import br.com.cdb.BancoDigitalJPA.entity.TipoSeguros;
import br.com.cdb.BancoDigitalJPA.repository.CartaoRepository;
import br.com.cdb.BancoDigitalJPA.repository.SeguroRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class SeguroService {

    @Autowired
    private SeguroRepository seguroRepository;
    @Autowired
    private CartaoRepository cartaoRepository;

    public Seguro contratarSeguro(CartaoCredito cartaoCredito, TipoSeguros tipoSeguros) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(cartaoCredito.getId());
        CartaoCredito cartaoCredito1 = (CartaoCredito) cartaoOptional.get();
        Seguro seguro = new Seguro();
        Random random = new Random();
        seguro.setCartao(cartaoCredito1);
        int numeroApolice = random.nextInt(9999999 - 1000000 + 1) + 1000000;
        seguro.setNumeroApolice(numeroApolice);
        LocalDate today = LocalDate.now();
        seguro.setDataContratacao(today);
        seguro.setTipoSeguros(tipoSeguros);
        if (tipoSeguros == TipoSeguros.FRAUDE) {
            seguro.setDescricao("Viva uma vida sem fraudes e com segurança com o seguro Fraude!");
            seguro.setValorApolice(5000.00);

        } else if (tipoSeguros == TipoSeguros.VIAGEM) {
            seguro.setDescricao("Esse seguro oferece toda a segurança que você merece para viajar feliz!");
            if (cartaoCredito1.getConta().getCliente().getTipoCliente() == TipoCliente.COMUM
                    || cartaoCredito1.getConta().getCliente().getTipoCliente() == TipoCliente.SUPER) {
                seguro.setValorApolice(50.00);
            } else {
                seguro.setValorApolice(0.0);
            }
        }
        return seguroRepository.save(seguro);
    }

}
