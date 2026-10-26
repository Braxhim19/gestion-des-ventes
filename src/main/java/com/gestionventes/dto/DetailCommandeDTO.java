package com.gestionventes.dto;

import java.math.BigDecimal;

public class DetailCommandeDTO {
    private Long commandeId;
    private Long produitId;
    private String produitDesignation;
    private Integer qteCmd;
    private BigDecimal prixUnitaire;
    private BigDecimal prixTotalLigne;
    
    public Long getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(Long commandeId) {
        this.commandeId = commandeId;
    }

    public Long getProduitId() {
        return produitId;
    }

    public void setProduitId(Long produitId) {
        this.produitId = produitId;
    }

    public String getProduitDesignation() {
        return produitDesignation;
    }

    public void setProduitDesignation(String produitDesignation) {
        this.produitDesignation = produitDesignation;
    }

    public Integer getQteCmd() {
        return qteCmd;
    }

    public void setQteCmd(Integer qteCmd) {
        this.qteCmd = qteCmd;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotalLigne() {
        return prixTotalLigne;
    }

    public void setPrixTotalLigne(BigDecimal prixTotalLigne) {
        this.prixTotalLigne = prixTotalLigne;
    }
}