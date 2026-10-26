package com.gestionventes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.gestionventes.model.Client;
import com.gestionventes.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
    
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }
    
    public Client createClient(Client client) {
        return clientRepository.save(client);
    }
    
    public Client updateClient(Long id, Client clientDetails) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        client.setNom(clientDetails.getNom());
        client.setPrenom(clientDetails.getPrenom());
        client.setAdresse(clientDetails.getAdresse());
        client.setTelephone(clientDetails.getTelephone());
        client.setEmail(clientDetails.getEmail());
        
        return clientRepository.save(client);
    }
    
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
    
    public List<Client> searchClients(String nom) {
        return clientRepository.findByNomContainingIgnoreCase(nom);
    }
    
    public Optional<Client> getClientByEmail(String email) {
        return Optional.ofNullable(clientRepository.findByEmail(email));
    }
    
    public Optional<Client> getClientByTelephone(String telephone) {
        return Optional.ofNullable(clientRepository.findByTelephone(telephone));
    }
    
    public long countClients() {
        return clientRepository.count();
    }
}