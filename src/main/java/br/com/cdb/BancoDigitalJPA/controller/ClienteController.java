/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.Endereco;
import br.com.cdb.BancoDigitalJPA.exception.CpfDuplicadoException;
import br.com.cdb.BancoDigitalJPA.exception.DataInvalidaException;
import br.com.cdb.BancoDigitalJPA.exception.EnderecoInvalidoException;
import br.com.cdb.BancoDigitalJPA.service.ClienteService;
import br.com.cdb.BancoDigitalJPA.service.EnderecoService;
import jakarta.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
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
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    EnderecoService enderecoService;

    @PostMapping("/add")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente) {
        try {
            Endereco endereco = enderecoService.cadastrarEndereco(cliente.getEndereco().getCep(), cliente.getEndereco().getRua(), cliente.getEndereco().getNumero(), cliente.getEndereco().getComplemento(), cliente.getEndereco().getCidade(),
                    cliente.getEndereco().getEstado());
            
            cliente.setEndereco(endereco);
            clienteService.salvarCliente(cliente.getNome(), cliente.getCpf(), cliente.getDataNascimento(), cliente.getEndereco(),
                    cliente.getTipoCliente());
            return ResponseEntity.ok("Cliente adicionado com sucesso!");

        } catch (CpfDuplicadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (ConstraintViolationException | DataInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de formatação de data: " + e.getMessage());
        } catch (EnderecoInvalidoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação de Endereço: " + e.getMessage());
        } 
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
}
