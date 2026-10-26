package com.gestionventes.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "PAIEMENT")
public class Paiement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pay")
    private Long idPay;
    
    @Column(name = "date_pay")
    private LocalDate datePay;
    
    @Column(name = "montant", precision = 10, scale = 2)
    private BigDecimal montant;
    
    @Column(name = "type_pay", length = 30)
    private String typePay;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cmd")
    private Commande commande;
    
    public Paiement() {
    }

    public Paiement(BigDecimal montant, String typePay, Commande commande) {
        this.montant = montant;
        this.typePay = typePay;
        this.commande = commande;
        this.datePay = LocalDate.now();
    }
    
    public Long getIdPay() {
        return idPay;
    }

    public void setIdPay(Long idPay) {
        this.idPay = idPay;
    }

    public LocalDate getDatePay() {
        return datePay;
    }

    public void setDatePay(LocalDate datePay) {
        this.datePay = datePay;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public String getTypePay() {
        return typePay;
    }

    public void setTypePay(String typePay) {
        this.typePay = typePay;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}