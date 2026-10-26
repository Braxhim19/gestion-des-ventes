package com.gestionventes.mapper;

import com.gestionventes.dto.ClientDTO;
import com.gestionventes.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {

    public ClientDTO toDto(Client client) {
        if (client == null) {
            return null;
        }

        ClientDTO dto = new ClientDTO();
        dto.setIdClient(client.getIdClient());
        dto.setNom(client.getNom());
        dto.setPrenom(client.getPrenom());
        dto.setAdresse(client.getAdresse());
        dto.setTelephone(client.getTelephone());
        dto.setEmail(client.getEmail());
        
        if (client.getCommandes() != null) {
            dto.setCommandeIds(
                client.getCommandes().stream()
                    .map(commande -> commande.getIdCmd())
                    .toList()
            );
        }

        return dto;
    }
    
    public Client toEntity(ClientDTO dto) {
        if (dto == null) {
            return null;
        }

        Client client = new Client();
        client.setIdClient(dto.getIdClient());
        client.setNom(dto.getNom());
        client.setPrenom(dto.getPrenom());
        client.setAdresse(dto.getAdresse());
        client.setTelephone(dto.getTelephone());
        client.setEmail(dto.getEmail());
        
        return client;
    }
}