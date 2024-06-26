/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package br.com.cdb.BancoDigitalJPA.repository;

import br.com.cdb.BancoDigitalJPA.entity.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mathe
 */
@Repository
public interface SeguroRepository extends JpaRepository<Seguro, Long>{
    
}
