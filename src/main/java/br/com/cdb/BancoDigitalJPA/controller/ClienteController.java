/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Cliente;
import br.com.cdb.BancoDigitalJPA.entity.TipoCliente;
import br.com.cdb.BancoDigitalJPA.exception.CpfDuplicadoException;
import br.com.cdb.BancoDigitalJPA.exception.DataInvalidaException;
import br.com.cdb.BancoDigitalJPA.exception.EnderecoInvalidoException;
import br.com.cdb.BancoDigitalJPA.service.ClienteService;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
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
    public ResponseEntity<String> addCliente(@RequestBody Map<String, Object> request) {
        try {
            String nome = (String) request.get("nome");
            String cpf = (String) request.get("cpf");
            LocalDate dataNascimento = LocalDate.parse((String) request.get("dataNascimento"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Map<String, Object> enderecoMap = (Map<String, Object>) request.get("endereco");
            String rua = (String) enderecoMap.get("rua");
            Integer numero = (Integer) enderecoMap.get("numero");
            String complemento = (String) enderecoMap.get("complemento");
            String cidade = (String) enderecoMap.get("cidade");
            String estado = (String) enderecoMap.get("estado");
            String cep = (String) enderecoMap.get("cep");

            if (rua == null || rua.isEmpty() || numero == null || complemento == null || complemento.isEmpty() || cidade == null || cidade.isEmpty() || estado == null || estado.isEmpty() || cep == null || cep.isEmpty()) {
                throw new EnderecoInvalidoException("O endereço não pode conter campos vazios");
            }

            String endereco = rua + ", " + numero + ", " + complemento + ", " + cidade + ", " + estado + ", " + cep;

            TipoCliente tipoCliente = TipoCliente.valueOf((String) request.get("tipoCliente"));

            clienteService.salvarCliente(nome, cpf, dataNascimento, endereco, tipoCliente);
            return ResponseEntity.ok("Cliente adicionado com sucesso!");
        } catch (CpfDuplicadoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (DataInvalidaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de validação: " + e.getMessage());
        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro de formatação de data: " + e.getMessage());
        }
    }

    @GetMapping("/listAll")
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clienteService.getClientes();
        return new ResponseEntity<List<Cliente>>(clientes, HttpStatus.OK);
    }
}
