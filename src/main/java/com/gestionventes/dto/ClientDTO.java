package com.gestionventes.dto;

import java.util.List;

public class ClientDTO {
    private Long idClient;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
    private List<Long> commandeIds;
    
    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Long> getCommandeIds() {
        return commandeIds;
    }

    public void setCommandeIds(List<Long> commandeIds) {
        this.commandeIds = commandeIds;
    }
}