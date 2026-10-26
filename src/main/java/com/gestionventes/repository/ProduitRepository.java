package com.gestionventes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestionventes.model.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByCategorie(String categorie);

    List<Produit> findByDesignationContainingIgnoreCase(String designation);
    List<Produit> findByQteStockGreaterThan(Integer qte);
}