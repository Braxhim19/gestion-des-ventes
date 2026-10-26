package com.gestionventes.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "FACTURE")
public class Facture {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_fact")
    private Long idFact;
    
    @Column(name = "date_fact")
    private LocalDate dateFact;
    
    @OneToOne
    @JoinColumn(name = "id_cmd", unique = true)
    private Commande commande;
    
    @Column(name = "total_facture", precision = 10, scale = 2)
    private BigDecimal totalFacture;
    
    public Facture() {
    }

    public Facture(Commande commande) {
        this.commande = commande;
        this.dateFact = LocalDate.now();
        this.totalFacture = calculerTotalFacture();
    }
    
    public Long getIdFact() {
        return idFact;
    }

    public void setIdFact(Long idFact) {
        this.idFact = idFact;
    }

    public LocalDate getDateFact() {
        return dateFact;
    }

    public void setDateFact(LocalDate dateFact) {
        this.dateFact = dateFact;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public BigDecimal getTotalFacture() {
        return totalFacture;
    }

    public void setTotalFacture(BigDecimal totalFacture) {
        this.totalFacture = totalFacture;
    }

    private BigDecimal calculerTotalFacture() {
        if (commande != null && commande.getDetails() != null) {
            BigDecimal total = BigDecimal.ZERO;
            for (DetailCommande detail : commande.getDetails()) {
                if (detail.getPrixTotalLigne() != null) {
                    total = total.add(detail.getPrixTotalLigne());
                }
            }
            return total;
        }
        return BigDecimal.ZERO;
    }
}