/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.entity;

import java.util.List;

/**
 *
 * @author mathe
 */
public class StreamsCliente {
    public Cliente searchByID(List<Cliente> cliente, long id){
        Cliente selected  = cliente.stream()
                .filter(i-> i.getId()== id)
                
                .findAny()
                .orElse(null);
        return selected;
    }
}
