package com.gestionventes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionventes.model.DetailCommande;
import com.gestionventes.model.DetailCommandeId;

public interface DetailCommandeRepository extends JpaRepository<DetailCommande, DetailCommandeId> {

    List<DetailCommande> findByCommandeIdCmd(Long commandeId);

    List<DetailCommande> findByProduitIdProd(Long produitId);

    void deleteByCommandeIdCmd(Long commandeId);
}