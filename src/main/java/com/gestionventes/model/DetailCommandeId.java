package com.gestionventes.model;

import java.io.Serializable;
import java.util.Objects;

public class DetailCommandeId implements Serializable {
    
    private Long commande;
    private Long produit;
    
    public DetailCommandeId() {
    }

    public DetailCommandeId(Long commande, Long produit) {
        this.commande = commande;
        this.produit = produit;
    }
    
    public Long getCommande() {
        return commande;
    }
    
    public void setCommande(Long commande) {
        this.commande = commande;
    }
    
    public Long getProduit() {
        return produit;
    }
    
    public void setProduit(Long produit) {
        this.produit = produit;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailCommandeId that = (DetailCommandeId) o;
        return Objects.equals(commande, that.commande) &&
                Objects.equals(produit, that.produit);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(commande, produit);
    }
    
    @Override
    public String toString() {
        return "DetailCommandeId{" +
                "commande=" + commande +
                ", produit=" + produit +
                '}';
    }
}