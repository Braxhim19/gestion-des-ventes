package com.gestionventes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionventes.model.Paiement;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByCommandeIdCmd(Long commandeId);
}