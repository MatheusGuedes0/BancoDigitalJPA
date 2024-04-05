/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.exception;

/**
 *
 * @author mathe
 */
public class SenhaIncorretaException extends RuntimeException{

 
    public SenhaIncorretaException(String msg) {
        super(msg);
    }
}
