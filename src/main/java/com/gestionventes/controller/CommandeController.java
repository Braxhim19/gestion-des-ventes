package com.gestionventes.controller;

import com.gestionventes.model.Commande;
import com.gestionventes.model.DetailCommande;
import com.gestionventes.model.Facture;
import com.gestionventes.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/commandes")
@CrossOrigin(origins = "*")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        return commandeService.getCommandeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/client/{clientId}")
    public ResponseEntity<Commande> createCommande(@PathVariable Long clientId) {
        Commande commande = commandeService.createCommande(clientId);
        return ResponseEntity.ok(commande);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Commande>> getCommandesByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(commandeService.getCommandesByClient(clientId));
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Commande>> getCommandesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(commandeService.getCommandesByDate(localDate));
    }
    
    @PostMapping("/{commandeId}/ajouter-produit")
    public ResponseEntity<DetailCommande> ajouterProduitCommande(
            @PathVariable Long commandeId,
            @RequestBody Map<String, Object> request) {
        
        Long produitId = Long.valueOf(request.get("produitId").toString());
        Integer quantite = Integer.valueOf(request.get("quantite").toString());
        
        DetailCommande detail = commandeService.ajouterProduitCommande(commandeId, produitId, quantite);
        return ResponseEntity.ok(detail);
    }
    
    @GetMapping("/{id}/total")
    public ResponseEntity<BigDecimal> calculerTotalCommande(@PathVariable Long id) {
        BigDecimal total = commandeService.calculerTotalCommande(id);
        return ResponseEntity.ok(total);
    }
    
    @PostMapping("/{id}/generer-facture")
    public ResponseEntity<Facture> genererFacture(@PathVariable Long id) {
        Facture facture = commandeService.genererFacture(id);
        return ResponseEntity.ok(facture);
    }
    
    @GetMapping("/statistiques/count")
    public ResponseEntity<Long> countCommandes() {
        return ResponseEntity.ok(commandeService.countCommandes());
    }
    
    @GetMapping("/statistiques/count/{date}")
    public ResponseEntity<Long> countCommandesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(commandeService.countCommandesByDate(localDate));
    }
}