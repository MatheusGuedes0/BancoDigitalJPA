/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.service;

import br.com.cdb.BancoDigitalJPA.entity.Seguro;
import br.com.cdb.BancoDigitalJPA.repository.SeguroRepository;
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

    public Seguro contratarSeguro() {
       
    }

    
    
    
}
