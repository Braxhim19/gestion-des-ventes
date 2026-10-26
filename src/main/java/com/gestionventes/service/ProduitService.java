package com.gestionventes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestionventes.model.Produit;
import com.gestionventes.repository.ProduitRepository;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
    
    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }
    
    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }
    
    public Produit updateProduit(Long id, Produit produitDetails) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        produit.setDesignation(produitDetails.getDesignation());
        produit.setPrixUnitaire(produitDetails.getPrixUnitaire());
        produit.setQteStock(produitDetails.getQteStock());
        produit.setCategorie(produitDetails.getCategorie());
        
        return produitRepository.save(produit);
    }
    
    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }
    
    public List<Produit> getProduitsByCategorie(String categorie) {
        return produitRepository.findByCategorie(categorie);
    }
    
    public List<Produit> searchProduits(String designation) {
        return produitRepository.findByDesignationContainingIgnoreCase(designation);
    }
    
    public List<Produit> getProduitsEnStock() {
        return produitRepository.findByQteStockGreaterThan(0);
    }
    
    public boolean verifierStock(Long produitId, Integer quantite) {
        return produitRepository.findById(produitId)
                .map(produit -> produit.getQteStock() != null && produit.getQteStock() >= quantite)
                .orElse(false);
    }
    
    public long countProduits() {
        return produitRepository.count();
    }
}