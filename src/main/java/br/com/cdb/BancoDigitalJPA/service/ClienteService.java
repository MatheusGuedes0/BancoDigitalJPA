/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.TipoCliente;
import br.com.cdb.BancoDigitalJPA.repository.ClienteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mathe
 */
@Service
public class ClienteService {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    public Cliente salvarCliente(String nome, Long cpf, Long dataNascimento, String endereco, TipoCliente tipoCliente){
        //VALIDAR OS CAMPOS
        Cliente cliente = new Cliente();
        cliente.setCpf(cpf);
        cliente.setNome(nome);
        cliente.setDataNascimento(dataNascimento);
        cliente.setEndereco(endereco);
        cliente.setTipoCliente(tipoCliente);
        
        return clienteRepository.save(cliente);
    }
    
    
    public List<Cliente>getClientes(){
        return clienteRepository.findAll();
    }
}
