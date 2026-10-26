package com.gestionventes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CommandeDTO {
    private Long idCmd;
    private LocalDate dateCmd;
    private Long clientId;
    private String clientNomComplet;
    private BigDecimal totalCommande;
    private List<DetailCommandeDTO> details;
    
    public Long getIdCmd() {
        return idCmd;
    }

    public void setIdCmd(Long idCmd) {
        this.idCmd = idCmd;
    }

    public LocalDate getDateCmd() {
        return dateCmd;
    }

    public void setDateCmd(LocalDate dateCmd) {
        this.dateCmd = dateCmd;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientNomComplet() {
        return clientNomComplet;
    }

    public void setClientNomComplet(String clientNomComplet) {
        this.clientNomComplet = clientNomComplet;
    }

    public BigDecimal getTotalCommande() {
        return totalCommande;
    }

    public void setTotalCommande(BigDecimal totalCommande) {
        this.totalCommande = totalCommande;
    }

    public List<DetailCommandeDTO> getDetails() {
        return details;
    }

    public void setDetails(List<DetailCommandeDTO> details) {
        this.details = details;
    }
}