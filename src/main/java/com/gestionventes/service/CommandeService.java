package com.gestionventes.service;

import jakarta.transaction.Transactional;

import com.gestionventes.model.Client;
import com.gestionventes.model.Commande;
import com.gestionventes.model.DetailCommande;
import com.gestionventes.model.Facture;
import com.gestionventes.model.Produit;
import com.gestionventes.repository.ClientRepository;
import com.gestionventes.repository.CommandeRepository;
import com.gestionventes.repository.DetailCommandeRepository;
import com.gestionventes.repository.FactureRepository;
import com.gestionventes.repository.ProduitRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final DetailCommandeRepository detailCommandeRepository;
    private final FactureRepository factureRepository;

    public CommandeService(
            CommandeRepository commandeRepository,
            ClientRepository clientRepository,
            ProduitRepository produitRepository,
            DetailCommandeRepository detailCommandeRepository,
            FactureRepository factureRepository) {
        this.commandeRepository = commandeRepository;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
        this.detailCommandeRepository = detailCommandeRepository;
        this.factureRepository = factureRepository;
    }

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }
    
    public Optional<Commande> getCommandeById(Long id) {
        return commandeRepository.findById(id);
    }
    
    @Transactional
    public Commande createCommande(Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Commande commande = new Commande(client);
        return commandeRepository.save(commande);
    }
    
    @Transactional
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }
    
    public List<Commande> getCommandesByClient(Long clientId) {
        return commandeRepository.findByClientIdClient(clientId);
    }
    
    public List<Commande> getCommandesByDate(LocalDate date) {
        return commandeRepository.findByDateCmd(date);
    }
    
    @Transactional
    public DetailCommande ajouterProduitCommande(Long commandeId, Long produitId, Integer quantite) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        // Vérifier le stock
        if (produit.getQteStock() < quantite) {
            throw new RuntimeException("Stock insuffisant pour " + produit.getDesignation());
        }
        
        // Mettre à jour le stock
        produit.setQteStock(produit.getQteStock() - quantite);
        produitRepository.save(produit);
        
        DetailCommande detail = new DetailCommande(commande, produit, quantite);
        return detailCommandeRepository.save(detail);
    }
    
    public BigDecimal calculerTotalCommande(Long commandeId) {
        List<DetailCommande> details = detailCommandeRepository.findByCommandeIdCmd(commandeId);
        
        return details.stream()
            .map(DetailCommande::getPrixTotalLigne)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    @Transactional
    public Facture genererFacture(Long commandeId) {
        Commande commande = commandeRepository.findById(commandeId)
            .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        
        // Vérifier si une facture existe déjà
        Optional<Facture> existingFacture = factureRepository.findByCommandeIdCmd(commandeId);
        if (existingFacture.isPresent()) {
            throw new RuntimeException("Une facture existe déjà pour cette commande");
        }
        
        Facture facture = new Facture(commande);
        return factureRepository.save(facture);
    }

    public Long countCommandes() {
        return commandeRepository.countAllCommandes();
    }
    
    public Long countCommandesByDate(LocalDate date) {
        return commandeRepository.countCommandesByDate(date);
    }
    
    public static class CommandeItem {
        private Long produitId;
        private String designation;
        private Integer quantite;
        private BigDecimal prixUnitaire;
        private BigDecimal totalLigne;
        
        public CommandeItem() {
        }

        public Long getProduitId() {
            return produitId;
        }

        public void setProduitId(Long produitId) {
            this.produitId = produitId;
        }

        public String getDesignation() {
            return designation;
        }

        public void setDesignation(String designation) {
            this.designation = designation;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }

        public BigDecimal getPrixUnitaire() {
            return prixUnitaire;
        }

        public void setPrixUnitaire(BigDecimal prixUnitaire) {
            this.prixUnitaire = prixUnitaire;
        }

        public BigDecimal getTotalLigne() {
            return totalLigne;
        }

        public void setTotalLigne(BigDecimal totalLigne) {
            this.totalLigne = totalLigne;
        }
    }
}