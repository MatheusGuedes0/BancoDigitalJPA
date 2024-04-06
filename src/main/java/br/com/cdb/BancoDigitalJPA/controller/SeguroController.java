/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.controller;

import br.com.cdb.BancoDigitalJPA.entity.Seguro;
import br.com.cdb.BancoDigitalJPA.service.SeguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mathe
 */

@RestController
@RequestMapping("/seguro")
public class SeguroController {
    
    @Autowired
    private SeguroService seguroService;
    
    
    @PostMapping("/add")
    public ResponseEntity<String>contratarSeguro(@RequestBody Seguro seguro){
        Seguro seguroAdicionado = seguroService.contratarSeguro();
    }
    
}
