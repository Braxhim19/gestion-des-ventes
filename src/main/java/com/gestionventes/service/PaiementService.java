package com.gestionventes.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestionventes.model.Commande;
import com.gestionventes.model.Facture;
import com.gestionventes.model.Paiement;
import com.gestionventes.repository.CommandeRepository;
import com.gestionventes.repository.FactureRepository;
import com.gestionventes.repository.PaiementRepository;

import jakarta.transaction.Transactional;

@Service
public class PaiementService {

    private final PaiementRepository paiementRepository;
    private final CommandeRepository commandeRepository;
    private final FactureRepository factureRepository;

    public PaiementService(
            PaiementRepository paiementRepository,
            CommandeRepository commandeRepository,
            FactureRepository factureRepository) {
        this.paiementRepository = paiementRepository;
        this.commandeRepository = commandeRepository;
        this.factureRepository = factureRepository;
    }

    public List<Paiement> getAllPaiements() {
        return paiementRepository.findAll();
    }
    
    public Optional<Paiement> getPaiementById(Long id) {
        return paiementRepository.findById(id);
    }
    
    public List<Paiement> getPaiementsByCommande(Long commandeId) {
        return paiementRepository.findByCommandeIdCmd(commandeId);
    }
    
    @Transactional
    public Paiement effectuerPaiement(Long commandeId, BigDecimal montant, String typePaiement) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        // Vérifier si une facture existe
        Facture facture = factureRepository.findByCommandeIdCmd(commandeId)
                .orElseThrow(() -> new RuntimeException("Aucune facture trouvée pour cette commande"));

        // Calculer le total déjà payé
        BigDecimal totalPaye = getTotalPaye(commandeId);
        BigDecimal montantRestant = facture.getTotalFacture().subtract(totalPaye);
        
        if (montant.compareTo(montantRestant) > 0) {
            throw new RuntimeException("Le montant dépasse le montant restant à payer: " + montantRestant);
        }
        
        Paiement paiement = new Paiement();
        paiement.setMontant(montant);
        paiement.setTypePay(typePaiement);
        paiement.setCommande(commande);
        
        return paiementRepository.save(paiement);
    }
    
    public BigDecimal getTotalPaye(Long commandeId) {
        List<Paiement> paiements = paiementRepository.findByCommandeIdCmd(commandeId);
        
        return paiements.stream()
            .map(Paiement::getMontant)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public BigDecimal getMontantRestant(Long commandeId) {
        Facture facture = factureRepository.findByCommandeIdCmd(commandeId)
                .orElseThrow(() -> new RuntimeException("Facture non trouvée"));

        BigDecimal totalPaye = getTotalPaye(commandeId);
        return facture.getTotalFacture().subtract(totalPaye);
    }
    
    public long countPaiements() {
        return paiementRepository.count();
    }
}