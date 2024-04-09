/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.Endereco;
import br.com.cdb.BancoDigitalJPA.entity.TipoCliente;
import br.com.cdb.BancoDigitalJPA.exception.CpfDuplicadoException;
import br.com.cdb.BancoDigitalJPA.exception.DataInvalidaException;
import br.com.cdb.BancoDigitalJPA.repository.ClienteRepository;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvarCliente(String nome, String cpf, LocalDate dataNascimento,  Endereco endereco, TipoCliente tipoCliente) {
        Cliente cliente = new Cliente();
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        LocalDate dataAtual = LocalDate.now();
        Period periodo = Period.between(dataNascimento, dataAtual);
        int idade = periodo.getYears();
        if (idade >= 18) {
            cliente.setDataNascimento(dataNascimento);
        } else {
            throw new DataInvalidaException("O cliente deve ter pelo menos 18 anos de idade.");
        }
            cliente.setEndereco(endereco);
        
        cliente.setTipoCliente(tipoCliente);
        try {
            return clienteRepository.save(cliente);
        } catch (DataIntegrityViolationException e) {
            throw new CpfDuplicadoException("CPF já cadastrado!");
        }catch(ConstraintViolationException e){
            throw new ConstraintViolationException("CPF inválido(XXX.XXX.XXX-XX)", e.getConstraintViolations());
        }
    }

    public List<Cliente> getClientes() {
        return clienteRepository.findAll();
    }
}
