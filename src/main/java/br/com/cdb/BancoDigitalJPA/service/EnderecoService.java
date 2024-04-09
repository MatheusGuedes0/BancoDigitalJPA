/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Endereco;
import br.com.cdb.BancoDigitalJPA.exception.EnderecoInvalidoException;
import br.com.cdb.BancoDigitalJPA.repository.EnderecoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Endereco cadastrarEndereco(String cep, String rua, Integer numero, String complemento, String cidade, String estado) {

        Endereco endereco = new Endereco();

        try {

            endereco.setCep(cep);
            endereco.setRua(rua);
            endereco.setNumero(numero);
            endereco.setComplemento(complemento);
            endereco.setCidade(cidade);
            endereco.setEstado(estado);
            if (endereco.getRua() == null || endereco.getNumero() == null || endereco.getComplemento() == null || endereco.getCidade() == null || endereco.getEstado() == null || endereco.getCep() == null) {
                throw new EnderecoInvalidoException("Algum campo do endereço está inválido ou nulo!");
            } else {
                return enderecoRepository.save(endereco);
            }
        }catch(ConstraintViolationException e){
            throw new ConstraintViolationException("CEP inválido(XXXXX-XXX)", e.getConstraintViolations());
        }
        }
    }
