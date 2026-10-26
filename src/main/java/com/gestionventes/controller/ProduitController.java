package com.gestionventes.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestionventes.model.Produit;
import com.gestionventes.service.ProduitService;

@RestController
@RequestMapping("/api/produits")
@CrossOrigin(origins = "*")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        return produitService.getProduitById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit savedProduit = produitService.createProduit(produit);
        return ResponseEntity.ok(savedProduit);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit updatedProduit = produitService.updateProduit(id, produit);
        return ResponseEntity.ok(updatedProduit);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<Produit>> getProduitsByCategorie(@PathVariable String categorie) {
        return ResponseEntity.ok(produitService.getProduitsByCategorie(categorie));
    }
    
    @GetMapping("/search")
    public ResponseEntity<List<Produit>> searchProduits(@RequestParam String designation) {
        return ResponseEntity.ok(produitService.searchProduits(designation));
    }
    
    @GetMapping("/stock/disponibles")
    public ResponseEntity<List<Produit>> getProduitsEnStock() {
        List<Produit> produits = produitService.getAllProduits().stream()
                .filter(produit -> produit.getQteStock() != null && produit.getQteStock() > 0)
                .toList();
        return ResponseEntity.ok(produits);
    }
    
    @GetMapping("/stock/rupture")
    public ResponseEntity<List<Produit>> getProduitsEnRupture() {
        List<Produit> produits = produitService.getAllProduits().stream()
                .filter(produit -> produit.getQteStock() != null && produit.getQteStock() == 0)
                .toList();
        return ResponseEntity.ok(produits);
    }
    
    @GetMapping("/verifier-stock/{id}/{quantite}")
    public ResponseEntity<Boolean> verifierStock(@PathVariable Long id, @PathVariable Integer quantite) {
        return produitService.getProduitById(id)
                .map(produit -> ResponseEntity.ok(
                        produit.getQteStock() != null && produit.getQteStock() >= quantite))
                .orElse(ResponseEntity.ok(false));
    }
    
    @GetMapping("/statistiques")
    public ResponseEntity<Map<String, Long>> getStatistiques() {
        List<Produit> produits = produitService.getAllProduits();
        long totalProduits = produits.size();
        long enStock = produits.stream()
                .filter(produit -> produit.getQteStock() != null && produit.getQteStock() > 0)
                .count();
        long enRupture = produits.stream()
                .filter(produit -> produit.getQteStock() != null && produit.getQteStock() == 0)
                .count();

        return ResponseEntity.ok(Map.of(
            "totalProduits", totalProduits,
            "enStock", enStock,
            "enRupture", enRupture
        ));
    }
}