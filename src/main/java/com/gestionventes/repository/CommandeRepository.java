package com.gestionventes.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gestionventes.model.Commande;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByClientIdClient(Long clientId);

    List<Commande> findByDateCmd(LocalDate date);

    List<Commande> findByDateCmdBetween(LocalDate start, LocalDate end);

    @Query("SELECT COUNT(c) FROM Commande c")
    Long countAllCommandes();

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.dateCmd = :date")
    Long countCommandesByDate(LocalDate date);
}