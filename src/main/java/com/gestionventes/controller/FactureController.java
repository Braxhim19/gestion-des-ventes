package com.gestionventes.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gestionventes.model.Facture;
import com.gestionventes.service.FactureService;

@RestController
@RequestMapping("/api/factures")
@CrossOrigin(origins = "*")
public class FactureController {

    private final FactureService factureService;

    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @GetMapping
    public ResponseEntity<List<Facture>> getAllFactures() {
        return ResponseEntity.ok(factureService.getAllFactures());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFactureById(@PathVariable Long id) {
        return factureService.getFactureById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public ResponseEntity<Facture> createFacture(@RequestBody Facture facture) {
        Facture savedFacture = factureService.createFacture(facture);
        return ResponseEntity.ok(savedFacture);
    }
    
    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<Facture> getFactureByCommandeId(@PathVariable Long commandeId) {
        return factureService.getFactureByCommandeId(commandeId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/periode")
    public ResponseEntity<List<Facture>> getFacturesByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(factureService.getFacturesByDateRange(start, end));
    }
    
    @GetMapping("/total/{date}")
    public ResponseEntity<BigDecimal> getTotalFacturesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return ResponseEntity.ok(factureService.getTotalFacturesByDate(localDate));
    }
    
    @GetMapping("/total/period")
    public ResponseEntity<BigDecimal> getTotalFacturesByPeriod(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        return ResponseEntity.ok(factureService.getTotalFacturesByPeriod(start, end));
    }
    
    @GetMapping("/statistiques/count")
    public ResponseEntity<Long> countFactures() {
        return ResponseEntity.ok(factureService.countFactures());
    }
    
    @GetMapping("/statistiques/{date}")
    public ResponseEntity<Map<String, Object>> getStatistiquesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Facture> factures = factureService.getAllFactures().stream()
                .filter(facture -> localDate.equals(facture.getDateFact()))
                .toList();

        BigDecimal total = factures.stream()
                .map(Facture::getTotalFacture)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return ResponseEntity.ok(Map.of(
            "date", date,
            "nombreFactures", factures.size(),
            "montantTotal", total
        ));
    }
}