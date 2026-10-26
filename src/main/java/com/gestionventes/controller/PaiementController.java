package com.gestionventes.controller;

import com.gestionventes.model.Paiement;
import com.gestionventes.service.PaiementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/api/paiements")
@CrossOrigin(origins = "*")
public class PaiementController {

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping
    public ResponseEntity<List<Paiement>> getAllPaiements() {
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Paiement> getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/commande/{commandeId}")
    public ResponseEntity<Paiement> createPaiement(
            @PathVariable Long commandeId,
            @RequestBody Paiement paiement) {
        
        Paiement savedPaiement = paiementService.effectuerPaiement(
                commandeId,
                paiement.getMontant(),
                paiement.getTypePay());
        return ResponseEntity.ok(savedPaiement);
    }
    
    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<Paiement>> getPaiementsByCommande(@PathVariable Long commandeId) {
        return ResponseEntity.ok(paiementService.getPaiementsByCommande(commandeId));
    }
    
    @GetMapping("/commande/{commandeId}/solde")
    public ResponseEntity<BigDecimal> getSoldeRestant(@PathVariable Long commandeId) {
        return ResponseEntity.ok(paiementService.getMontantRestant(commandeId));
    }
    
    @GetMapping("/date/{date}")
    public ResponseEntity<List<Paiement>> getPaiementsByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Paiement> paiements = paiementService.getAllPaiements().stream()
                .filter(paiement -> localDate.equals(paiement.getDatePay()))
                .toList();
        return ResponseEntity.ok(paiements);
    }
    
    @GetMapping("/statistiques/{date}")
    public ResponseEntity<Map<String, Object>> getStatistiquesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Paiement> paiements = paiementService.getAllPaiements().stream()
                .filter(paiement -> localDate.equals(paiement.getDatePay()))
                .toList();

        BigDecimal total = paiements.stream()
                .map(Paiement::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(Map.of(
            "date", date,
            "totalPaiements", paiements.size(),
            "montantTotal", total
        ));
    }
}