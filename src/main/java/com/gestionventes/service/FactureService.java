package com.gestionventes.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestionventes.model.Facture;
import com.gestionventes.repository.FactureRepository;

@Service
public class FactureService {

    private final FactureRepository factureRepository;

    public FactureService(FactureRepository factureRepository) {
        this.factureRepository = factureRepository;
    }

    public List<Facture> getAllFactures() {
        return factureRepository.findAll();
    }
    
    public Optional<Facture> getFactureById(Long id) {
        return factureRepository.findById(id);
    }
    
    public Facture createFacture(Facture facture) {
        return factureRepository.save(facture);
    }
    
    public Optional<Facture> getFactureByCommandeId(Long commandeId) {
        return factureRepository.findByCommandeIdCmd(commandeId);
    }
    
    public List<Facture> getFacturesByDateRange(LocalDate start, LocalDate end) {
        return factureRepository.findByDateFactBetween(start, end);
    }
    
    public BigDecimal getTotalFacturesByDate(LocalDate date) {
        BigDecimal total = factureRepository.getTotalFacturesByDate(date);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public BigDecimal getTotalFacturesByPeriod(LocalDate start, LocalDate end) {
        BigDecimal total = factureRepository.getTotalFacturesByPeriod(start, end);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public long countFactures() {
        return factureRepository.count();
    }
}