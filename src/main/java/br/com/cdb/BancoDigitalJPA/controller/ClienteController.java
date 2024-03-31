/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.service.ClienteService;
import java.util.List;
import org.apache.catalina.connector.Response;
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

    @PostMapping("/add")
    public ResponseEntity<String> addCliente(@RequestBody Cliente cliente) {
        Cliente clienteAdicionado = clienteService.salvarCliente(cliente.getNome(),
                cliente.getCpf(), cliente.getDataNascimento(), cliente.getEndereco(), cliente.getTipoCliente());

        if (clienteAdicionado != null) {
            return new ResponseEntity<>("Cliente" + cliente.getNome() + "adicionada com sucesso!",
                    HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Um dos campos do cliente é inválido!",
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
}
