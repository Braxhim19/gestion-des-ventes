package com.gestionventes.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "DETAIL_CMD")
@IdClass(DetailCommandeId.class)
public class DetailCommande {
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cmd")
    private Commande commande;
    
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prod")
    private Produit produit;
    
    @Column(name = "qte_cmd")
    private Integer qteCmd;
    
    @Column(name = "prix_total_ligne", precision = 10, scale = 2)
    private BigDecimal prixTotalLigne;
    
    public DetailCommande() {
    }

    public DetailCommande(Commande commande, Produit produit, Integer qteCmd) {
        this.commande = commande;
        this.produit = produit;
        this.qteCmd = qteCmd;
        if (produit != null && produit.getPrixUnitaire() != null && qteCmd != null) {
            this.prixTotalLigne = produit.getPrixUnitaire().multiply(BigDecimal.valueOf(qteCmd));
        }
    }
    
    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Integer getQteCmd() {
        return qteCmd;
    }

    public void setQteCmd(Integer qteCmd) {
        this.qteCmd = qteCmd;
    }

    public BigDecimal getPrixTotalLigne() {
        return prixTotalLigne;
    }

    public void setPrixTotalLigne(BigDecimal prixTotalLigne) {
        this.prixTotalLigne = prixTotalLigne;
    }
}